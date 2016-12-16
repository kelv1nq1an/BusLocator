package me.fattycat.kun.bustimer.ui.detail;

import me.fattycat.kun.bustimer.data.model.BusGPSModel;
import me.fattycat.kun.bustimer.data.model.LineInfoModel;
import me.fattycat.kun.bustimer.data.model.StationModel;
import me.fattycat.kun.bustimer.data.source.BusRepository;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Author: qk329
 * Date: 2016/12/16
 */

public class DetailPresenter implements DetailContract.Presenter {

    private DetailContract.View   view;
    private CompositeSubscription compositeSubscription;

    public DetailPresenter(DetailContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void getLineInfo(String runPathID) {
        Subscription subscription = BusRepository.getInstance().getLineInfo(runPathID)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Subscriber<LineInfoModel>() {
                                                @Override
                                                public void onCompleted() {

                                                }

                                                @Override
                                                public void onError(Throwable e) {
                                                    view.onGetLineInfoError();
                                                }

                                                @Override
                                                public void onNext(LineInfoModel lineInfoModel) {
                                                    view.onGetLineInfoSuccess(lineInfoModel);
                                                }
                                            });
        compositeSubscription.add(subscription);
    }

    @Override
    public void getStations(String runPathID) {
        Subscription subscription = BusRepository.getInstance().getStations(runPathID)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Subscriber<StationModel>() {
                                                @Override
                                                public void onCompleted() {

                                                }

                                                @Override
                                                public void onError(Throwable e) {
                                                    view.onGetStationsError();
                                                }

                                                @Override
                                                public void onNext(StationModel stationModel) {
                                                    view.onGetStationsSuccess(stationModel);
                                                }
                                            });
        compositeSubscription.add(subscription);
    }

    @Override
    public void getBusGPS(String runPathID, String flag) {
        Subscription subscription = BusRepository.getInstance().getBusGPS(runPathID, flag)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Subscriber<BusGPSModel>() {
                                                @Override
                                                public void onCompleted() {

                                                }

                                                @Override
                                                public void onError(Throwable e) {
                                                    view.onGetBusGPSError();
                                                }

                                                @Override
                                                public void onNext(BusGPSModel busGPSModel) {
                                                    view.onGetBusGPSSuccess(busGPSModel);
                                                }
                                            });
        compositeSubscription.add(subscription);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        this.view = null;
        compositeSubscription.clear();
    }
}
