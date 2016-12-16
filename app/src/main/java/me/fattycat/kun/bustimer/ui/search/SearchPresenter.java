package me.fattycat.kun.bustimer.ui.search;

import me.fattycat.kun.bustimer.data.model.SearchModel;
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

public class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View   view;
    private CompositeSubscription compositeSubscription;

    public SearchPresenter(SearchContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void searchByName(String name) {
        compositeSubscription.clear();
        Subscription subscription = BusRepository.getInstance().searchByName(name)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Subscriber<SearchModel>() {

                                                @Override
                                                public void onStart() {
                                                    view.onSearchStart();
                                                }

                                                @Override
                                                public void onCompleted() {

                                                }

                                                @Override
                                                public void onError(Throwable e) {
                                                    view.onSearchError();
                                                }

                                                @Override
                                                public void onNext(SearchModel searchModel) {
                                                    view.onSearchSuccess(searchModel);
                                                }
                                            });
        compositeSubscription.add(subscription);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        view = null;
        compositeSubscription.clear();
    }
}
