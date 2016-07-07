package me.fattycat.kun.bustimer;

import me.fattycat.kun.buslocator.model.BusGPSEntity;
import me.fattycat.kun.buslocator.model.LineEntity;
import me.fattycat.kun.bustimer.model.LineListEntity;
import me.fattycat.kun.buslocator.model.StationListEntity;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Author: Kelvinkun
 * Date: 16/7/1
 */

public class BusTimerApi {
    public static final String BASE_URL = "http://61.177.44.242:8080/BusSysWebService/";

    public interface Bus {
        // 搜索匹配输入内容的线路
        @POST("bus/allStationOfRPName")
        Call<LineListEntity> line(@Query("name") String name);

        // 搜索指定线路指定方向上在线的公交信息
        @POST("bus/gpsForRPF")
        Call<BusGPSEntity> gps(@Query("rpId") String rpid, @Query("flag") String flag);
    }

    public interface Line {
        // 搜索指定线路指定方向的运营信息
        @POST("common/busQuery")
        Call<LineEntity> info(@Query("runPathId") String runPathId, @Query("flag") String flag);

        // 搜索指定线路上的公交站台
        @POST("bus/searchSSR")
        Call<StationListEntity> station(@Query("rpId") String rpid);
    }
}
