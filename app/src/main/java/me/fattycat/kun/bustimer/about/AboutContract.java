package me.fattycat.kun.bustimer.about;

import me.fattycat.kun.bustimer.BasePresenter;
import me.fattycat.kun.bustimer.BaseView;

/**
 * Author: Kelvinkun
 * Date: 16/6/30
 */

public interface AboutContract {
    interface View extends BaseView<Presenter> {
        void  onGetLatestVersion(String appVersion);
    }

    interface Presenter extends BasePresenter {
        void getLatestVersion();
    }
}
