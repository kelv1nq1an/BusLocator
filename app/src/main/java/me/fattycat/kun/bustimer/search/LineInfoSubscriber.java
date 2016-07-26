package me.fattycat.kun.bustimer.search;

import me.fattycat.kun.bustimer.model.LineEntity;
import rx.Subscriber;

/**
 * Author: Kelvinkun
 * Date: 16/7/8
 */

public class LineInfoSubscriber extends Subscriber<LineEntity> {

    private LinesInfoOnNextListener listener;
    private String flag;

    LineInfoSubscriber(LinesInfoOnNextListener listener, String flag) {
        this.listener = listener;
        this.flag = flag;
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
            listener.onNext(lineEntity, flag);
        }
    }

    interface LinesInfoOnNextListener {
        void onNext(LineEntity lineEntity, String flag);

        void onComplete();

        void onError();
    }
}
