package me.fattycat.kun.bustimer;

import android.app.Application;

import im.fir.sdk.FIR;

/**
 * Author: Kelvinkun
 * Date: 16/7/7
 */

public class BusTimer extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FIR.init(this);
    }
}
