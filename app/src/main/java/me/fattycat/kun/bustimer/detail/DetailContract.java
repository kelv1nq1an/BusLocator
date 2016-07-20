package me.fattycat.kun.bustimer.detail;

import me.fattycat.kun.bustimer.BasePresenter;
import me.fattycat.kun.bustimer.BaseView;
import me.fattycat.kun.bustimer.model.BusGPSEntity;
import me.fattycat.kun.bustimer.model.StationListEntity;

/**
 * Author: Kelvinkun
 * Date: 16/7/11
 */

public interface DetailContract {
    interface View extends BaseView<Presenter> {
        void onStationLoaded(StationListEntity stationListEntity);

        void onStationLoadFailed();

        void onBusGpsLoaded(BusGPSEntity busGPSEntity);

        void onBusGpdLoadFailed();
    }

    interface Presenter extends BasePresenter {
        void getLineStations(String rpid, String flag);

        void getBusGps(String rpid, String flag);
    }
}
