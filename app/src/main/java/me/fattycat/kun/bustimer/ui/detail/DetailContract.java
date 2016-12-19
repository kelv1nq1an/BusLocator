package me.fattycat.kun.bustimer.ui.detail;

import java.util.List;

import me.fattycat.kun.bustimer.data.entity.FavouriteEntity;
import me.fattycat.kun.bustimer.data.model.BusGPSModel;
import me.fattycat.kun.bustimer.data.model.LineInfoModel;
import me.fattycat.kun.bustimer.data.model.StationModel;
import me.fattycat.kun.bustimer.ui.base.BasePresenter;
import me.fattycat.kun.bustimer.ui.base.BaseView;

/**
 * Author: qk329
 * Date: 2016/12/16
 */

public interface DetailContract {

    interface View extends BaseView<Presenter> {
        void onGetLineInfoStart();

        void onGetLineInfoSuccess(LineInfoModel lineInfoModel);

        void onGetLineInfoError();

        void onGetStationsStart();

        void onGetStationsSuccess(StationModel stationModel);

        void onGetStationsError();

        void onGetBusGPSStart();

        void onGetBusGPSSuccess(BusGPSModel busGPSModel);

        void onGetBusGPSError();

        void onFavouriteLoadStart();

        void onFavouriteLoadSuccess(List<FavouriteEntity> favouriteEntities);

        void onFavouriteLoadError();
    }

    interface Presenter extends BasePresenter {
        void getLineInfo(String runPathID);

        void getStations(String runPathID);

        void getBusGPS(String runPathID, String flag);

        void getFavouriteList();

    }
}
