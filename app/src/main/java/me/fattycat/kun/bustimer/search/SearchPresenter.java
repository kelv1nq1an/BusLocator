package me.fattycat.kun.bustimer.search;

import me.fattycat.kun.bustimer.model.LineListEntity;
import me.fattycat.kun.bustimer.BusTimerApi;
import me.fattycat.kun.bustimer.BusTimerRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author: Kelvinkun
 * Date: 16/7/7
 */

public class SearchPresenter implements SearchContract.Presenter {

    private final SearchContract.View searchView;
    private Call<LineListEntity> searchLinesCall;

    public SearchPresenter(SearchContract.View searchView) {
        this.searchView = searchView;
        this.searchView.setPresenter(this);
    }

    @Override
    public void searchBusLine(String oldString, String newString) {
        if (searchLinesCall != null) {
            searchLinesCall.cancel();
        }

        BusTimerApi.Bus bus = BusTimerRetrofit.getInstance().create(BusTimerApi.Bus.class);
        searchLinesCall = bus.line(newString);
        searchLinesCall.enqueue(new Callback<LineListEntity>() {
            @Override
            public void onResponse(Call<LineListEntity> call, Response<LineListEntity> response) {
                if (response.body().getResult() != null & response.body().getResult().getLines() != null) {
                    searchView.onLinesSearchSuccess(response.body().getResult().getLines());
                }
            }

            @Override
            public void onFailure(Call<LineListEntity> call, Throwable t) {

            }
        });

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
