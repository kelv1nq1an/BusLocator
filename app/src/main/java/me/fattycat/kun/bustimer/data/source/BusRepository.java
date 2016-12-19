package me.fattycat.kun.bustimer.data.source;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.fattycat.kun.bustimer.data.Injection;
import me.fattycat.kun.bustimer.data.entity.FavouriteEntity;
import me.fattycat.kun.bustimer.data.model.BusGPSModel;
import me.fattycat.kun.bustimer.data.model.LineInfoModel;
import me.fattycat.kun.bustimer.data.model.SearchModel;
import me.fattycat.kun.bustimer.data.model.StationModel;
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
        favouriteEntities = getAllFavouriteLines();
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
        for (FavouriteEntity favouriteEntity1 : favouriteEntities) {
            if (TextUtils.equals(favouriteEntity1.rpid, favouriteEntity.rpid)) {
                return;
            }
        }
        favouriteEntities.add(favouriteEntity);
        localDataSource.saveFavouriteLine(favouriteEntities);
    }

    @Override
    public void deleteFavouriteLine(String rpid) {
        if (favouriteEntities == null) {
            favouriteEntities = getAllFavouriteLines();
        }
        for(Iterator iterator=favouriteEntities.iterator();iterator.hasNext();){
            FavouriteEntity favouriteEntity = (FavouriteEntity) iterator.next();
            if(TextUtils.equals(favouriteEntity.rpid,rpid)){
                iterator.remove();
                break;
            }
        }

        localDataSource.saveFavouriteLine(favouriteEntities);
    }

    @Override
    public Observable<SearchModel> searchByName(String name) {
        return remoteDataSource.searchByName(name);
    }

    @Override
    public Observable<LineInfoModel> getLineInfo(String runPathID) {
        return remoteDataSource.getLineInfo(runPathID);
    }

    @Override
    public Observable<BusGPSModel> getBusGPS(String runPathID, String flag) {
        return remoteDataSource.getBusGPS(runPathID, flag);
    }

    @Override
    public Observable<StationModel> getStations(String runPathID) {
        return remoteDataSource.getStations(runPathID);
    }
}
