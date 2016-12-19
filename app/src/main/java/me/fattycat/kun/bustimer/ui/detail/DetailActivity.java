package me.fattycat.kun.bustimer.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fattycat.kun.bustimer.R;
import me.fattycat.kun.bustimer.data.entity.FavouriteEntity;
import me.fattycat.kun.bustimer.data.model.BusGPSModel;
import me.fattycat.kun.bustimer.data.model.LineInfoModel;
import me.fattycat.kun.bustimer.data.model.StationModel;
import me.fattycat.kun.bustimer.data.source.BusRepository;
import rx.Observable;
import rx.Observer;
import rx.Subscription;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {
    private static final String EXTRA_KEY      = "RPID";
    private static final String EXTRA_KEY_NAME = "NAME";
    public static final  String FLAG_BUS_SHANG = "1";
    public static final  String FLAG_BUS_XIA   = "3";
    private static final Long   TIMER_INTERVAL = 15L;

    @BindView(R.id.top_bar_detail_title_text_view)
    TextView     topBarTitleTextView;
    @BindView(R.id.detail_favourite_text_view)
    TextView     detailFavouriteTextView;
    @BindView(R.id.detail_first_text_view)
    TextView     detailFirstTextView;
    @BindView(R.id.detail_gap_text_view)
    TextView     detailGapTextView;
    @BindView(R.id.detail_end_text_view)
    TextView     detailEndTextView;
    @BindView(R.id.detail_recycler_view)
    RecyclerView detailRecyclerView;
    @BindView(R.id.detail_line_switch_image_view)
    ImageView    detailLineSwitchImageView;

    private String                   rpid;
    private String                   lineName;
    private LineInfoModel            lineInfoModel;
    private StationModel             stationModel;
    private DetailContract.Presenter presenter;
    private DetailAdapter            detailAdapter;
    private List<DetailWrapper> detailWrappers = new ArrayList<>();
    private Subscription    timerTask;

    private String  flag            = FLAG_BUS_SHANG;
    private boolean isFavourited    = false;
    private boolean isFavouriteLoad = false;

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

        rpid = getIntent().getStringExtra(EXTRA_KEY);
        lineName = getIntent().getStringExtra(EXTRA_KEY_NAME);
        topBarTitleTextView.setText(lineName);

        detailAdapter = new DetailAdapter();
        detailRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailRecyclerView.setAdapter(detailAdapter);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unSubscribe();
        timerTask.unsubscribe();
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

    @Override
    public void onGetLineInfoStart() {

    }

    @Override
    public void onGetLineInfoSuccess(LineInfoModel lineInfoModel) {
        // TODO: 2016/12/16 夜班车
        this.lineInfoModel = lineInfoModel;
        detailFirstTextView.setText(String.format("首班:%s", lineInfoModel.getStartTime()));
        detailEndTextView.setText(String.format("末班:%s", lineInfoModel.getEndTime()));
        detailGapTextView.setText(String.format("间隔:%s分钟", lineInfoModel.getBusInterval()));
    }

    @Override
    public void onGetLineInfoError() {

    }

    @Override
    public void onGetStationsStart() {

    }

    @Override
    public void onGetStationsSuccess(StationModel stationModel) {
        this.stationModel = stationModel;
        updateStations(stationModel);
    }

    private void updateStations(StationModel stationModel) {
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

    }

    @Override
    public void onGetBusGPSStart() {

    }

    @Override
    public void onGetBusGPSSuccess(BusGPSModel busGPSModel) {
        if (detailWrappers.size() == 0) {
            return;
        }
        for (DetailWrapper detailWrapper : detailWrappers) {
            List<BusGPSModel.BusEntity> busEntityList = detailWrapper.getBusEntityList();
            busEntityList.clear();
            for (BusGPSModel.BusEntity busEntity : busGPSModel.getLists()) {
                if (TextUtils.equals(busEntity.getBusStationId(), detailWrapper.getStationEntity().getBusStationId())) {
                    busEntityList.add(busEntity);
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
