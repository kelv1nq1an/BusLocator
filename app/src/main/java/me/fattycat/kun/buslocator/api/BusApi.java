/*
 * Copyright (C) 2016 FattycatR<kelv1nq1an>
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
