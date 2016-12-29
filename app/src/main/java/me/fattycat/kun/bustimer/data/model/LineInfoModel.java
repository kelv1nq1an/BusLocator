package me.fattycat.kun.bustimer.data.model;

/**
 * Author: qk329
 * Date: 2016/12/16
 */

public class LineInfoModel {
    /**
     * endTime1 : 18:00
     * startTime : 05:50
     * startStation : 蒋桥公交首末站
     * runFlag : 0
     * runPathName : 1路
     * busInterval : 10~16
     * endStation : 城北立交桥
     * runPathId : 61
     * endTime : 18:00
     * startTime1 : 05:50
     */

    private String runPathId;
    private String runPathName;
    private String startTime;
    private String endTime;
    private String startTime1;
    private String endTime1;
    private String startStation;
    private String endStation;
    private String busInterval;
    private String runFlag;

    public String getRunPathId() {
        return runPathId;
    }

    public void setRunPathId(String runPathId) {
        this.runPathId = runPathId;
    }

    public String getRunPathName() {
        return runPathName;
    }

    public void setRunPathName(String runPathName) {
        this.runPathName = runPathName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime1() {
        return startTime1;
    }

    public void setStartTime1(String startTime1) {
        this.startTime1 = startTime1;
    }

    public String getEndTime1() {
        return endTime1;
    }

    public void setEndTime1(String endTime1) {
        this.endTime1 = endTime1;
    }

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    public String getBusInterval() {
        return busInterval;
    }

    public void setBusInterval(String busInterval) {
        this.busInterval = busInterval;
    }

    public String getRunFlag() {
        return runFlag;
    }

    public void setRunFlag(String runFlag) {
        this.runFlag = runFlag;
    }
}
