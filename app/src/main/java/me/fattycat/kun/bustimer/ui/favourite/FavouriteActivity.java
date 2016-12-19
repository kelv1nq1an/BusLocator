package me.fattycat.kun.bustimer.ui.favourite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fattycat.kun.bustimer.R;
import me.fattycat.kun.bustimer.data.entity.FavouriteEntity;
import me.fattycat.kun.bustimer.ui.main.FavouriteAdapter;
import me.fattycat.kun.bustimer.ui.main.MainContract;
import me.fattycat.kun.bustimer.ui.main.MainPresenter;

public class FavouriteActivity extends AppCompatActivity implements MainContract.View {

    @BindView(R.id.favourite_recycler_view)
    RecyclerView favouriteRecyclerView;

    private FavouriteAdapter       favouriteAdapter;
    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        ButterKnife.bind(this);

        favouriteAdapter = new FavouriteAdapter();
        favouriteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        favouriteRecyclerView.setAdapter(favouriteAdapter);

        new MainPresenter(this).subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unSubscribe();
    }

    @OnClick(R.id.favourite_close_image_view)
    public void close() {
        finish();
    }

    @Override
    public void onFavouriteLoadStart() {

    }

    @Override
    public void onFavouriteLoadSuccess(List<FavouriteEntity> favouriteEntities) {
        favouriteAdapter.setData(favouriteEntities);
    }

    @Override
    public void onFavouriteLoadError() {

    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
