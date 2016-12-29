package me.fattycat.kun.bustimer.data.source.remote;

import me.fattycat.kun.bustimer.data.model.BusGPSModel;
import me.fattycat.kun.bustimer.data.model.LineInfoModel;
import me.fattycat.kun.bustimer.data.model.ModelWrapper;
import me.fattycat.kun.bustimer.data.model.SearchModel;
import me.fattycat.kun.bustimer.data.model.StationModel;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Author: qk329
 * Date: 2016/12/14
 */

public interface ApiService {

    /**
     * 搜索匹配输入内容的线路
     *
     * @param name 线路名称
     */
    @GET("bus/allStationOfRPName")
    Observable<ModelWrapper<SearchModel>> getLines(@Query("name") String name);

    /**
     * 搜索指定线路指定方向上在线的公交信息
     *
     * @param rpid 线路id
     * @param flag 线路上下行
     */
    @GET("bus/gpsForRPF")
    Observable<ModelWrapper<BusGPSModel>> getLineGPS(@Query("rpId") String rpid, @Query("flag") String flag);

    /**
     * 搜索指定线路指定方向的运营信息
     *
     * @param runPathId 线路id
     */
    @GET("common/busQuery")
    Observable<ModelWrapper<LineInfoModel>> getLineInfo(@Query("runPathId") String runPathId);

    /**
     * 搜索指定线路上的公交站台
     *
     * @param rpid 线路id
     */
    @GET("bus/searchSSR")
    Observable<ModelWrapper<StationModel>> getStations(@Query("rpId") String rpid);
}
