package me.fattycat.kun.bustimer.data.source;

import java.util.List;

import me.fattycat.kun.bustimer.data.entity.FavouriteEntity;
import me.fattycat.kun.bustimer.data.model.BusGPSModel;
import me.fattycat.kun.bustimer.data.model.LineInfoModel;
import me.fattycat.kun.bustimer.data.model.SearchModel;
import me.fattycat.kun.bustimer.data.model.StationModel;
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
        Observable<SearchModel> searchByName(String name);

        Observable<LineInfoModel> getLineInfo(String runPathID);

        Observable<BusGPSModel> getBusGPS(String runPathID, String flag);

        Observable<StationModel> getStations(String runPathID);
    }

    List<FavouriteEntity> getAllFavouriteLines();

    Observable<List<FavouriteEntity>> getFavouriteLines();

    void saveFavouriteLine(FavouriteEntity favouriteEntity);

    void deleteFavouriteLine(String rpid);

    Observable<SearchModel> searchByName(String name);

    Observable<LineInfoModel> getLineInfo(String runPathID);

    Observable<BusGPSModel> getBusGPS(String runPathID, String flag);

    Observable<StationModel> getStations(String runPathID);

}
