package me.fattycat.kun.bustimer.Http;

import me.fattycat.kun.buslocator.model.BusGPSEntity;
import me.fattycat.kun.bustimer.model.LineEntity;
import me.fattycat.kun.bustimer.model.StationListEntity;
import me.fattycat.kun.bustimer.model.LineListEntity;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Author: Kelvinkun
 * Date: 16/7/1
 */

public class BusTimerApi {
    public static final String FLAG_LINE_GO = "1";
    public static final String FLAG_LINE_BACK = "2";
    public static final String FLAG_BUS_GO = "1";
    public static final String FLAG_BUS_BACK = "3";
    public static final String BASE_URL = "http://61.177.44.242:8080/BusSysWebService/";

    public interface Bus {

        /**
         * 搜索匹配输入内容的线路
         *
         * @param name 线路名称
         */
        @POST("bus/allStationOfRPName")
        Observable<LineListEntity> line(@Query("name") String name);

        /**
         * 搜索指定线路指定方向上在线的公交信息
         *
         * @param rpid 线路id
         * @param flag 线路上下行
         */
        @POST("bus/gpsForRPF")
        Observable<BusGPSEntity> gps(@Query("rpId") String rpid, @Query("flag") String flag);
    }

    public interface Line {
        /**
         * 搜索指定线路指定方向的运营信息
         *
         * @param runPathId 线路id
         * @param flag      线路上下行
         */
        @POST("common/busQuery")
        Observable<LineEntity> info(@Query("runPathId") String runPathId, @Query("flag") String flag);

        /**
         * 搜索指定线路上的公交站台
         *
         * @param rpid 线路id
         */
        @POST("bus/searchSSR")
        Observable<StationListEntity> station(@Query("rpId") String rpid);
    }
}
