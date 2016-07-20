package me.fattycat.kun.bustimer.Http;

import me.fattycat.kun.bustimer.model.BusGPSEntity;
import me.fattycat.kun.bustimer.model.LineEntity;
import me.fattycat.kun.bustimer.model.LineListEntity;
import me.fattycat.kun.bustimer.model.StationListEntity;
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
                .baseUrl(BusTimerApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        busService = busTimerRetrofit.create(BusTimerApi.Bus.class);
        lineService = busTimerRetrofit.create(BusTimerApi.Line.class);
    }

    private static class SingletonHolder {
        private static final BusTimerRetrofit INSTANCE = new BusTimerRetrofit();
    }

    public static BusTimerRetrofit getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Subscription searchBusLine(Subscriber<LineListEntity> subscriber, String lineName) {
        Observable observable = busService.line(lineName);
        return toSubscribe(observable, subscriber);
    }

    public Subscription searchLineInfo(Subscriber<LineEntity> subscriber, String rpid, String flag) {
        Observable observable = lineService.info(rpid, flag);
        return toSubscribe(observable, subscriber);
    }

    public Subscription getLineStations(Subscriber<StationListEntity> subscriber, String rpid) {
        Observable observable = lineService.station(rpid);
        return toSubscribe(observable, subscriber);
    }

    public Subscription getBusGps(Subscriber<BusGPSEntity> subscriber, String rpid, String flag) {
        Observable observable = busService.gps(rpid, flag);
        return toSubscribe(observable, subscriber);
    }

    private <T> Subscription toSubscribe(Observable<T> o, Subscriber<T> s) {
        return o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }
}
