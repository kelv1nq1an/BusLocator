package me.fattycat.kun.bustimer.data.event;

/**
 * Author: qk329
 * Date: 2016/12/20
 */

public class StationAlarmEvent {
    public String  stationName;
    public boolean hasAlarm;

    public StationAlarmEvent(String stationName, boolean hasAlarm) {
        this.stationName = stationName;
        this.hasAlarm = hasAlarm;
    }
}
