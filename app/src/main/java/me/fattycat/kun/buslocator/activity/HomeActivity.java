package me.fattycat.kun.buslocator.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
    @Bind(R.id.homeLineInfo)
    RelativeLayout mHomeLineInfo;
    @Bind(R.id.homeLineName)
    TextView mHomeLineName;
    @Bind(R.id.homeLineStartStation)
    TextView mHomeLineStartStation;
    @Bind(R.id.homeLineEndStation)
    TextView mHomeLineEndStation;
    @Bind(R.id.homeLineTime)
    TextView mHomeLineTime;
    @Bind(R.id.homeLineInterval)
    TextView mHomeLineInterval;
    @Bind(R.id.homeLineBusNum)
    TextView mHomeLineBusNum;

    private long mClickTime = 0;

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
        mPersistentSearchBox.setMenuListener(new SearchBox.MenuListener() {

            @Override
            public void onMenuClick() {
                mPersistentSearchBox.toggleSearch();
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
                        for (int i = 0; i < Math.min(5, response.body().getResult().getLines().size()); i++) {
                            LineList.ResultEntity.LinesEntity linesEntity = response.body().getResult().getLines().get(i);
                            mLinesResultList.add(new LinesResult(linesEntity.getRunPathId(), linesEntity.getRunPathName()));
                        }
                       /*
                        for (LineList.ResultEntity.LinesEntity linesEntity : response.body().getResult().getLines()) {
                            mLinesResultList.add(new LinesResult(linesEntity.getRunPathId(), linesEntity.getRunPathName()));
                        }*/

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
                    hideSearchBox();

                    Line.ResultEntity resultEntity = response.body().getResult();
                    mHomeLineName.setText(resultEntity.getRunPathName());
                    mHomeLineStartStation.setText(resultEntity.getStartStation());
                    mHomeLineEndStation.setText(resultEntity.getEndStation());
                    if (resultEntity.getRunFlag().equals("0")) {
                        mHomeLineTime.setText(String.format("首末班:%s-%s", resultEntity.getStartTime(), resultEntity.getEndTime()));
                        mHomeLineInterval.setText(String.format("间隔:%s min", resultEntity.getBusInterval()));
                    } else {
                        mHomeLineTime.setText(String.format("发班:%s", resultEntity.getStartTime()));
                        mHomeLineInterval.setText("");
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }

    public void showSearchBox() {
        mPersistentSearchBox.revealFromMenuItem(R.id.menu_item_search, HomeActivity.this);
        mHomeLineInfo.setVisibility(View.INVISIBLE);
        // really can't understand, this should hide search mode but the result is opposite.
        mPersistentSearchBox.closeSearch();
    }

    public void hideSearchBox() {
        mPersistentSearchBox.hideCircularly(HomeActivity.this);
        mHomeLineInfo.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_search) {
            if (mPersistentSearchBox.getSearchVisibility()) {
                hideSearchBox();
            } else {
                showSearchBox();
            }
        }
        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mClickTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次后退键退出程序", Toast.LENGTH_SHORT).show();
            mClickTime = System.currentTimeMillis();
        } else {
            this.finish();
            System.exit(0);
        }
    }
}
