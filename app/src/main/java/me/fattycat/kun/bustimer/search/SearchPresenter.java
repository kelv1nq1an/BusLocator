package me.fattycat.kun.bustimer.search;

import me.fattycat.kun.bustimer.Http.BusTimerRetrofit;
import me.fattycat.kun.bustimer.model.LineEntity;
import me.fattycat.kun.bustimer.model.LineListEntity;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Author: Kelvinkun
 * Date: 16/7/7
 */

public class SearchPresenter implements SearchContract.Presenter {

    private final SearchContract.View searchView;

    private LinesSearchSubscriber.LinesSearchOnNextListener linesSearchOnNextListener;
    private LineInfoSubscriber.LinesInfoOnNextListener linesInfoOnNextListener;
    private CompositeSubscription subscription;

    private Subscription busLineSubscription;

    public SearchPresenter(SearchContract.View searchView) {
        this.searchView = searchView;
        this.searchView.setPresenter(this);

        this.subscription = new CompositeSubscription();

        linesSearchOnNextListener = new LinesSearchSubscriber.LinesSearchOnNextListener() {
            @Override
            public void onNext(LineListEntity lineListEntity) {
                if (lineListEntity == null || lineListEntity.getResult() == null || lineListEntity.getResult().getLines() == null) {
                    SearchPresenter.this.searchView.onLinesSearchFailed();
                    return;
                }
                SearchPresenter.this.searchView.onLinesSearchSuccess(lineListEntity.getResult().getLines());
            }

            @Override
            public void onComplete() {
                // TODO: 16/7/8
            }

            @Override
            public void onError() {
                SearchPresenter.this.searchView.onLinesSearchFailed();
            }
        };

        linesInfoOnNextListener = new LineInfoSubscriber.LinesInfoOnNextListener() {
            @Override
            public void onNext(LineEntity lineEntity) {
                SearchPresenter.this.searchView.onLineInfoSearchSuccess(lineEntity);
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError() {
                SearchPresenter.this.searchView.onLineInfoSearchFailed();
            }
        };
    }

    @Override
    public void searchBusLine(String lineName) {
        if (busLineSubscription != null) {
            subscription.remove(busLineSubscription);
            busLineSubscription.unsubscribe();
        }
        busLineSubscription = BusTimerRetrofit.getInstance().searchBusLine(new LinesSearchSubscriber(linesSearchOnNextListener), lineName);
        subscription.add(busLineSubscription);
    }

    @Override
    public void searchLineInfo(String rpid, String flag) {
        Subscription lineInfoSubscription = BusTimerRetrofit.getInstance().searchLineInfo(new LineInfoSubscriber(linesInfoOnNextListener), rpid, flag);
        subscription.add(lineInfoSubscription);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        subscription.clear();
    }
}
