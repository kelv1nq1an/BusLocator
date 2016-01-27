package me.fattycat.kun.buslocator.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.fattycat.kun.buslocator.R;
import me.fattycat.kun.buslocator.api.BusApi;
import me.fattycat.kun.buslocator.model.Line;
import me.fattycat.kun.buslocator.model.LineList;
import me.fattycat.kun.buslocator.model.LinesResult;
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
    @Bind(R.id.homeToolbar)
    Toolbar mHomeToolbar;

    private ArrayList<LinesResult> mLinesResultList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        mHomeToolbar.setTitle("港城公交");
        mHomeToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mHomeToolbar);

        setupSearchBox();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SearchBox.VOICE_RECOGNITION_CODE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            mPersistentSearchBox.populateEditText(matches.get(0));
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setupSearchBox() {
        mPersistentSearchBox.enableVoiceRecognition(this);
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
            public void onSearchTermChanged(String lineText) {
                search(lineText);
            }

            @Override
            public void onSearch(String lineText) {
                search(lineText);
            }

            @Override
            public void onResultClick(SearchResult searchResult) {
                for (LinesResult linesResult : mLinesResultList) {
                    if (linesResult.runPathName.equals(searchResult.title)) {
                        searchLine(linesResult.runPathId);
                    }
                }
            }
        });
    }

    public void search(String lineText) {
        mPersistentSearchBox.showLoading(true);

        BusApi.BusLineList busLineList = mBusRetrofit.create(BusApi.BusLineList.class);
        final Call<LineList> lineListCall = busLineList.lineList(lineText.trim());

        lineListCall.enqueue(new Callback<LineList>() {

            @Override
            public void onResponse(Response<LineList> response) {
                mPersistentSearchBox.clearSearchable();
                mLinesResultList.clear();

                if (response.body() != null) {
                    if (response.body().getResult() != null && response.body().getResult().getLines() != null) {
                        for (LineList.ResultEntity.LinesEntity linesEntity : response.body().getResult().getLines()) {
                            mLinesResultList.add(new LinesResult(linesEntity.getRunPathId(), linesEntity.getRunPathName()));
                        }

                        mPersistentSearchBox.addAllSearchables(mLinesResultList);
                        mPersistentSearchBox.updateResults();
                    }
                    mPersistentSearchBox.showLoading(false);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mPersistentSearchBox.showLoading(false);
            }
        });

    }

    public void searchLine(String runPathId) {
        BusApi.BusLine busLine = mBusRetrofit.create(BusApi.BusLine.class);
        Call<Line> lineCall = busLine.line(runPathId, "1");

        lineCall.enqueue(new Callback<Line>() {
            @Override
            public void onResponse(Response<Line> response) {
                if (response.body() != null) {
                    String content = response.body().getResult().getRunPathId() + "\n" +
                            response.body().getResult().getRunPathName() + "\n" +
                            response.body().getResult().getStartStation() + "\n" +
                            response.body().getResult().getStartTime() + "\n" +
                            response.body().getResult().getEndStation() + "\n" +
                            response.body().getResult().getEndTime();

                    Toast.makeText(HomeActivity.this, content, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_search) {
            mPersistentSearchBox.revealFromMenuItem(R.id.menu_item_search, HomeActivity.this);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
