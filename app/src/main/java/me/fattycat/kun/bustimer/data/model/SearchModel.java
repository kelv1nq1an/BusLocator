package me.fattycat.kun.bustimer.data.model;

import java.util.List;

/**
 * Author: qk329
 * Date: 2016/12/15
 */

public class SearchModel {

    private List<LinesEntity> lines;

    public List<LinesEntity> getLines() { return lines;}

    public void setLines(List<LinesEntity> lines) { this.lines = lines;}

    public static class LinesEntity {
        /**
         * endId : 1907
         * startName : 蒋桥公交首末站
         * startId : 3529
         * runPathName : 1路
         * runPathId : 61
         * endName : 城北立交桥
         */

        private String endId;
        private String startName;
        private String startId;
        private String runPathName;
        private String runPathId;
        private String endName;

        public String getEndId() { return endId;}

        public void setEndId(String endId) { this.endId = endId;}

        public String getStartName() { return startName;}

        public void setStartName(String startName) { this.startName = startName;}

        public String getStartId() { return startId;}

        public void setStartId(String startId) { this.startId = startId;}

        public String getRunPathName() { return runPathName;}

        public void setRunPathName(String runPathName) { this.runPathName = runPathName;}

        public String getRunPathId() { return runPathId;}

        public void setRunPathId(String runPathId) { this.runPathId = runPathId;}

        public String getEndName() { return endName;}

        public void setEndName(String endName) { this.endName = endName;}
    }
}
