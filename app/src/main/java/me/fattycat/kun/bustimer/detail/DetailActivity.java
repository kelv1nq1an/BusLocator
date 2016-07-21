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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.fattycat.kun.bustimer.Http.BusTimerApi;
import me.fattycat.kun.bustimer.R;
import me.fattycat.kun.bustimer.model.StationWrapper;
import me.fattycat.kun.bustimer.model.BusGPSEntity;
import me.fattycat.kun.bustimer.model.StationListEntity;

/**
 * Author: Kelvinkun
 * Date: 16/7/11
 */

public class DetailActivity extends AppCompatActivity implements DetailContract.View {
    private static final String RPID = "rpid";
    private static final String DIRECTION = "direction";
    private static final String LINE_NAME = "lineName";
    private static final String START_STATION = "startStation";
    private static final String END_STATION = "endStation";
    private static final int REFRESH_TIME = 5 * 1000;

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

    private String rpid;
    private String direcation;
    private String lineName;
    private String startStation;
    private String endStation;
    private String flag;

    private DetailContract.Presenter detailContractPresenter;
    private DetailStationListAdapter detailStationListAdapter;

    private List<StationWrapper> stationWrapperList;
    private Handler mRefreshHandler = new Handler();
    private Runnable mRefreshRunnable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_detail);
        ButterKnife.bind(this);
        new DetailPresenter(this);

        rpid = getIntent().getStringExtra(RPID);
        direcation = getIntent().getStringExtra(DIRECTION);
        lineName = getIntent().getStringExtra(LINE_NAME);
        startStation = getIntent().getStringExtra(START_STATION);
        endStation = getIntent().getStringExtra(END_STATION);

        setupViews();

        stationWrapperList = new ArrayList<>();
        detailContractPresenter.getLineStations(rpid, direcation);
    }

    public static Intent getCallingIntent(Context context, String rpid, String direction, String lineName, String startStation, String endStation) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(RPID, rpid);
        intent.putExtra(DIRECTION, direction);
        intent.putExtra(LINE_NAME, lineName);
        intent.putExtra(START_STATION, startStation);
        intent.putExtra(END_STATION, endStation);
        return intent;
    }

    private void setupViews() {
        detailToolbar.setTitle("");
        setSupportActionBar(detailToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        LayoutInflater inflater = LayoutInflater.from(this);
        View favoriteButtonView = inflater.inflate(R.layout.layout_favorite, detailToolbar, false);
        detailToolbar.addView(favoriteButtonView);

        detailHeadLineName.setText(lineName);
        detailHeadLineStart.setText(startStation);
        detailHeadLineEnd.setText(endStation);

        detailStationListAdapter = new DetailStationListAdapter(this);

        detailStationListAdapter.setData(null);
        detailStationList.setLayoutManager(new LinearLayoutManager(this));
        detailStationList.setAdapter(detailStationListAdapter);

        autoRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRefreshHandler.removeCallbacks(mRefreshRunnable);
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
        stationWrapperList.clear();

        if (TextUtils.equals(direcation, BusTimerApi.FLAG_LINE_GO)) {
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
                detailContractPresenter.getLineStations(rpid, direcation);
            }
        }).show();
    }

    @Override
    public void onBusGpsLoaded(BusGPSEntity busGPSEntity) {
        for (StationWrapper stationWrapper : stationWrapperList) {
            List<BusGPSEntity.ResultEntity.ListsEntity> busList = new ArrayList<>();
            StationListEntity.ResultEntity.StationEntity stationEntity = stationWrapper.getStation();
            for (BusGPSEntity.ResultEntity.ListsEntity listsEntity : busGPSEntity.getResult().getLists()) {
                if (TextUtils.equals(stationEntity.getBusStationId(), listsEntity.getBusStationId())) {
                    busList.add(listsEntity);
                }
            }
            stationWrapper.setBusList(busList);
        }
        detailStationListAdapter.setData(stationWrapperList);
    }

    @Override
    public void onBusGpdLoadFailed() {

    }
}
