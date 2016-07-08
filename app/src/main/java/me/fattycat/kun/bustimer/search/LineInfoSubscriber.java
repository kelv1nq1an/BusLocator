package me.fattycat.kun.bustimer.search;

import me.fattycat.kun.bustimer.model.LineEntity;
import rx.Subscriber;

/**
 * Author: Kelvinkun
 * Date: 16/7/8
 */

public class LineInfoSubscriber extends Subscriber<LineEntity> {

    private LinesInfoOnNextListener listener;

    LineInfoSubscriber(LinesInfoOnNextListener listener) {
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
    public void onNext(LineEntity lineEntity) {
        if (listener != null) {
            listener.onNext(lineEntity);
        }
    }

    interface LinesInfoOnNextListener {
        void onNext(LineEntity lineEntity);

        void onComplete();

        void onError();
    }
}
