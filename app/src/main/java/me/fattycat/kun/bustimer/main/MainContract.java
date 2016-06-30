package me.fattycat.kun.bustimer.main;

import me.fattycat.kun.bustimer.BasePresenter;
import me.fattycat.kun.bustimer.BaseView;

/**
 * Author: Kelvinkun
 * Date: 16/6/30
 */

public interface MainContract {
    interface MainView extends BaseView<MainPresenter> {

    }

    interface MainPresenter extends BasePresenter {
    }
}
