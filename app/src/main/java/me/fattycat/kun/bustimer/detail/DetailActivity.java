package me.fattycat.kun.bustimer.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.AVAnalytics;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.fattycat.kun.bustimer.R;
import me.fattycat.kun.bustimer.model.BusGPSEntity;
import me.fattycat.kun.bustimer.model.LineInfoSerializable;
import me.fattycat.kun.bustimer.model.StationListEntity;
import me.fattycat.kun.bustimer.model.StationWrapper;
import me.fattycat.kun.bustimer.net.BusTimerApi;
import me.fattycat.kun.bustimer.util.FavoriteDataUtil;
import me.fattycat.kun.bustimer.util.NotificationUtil;

/**
 * Author: Kelvinkun
 * Date: 16/7/11
 */

public class DetailActivity extends AppCompatActivity implements DetailContract.View {
    private static final String EXTRA_DATA = "lineInfo";
    private static final int REFRESH_TIME = 15 * 1000;

    @BindView(R.id.detail_station_list)
    RecyclerView detailStationList;
    @BindView(R.id.detail_toolbar)
    Toolbar detailToolbar;
    @BindView(R.id.detail_head_line_name)
    TextView detailHeadLineName;
    @BindView(R.id.detail_head_line_start)
    TextView detailHeadLineStart;
    @BindView(R.id.detail_head_line_end)
    TextView detailHeadLineEnd;
    @BindView(R.id.detail_head_bus_count)
    TextView detailHeadBusCount;
    @BindView(R.id.detail_head_bus_interval)
    TextView detailHeadBusInterval;
    @BindView(R.id.detail_loading_view)
    AVLoadingIndicatorView detailLoadingView;

    private String rpid;
    private String direction;
    private String lineName;
    private String busInterval;
    private String startStation;
    private String endStation;
    private String flag;

    private DetailContract.Presenter detailContractPresenter;
    private DetailStationListAdapter detailStationListAdapter;

    private List<StationWrapper> stationWrapperList;
    private Handler mRefreshHandler = new Handler();
    private Runnable mRefreshRunnable;
    private LineInfoSerializable lineInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_detail);
        ButterKnife.bind(this);
        new DetailPresenter(this);

        lineInfo = (LineInfoSerializable) getIntent().getSerializableExtra(EXTRA_DATA);
        rpid = lineInfo.getRunPathId();
        direction = lineInfo.getFlag();
        lineName = lineInfo.getRunPathName();
        busInterval = String.format("间隔时间：%s分钟", lineInfo.getBusInterval());
        startStation = lineInfo.getStartStation();
        endStation = lineInfo.getEndStation();

        setupViews();

        stationWrapperList = new ArrayList<>();
        detailContractPresenter.getLineStations(rpid, direction);
    }

    protected void onResume() {
        super.onResume();
        AVAnalytics.onResume(this);
    }

    protected void onPause() {
        super.onPause();
        AVAnalytics.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRefreshHandler.removeCallbacks(mRefreshRunnable);
    }

    public static Intent getCallingIntent(Context context, LineInfoSerializable lineInfo) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_DATA, lineInfo);
        return intent;
    }

    private void setupViews() {
        detailToolbar.setTitle("");
        setSupportActionBar(detailToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        detailToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        LayoutInflater inflater = LayoutInflater.from(this);
        View favoriteButtonView = inflater.inflate(R.layout.layout_favorite, detailToolbar, false);
        ShineButton favoriteButton = (ShineButton) favoriteButtonView.findViewById(R.id.detail_head_favourite);
        favoriteButton.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean checked) {
                if (checked) {
                    FavoriteDataUtil.saveFavoriteLine(DetailActivity.this, lineInfo);
                } else {
                    FavoriteDataUtil.deleteFavoriteLine(DetailActivity.this, lineInfo);
                }
            }
        });
        List<LineInfoSerializable> savedFavoriteLines = FavoriteDataUtil.loadAllFavoriteLine(DetailActivity.this);
        if (savedFavoriteLines != null && savedFavoriteLines.size() == 0) {
            for (LineInfoSerializable savedLineInfoItem : savedFavoriteLines) {
                if (TextUtils.equals(savedLineInfoItem.getRunPathId(), lineInfo.getRunPathId())
                        && TextUtils.equals(savedLineInfoItem.getFlag(), lineInfo.getFlag())) {
                    favoriteButton.setChecked(true);
                    break;
                }
            }
        }
        detailToolbar.addView(favoriteButtonView);

        detailHeadLineName.setText(lineName);
        detailHeadBusInterval.setText(busInterval);
        detailHeadLineStart.setText(startStation);
        detailHeadLineEnd.setText(endStation);

        detailStationListAdapter = new DetailStationListAdapter(this);
        detailStationListAdapter.setData(null);

        detailStationList.setLayoutManager(new LinearLayoutManager(this));
        detailStationList.setAdapter(detailStationListAdapter);

        autoRefresh();
    }

    private void autoRefresh() {
        mRefreshRunnable = new Runnable() {
            @Override
            public void run() {
                detailContractPresenter.getBusGps(rpid, flag);
                mRefreshHandler.postDelayed(this, REFRESH_TIME);
            }
        };
    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
        this.detailContractPresenter = presenter;
    }

    @Override
    public void onStationLoaded(StationListEntity stationListEntity) {
        detailHeadBusCount.setText("正在获取公交信息");
        detailLoadingView.setVisibility(View.GONE);
        stationWrapperList.clear();

        if (TextUtils.equals(direction, BusTimerApi.FLAG_LINE_GO)) {
            for (StationListEntity.ResultEntity.StationEntity station : stationListEntity.getResult().getShangxing()) {
                stationWrapperList.add(new StationWrapper(station, null));
            }
            detailStationListAdapter.setData(stationWrapperList);
            flag = BusTimerApi.FLAG_BUS_GO;
        } else {
            for (StationListEntity.ResultEntity.StationEntity station : stationListEntity.getResult().getXiaxing()) {
                stationWrapperList.add(new StationWrapper(station, null));
            }
            detailStationListAdapter.setData(stationWrapperList);
            flag = BusTimerApi.FLAG_BUS_BACK;
        }
        mRefreshHandler.postDelayed(mRefreshRunnable, 0);
    }

    @Override
    public void onStationLoadFailed() {
        Snackbar.make(detailStationList, "获取站台数据失败", Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailContractPresenter.getLineStations(rpid, direction);
            }
        }).show();
    }

    @Override
    public void onBusGpsLoaded(BusGPSEntity busGPSEntity) {
        String busCount;
        if (busGPSEntity == null) {
            busCount = "当前没有公交在线";
        } else {
            busCount = String.format("当前有 %s 辆公交在线", busGPSEntity.getResult().getLists().size());
            for (int i = 0; i < stationWrapperList.size(); i++) {
                StationWrapper stationWrapper = stationWrapperList.get(i);
                List<BusGPSEntity.ResultEntity.ListsEntity> busList = new ArrayList<>();
                StationListEntity.ResultEntity.StationEntity stationEntity = stationWrapper.getStation();
                for (BusGPSEntity.ResultEntity.ListsEntity listsEntity : busGPSEntity.getResult().getLists()) {
                    if (TextUtils.equals(stationEntity.getBusStationId(), listsEntity.getBusStationId())) {
                        busList.add(listsEntity);
                    }
                }

                if (stationWrapper.isHasAlarm()) {
                    if (!stationWrapper.arrived && busList.size() > 0) {
                        NotificationUtil.createNotification(this,
                                String.format("%s 已到站", lineName),
                                String.format("下一辆车已到达 %s", stationWrapper.getStation().getBusStationName()),
                                Integer.valueOf(stationWrapper.getStation().getBusStationId()));
                        stationWrapper.arrived = true;
                    } else if (!stationWrapper.arrivingOneStation && (i - 1) > 0 && stationWrapperList.get(i - 1).getBusList().size() > 0) {
                        NotificationUtil.createNotification(this,
                                String.format("%s 正在路上", lineName),
                                String.format("下一辆车还有 1 站到达 %s", stationWrapper.getStation().getBusStationName()),
                                Integer.valueOf(stationWrapper.getStation().getBusStationId()));
                        stationWrapper.arrivingOneStation = true;
                    } else if (!stationWrapper.arrivingTwoStation && (i - 2) > 0 && stationWrapperList.get(i - 2).getBusList().size() > 0) {
                        NotificationUtil.createNotification(this,
                                String.format("%s 正在路上", lineName),
                                String.format("下一辆车还有 2 站到达 %s", stationWrapper.getStation().getBusStationName()),
                                Integer.valueOf(stationWrapper.getStation().getBusStationId()));
                        stationWrapper.arrivingTwoStation = true;
                    }
                }
                stationWrapper.setBusList(busList);
            }
        }
        detailHeadBusCount.setText(busCount);
        detailStationListAdapter.setData(stationWrapperList);
    }

    @Override
    public void onBusGpsLoadFailed() {

    }
}
