package me.fattycat.kun.bustimer.data.source;

import java.util.List;

import me.fattycat.kun.bustimer.data.entity.FavouriteEntity;
import rx.Observable;

/**
 * Author: qk329
 * Date: 2016/12/15
 */

public interface BusContract {

    interface local {
        List<FavouriteEntity> getAllFavouriteLines();

        void saveFavouriteLine(List<FavouriteEntity> favouriteEntityList);

    }

    interface remote {


    }

    List<FavouriteEntity> getAllFavouriteLines();

    Observable<List<FavouriteEntity>> getFavouriteLines();

    void saveFavouriteLine(FavouriteEntity favouriteEntity);

    void deleteFavouriteLine(FavouriteEntity favouriteEntity);
}
