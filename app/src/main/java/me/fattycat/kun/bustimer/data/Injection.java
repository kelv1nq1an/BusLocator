package me.fattycat.kun.bustimer.data;

import android.content.Context;

import me.fattycat.kun.bustimer.BusTimer;
import me.fattycat.kun.bustimer.data.source.remote.ApiService;
import me.fattycat.kun.bustimer.network.RetrofitClient;

/**
 * Author: Kelvinkun
 * Date: 16/8/29
 */

public class Injection {
    public static Context provideContext() {
        return BusTimer.getInstance();
    }

    public static ApiService provideRestfulApi() {
        return RetrofitClient.defaultInstance().create(ApiService.class);
    }
}
