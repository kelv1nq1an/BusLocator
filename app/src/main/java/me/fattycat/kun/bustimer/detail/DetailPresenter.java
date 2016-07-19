package me.fattycat.kun.bustimer.detail;

import me.fattycat.kun.bustimer.Http.BusTimerRetrofit;
import me.fattycat.kun.bustimer.model.StationListEntity;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Author: Kelvinkun
 * Date: 16/7/11
 */

public class DetailPresenter implements DetailContract.Presenter {
    private DetailContract.View detailView;

    private LineStationSubscriber.LineStationListener lineStationListener;
    private CompositeSubscription subscription;

    public DetailPresenter(DetailContract.View view) {
        this.detailView = view;
        this.detailView.setPresenter(this);
        this.subscription = new CompositeSubscription();

        lineStationListener = new LineStationSubscriber.LineStationListener() {
            @Override
            public void onNext(StationListEntity stationListEntity) {
                DetailPresenter.this.detailView.onStationLoaded(stationListEntity);
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError() {
                DetailPresenter.this.detailView.onStationLoadFailed();
            }
        };
    }

    @Override
    public void getLineStations(String rpid, String flag) {
        Subscription lineStationSubscription = BusTimerRetrofit.getInstance()
                .getLineStations(new LineStationSubscriber(lineStationListener), rpid);
        subscription.add(lineStationSubscription);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        subscription.clear();
    }
}
