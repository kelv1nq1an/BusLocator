package me.fattycat.kun.bustimer.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import me.fattycat.kun.bustimer.R;
import me.fattycat.kun.bustimer.data.entity.FavouriteEntity;
import me.fattycat.kun.bustimer.data.event.StationAlarmEvent;
import me.fattycat.kun.bustimer.data.model.BusGPSModel;
import me.fattycat.kun.bustimer.data.model.LineInfoModel;
import me.fattycat.kun.bustimer.data.model.StationModel;
import me.fattycat.kun.bustimer.data.source.BusRepository;
import me.fattycat.kun.bustimer.ui.view.MultiStateView;
import me.fattycat.kun.bustimer.util.NotificationUtil;
import rx.Observable;
import rx.Observer;
import rx.Subscription;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {
    private static final String EXTRA_KEY      = "RPID";
    private static final String EXTRA_KEY_NAME = "NAME";
    public static final  String FLAG_BUS_SHANG = "1";
    public static final  String FLAG_BUS_XIA   = "3";
    private static final Long   TIMER_INTERVAL = 15L;

    @BindView(R.id.detail_title_text_view)
    TextView       detailTitleTextView;
    @BindView(R.id.detail_first_text_view)
    TextView       detailFirstTextView;
    @BindView(R.id.detail_gap_text_view)
    TextView       detailGapTextView;
    @BindView(R.id.detail_end_text_view)
    TextView       detailEndTextView;
    @BindView(R.id.detail_recycler_view)
    RecyclerView   detailRecyclerView;
    @BindView(R.id.detail_state_view)
    MultiStateView detailStateView;
    @BindView(R.id.detail_line_switch_image_view)
    ImageView      detailLineSwitchImageView;
    @BindView(R.id.detail_favourite_text_view)
    TextView       detailFavouriteTextView;
    @BindView(R.id.detail_line_alarm_image_view)
    ImageView      detailLineAlarmImageView;

    private String                   rpid;
    private String                   lineName;
    private LineInfoModel            lineInfoModel;
    private StationModel             stationModel;
    private DetailContract.Presenter presenter;
    private DetailAdapter            detailAdapter;
    private List<DetailWrapper>      detailWrappers;
    private Subscription             timerTask;

    private String  flag            = FLAG_BUS_SHANG;
    private boolean isFavourited    = false;
    private boolean isFavouriteLoad = false;
    private boolean isAlarmSet      = false;

    public static Intent newIntent(Context context, String rpid, String lineName) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_KEY, rpid);
        intent.putExtra(EXTRA_KEY_NAME, lineName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        rpid = getIntent().getStringExtra(EXTRA_KEY);
        lineName = getIntent().getStringExtra(EXTRA_KEY_NAME);
        detailTitleTextView.setText(lineName);

        detailAdapter = new DetailAdapter();
        detailRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailRecyclerView.setAdapter(detailAdapter);
        initMultiStateView();
        detailWrappers = new ArrayList<>();

        new DetailPresenter(this).subscribe();
        presenter.getLineInfo(rpid);
        presenter.getStations(rpid);
        presenter.getBusGPS(rpid, flag);

        timerTask = Observable.interval(TIMER_INTERVAL, TimeUnit.SECONDS)
                            .subscribe(new Observer<Long>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(Long aLong) {
                                    presenter.getBusGPS(rpid, flag);
                                }
                            });
    }

    private void initMultiStateView() {
        detailStateView.addViewForStatus(MultiStateView.STATE_EMPTY, R.layout.layout_empty);
        detailStateView.addViewForStatus(MultiStateView.STATE_LOADING, R.layout.layout_loading);
        detailStateView.addViewForStatus(MultiStateView.STATE_FAIL, R.layout.layout_error);

        detailStateView.setOnInflateListener(new MultiStateView.OnInflateListener() {
            @Override
            public void onInflate(int state, View view) {
                if (state == MultiStateView.STATE_FAIL) {
                    view.findViewById(R.id.layout_error_retry_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            presenter.getLineInfo(rpid);
                            presenter.getStations(rpid);
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unSubscribe();
        timerTask.unsubscribe();
        EventBus.getDefault().unregister(this);
    }

    private void updateFavouriteStatus() {
        if (isFavourited) {
            detailFavouriteTextView.setText("取消收藏");
            detailFavouriteTextView.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_unfavourite));
        } else {
            detailFavouriteTextView.setText("收藏");
            detailFavouriteTextView.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_favourite));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStationAlarmEvent(StationAlarmEvent stationAlarmEvent) {
        for (int i = 0; i < detailWrappers.size(); i++) {
            if (TextUtils.equals(stationAlarmEvent.stationName, detailWrappers.get(i).getStationEntity().getBusStationName())) {
                detailWrappers.get(i).getStationEntity().setHasAlarm(true);
                detailLineAlarmImageView.setImageResource(R.drawable.ic_alarm_active);
                isAlarmSet = true;
            } else {
                detailWrappers.get(i).getStationEntity().setHasAlarm(false);
            }
        }

        detailAdapter.setData(detailWrappers);
    }

    @OnClick(R.id.detail_favourite_text_view)
    public void favourite() {
        if (!isFavouriteLoad || stationModel == null || lineInfoModel == null) {
            Toast.makeText(this, "请稍等", Toast.LENGTH_SHORT).show();
        } else {
            List<StationModel.StationEntity> stationEntityList = stationModel.getShangxing();
            FavouriteEntity favouriteEntity = new FavouriteEntity(rpid,
                                                                         lineName,
                                                                         stationEntityList.get(0).getBusStationName(),
                                                                         stationEntityList.get(
                                                                                 stationEntityList.size() - 1).getBusStationName(),
                                                                         0,
                                                                         lineInfoModel.getStartTime(),
                                                                         lineInfoModel.getEndTime(),
                                                                         lineInfoModel.getBusInterval());

            if (!isFavourited) {
                BusRepository.getInstance().saveFavouriteLine(favouriteEntity);
            } else {
                BusRepository.getInstance().deleteFavouriteLine(rpid);
            }
            isFavourited = !isFavourited;
            updateFavouriteStatus();
        }
    }

    @OnClick(R.id.detail_line_switch_image_view)
    public void switchDirection() {
        detailRecyclerView.getLayoutManager().scrollToPosition(0);
        if (TextUtils.equals(flag, FLAG_BUS_SHANG)) {
            flag = FLAG_BUS_XIA;
            detailLineSwitchImageView.setImageResource(R.drawable.ic_indicator_back);
        } else {
            flag = FLAG_BUS_SHANG;
            detailLineSwitchImageView.setImageResource(R.drawable.ic_indicator_go);
        }
        updateStations(stationModel);
        presenter.getBusGPS(rpid, flag);
    }

    @OnClick(R.id.detail_line_alarm_image_view)
    public void setAlarm() {
        if (stationModel == null) {
            Toast.makeText(this, "请稍等", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isAlarmSet) {
            new SweetAlertDialog(this).setTitleText("提示").setContentText("点击站台名称即可设置到站提醒哦，公交到站前我会提醒您的哦").show();
        } else {
            for (DetailWrapper detailWrapper : detailWrappers) {
                detailWrapper.getStationEntity().setHasAlarm(false);
            }
            detailAdapter.setData(detailWrappers);
            detailLineAlarmImageView.setImageResource(R.drawable.ic_alarm_inactive);
            isAlarmSet = false;
        }
    }

    @Override
    public void onGetLineInfoStart() {
        detailStateView.setViewState(MultiStateView.STATE_LOADING);
    }

    @Override
    public void onGetLineInfoSuccess(LineInfoModel lineInfoModel) {
        if (stationModel != null) {
            detailStateView.setViewState(MultiStateView.STATE_CONTENT);
        }
        // TODO: 2016/12/16 夜班车
        this.lineInfoModel = lineInfoModel;
        detailFirstTextView.setText(String.format("首班:%s", lineInfoModel.getStartTime()));
        detailEndTextView.setText(String.format("末班:%s", lineInfoModel.getEndTime()));
        detailGapTextView.setText(String.format("间隔:%s分钟", lineInfoModel.getBusInterval()));
    }

    @Override
    public void onGetLineInfoError() {
        detailStateView.setViewState(MultiStateView.STATE_FAIL);
    }

    @Override
    public void onGetStationsStart() {
        detailStateView.setViewState(MultiStateView.STATE_LOADING);
    }

    @Override
    public void onGetStationsSuccess(StationModel stationModel) {
        this.stationModel = stationModel;
        updateStations(stationModel);
    }

    private void updateStations(StationModel stationModel) {
        if (lineInfoModel != null) {
            detailStateView.setViewState(MultiStateView.STATE_CONTENT);
        }
        detailWrappers.clear();
        if (TextUtils.equals(flag, FLAG_BUS_SHANG)) {
            for (StationModel.StationEntity stationEntity : stationModel.getShangxing()) {
                detailWrappers.add(new DetailWrapper(stationEntity));
            }
        } else {
            for (StationModel.StationEntity stationEntity : stationModel.getXiaxing()) {
                detailWrappers.add(new DetailWrapper(stationEntity));
            }
        }
        detailAdapter.setData(detailWrappers);
    }

    @Override
    public void onGetStationsError() {
        detailStateView.setViewState(MultiStateView.STATE_FAIL);
    }

    @Override
    public void onGetBusGPSStart() {

    }

    @Override
    public void onGetBusGPSSuccess(BusGPSModel busGPSModel) {
        if (detailWrappers.size() == 0) {
            return;
        }
        for (int i = 0; i < detailWrappers.size(); i++) {
            DetailWrapper               detailWrapper = detailWrappers.get(i);
            List<BusGPSModel.BusEntity> busEntityList = detailWrapper.getBusEntityList();
            busEntityList.clear();
            for (BusGPSModel.BusEntity busEntity : busGPSModel.getLists()) {
                if (TextUtils.equals(busEntity.getBusStationId(), detailWrapper.getStationEntity().getBusStationId())) {
                    busEntityList.add(busEntity);

                }
            }
            DetailWrapper detailWrapperSecond = null;
            DetailWrapper detailWrapperThird  = null;
            if (i + 1 < detailWrappers.size()) {
                detailWrapperSecond = detailWrappers.get(i + 1);
            }
            if (i + 2 < detailWrappers.size()) {
                detailWrapperThird = detailWrappers.get(i + 2);
            }
            if (detailWrapper.getBusEntityList().size() > 0) {
                if (detailWrapper.getStationEntity().isHasAlarm()) {
                    NotificationUtil.createNotification(this, "公交已经到站", "请留意公交，及时上车，注意安全", Integer.parseInt(detailWrapper.getStationEntity().getBusStationId()));
                    new SweetAlertDialog(this).setTitleText("公交到站").setContentText("公交已经到了哦，上下车注意安全").show();
                }
                if (detailWrapperSecond != null && detailWrapperSecond.getStationEntity().isHasAlarm()) {
                    NotificationUtil.createNotification(this, "公交还有一站到哦", "马上到了哟，多看一下路上的公交吧", Integer.parseInt(detailWrapperSecond.getStationEntity().getBusStationId()));
                }
                if (detailWrapperThird != null && detailWrapperThird.getStationEntity().isHasAlarm()) {
                    NotificationUtil.createNotification(this, "公交还有两站到哦", "还有一会到哦，可以做做准备了哦", Integer.parseInt(detailWrapperThird.getStationEntity().getBusStationId()));
                }
            }
        }
        detailAdapter.setData(detailWrappers);
    }

    @Override
    public void onGetBusGPSError() {

    }

    @Override
    public void onFavouriteLoadStart() {

    }

    @Override
    public void onFavouriteLoadSuccess(List<FavouriteEntity> favouriteEntities) {
        for (FavouriteEntity favouriteEntity : favouriteEntities) {
            if (TextUtils.equals(favouriteEntity.lineName, lineName)) {
                isFavourited = true;
                break;
            }
        }
        isFavouriteLoad = true;
        updateFavouriteStatus();
    }

    @Override
    public void onFavouriteLoadError() {

    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
