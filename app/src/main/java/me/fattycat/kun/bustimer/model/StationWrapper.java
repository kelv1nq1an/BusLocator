package me.fattycat.kun.bustimer.model;

import java.util.List;

/**
 * Author: Kelvinkun
 * Date: 16/7/20
 */

public class StationWrapper {
    private StationListEntity.ResultEntity.StationEntity station;
    private List<BusGPSEntity.ResultEntity.ListsEntity> busList;
    private boolean hasAlarm;
    public boolean arrived = false;
    public boolean arrivingOneStation = false;
    public boolean arrivingTwoStation = false;

    public StationWrapper(StationListEntity.ResultEntity.StationEntity station, List<BusGPSEntity.ResultEntity.ListsEntity> buses) {
        this.station = station;
        this.busList = buses;
        hasAlarm = false;
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

    public boolean isHasAlarm() {
        return hasAlarm;
    }

    public void setHasAlarm(boolean hasAlarm) {
        this.hasAlarm = hasAlarm;
    }
}
