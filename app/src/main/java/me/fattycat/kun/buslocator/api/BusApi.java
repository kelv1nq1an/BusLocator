package me.fattycat.kun.buslocator.api;

import me.fattycat.kun.buslocator.model.BusGPSEntity;
import me.fattycat.kun.buslocator.model.LineEntity;
import me.fattycat.kun.buslocator.model.LineListEntity;
import me.fattycat.kun.buslocator.model.StationListEntity;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Author: Kelvinkun
 * Time: 16/1/27
 * Description: All api information of buses in ZhangJiaGang.
 */
public class BusApi {
    public static final String BASE_URL = "http://61.177.44.242:8080/BusSysWebService/";

    public interface BusLineList {
        @POST("bus/allStationOfRPName")
        Call<LineListEntity> lineList(@Query("name") String name);
    }

    public interface BusLine {
        @POST("common/busQuery")
        Call<LineEntity> line(@Query("runPathId") String runPathId, @Query("flag") String flag);
    }

    public interface AllStation {
        @POST("bus/searchSSR")
        Call<StationListEntity> stationList(@Query("rpId") String rpid);
    }

    public interface BusGPS {
        @POST("bus/gpsForRPF")
        Call<BusGPSEntity> GPS(@Query("rpId") String rpid, @Query("flag") String flag);
    }

    // TODO: 16/1/27 NearbyStations
}
