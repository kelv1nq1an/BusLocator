package me.fattycat.kun.bustimer.net;

import me.fattycat.kun.bustimer.model.BusGPSEntity;
import me.fattycat.kun.bustimer.model.LineEntity;
import me.fattycat.kun.bustimer.model.LineListEntity;
import me.fattycat.kun.bustimer.model.StationListEntity;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: Kelvinkun
 * Date: 16/7/7
 */

public class BusTimerRetrofit {
    private BusTimerApi.Bus busService;
    private BusTimerApi.Line lineService;

    private BusTimerRetrofit() {
        Retrofit busTimerRetrofit = new Retrofit.Builder()
                .client(getClient())
                .baseUrl(BusTimerApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        busService = busTimerRetrofit.create(BusTimerApi.Bus.class);
        lineService = busTimerRetrofit.create(BusTimerApi.Line.class);
    }

    private OkHttpClient getClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
    }

    private static class SingletonHolder {
        private static final BusTimerRetrofit INSTANCE = new BusTimerRetrofit();
    }

    public static BusTimerRetrofit getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Subscription searchBusLine(Subscriber<LineListEntity> subscriber, String lineName) {
        Observable<LineListEntity> observable = busService.line(lineName);
        return toSubscribe(observable, subscriber);
    }

    public Subscription searchLineInfo(Subscriber<LineEntity> subscriber, String rpid, String flag) {
        Observable<LineEntity> observable = lineService.info(rpid, flag);
        return toSubscribe(observable, subscriber);
    }

    public Subscription getLineStations(Subscriber<StationListEntity> subscriber, String rpid) {
        Observable<StationListEntity> observable = lineService.station(rpid);
        return toSubscribe(observable, subscriber);
    }

    public Subscription getBusGps(Subscriber<BusGPSEntity> subscriber, String rpid, String flag) {
        Observable<BusGPSEntity> observable = busService.gps(rpid, flag);
        return toSubscribe(observable, subscriber);
    }

    private <T> Subscription toSubscribe(Observable<T> o, Subscriber<T> s) {
        return o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }
}
