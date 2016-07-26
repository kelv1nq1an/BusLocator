package me.fattycat.kun.bustimer;

import android.app.Application;
import com.umeng.analytics.MobclickAgent;
import im.fir.sdk.FIR;

/**
 * Author: Kelvinkun
 * Date: 16/7/7
 */

public class BusTimer extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FIR.init(this, AppSecret.BUGHD_APP_KEY);
        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this, AppSecret.UEMNG_APP_KEY, "fir"));
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setCatchUncaughtExceptions(false);
    }
}
