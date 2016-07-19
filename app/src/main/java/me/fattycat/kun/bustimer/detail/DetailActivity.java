package me.fattycat.kun.bustimer.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.fattycat.kun.bustimer.Http.BusTimerApi;
import me.fattycat.kun.bustimer.R;
import me.fattycat.kun.bustimer.model.StationListEntity;

/**
 * Author: Kelvinkun
 * Date: 16/7/11
 */

public class DetailActivity extends AppCompatActivity implements DetailContract.View {
    private static final String RPID = "rpid";
    private static final String DIRECATION = "direcation";
    private static final String LINE_NAME = "lineName";
    private static final String START_STATION = "startStation";
    private static final String END_STATION = "endStation";

    @BindView(R.id.detail_station_list)
    RecyclerView detailStationList;
    @BindView(R.id.detail_toolbar)
    Toolbar detailToolbar;
    @BindView(R.id.detail_fab)
    FloatingActionButton detailFab;
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

    private DetailContract.Presenter detailContractPresenter;
    private DetailStationListAdapter detailStationListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_detail);
        ButterKnife.bind(this);
        new DetailPresenter(this);

        rpid = getIntent().getStringExtra(RPID);
        direcation = getIntent().getStringExtra(DIRECATION);
        lineName = getIntent().getStringExtra(LINE_NAME);
        startStation = getIntent().getStringExtra(START_STATION);
        endStation = getIntent().getStringExtra(END_STATION);

        setupViews();

        detailContractPresenter.getLineStations(rpid, direcation);
    }

    public static Intent getCallingIntent(Context context, String rpid, String direcation, String lineName, String startStation, String endStation) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(RPID, rpid);
        intent.putExtra(DIRECATION, direcation);
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
    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
        this.detailContractPresenter = presenter;
    }

    @Override
    public void onStationLoaded(StationListEntity stationListEntity) {
        if (TextUtils.equals(direcation, BusTimerApi.FLAG_LINE_GO)) {
            detailStationListAdapter.setData(stationListEntity.getResult().getShangxing());
        } else {
            detailStationListAdapter.setData(stationListEntity.getResult().getXiaxing());
        }
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
}
