package me.fattycat.kun.bustimer.ui.search;

import me.fattycat.kun.bustimer.data.model.SearchModel;
import me.fattycat.kun.bustimer.ui.base.BasePresenter;
import me.fattycat.kun.bustimer.ui.base.BaseView;

/**
 * Author: qk329
 * Date: 2016/12/15
 */

public interface SearchContract {

    interface View extends BaseView<Presenter> {

        void onSearchStart();

        void onSearchSuccess(SearchModel searchModel);

        void onSearchError();

    }

    interface Presenter extends BasePresenter {
        void searchByName(String name);
    }
}
