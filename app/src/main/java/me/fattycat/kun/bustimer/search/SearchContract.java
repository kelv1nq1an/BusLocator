package me.fattycat.kun.bustimer.search;

import java.util.List;

import me.fattycat.kun.bustimer.model.LineListEntity;
import me.fattycat.kun.bustimer.BasePresenter;
import me.fattycat.kun.bustimer.BaseView;

/**
 * Author: Kelvinkun
 * Date: 16/6/30
 */

public interface SearchContract {
    interface View extends BaseView<Presenter> {
        void onLinesSearchSuccess(List<LineListEntity.ResultEntity.LinesEntity> lines);
    }

    interface Presenter extends BasePresenter {
        void searchBusLine(String oldString, String newString);
    }
}
