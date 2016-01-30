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
    public final static int DAO_STATE_ARRIVE = 1;
    public final static int DAO_STATE_LEAVE = 2;

    public int type = 0;
    public int busState = 0;
    public BusGPSEntity.ResultEntity.ListsEntity bus;
    public StationListEntity.ResultEntity.StationEntity station;

}
