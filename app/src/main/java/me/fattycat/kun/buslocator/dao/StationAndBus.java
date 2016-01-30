package me.fattycat.kun.buslocator.dao;

import me.fattycat.kun.buslocator.model.BusGPSEntity;
import me.fattycat.kun.buslocator.model.StationListEntity;

/**
 * Author: Kelvinkun
 * Time: 16/1/30
 * Descirption:
 */
public class StationAndBus {
    public final static int DAO_TYPE_STATION = 1;
    public final static int DAO_TYPE_BUS = 2;

    public int type = 0;
    public StationListEntity.ResultEntity.StationEntity station;
    public BusGPSEntity.ResultEntity.ListsEntity bus;

}
