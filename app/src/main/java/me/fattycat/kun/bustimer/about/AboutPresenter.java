package me.fattycat.kun.bustimer.about;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import me.fattycat.kun.bustimer.model.AppInfoEntity;

/**
 * Author: Kelvinkun
 * Date: 16/7/26
 */

class AboutPresenter implements AboutContract.Presenter {
    private AboutContract.View aboutView;

    AboutPresenter(AboutContract.View view) {
        this.aboutView = view;
        this.aboutView.setPresenter(this);
    }

    @Override
    public void getLatestVersion() {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
