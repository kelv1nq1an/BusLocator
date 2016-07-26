package me.fattycat.kun.bustimer.detail;

import me.fattycat.kun.bustimer.model.StationListEntity;
import rx.Subscriber;

/**
 * Author: Kelvinkun
 * Date: 16/7/19
 */

public class LineStationSubscriber extends Subscriber<StationListEntity> {

    private LineStationListener listener;

    LineStationSubscriber(LineStationListener lineStationListener) {
        this.listener = lineStationListener;
    }

    @Override
    public void onCompleted() {
        if (listener != null) {
            listener.onComplete();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (listener != null) {
            listener.onError();
        }
    }

    @Override
    public void onNext(StationListEntity stationListEntity) {
        if (listener != null) {
            listener.onNext(stationListEntity);
        }
    }

    interface LineStationListener {
        void onNext(StationListEntity stationListEntity);

        void onComplete();

        void onError();
    }
}
