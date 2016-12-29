package me.fattycat.kun.bustimer.data.entity;

/**
 * Author: qk329
 * Date: 2016/12/14
 */

public class FavouriteEntity {
    public String rpid;
    public String lineName;
    public String startStation;
    public String endStation;
    public int    lineType;
    public String firstBus;
    public String finalBus;
    public String gapTime;

    public FavouriteEntity(String rpid, String lineName, String startStation, String endStation, int lineType, String firstBus, String finalBus, String gapTime) {
        this.rpid = rpid;
        this.lineName = lineName;
        this.startStation = startStation;
        this.endStation = endStation;
        this.lineType = lineType;
        this.firstBus = firstBus;
        this.finalBus = finalBus;
        this.gapTime = gapTime;
    }
}
