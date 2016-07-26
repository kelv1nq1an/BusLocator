package me.fattycat.kun.bustimer.detail;

import me.fattycat.kun.bustimer.model.BusGPSEntity;
import rx.Subscriber;

/**
 * Author: Kelvinkun
 * Date: 16/7/20
 */

public class BusGpsSubscriber extends Subscriber<BusGPSEntity> {

    private BusGpsListener listener;

    BusGpsSubscriber(BusGpsListener busGpsListener) {
        this.listener = busGpsListener;
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
    public void onNext(BusGPSEntity busGPSEntity) {
        if (listener != null) {
            listener.onNext(busGPSEntity);
        }
    }

    interface BusGpsListener {
        void onNext(BusGPSEntity busGPSEntity);

        void onComplete();

        void onError();
    }
}
