package me.fattycat.kun.buslocator.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import me.fattycat.kun.buslocator.Event;
import me.fattycat.kun.buslocator.R;
import me.fattycat.kun.buslocator.adapter.LineListAdapter;
import me.fattycat.kun.buslocator.api.BusApi;
import me.fattycat.kun.buslocator.model.Line;
import me.fattycat.kun.buslocator.model.LineList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author: Kelvinkun
 * Time: 16/1/26
 * Description:
 */
public class HomeActivity extends BaseActivity {
    @Bind(R.id.persistentSearchBox)
    SearchBox mPersistentSearchBox;
    @Bind(R.id.lineList)
    RecyclerView mLineList;

    private LineListAdapter mLineListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setupSearchBox();
        setupLineList();

        EventBus.getDefault().register(this);
    }

    private void setupLineList() {
        mLineListAdapter = new LineListAdapter(this);

        mLineList.setLayoutManager(new LinearLayoutManager(this));
        mLineList.setAdapter(mLineListAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    private void setupSearchBox() {
        mPersistentSearchBox.setLogoText("请输入您要查询的线路");
        mPersistentSearchBox.setLogoTextColor(R.color.colorSecondaryText);
        mPersistentSearchBox.setHint("'227'、'211'");
        mPersistentSearchBox.setSearchListener(new SearchBox.SearchListener() {
            @Override
            public void onSearchOpened() {

            }

            @Override
            public void onSearchCleared() {

            }

            @Override
            public void onSearchClosed() {

            }

            @Override
            public void onSearchTermChanged(String s) {
                search(s);
            }

            @Override
            public void onSearch(String s) {

            }

            @Override
            public void onResultClick(SearchResult searchResult) {

            }
        });
    }

    public void search(String busName) {
        BusApi.BusLineList busLineList = mBusRetrofit.create(BusApi.BusLineList.class);

        Call<LineList> lineListCall = busLineList.lineList(busName.trim());

        lineListCall.enqueue(new Callback<LineList>() {

            @Override
            public void onResponse(Response<LineList> response) {
                if (response.body().getStatus().equals("SUCCESS")) {
                    mLineListAdapter.refreshData(response.body().getResult().getLines());
                } else {
                    mLineListAdapter.clearData();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }

    @Subscribe(threadMode = ThreadMode.Async)
    public void searchLine(Event.SearchLineEvent event) {
        BusApi.BusLine busLine = mBusRetrofit.create(BusApi.BusLine.class);

        Call<Line> lineCall = busLine.line(event.runPathId, "1");

        lineCall.enqueue(new Callback<Line>() {
            @Override
            public void onResponse(Response<Line> response) {
                String content = response.body().getResult().getRunPathId() + "\n" +
                        response.body().getResult().getRunPathName() + "\n" +
                        response.body().getResult().getStartStation() + "\n" +
                        response.body().getResult().getStartTime() + "\n" +
                        response.body().getResult().getEndStation() + "\n" +
                        response.body().getResult().getEndTime();

                Toast.makeText(HomeActivity.this, content, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }

}
