package me.fattycat.kun.bustimer.ui.main;

import java.util.List;

import me.fattycat.kun.bustimer.data.entity.FavouriteEntity;
import me.fattycat.kun.bustimer.data.source.BusRepository;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Author: qk329
 * Date: 2016/12/14
 */

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View     view;
    private CompositeSubscription compositeSubscription;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void getFavouriteList() {
        Subscription subscription = BusRepository.getInstance().getFavouriteLines()
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Subscriber<List<FavouriteEntity>>() {
                                                @Override
                                                public void onCompleted() {

                                                }

                                                @Override
                                                public void onError(Throwable e) {
                                                    view.onFavouriteLoadError();
                                                }

                                                @Override
                                                public void onNext(List<FavouriteEntity> favouriteEntities) {
                                                    view.onFavouriteLoadSuccess(favouriteEntities);
                                                }
                                            });
        compositeSubscription.add(subscription);
    }

    @Override
    public void subscribe() {
        getFavouriteList();
    }

    @Override
    public void unSubscribe() {
        view = null;
        compositeSubscription.clear();
    }
}
