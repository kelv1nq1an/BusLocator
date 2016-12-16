package me.fattycat.kun.bustimer.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fattycat.kun.bustimer.R;
import me.fattycat.kun.bustimer.data.entity.FavouriteEntity;
import me.fattycat.kun.bustimer.ui.favourite.FavouriteActivity;
import me.fattycat.kun.bustimer.ui.search.SearchActivity;

/**
 * Author: qk329
 * Date: 2016/11/25
 */

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @BindView(R.id.top_bar_title_text_view)
    TextView     topBarTitleTextView;
    @BindView(R.id.main_favourite_recycler_view)
    RecyclerView mainFavouriteRecyclerView;

    private MainContract.Presenter presenter;
    private FavouriteAdapter       favouriteAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        topBarTitleTextView.setText("港城公交");

        favouriteAdapter = new FavouriteAdapter();
        mainFavouriteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainFavouriteRecyclerView.setAdapter(favouriteAdapter);

        new MainPresenter(this).subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unSubscribe();
    }

    @OnClick(R.id.top_bar_favourite_image_view)
    public void onFavouriteClick() {
        startActivity(new Intent(this, FavouriteActivity.class));
    }

    @OnClick(R.id.top_bar_info_image_view)
    public void onInfoClick() {

    }

    @OnClick(R.id.main_search_image_view)
    public void onSearchClick() {
        startActivity(new Intent(this, SearchActivity.class));
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onFavouriteLoadStart() {

    }

    @Override
    public void onFavouriteLoadSuccess(List<FavouriteEntity> favouriteEntities) {
        // TODO: 2016/12/15 temp
        FavouriteEntity favouriteEntity = new FavouriteEntity();
        favouriteEntity.lineName = "233路";
        favouriteEntity.startStation = "港城汽车站";
        favouriteEntity.endStation = "购物公园";
        favouriteEntity.firstBus = "05:00";
        favouriteEntity.finalBus = "21:00";
        favouriteEntity.gapTime = "00:10";

        favouriteEntities.add(favouriteEntity);
        favouriteEntities.add(favouriteEntity);
        favouriteEntities.add(favouriteEntity);
        favouriteAdapter.setData(favouriteEntities);
    }

    @Override
    public void onFavouriteLoadError() {

    }
}
