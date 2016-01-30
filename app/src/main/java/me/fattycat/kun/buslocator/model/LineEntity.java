package me.fattycat.kun.buslocator.model;

/**
 * Author: Kelvinkun
 * Time: 16/1/27
 * Description:
 */
public class LineEntity extends BaseEntity {

    /**
     * endTime1 : 21:00
     * startTime : 18:30,19:10,20:00
     * startStation : 南丰客运站
     * runFlag : 1
     * runPathName : 222路夜班
     * busInterval : 0~0
     * endStation : 客运站
     * runPathId : 143
     * endTime : 20:00
     * startTime1 : 18:30,19:45,21:00
     */

    private ResultEntity result;

    /**
     * result : {"endTime1":"21:00","startTime":"18:30,19:10,20:00","startStation":"南丰客运站","runFlag":"1","runPathName":"222路夜班","busInterval":"0~0","endStation":"客运站","runPathId":"143","endTime":"20:00","startTime1":"18:30,19:45,21:00"}
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

        public void setEndTime1(String endTime1) {
            this.endTime1 = endTime1;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public void setStartStation(String startStation) {
            this.startStation = startStation;
        }

        public void setRunFlag(String runFlag) {
            this.runFlag = runFlag;
        }

        public void setRunPathName(String runPathName) {
            this.runPathName = runPathName;
        }

        public void setBusInterval(String busInterval) {
            this.busInterval = busInterval;
        }

        public void setEndStation(String endStation) {
            this.endStation = endStation;
        }

        public void setRunPathId(String runPathId) {
            this.runPathId = runPathId;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public void setStartTime1(String startTime1) {
            this.startTime1 = startTime1;
        }

        public String getEndTime1() {
            return endTime1;
        }

        public String getStartTime() {
            return startTime;
        }

        public String getStartStation() {
            return startStation;
        }

        public String getRunFlag() {
            return runFlag;
        }

        public String getRunPathName() {
            return runPathName;
        }

        public String getBusInterval() {
            return busInterval;
        }

        public String getEndStation() {
            return endStation;
        }

        public String getRunPathId() {
            return runPathId;
        }

        public String getEndTime() {
            return endTime;
        }

        public String getStartTime1() {
            return startTime1;
        }
    }
}
