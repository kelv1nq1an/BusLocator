package me.fattycat.kun.bustimer.detail;

/**
 * Author: Kelvinkun
 * Date: 16/7/11
 */

public class DetailPresenter implements DetailContract.Presenter {
    private DetailContract.View detailView;

    public DetailPresenter(DetailContract.View view) {
        this.detailView = view;
        this.detailView.setPresenter(this);

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
