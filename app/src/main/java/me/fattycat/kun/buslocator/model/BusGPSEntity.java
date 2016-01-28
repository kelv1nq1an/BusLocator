package me.fattycat.kun.buslocator.model;

import java.util.List;

/**
 * Author: Kelvinkun
 * Time: 16/1/27
 * Descirption:
 */
public class BusGPSEntity extends BaseEntity {

    private ResultEntity result;

    /**
     * result : {"lists":[{"simno":"18862658544","voiceSn":"7","numberPlate":"苏EY0063","shangxiaxing":"1","outstate":"1","gPSTime":"2016-01-27 08:52:27.0","busStationName":"妙桥农贸市场","busStationId":"3075"},{"simno":"18862659004","voiceSn":"18","numberPlate":"苏EY0069","shangxiaxing":"1","outstate":"1","gPSTime":"2016-01-27 08:52:56.0","busStationName":"青龙北桥","busStationId":"2261"},{"simno":"13405604132","voiceSn":"27","numberPlate":"苏EY0078","shangxiaxing":"1","outstate":"1","gPSTime":"2016-01-27 08:52:54.0","busStationName":"鹿苑中转站","busStationId":"3287"},{"simno":"18862659024","voiceSn":"36","numberPlate":"苏EY0090","shangxiaxing":"1","outstate":"1","gPSTime":"2016-01-27 08:52:40.0","busStationName":"妇保所","busStationId":"2540"},{"simno":"18862659134","voiceSn":"40","numberPlate":"苏EY0086","shangxiaxing":"1","outstate":"1","gPSTime":"2016-01-27 08:50:43.0","busStationName":"玉蕙口腔医院（振丰新村）","busStationId":"3483"}]}
     * error :
     * status : SUCCESS
     * errorCode : EMPTYERROR
     */

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public ResultEntity getResult() {
        return result;
    }

    public static class ResultEntity {
        /**
         * simno : 18862658544
         * voiceSn : 7
         * numberPlate : 苏EY0063
         * shangxiaxing : 1
         * outstate : 1
         * gPSTime : 2016-01-27 08:52:27.0
         * busStationName : 妙桥农贸市场
         * busStationId : 3075
         */

        private List<ListsEntity> lists;

        public void setLists(List<ListsEntity> lists) {
            this.lists = lists;
        }

        public List<ListsEntity> getLists() {
            return lists;
        }

        public static class ListsEntity {
            private String simno;
            private String voiceSn;
            private String numberPlate;
            private String shangxiaxing;
            private String outstate;
            private String gPSTime;
            private String busStationName;
            private String busStationId;

            public void setSimno(String simno) {
                this.simno = simno;
            }

            public void setVoiceSn(String voiceSn) {
                this.voiceSn = voiceSn;
            }

            public void setNumberPlate(String numberPlate) {
                this.numberPlate = numberPlate;
            }

            public void setShangxiaxing(String shangxiaxing) {
                this.shangxiaxing = shangxiaxing;
            }

            public void setOutstate(String outstate) {
                this.outstate = outstate;
            }

            public void setGPSTime(String gPSTime) {
                this.gPSTime = gPSTime;
            }

            public void setBusStationName(String busStationName) {
                this.busStationName = busStationName;
            }

            public void setBusStationId(String busStationId) {
                this.busStationId = busStationId;
            }

            public String getSimno() {
                return simno;
            }

            public String getVoiceSn() {
                return voiceSn;
            }

            public String getNumberPlate() {
                return numberPlate;
            }

            public String getShangxiaxing() {
                return shangxiaxing;
            }

            public String getOutstate() {
                return outstate;
            }

            public String getGPSTime() {
                return gPSTime;
            }

            public String getBusStationName() {
                return busStationName;
            }

            public String getBusStationId() {
                return busStationId;
            }
        }
    }
}
