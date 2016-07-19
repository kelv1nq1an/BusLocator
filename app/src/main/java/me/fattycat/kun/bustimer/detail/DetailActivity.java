package me.fattycat.kun.bustimer.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

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

    @BindView(R.id.detail_station_list)
    RecyclerView detailStationList;
    @BindView(R.id.detail_toolbar)
    Toolbar detailToolbar;
    @BindView(R.id.detail_fab)
    FloatingActionButton detailFab;

    private DetailStationListAdapter detailStationListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_detail);
        ButterKnife.bind(this);

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
