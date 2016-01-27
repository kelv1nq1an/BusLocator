package me.fattycat.kun.buslocator.api;

import me.fattycat.kun.buslocator.model.Line;
import me.fattycat.kun.buslocator.model.LineList;
import me.fattycat.kun.buslocator.model.StationList;
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
        Call<LineList> lineList(@Query("name") String name);
    }

    public interface BusLine {
        @POST("common/busQuery")
        Call<Line> line(@Query("runPathId") String runPathId, @Query("flag") String flag);
    }

    public interface AllStation {
        @POST("bus/searchSSR")
        Call<StationList> stationList(@Query("rpId") String rpid);
    }

    public interface BusGPS {
        @POST("bus/gpsForRPF")
        Call<StationList> stationList(@Query("rpId") String rpid, @Query("flag") String flag);
    }

    // TODO: 16/1/27 NearbyStations
}
