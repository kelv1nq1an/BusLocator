package me.fattycat.kun.bustimer.model;

import java.io.Serializable;

/**
 * Author: Kelvinkun
 * Date: 16/7/21
 */

public class LineInfoSerializable implements Serializable {
    private String endTime1;
    private String startTime;
    private String startStation;
    private String runFlag;
    private String runPathName;
    private String busInterval;
    private String endStation;
    private String runPathId;
    private String endTime;
    private String startTime1;
    private String flag;

    public LineInfoSerializable() {

    }

    public String getEndTime1() {
        return endTime1;
    }

    public void setEndTime1(String endTime1) {
        this.endTime1 = endTime1;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getRunFlag() {
        return runFlag;
    }

    public void setRunFlag(String runFlag) {
        this.runFlag = runFlag;
    }

    public String getRunPathName() {
        return runPathName;
    }

    public void setRunPathName(String runPathName) {
        this.runPathName = runPathName;
    }

    public String getBusInterval() {
        return busInterval;
    }

    public void setBusInterval(String busInterval) {
        this.busInterval = busInterval;
    }

    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    public String getRunPathId() {
        return runPathId;
    }

    public void setRunPathId(String runPathId) {
        this.runPathId = runPathId;
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
