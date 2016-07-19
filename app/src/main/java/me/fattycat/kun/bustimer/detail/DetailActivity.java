package me.fattycat.kun.bustimer.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.fattycat.kun.bustimer.R;

/**
 * Author: Kelvinkun
 * Date: 16/7/11
 */

public class DetailActivity extends AppCompatActivity implements DetailContract.View {
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

    private String lineName;
    private String startStation;
    private String endStation;

    private DetailStationListAdapter detailStationListAdapter;

    public static Intent getCallingIntent(Context context, String lineName, String startStation, String endStation) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(LINE_NAME, lineName);
        intent.putExtra(START_STATION, startStation);
        intent.putExtra(END_STATION, endStation);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_detail);
        ButterKnife.bind(this);

        lineName = getIntent().getStringExtra(LINE_NAME);
        startStation = getIntent().getStringExtra(START_STATION);
        endStation = getIntent().getStringExtra(END_STATION);

        setupViews();

        DetailPresenter detailPresenter = new DetailPresenter(this);
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

        // FIXME: 16/7/12 test
        List<String> test = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            test.add("kk");
        }

        detailStationListAdapter.setData(test);
        detailStationList.setLayoutManager(new LinearLayoutManager(this));
        detailStationList.setAdapter(detailStationListAdapter);
    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {

    }
}
