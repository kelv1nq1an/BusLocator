package me.fattycat.kun.bustimer.model;

import java.util.List;

/**
 * Author: Kelvinkun
 * Date: 16/7/20
 */

public class StationWrapper {
    private StationListEntity.ResultEntity.StationEntity station;
    private List<BusGPSEntity.ResultEntity.ListsEntity> busList;

    public StationWrapper(StationListEntity.ResultEntity.StationEntity station, List<BusGPSEntity.ResultEntity.ListsEntity> buses) {
        this.station = station;
        this.busList = buses;
    }

    public List<BusGPSEntity.ResultEntity.ListsEntity> getBusList() {
        return busList;
    }

    public void setBusList(List<BusGPSEntity.ResultEntity.ListsEntity> busList) {
        this.busList = busList;
    }

    public StationListEntity.ResultEntity.StationEntity getStation() {
        return station;
    }

    public void setStation(StationListEntity.ResultEntity.StationEntity station) {
        this.station = station;
    }
}
