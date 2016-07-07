package me.fattycat.kun.bustimer;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author: Kelvinkun
 * Date: 16/7/7
 */

public class BusTimerRetrofit {
    private static Retrofit busTimerRetrofit = null;

    public static Retrofit getInstance() {

        if (busTimerRetrofit == null) {
            busTimerRetrofit = new Retrofit.Builder()
                    .baseUrl(BusTimerApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return busTimerRetrofit;
    }
}
