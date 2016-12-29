package me.fattycat.kun.bustimer.ui.detail;

import java.util.ArrayList;
import java.util.List;

import me.fattycat.kun.bustimer.data.model.BusGPSModel;
import me.fattycat.kun.bustimer.data.model.StationModel;

/**
 * Author: qk329
 * Date: 2016/12/19
 */

public class DetailWrapper {
    private StationModel.StationEntity  stationEntity;
    private List<BusGPSModel.BusEntity> busEntityList;

    public DetailWrapper(StationModel.StationEntity stationEntity) {
        this.stationEntity = stationEntity;
        this.busEntityList = new ArrayList<>();
    }

    public StationModel.StationEntity getStationEntity() {
        return stationEntity;
    }

    public void setStationEntity(StationModel.StationEntity stationEntity) {
        this.stationEntity = stationEntity;
    }

    public List<BusGPSModel.BusEntity> getBusEntityList() {
        return busEntityList;
    }

    public void setBusEntityList(List<BusGPSModel.BusEntity> busEntityList) {
        this.busEntityList = busEntityList;
    }
}
