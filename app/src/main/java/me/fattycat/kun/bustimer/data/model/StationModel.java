package me.fattycat.kun.bustimer.data.model;

import java.util.List;

/**
 * Author: qk329
 * Date: 2016/12/16
 */

public class StationModel {
    /**
     * runPathName : 1路
     * xiaxing : [{"sn":"29","flag":"2","busStationName":"城北立交桥","busStationId":"1907"},{"sn":"30","flag":"3","busStationName":"城北新村","busStationId":"141"},{"sn":"31","flag":"3","busStationName":"电信大厦","busStationId":"176"},{"sn":"32","flag":"3","busStationName":"滋生桥","busStationId":"3283"},{"sn":"33","flag":"3","busStationName":"慕嘏桥","busStationId":"3450"},{"sn":"34","flag":"3","busStationName":"实验小学","busStationId":"1762"},{"sn":"35","flag":"3","busStationName":"移动公司","busStationId":"1848"},{"sn":"36","flag":"3","busStationName":"市一中","busStationId":"1767"},{"sn":"37","flag":"3","busStationName":"前溪巷","busStationId":"1738"},{"sn":"38","flag":"3","busStationName":"花园浜","busStationId":"1643"},{"sn":"39","flag":"3","busStationName":"梁丰花园北门","busStationId":"3969"},{"sn":"40","flag":"3","busStationName":"梁丰生态园北门","busStationId":"3971"},{"sn":"41","flag":"3","busStationName":"东二环路","busStationId":"2219"},{"sn":"42","flag":"3","busStationName":"第一加油站","busStationId":"2216"},{"sn":"43","flag":"3","busStationName":"张视电商会展中心","busStationId":"3973"},{"sn":"44","flag":"3","busStationName":"兴华豪苑","busStationId":"3957"},{"sn":"45","flag":"3","busStationName":"香港城","busStationId":"87"},{"sn":"46","flag":"3","busStationName":"乘航派出所","busStationId":"17"},{"sn":"47","flag":"3","busStationName":"工贸中学","busStationId":"4034"},{"sn":"48","flag":"3","busStationName":"新丰苑小区东门","busStationId":"4036"},{"sn":"49","flag":"3","busStationName":"新航花苑","busStationId":"4089"},{"sn":"50","flag":"3","busStationName":"钢材市场","busStationId":"1597"},{"sn":"51","flag":"3","busStationName":"庆安大桥","busStationId":"69"},{"sn":"52","flag":"3","busStationName":"新乘花苑北门","busStationId":"3857"},{"sn":"53","flag":"3","busStationName":"信和纺织","busStationId":"1837"},{"sn":"54","flag":"3","busStationName":"骏马农林科技","busStationId":"2228"},{"sn":"55","flag":"3","busStationName":"双鹿村","busStationId":"3532"},{"sn":"56","flag":"3","busStationName":"毛家堂","busStationId":"3881"},{"sn":"57","flag":"3","busStationName":"骏马工业园","busStationId":"1676"},{"sn":"58","flag":"4","busStationName":"蒋桥公交首末站","busStationId":"3529"}]
     * runPathId : 61
     * roundRunPath : 1
     * shangxing : [{"sn":"1","flag":"0","busStationName":"蒋桥公交首末站","busStationId":"3529"},{"sn":"2","flag":"1","busStationName":"骏马工业园","busStationId":"1677"},{"sn":"3","flag":"1","busStationName":"毛家堂","busStationId":"3882"},{"sn":"4","flag":"1","busStationName":"双鹿村","busStationId":"1955"},{"sn":"5","flag":"1","busStationName":"骏马农林科技","busStationId":"2229"},{"sn":"6","flag":"1","busStationName":"信和纺织","busStationId":"1838"},{"sn":"7","flag":"1","busStationName":"新乘花苑北门","busStationId":"3858"},{"sn":"8","flag":"1","busStationName":"庆安大桥","busStationId":"70"},{"sn":"9","flag":"1","busStationName":"钢材市场","busStationId":"1598"},{"sn":"10","flag":"1","busStationName":"新航花苑","busStationId":"4090"},{"sn":"11","flag":"1","busStationName":"新丰苑小区东门","busStationId":"4037"},{"sn":"12","flag":"1","busStationName":"工贸中学","busStationId":"4035"},{"sn":"13","flag":"1","busStationName":"乘航派出所","busStationId":"16"},{"sn":"14","flag":"1","busStationName":"香港城","busStationId":"86"},{"sn":"15","flag":"1","busStationName":"兴华豪苑","busStationId":"3956"},{"sn":"16","flag":"1","busStationName":"张视电商会展中心","busStationId":"3972"},{"sn":"17","flag":"1","busStationName":"东二环路","busStationId":"2219"},{"sn":"18","flag":"1","busStationName":"梁丰生态园北门","busStationId":"3970"},{"sn":"19","flag":"1","busStationName":"梁丰花园北门","busStationId":"3968"},{"sn":"20","flag":"1","busStationName":"花园浜","busStationId":"1927"},{"sn":"21","flag":"1","busStationName":"前溪巷","busStationId":"2234"},{"sn":"22","flag":"1","busStationName":"市一中","busStationId":"1768"},{"sn":"23","flag":"1","busStationName":"移动公司","busStationId":"1847"},{"sn":"24","flag":"1","busStationName":"实验小学","busStationId":"1761"},{"sn":"25","flag":"1","busStationName":"慕嘏桥","busStationId":"3447"},{"sn":"26","flag":"1","busStationName":"滋生桥","busStationId":"3282"},{"sn":"27","flag":"1","busStationName":"电信大厦","busStationId":"175"},{"sn":"28","flag":"1","busStationName":"城北新村","busStationId":"142"},{"sn":"29","flag":"2","busStationName":"城北立交桥","busStationId":"1907"}]
     */

    private String              runPathName;
    private String              runPathId;
    private String              roundRunPath;
    private List<StationEntity> xiaxing;
    private List<StationEntity> shangxing;

    public String getRunPathName() { return runPathName;}

    public void setRunPathName(String runPathName) { this.runPathName = runPathName;}

    public String getRunPathId() { return runPathId;}

    public void setRunPathId(String runPathId) { this.runPathId = runPathId;}

    public String getRoundRunPath() { return roundRunPath;}

    public void setRoundRunPath(String roundRunPath) { this.roundRunPath = roundRunPath;}

    public List<StationEntity> getXiaxing() { return xiaxing;}

    public void setXiaxing(List<StationEntity> xiaxing) { this.xiaxing = xiaxing;}

    public List<StationEntity> getShangxing() { return shangxing;}

    public void setShangxing(List<StationEntity> shangxing) { this.shangxing = shangxing;}

    public static class StationEntity {
        /**
         * sn : 29
         * flag : 2
         * busStationName : 城北立交桥
         * busStationId : 1907
         */

        private String  sn;
        private String  flag;
        private String  busStationName;
        private String  busStationId;
        private boolean hasAlarm;

        public String getSn() { return sn;}

        public void setSn(String sn) { this.sn = sn;}

        public String getFlag() { return flag;}

        public void setFlag(String flag) { this.flag = flag;}

        public String getBusStationName() { return busStationName;}

        public void setBusStationName(String busStationName) { this.busStationName = busStationName;}

        public String getBusStationId() { return busStationId;}

        public void setBusStationId(String busStationId) { this.busStationId = busStationId;}

        public boolean isHasAlarm() {
            return hasAlarm;
        }

        public void setHasAlarm(boolean hasAlarm) {
            this.hasAlarm = hasAlarm;
        }
    }
}
