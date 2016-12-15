package me.fattycat.kun.bustimer.about;

import me.fattycat.kun.bustimer.ui.base.BasePresenter;
import me.fattycat.kun.bustimer.ui.base.BaseView;
import me.fattycat.kun.bustimer.model.AppInfoEntity;

/**
 * Author: Kelvinkun
 * Date: 16/6/30
 */

public interface AboutContract {
    interface View extends BaseView<Presenter> {
        void  onGetLatestVersion(AppInfoEntity appVersion);
    }

    interface Presenter extends BasePresenter {
        void getLatestVersion();
    }
}
