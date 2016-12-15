package me.fattycat.kun.bustimer.data.source;

import java.util.ArrayList;
import java.util.List;

import me.fattycat.kun.bustimer.data.Injection;
import me.fattycat.kun.bustimer.data.entity.FavouriteEntity;
import me.fattycat.kun.bustimer.data.source.local.LocalBusDataSource;
import me.fattycat.kun.bustimer.data.source.remote.RemoteBusDataSource;
import rx.Observable;
import rx.Subscriber;

/**
 * Author: qk329
 * Date: 2016/12/15
 */

public class BusRepository implements BusContract {
    private static BusRepository instance;

    private BusContract.local  localDataSource;
    private BusContract.remote remoteDataSource;

    private List<FavouriteEntity> favouriteEntities;

    private BusRepository() {
        localDataSource = new LocalBusDataSource(Injection.provideContext());
        remoteDataSource = new RemoteBusDataSource(Injection.provideRestfulApi());
    }

    public static BusRepository getInstance() {
        if (instance == null) {
            synchronized (BusRepository.class) {
                if (instance == null) {
                    instance = new BusRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public List<FavouriteEntity> getAllFavouriteLines() {
        return localDataSource.getAllFavouriteLines();
    }

    @Override
    public Observable<List<FavouriteEntity>> getFavouriteLines() {
        return Observable.create(new Observable.OnSubscribe<List<FavouriteEntity>>() {
            @Override
            public void call(Subscriber<? super List<FavouriteEntity>> subscriber) {
                List<FavouriteEntity> entities = localDataSource.getAllFavouriteLines();
                if (entities == null) {
                    entities = new ArrayList<>();
                }
                subscriber.onNext(entities);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public void saveFavouriteLine(FavouriteEntity favouriteEntity) {
        if (favouriteEntities == null) {
            favouriteEntities = getAllFavouriteLines();
        }
        favouriteEntities.add(favouriteEntity);
        localDataSource.saveFavouriteLine(favouriteEntities);
    }

    @Override
    public void deleteFavouriteLine(FavouriteEntity favouriteEntity) {
        if (favouriteEntities == null) {
            favouriteEntities = getAllFavouriteLines();
        }
        favouriteEntities.remove(favouriteEntity);
        localDataSource.saveFavouriteLine(favouriteEntities);
    }
}
