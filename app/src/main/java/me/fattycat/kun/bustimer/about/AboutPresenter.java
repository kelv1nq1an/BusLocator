package me.fattycat.kun.bustimer.about;

import android.util.Log;

import im.fir.sdk.FIR;
import im.fir.sdk.VersionCheckCallback;

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
        FIR.checkForUpdateInAppStore(new VersionCheckCallback() {
                                         @Override
                                         public void onSuccess(String versionJson) {
                                             // TODO: 16/7/26 检查返回值
                                             Log.i("233333", versionJson);
                                             aboutView.onGetLatestVersion(versionJson);
                                         }

                                         @Override
                                         public void onFail(Exception exception) {

                                         }

                                         @Override
                                         public void onStart() {

                                         }

                                         @Override
                                         public void onFinish() {

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
