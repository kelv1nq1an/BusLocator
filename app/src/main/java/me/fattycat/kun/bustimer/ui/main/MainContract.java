package me.fattycat.kun.bustimer.ui.main;

import java.util.List;

import me.fattycat.kun.bustimer.data.entity.FavouriteEntity;
import me.fattycat.kun.bustimer.ui.base.BasePresenter;
import me.fattycat.kun.bustimer.ui.base.BaseView;

/**
 * Author: Kelvinkun
 * Date: 16/6/30
 */

public interface MainContract {
    interface View extends BaseView<Presenter> {
        void onFavouriteLoadStart();

        void onFavouriteLoadSuccess(List<FavouriteEntity> favouriteEntities);

        void onFavouriteLoadError();
    }

    interface Presenter extends BasePresenter {
        void getFavouriteList();
    }
}
