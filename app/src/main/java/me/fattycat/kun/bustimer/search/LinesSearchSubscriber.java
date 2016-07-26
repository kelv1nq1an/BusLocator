package me.fattycat.kun.bustimer.search;

import me.fattycat.kun.bustimer.model.LineListEntity;
import rx.Subscriber;

/**
 * Author: Kelvinkun
 * Date: 16/7/8
 */

class LinesSearchSubscriber extends Subscriber<LineListEntity> {

    private LinesSearchOnNextListener listener;

    LinesSearchSubscriber(LinesSearchOnNextListener listener) {
        this.listener = listener;
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
    public void onNext(LineListEntity lineListEntity) {
        if (listener != null) {
            listener.onNext(lineListEntity);
        }
    }

    interface LinesSearchOnNextListener {
        void onNext(LineListEntity lineListEntity);

        void onComplete();

        void onError();
    }
}
