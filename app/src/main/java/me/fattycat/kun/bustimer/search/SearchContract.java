package me.fattycat.kun.bustimer.search;

import java.util.List;

import me.fattycat.kun.bustimer.BasePresenter;
import me.fattycat.kun.bustimer.BaseView;
import me.fattycat.kun.bustimer.model.LineEntity;
import me.fattycat.kun.bustimer.model.LineListEntity;

/**
 * Author: Kelvinkun
 * Date: 16/6/30
 */

public interface SearchContract {
    interface View extends BaseView<Presenter> {
        void onLinesSearchSuccess(List<LineListEntity.ResultEntity.LinesEntity> lines);

        void onLinesSearchFailed();

        void onLineInfoGoSearchSuccess(LineEntity line);

        void onLineInfoBackSearchSuccess(LineEntity line);

        void onLineInfoSearchFailed();
    }

    interface Presenter extends BasePresenter {
        void searchBusLine(String lineName);

        void searchLineInfo(String rpid, String flag);
    }
}
