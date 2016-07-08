package me.fattycat.kun.bustimer.favourite;

/**
 * Author: Kelvinkun
 * Date: 16/7/8
 */

public class FavouritePresenter implements FavouriteContract.Presenter {
    private final FavouriteContract.View favouriteView;

    public FavouritePresenter(FavouriteContract.View view) {
        this.favouriteView = view;
        this.favouriteView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void loadLocalData() {
        favouriteView.onDataLoaded();
    }
}
