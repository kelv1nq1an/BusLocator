package me.fattycat.kun.buslocator.model;

import java.util.List;

/**
 * Author: Kelvinkun
 * Time: 16/1/26
 * Description:
 */
public class LineList extends BaseEntity {

    private ResultEntity result;
    /**
     * result : {"lineList":[{"endId":"2462","startName":"阳光e驾塘桥分校","startId":"3652","runPathName":"227路","runPathId":"16","endName":"人民西路停车场"},{"endId":"1559","startName":"妙桥公交首末站","startId":"3656","runPathName":"227路夜班","runPathId":"136","endName":"第一人民医院"}]}
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
         * endId : 2462
         * startName : 阳光e驾塘桥分校
         * startId : 3652
         * runPathName : 227路
         * runPathId : 16
         * endName : 人民西路停车场
         */

        private List<LinesEntity> lines;

        public void setLines(List<LinesEntity> lines) {
            this.lines = lines;
        }

        public List<LinesEntity> getLines() {
            return lines;
        }

        public static class LinesEntity {
            private String endId;
            private String startName;
            private String startId;
            private String runPathName;
            private String runPathId;
            private String endName;

            public void setEndId(String endId) {
                this.endId = endId;
            }

            public void setStartName(String startName) {
                this.startName = startName;
            }

            public void setStartId(String startId) {
                this.startId = startId;
            }

            public void setRunPathName(String runPathName) {
                this.runPathName = runPathName;
            }

            public void setRunPathId(String runPathId) {
                this.runPathId = runPathId;
            }

            public void setEndName(String endName) {
                this.endName = endName;
            }

            public String getEndId() {
                return endId;
            }

            public String getStartName() {
                return startName;
            }

            public String getStartId() {
                return startId;
            }

            public String getRunPathName() {
                return runPathName;
            }

            public String getRunPathId() {
                return runPathId;
            }

            public String getEndName() {
                return endName;
            }
        }
    }
}
