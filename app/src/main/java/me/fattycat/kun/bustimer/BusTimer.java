package me.fattycat.kun.bustimer;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.tencent.bugly.Bugly;

/**
 * Author: Kelvinkun
 * Date: 16/7/7
 */

public class BusTimer extends Application {
    private static BusTimer instance;

    public static BusTimer getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Bugly.init(getApplicationContext(), "85582745fd", false);
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }
}
