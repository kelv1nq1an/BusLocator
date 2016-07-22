package me.fattycat.kun.bustimer;

import android.app.Application;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;

/**
 * Author: Kelvinkun
 * Date: 16/7/7
 */

public class BusTimer extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this, LCInfo.APP_ID, LCInfo.APP_KEY);
        AVAnalytics.enableCrashReport(this, true);
    }
}
