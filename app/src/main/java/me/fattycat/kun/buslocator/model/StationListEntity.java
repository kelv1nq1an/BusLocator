package me.fattycat.kun.buslocator.model;

import java.util.List;

/**
 * Author: Kelvinkun
 * Time: 16/1/27
 * Descirption:
 */
public class StationListEntity extends BaseEntity {

    /**
     * runPathName : 222路夜班
     * xiaxing : [{"sn":"48","busStationName":"客运站","flag":"2","busStationId":"55"},{"sn":"49","busStationName":"客运站南门","flag":"3","busStationId":"3915"},{"sn":"50","busStationName":"河西南路","flag":"3","busStationId":"3945"},{"sn":"51","busStationName":"湖东花苑","flag":"3","busStationId":"3949"},{"sn":"52","busStationName":"湖滨国际","flag":"3","busStationId":"2379"},{"sn":"53","busStationName":"丽景华都","flag":"3","busStationId":"2864"},{"sn":"54","busStationName":"湖滨新村","flag":"3","busStationId":"1925"},{"sn":"55","busStationName":"南城花园","flag":"3","busStationId":"2258"},{"sn":"56","busStationName":"港城汽车站西","flag":"3","busStationId":"2299"},{"sn":"57","busStationName":"国际购物中心","flag":"3","busStationId":"1619"},{"sn":"58","busStationName":"实验小学","flag":"3","busStationId":"1762"},{"sn":"59","busStationName":"慕嘏桥","flag":"3","busStationId":"3450"},{"sn":"60","busStationName":"滋生桥","flag":"3","busStationId":"3283"},{"sn":"61","busStationName":"电信大厦","flag":"3","busStationId":"176"},{"sn":"62","busStationName":"国贸酒店","flag":"3","busStationId":"217"},{"sn":"63","busStationName":"市政府","flag":"3","busStationId":"342"},{"sn":"64","busStationName":"电视大学","flag":"3","busStationId":"174"},{"sn":"65","busStationName":"检察院","flag":"3","busStationId":"3680"},{"sn":"66","busStationName":"东渡大厦","flag":"3","busStationId":"2001"},{"sn":"67","busStationName":"勤丰苑","flag":"3","busStationId":"1740"},{"sn":"68","busStationName":"文化中心","flag":"3","busStationId":"1804"},{"sn":"69","busStationName":"汇金中心","flag":"3","busStationId":"3953"},{"sn":"70","busStationName":"联发汽车城（阳光e驾港城驾校）","flag":"3","busStationId":"3715"},{"sn":"71","busStationName":"蒋桥","flag":"3","busStationId":"2009"},{"sn":"72","busStationName":"骏马工业园","flag":"3","busStationId":"1677"},{"sn":"73","busStationName":"华夏集团","flag":"3","busStationId":"2852"},{"sn":"74","busStationName":"黎明村","flag":"3","busStationId":"2862"},{"sn":"75","busStationName":"阀门厂","flag":"3","busStationId":"2850"},{"sn":"76","busStationName":"张家港电厂","flag":"3","busStationId":"2896"},{"sn":"77","busStationName":"东莱徐丰小区","flag":"3","busStationId":"2842"},{"sn":"78","busStationName":"东莱菜场","flag":"3","busStationId":"1911"},{"sn":"79","busStationName":"百得医疗","flag":"3","busStationId":"2840"},{"sn":"80","busStationName":"庆桥站","flag":"3","busStationId":"2882"},{"sn":"81","busStationName":"庆桥站（东）","flag":"3","busStationId":"2884"},{"sn":"82","busStationName":"庆东小学","flag":"3","busStationId":"2876"},{"sn":"83","busStationName":"庆东站","flag":"3","busStationId":"2878"},{"sn":"84","busStationName":"庆东站（东）","flag":"3","busStationId":"2880"},{"sn":"85","busStationName":"团结闸","flag":"3","busStationId":"2888"},{"sn":"86","busStationName":"南丰开发区","flag":"3","busStationId":"2062"},{"sn":"87","busStationName":"海新南路","flag":"3","busStationId":"2035"},{"sn":"88","busStationName":"南丰中学","flag":"3","busStationId":"2067"},{"sn":"89","busStationName":"兴园路","flag":"3","busStationId":"2092"},{"sn":"90","busStationName":"海坝村委","flag":"3","busStationId":"2031"},{"sn":"91","busStationName":"南丰农商行","flag":"3","busStationId":"2064"},{"sn":"92","busStationName":"南丰中行","flag":"3","busStationId":"1949"},{"sn":"93","busStationName":"南丰镇政府","flag":"3","busStationId":"2066"},{"sn":"94","busStationName":"南丰幼儿园","flag":"3","busStationId":"2872"},{"sn":"95","busStationName":"南丰小学","flag":"3","busStationId":"2870"},{"sn":"96","busStationName":"新丰小区西门","flag":"3","busStationId":"2890"},{"sn":"97","busStationName":"南丰客运站","flag":"4","busStationId":"2869"}]
     * runPathId : 143
     * roundRunPath : 1
     * shangxing : [{"sn":"1","busStationName":"南丰客运站","flag":"0","busStationId":"2869"},{"sn":"2","busStationName":"新丰小区西门","flag":"1","busStationId":"2889"},{"sn":"3","busStationName":"南丰小学","flag":"1","busStationId":"604"},{"sn":"4","busStationName":"南丰幼儿园","flag":"1","busStationId":"2871"},{"sn":"5","busStationName":"南丰镇政府","flag":"1","busStationId":"2065"},{"sn":"6","busStationName":"南丰中行","flag":"1","busStationId":"1948"},{"sn":"7","busStationName":"南丰农商行","flag":"1","busStationId":"2063"},{"sn":"8","busStationName":"海坝村委","flag":"1","busStationId":"2030"},{"sn":"9","busStationName":"兴园路","flag":"1","busStationId":"2091"},{"sn":"10","busStationName":"南丰中学","flag":"1","busStationId":"605"},{"sn":"11","busStationName":"海新南路","flag":"1","busStationId":"2034"},{"sn":"12","busStationName":"南丰开发区","flag":"1","busStationId":"2059"},{"sn":"13","busStationName":"团结闸","flag":"1","busStationId":"2887"},{"sn":"14","busStationName":"庆东站（东）","flag":"1","busStationId":"2879"},{"sn":"15","busStationName":"庆东站","flag":"1","busStationId":"2877"},{"sn":"16","busStationName":"庆东小学","flag":"1","busStationId":"2875"},{"sn":"17","busStationName":"庆桥站（东）","flag":"1","busStationId":"2883"},{"sn":"18","busStationName":"庆桥站","flag":"1","busStationId":"2881"},{"sn":"19","busStationName":"百得医疗","flag":"1","busStationId":"2839"},{"sn":"20","busStationName":"东莱菜场","flag":"1","busStationId":"1912"},{"sn":"21","busStationName":"东莱徐丰小区","flag":"1","busStationId":"2841"},{"sn":"22","busStationName":"张家港电厂","flag":"1","busStationId":"2895"},{"sn":"23","busStationName":"阀门厂","flag":"1","busStationId":"2849"},{"sn":"24","busStationName":"黎明村","flag":"1","busStationId":"2861"},{"sn":"25","busStationName":"华夏集团","flag":"1","busStationId":"2851"},{"sn":"26","busStationName":"骏马工业园","flag":"1","busStationId":"3302"},{"sn":"27","busStationName":"蒋桥","flag":"1","busStationId":"2008"},{"sn":"28","busStationName":"汇金中心","flag":"1","busStationId":"3952"},{"sn":"29","busStationName":"文化中心","flag":"1","busStationId":"1803"},{"sn":"30","busStationName":"勤丰苑","flag":"1","busStationId":"1739"},{"sn":"31","busStationName":"东渡大厦","flag":"1","busStationId":"2000"},{"sn":"32","busStationName":"检察院","flag":"1","busStationId":"3679"},{"sn":"33","busStationName":"电视大学","flag":"1","busStationId":"173"},{"sn":"34","busStationName":"市政府","flag":"1","busStationId":"341"},{"sn":"35","busStationName":"国贸酒店","flag":"1","busStationId":"216"},{"sn":"36","busStationName":"电信大厦","flag":"1","busStationId":"175"},{"sn":"37","busStationName":"滋生桥","flag":"1","busStationId":"3282"},{"sn":"38","busStationName":"慕嘏桥","flag":"1","busStationId":"3447"},{"sn":"39","busStationName":"实验小学","flag":"1","busStationId":"1761"},{"sn":"40","busStationName":"国际购物中心","flag":"1","busStationId":"1618"},{"sn":"41","busStationName":"港城汽车站西","flag":"1","busStationId":"2298"},{"sn":"42","busStationName":"南城花园","flag":"1","busStationId":"2257"},{"sn":"43","busStationName":"湖滨新村","flag":"1","busStationId":"1926"},{"sn":"44","busStationName":"丽景华都","flag":"1","busStationId":"2863"},{"sn":"45","busStationName":"湖东花苑","flag":"1","busStationId":"3948"},{"sn":"46","busStationName":"河西南路","flag":"1","busStationId":"3944"},{"sn":"47","busStationName":"客运站南门","flag":"1","busStationId":"3916"},{"sn":"48","busStationName":"客运站","flag":"2","busStationId":"55"}]
     */

    private ResultEntity result;

    /**
     * result : {"runPathName":"222路夜班","xiaxing":[{"sn":"48","busStationName":"客运站","flag":"2","busStationId":"55"},{"sn":"49","busStationName":"客运站南门","flag":"3","busStationId":"3915"},{"sn":"50","busStationName":"河西南路","flag":"3","busStationId":"3945"},{"sn":"51","busStationName":"湖东花苑","flag":"3","busStationId":"3949"},{"sn":"52","busStationName":"湖滨国际","flag":"3","busStationId":"2379"},{"sn":"53","busStationName":"丽景华都","flag":"3","busStationId":"2864"},{"sn":"54","busStationName":"湖滨新村","flag":"3","busStationId":"1925"},{"sn":"55","busStationName":"南城花园","flag":"3","busStationId":"2258"},{"sn":"56","busStationName":"港城汽车站西","flag":"3","busStationId":"2299"},{"sn":"57","busStationName":"国际购物中心","flag":"3","busStationId":"1619"},{"sn":"58","busStationName":"实验小学","flag":"3","busStationId":"1762"},{"sn":"59","busStationName":"慕嘏桥","flag":"3","busStationId":"3450"},{"sn":"60","busStationName":"滋生桥","flag":"3","busStationId":"3283"},{"sn":"61","busStationName":"电信大厦","flag":"3","busStationId":"176"},{"sn":"62","busStationName":"国贸酒店","flag":"3","busStationId":"217"},{"sn":"63","busStationName":"市政府","flag":"3","busStationId":"342"},{"sn":"64","busStationName":"电视大学","flag":"3","busStationId":"174"},{"sn":"65","busStationName":"检察院","flag":"3","busStationId":"3680"},{"sn":"66","busStationName":"东渡大厦","flag":"3","busStationId":"2001"},{"sn":"67","busStationName":"勤丰苑","flag":"3","busStationId":"1740"},{"sn":"68","busStationName":"文化中心","flag":"3","busStationId":"1804"},{"sn":"69","busStationName":"汇金中心","flag":"3","busStationId":"3953"},{"sn":"70","busStationName":"联发汽车城（阳光e驾港城驾校）","flag":"3","busStationId":"3715"},{"sn":"71","busStationName":"蒋桥","flag":"3","busStationId":"2009"},{"sn":"72","busStationName":"骏马工业园","flag":"3","busStationId":"1677"},{"sn":"73","busStationName":"华夏集团","flag":"3","busStationId":"2852"},{"sn":"74","busStationName":"黎明村","flag":"3","busStationId":"2862"},{"sn":"75","busStationName":"阀门厂","flag":"3","busStationId":"2850"},{"sn":"76","busStationName":"张家港电厂","flag":"3","busStationId":"2896"},{"sn":"77","busStationName":"东莱徐丰小区","flag":"3","busStationId":"2842"},{"sn":"78","busStationName":"东莱菜场","flag":"3","busStationId":"1911"},{"sn":"79","busStationName":"百得医疗","flag":"3","busStationId":"2840"},{"sn":"80","busStationName":"庆桥站","flag":"3","busStationId":"2882"},{"sn":"81","busStationName":"庆桥站（东）","flag":"3","busStationId":"2884"},{"sn":"82","busStationName":"庆东小学","flag":"3","busStationId":"2876"},{"sn":"83","busStationName":"庆东站","flag":"3","busStationId":"2878"},{"sn":"84","busStationName":"庆东站（东）","flag":"3","busStationId":"2880"},{"sn":"85","busStationName":"团结闸","flag":"3","busStationId":"2888"},{"sn":"86","busStationName":"南丰开发区","flag":"3","busStationId":"2062"},{"sn":"87","busStationName":"海新南路","flag":"3","busStationId":"2035"},{"sn":"88","busStationName":"南丰中学","flag":"3","busStationId":"2067"},{"sn":"89","busStationName":"兴园路","flag":"3","busStationId":"2092"},{"sn":"90","busStationName":"海坝村委","flag":"3","busStationId":"2031"},{"sn":"91","busStationName":"南丰农商行","flag":"3","busStationId":"2064"},{"sn":"92","busStationName":"南丰中行","flag":"3","busStationId":"1949"},{"sn":"93","busStationName":"南丰镇政府","flag":"3","busStationId":"2066"},{"sn":"94","busStationName":"南丰幼儿园","flag":"3","busStationId":"2872"},{"sn":"95","busStationName":"南丰小学","flag":"3","busStationId":"2870"},{"sn":"96","busStationName":"新丰小区西门","flag":"3","busStationId":"2890"},{"sn":"97","busStationName":"南丰客运站","flag":"4","busStationId":"2869"}],"runPathId":"143","roundRunPath":"1","shangxing":[{"sn":"1","busStationName":"南丰客运站","flag":"0","busStationId":"2869"},{"sn":"2","busStationName":"新丰小区西门","flag":"1","busStationId":"2889"},{"sn":"3","busStationName":"南丰小学","flag":"1","busStationId":"604"},{"sn":"4","busStationName":"南丰幼儿园","flag":"1","busStationId":"2871"},{"sn":"5","busStationName":"南丰镇政府","flag":"1","busStationId":"2065"},{"sn":"6","busStationName":"南丰中行","flag":"1","busStationId":"1948"},{"sn":"7","busStationName":"南丰农商行","flag":"1","busStationId":"2063"},{"sn":"8","busStationName":"海坝村委","flag":"1","busStationId":"2030"},{"sn":"9","busStationName":"兴园路","flag":"1","busStationId":"2091"},{"sn":"10","busStationName":"南丰中学","flag":"1","busStationId":"605"},{"sn":"11","busStationName":"海新南路","flag":"1","busStationId":"2034"},{"sn":"12","busStationName":"南丰开发区","flag":"1","busStationId":"2059"},{"sn":"13","busStationName":"团结闸","flag":"1","busStationId":"2887"},{"sn":"14","busStationName":"庆东站（东）","flag":"1","busStationId":"2879"},{"sn":"15","busStationName":"庆东站","flag":"1","busStationId":"2877"},{"sn":"16","busStationName":"庆东小学","flag":"1","busStationId":"2875"},{"sn":"17","busStationName":"庆桥站（东）","flag":"1","busStationId":"2883"},{"sn":"18","busStationName":"庆桥站","flag":"1","busStationId":"2881"},{"sn":"19","busStationName":"百得医疗","flag":"1","busStationId":"2839"},{"sn":"20","busStationName":"东莱菜场","flag":"1","busStationId":"1912"},{"sn":"21","busStationName":"东莱徐丰小区","flag":"1","busStationId":"2841"},{"sn":"22","busStationName":"张家港电厂","flag":"1","busStationId":"2895"},{"sn":"23","busStationName":"阀门厂","flag":"1","busStationId":"2849"},{"sn":"24","busStationName":"黎明村","flag":"1","busStationId":"2861"},{"sn":"25","busStationName":"华夏集团","flag":"1","busStationId":"2851"},{"sn":"26","busStationName":"骏马工业园","flag":"1","busStationId":"3302"},{"sn":"27","busStationName":"蒋桥","flag":"1","busStationId":"2008"},{"sn":"28","busStationName":"汇金中心","flag":"1","busStationId":"3952"},{"sn":"29","busStationName":"文化中心","flag":"1","busStationId":"1803"},{"sn":"30","busStationName":"勤丰苑","flag":"1","busStationId":"1739"},{"sn":"31","busStationName":"东渡大厦","flag":"1","busStationId":"2000"},{"sn":"32","busStationName":"检察院","flag":"1","busStationId":"3679"},{"sn":"33","busStationName":"电视大学","flag":"1","busStationId":"173"},{"sn":"34","busStationName":"市政府","flag":"1","busStationId":"341"},{"sn":"35","busStationName":"国贸酒店","flag":"1","busStationId":"216"},{"sn":"36","busStationName":"电信大厦","flag":"1","busStationId":"175"},{"sn":"37","busStationName":"滋生桥","flag":"1","busStationId":"3282"},{"sn":"38","busStationName":"慕嘏桥","flag":"1","busStationId":"3447"},{"sn":"39","busStationName":"实验小学","flag":"1","busStationId":"1761"},{"sn":"40","busStationName":"国际购物中心","flag":"1","busStationId":"1618"},{"sn":"41","busStationName":"港城汽车站西","flag":"1","busStationId":"2298"},{"sn":"42","busStationName":"南城花园","flag":"1","busStationId":"2257"},{"sn":"43","busStationName":"湖滨新村","flag":"1","busStationId":"1926"},{"sn":"44","busStationName":"丽景华都","flag":"1","busStationId":"2863"},{"sn":"45","busStationName":"湖东花苑","flag":"1","busStationId":"3948"},{"sn":"46","busStationName":"河西南路","flag":"1","busStationId":"3944"},{"sn":"47","busStationName":"客运站南门","flag":"1","busStationId":"3916"},{"sn":"48","busStationName":"客运站","flag":"2","busStationId":"55"}]}
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
        private String runPathName;
        private String runPathId;
        private String roundRunPath;
        /**
         * sn : 48
         * busStationName : 客运站
         * flag : 2
         * busStationId : 55
         */

        private List<StationEntity> xiaxing;
        /**
         * sn : 1
         * busStationName : 南丰客运站
         * flag : 0
         * busStationId : 2869
         */

        private List<StationEntity> shangxing;

        public void setRunPathName(String runPathName) {
            this.runPathName = runPathName;
        }

        public void setRunPathId(String runPathId) {
            this.runPathId = runPathId;
        }

        public void setRoundRunPath(String roundRunPath) {
            this.roundRunPath = roundRunPath;
        }

        public void setXiaxing(List<StationEntity> xiaxing) {
            this.xiaxing = xiaxing;
        }

        public void setShangxing(List<StationEntity> shangxing) {
            this.shangxing = shangxing;
        }

        public String getRunPathName() {
            return runPathName;
        }

        public String getRunPathId() {
            return runPathId;
        }

        public String getRoundRunPath() {
            return roundRunPath;
        }

        public List<StationEntity> getXiaxing() {
            return xiaxing;
        }

        public List<StationEntity> getShangxing() {
            return shangxing;
        }

        public static class StationEntity {
            private String sn;
            private String busStationName;
            private String flag;
            private String busStationId;

            public void setSn(String sn) {
                this.sn = sn;
            }

            public void setBusStationName(String busStationName) {
                this.busStationName = busStationName;
            }

            public void setFlag(String flag) {
                this.flag = flag;
            }

            public void setBusStationId(String busStationId) {
                this.busStationId = busStationId;
            }

            public String getSn() {
                return sn;
            }

            public String getBusStationName() {
                return busStationName;
            }

            public String getFlag() {
                return flag;
            }

            public String getBusStationId() {
                return busStationId;
            }
        }
    }
}
