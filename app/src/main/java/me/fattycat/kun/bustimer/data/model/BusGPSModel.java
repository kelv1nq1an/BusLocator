package me.fattycat.kun.bustimer.data.model;

import java.util.List;

/**
 * Author: qk329
 * Date: 2016/12/16
 */

public class BusGPSModel {
    private List<ListsEntity> lists;

    public List<ListsEntity> getLists() { return lists;}

    public void setLists(List<ListsEntity> lists) { this.lists = lists;}

    public static class ListsEntity {
        /**
         * simno : 15162326834
         * voiceSn : 37
         * numberPlate : 苏E5N925
         * shangxiaxing : 3
         * outstate : 0
         * gPSTime : 2016-12-16 13:06:00.0
         * busStationName : 前溪巷
         * busStationId : 1738
         */

        private String simno;
        private String voiceSn;
        private String numberPlate;
        private String shangxiaxing;
        private String outstate;
        private String gPSTime;
        private String busStationName;
        private String busStationId;

        public String getSimno() { return simno;}

        public void setSimno(String simno) { this.simno = simno;}

        public String getVoiceSn() { return voiceSn;}

        public void setVoiceSn(String voiceSn) { this.voiceSn = voiceSn;}

        public String getNumberPlate() { return numberPlate;}

        public void setNumberPlate(String numberPlate) { this.numberPlate = numberPlate;}

        public String getShangxiaxing() { return shangxiaxing;}

        public void setShangxiaxing(String shangxiaxing) { this.shangxiaxing = shangxiaxing;}

        public String getOutstate() { return outstate;}

        public void setOutstate(String outstate) { this.outstate = outstate;}

        public String getGPSTime() { return gPSTime;}

        public void setGPSTime(String gPSTime) { this.gPSTime = gPSTime;}

        public String getBusStationName() { return busStationName;}

        public void setBusStationName(String busStationName) { this.busStationName = busStationName;}

        public String getBusStationId() { return busStationId;}

        public void setBusStationId(String busStationId) { this.busStationId = busStationId;}
    }
}
