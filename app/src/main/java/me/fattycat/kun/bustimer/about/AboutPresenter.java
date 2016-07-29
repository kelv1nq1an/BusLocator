package me.fattycat.kun.bustimer.about;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import im.fir.sdk.FIR;
import im.fir.sdk.VersionCheckCallback;
import me.fattycat.kun.bustimer.AppSecret;
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
        FIR.checkForUpdateInFIR(AppSecret.FIR_APP_KEY, new VersionCheckCallback() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            AppInfoEntity appInfo = new Gson().fromJson(s, AppInfoEntity.class);
                            aboutView.onGetLatestVersion(appInfo);
                        } catch (JsonSyntaxException e) {
                            Log.i("getVersion", "Gson convert error");
                        }
                    }
                }
        );
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
