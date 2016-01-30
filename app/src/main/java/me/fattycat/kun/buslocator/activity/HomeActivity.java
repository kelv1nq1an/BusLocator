package me.fattycat.kun.buslocator.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kennyc.view.MultiStateView;
import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.fattycat.kun.buslocator.R;
import me.fattycat.kun.buslocator.adapter.LineAdapter;
import me.fattycat.kun.buslocator.api.BusApi;
import me.fattycat.kun.buslocator.dao.LinesResult;
import me.fattycat.kun.buslocator.dao.StationAndBus;
import me.fattycat.kun.buslocator.model.BusGPSEntity;
import me.fattycat.kun.buslocator.model.LineEntity;
import me.fattycat.kun.buslocator.model.LineListEntity;
import me.fattycat.kun.buslocator.model.StationListEntity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author: Kelvinkun
 * Time: 16/1/26
 * Description:
 */
public class HomeActivity extends BaseActivity {
    private static final String LINE_FLAG_GO = "1";
    private static final String LINE_FLAG_BACK = "2";
    private static final String BUS_FLAG_GO = "1";
    private static final String BUS_FLAG_BACK = "3";

    private static final int DIRECTION_SHANGXING = 1;
    private static final int DIRECTION_XIAXING = 2;

    @Bind(R.id.homeToolbar)
    Toolbar mHomeToolbar;
    @Bind(R.id.persistentSearchBox)
    SearchBox mPersistentSearchBox;
    @Bind(R.id.homeLineInfo)
    RelativeLayout mHomeLineInfo;
    @Bind(R.id.homeLineName)
    TextView mHomeLineName;
    @Bind(R.id.homeLineInfoRight)
    LinearLayout mHomeLineInfoRight;
    @Bind(R.id.homeLineLoadingView)
    AVLoadingIndicatorView mHomeLineLoadingView;
    @Bind(R.id.homeLineTimeTitle)
    TextView mHomeLineTimeTitle;
    @Bind(R.id.homeLineTime)
    TextView mHomeLineTime;
    @Bind(R.id.homeLineIntervalTitle)
    TextView mHomeLineIntervalTitle;
    @Bind(R.id.homeLineInterval)
    TextView mHomeLineInterval;
    @Bind(R.id.homeLineStartStation)
    TextView mHomeLineStartStation;
    @Bind(R.id.homeLineSwap)
    ImageView mHomeLineSwap;
    @Bind(R.id.homeLineEndStation)
    TextView mHomeLineEndStation;
    @Bind(R.id.multiStateView)
    MultiStateView mMultiStateView;
    @Bind(R.id.homeBusList)
    RecyclerView mHomeBusList;

    private long mClickTime = 0;
    private int mDirection = DIRECTION_SHANGXING;
    private String mLineFlag = LINE_FLAG_GO;
    private String mBusFlag = BUS_FLAG_GO;
    private String mRunPathName;

    private LinesResult mLinesResult;
    private LineAdapter mLineAdapter;
    private ArrayList<LinesResult> mLinesResultList = new ArrayList<>();
    private List<StationListEntity.ResultEntity.StationEntity> mStationList = new ArrayList<>();
    private List<StationAndBus> mStationAndBusList = new ArrayList<>();
    private Call<LineListEntity> mLineListCall;
    private Call<LineEntity> mLineCall;
    private Call<BusGPSEntity> mBusGPSCall;
    private Call<StationListEntity> mAllStationCall;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        mHomeToolbar.setTitle("公交在线");
        mHomeToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mHomeToolbar);

        setupSearchBox();
        setupBusList();

        mHomeLineSwap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mLineFlag.equals(BUS_FLAG_GO)) {
                    mLineFlag = LINE_FLAG_BACK;
                    mBusFlag = BUS_FLAG_BACK;

                    mDirection = DIRECTION_XIAXING;
                } else {
                    mLineFlag = LINE_FLAG_GO;
                    mBusFlag = BUS_FLAG_GO;

                    mDirection = DIRECTION_SHANGXING;
                }

                searchBusesInfo();
            }
        });
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
        mPersistentSearchBox.setLogoTextColor(R.color.color_secondary_text);
        mPersistentSearchBox.setHint("'227'、'211'");
        mPersistentSearchBox.setSearchType(InputType.TYPE_CLASS_NUMBER);
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
                        mLinesResult = linesResult;
                        mRunPathName = linesResult.runPathName;
                        searchBusesInfo();
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

    private void setupBusList() {
        mLineAdapter = new LineAdapter(HomeActivity.this);
        mHomeBusList.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
        mHomeBusList.setAdapter(mLineAdapter);
    }

    private void search(String lineText) {
        if (mLineListCall != null) {
            mLineListCall.cancel();
        }

        mPersistentSearchBox.showLoading(true);
        BusApi.BusLineList busLineList = mBusRetrofit.create(BusApi.BusLineList.class);
        mLineListCall = busLineList.lineList(lineText.trim());

        mLineListCall.enqueue(new Callback<LineListEntity>() {

            @Override
            public void onResponse(Response<LineListEntity> response) {
                mPersistentSearchBox.clearSearchable();
                mLinesResultList.clear();

                if (response.body() != null) {
                    if (response.body().getResult() != null && response.body().getResult().getLines() != null) {
                        for (int i = 0; i < Math.min(5, response.body().getResult().getLines().size()); i++) {
                            LineListEntity.ResultEntity.LinesEntity linesEntity = response.body().getResult().getLines().get(i);
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
                // TODO
                mPersistentSearchBox.showLoading(false);
            }
        });

    }

    private void searchLine(String runPathId, String flag) {
        if (mLineCall != null) {
            mLineCall.cancel();
        }

        hideSearchBox();
        showLineLoading();

        BusApi.BusLine busLine = mBusRetrofit.create(BusApi.BusLine.class);
        mLineCall = busLine.line(runPathId, flag);

        mLineCall.enqueue(new Callback<LineEntity>() {
            @Override
            public void onResponse(Response<LineEntity> response) {
                if (response.body() != null) {
                    LineEntity.ResultEntity resultEntity = response.body().getResult();
                    mHomeLineName.setText(resultEntity.getRunPathName());
                    mHomeLineStartStation.setText(resultEntity.getStartStation());
                    mHomeLineEndStation.setText(resultEntity.getEndStation());
                    if (resultEntity.getRunFlag().equals("0")) {
                        mHomeLineTimeTitle.setText(R.string.text_time);
                        mHomeLineTime.setText(String.format("%s - %s", resultEntity.getStartTime(), resultEntity.getEndTime()));
                        mHomeLineIntervalTitle.setText(R.string.text_interval);
                        mHomeLineInterval.setText(String.format("%s min", resultEntity.getBusInterval()));
                    } else {
                        mHomeLineTimeTitle.setText(R.string.text_start_time);
                        mHomeLineTime.setText(String.format("%s", resultEntity.getStartTime()));
                        mHomeLineIntervalTitle.setText("");
                        mHomeLineInterval.setText("");
                    }
                    hideLineLoading();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                showSearchBox();
                Toast.makeText(HomeActivity.this, "网络错误，请重试", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void searchStations(String runPathId) {
        if (mAllStationCall != null) {
            mAllStationCall.cancel();
        }

        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);

        BusApi.AllStation allStation = mBusRetrofit.create(BusApi.AllStation.class);
        mAllStationCall = allStation.stationList(runPathId);

        mAllStationCall.enqueue(new Callback<StationListEntity>() {
            @Override
            public void onResponse(Response<StationListEntity> response) {
                if (response.body() != null) {

                    if (mDirection == DIRECTION_SHANGXING) {
                        mStationList.addAll(response.body().getResult().getShangxing());
                    } else {
                        mStationList.addAll(response.body().getResult().getXiaxing());
                    }

                    searchBus(mLinesResult.runPathId, mBusFlag, mLinesResult.runPathName);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
            }
        });
    }

    private void searchBus(String runPathId, String flag, final String runPathName) {
        if (mBusGPSCall != null) {
            mBusGPSCall.cancel();
        }

        BusApi.BusGPS busGPS = mBusRetrofit.create(BusApi.BusGPS.class);
        mBusGPSCall = busGPS.GPS(runPathId, flag);

        mBusGPSCall.enqueue(new Callback<BusGPSEntity>() {
            @Override
            public void onResponse(Response<BusGPSEntity> response) {
                if (response.body() != null) {
                    // FIXME: 16/1/30 test code
                    BusGPSEntity.ResultEntity.ListsEntity a = new BusGPSEntity.ResultEntity.ListsEntity();
                    a.setBusStationId("3512");
                    a.setBusStationName("港城汽车站");
                    a.setGPSTime("2016.01.30 19:29:33.3");
                    a.setNumberPlate("eeeeee");
                    a.setOutstate("0");

                    response.body().getResult().getLists().add(a);
                    response.body().getResult().getLists().add(a);
                    a = new BusGPSEntity.ResultEntity.ListsEntity();
                    a.setBusStationId("3512");
                    a.setBusStationName("港城汽车站");
                    a.setGPSTime("2016.01.30 19:29:33.3");
                    a.setNumberPlate("eeeeee");
                    a.setOutstate("1");
                    response.body().getResult().getLists().add(a);
                    a = new BusGPSEntity.ResultEntity.ListsEntity();
                    a.setBusStationId("1740");
                    a.setBusStationName("勤丰苑");
                    a.setGPSTime("2016.01.30 19:29:33.3");
                    a.setNumberPlate("eeeeee");
                    a.setOutstate("1");
                    response.body().getResult().getLists().add(a);

                    mLineAdapter.setRunPathName(runPathName);

                    for (StationListEntity.ResultEntity.StationEntity stationEntity : mStationList) {
                        addStation(stationEntity);
                        for (BusGPSEntity.ResultEntity.ListsEntity listsEntity : response.body().getResult().getLists()) {
                            if (listsEntity.getBusStationId().equals(stationEntity.getBusStationId())) {
                                StationAndBus stationAndBus = mStationAndBusList.get(mStationAndBusList.size() - 1);
                                if (listsEntity.getOutstate().equals("0")) {
                                    stationAndBus.busState = StationAndBus.DAO_STATE_ARRIVE;
                                } else if (listsEntity.getOutstate().equals("1")) {
                                    stationAndBus.busState = StationAndBus.DAO_STATE_LEAVE;
                                }
                                addBus(listsEntity);
                            }
                        }
                    }
                    mLineAdapter.refreshData(mStationAndBusList);
                    if (response.body().getResult().getLists().size() == 0) {
                        Toast.makeText(HomeActivity.this, "当前没有公交在线", Toast.LENGTH_SHORT).show();
                    }
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
            }
        });
    }

    private void addBus(BusGPSEntity.ResultEntity.ListsEntity listsEntity) {
        StationAndBus dao = new StationAndBus();
        dao.type = StationAndBus.DAO_TYPE_BUS;
        dao.bus = listsEntity;
        mStationAndBusList.add(dao);
    }

    private void addStation(StationListEntity.ResultEntity.StationEntity stationEntity) {
        StationAndBus dao = new StationAndBus();
        dao.type = StationAndBus.DAO_TYPE_STATION;
        dao.station = stationEntity;
        mStationAndBusList.add(dao);
    }

    private void searchBusesInfo() {
        if (mRunPathName != null) {
            mHomeLineName.setText(mRunPathName);
        }

        mStationList.clear();
        mStationAndBusList.clear();

        searchLine(mLinesResult.runPathId, mLineFlag);
        searchStations(mLinesResult.runPathId);
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

    private void showLineLoading() {
        mHomeLineLoadingView.setVisibility(View.VISIBLE);
        mHomeLineInfoRight.setVisibility(View.GONE);
    }

    private void hideLineLoading() {
        mHomeLineLoadingView.setVisibility(View.GONE);
        mHomeLineInfoRight.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_search) {
            if (mLinesResult != null) {
                if (mPersistentSearchBox.getSearchVisibility()) {
                    hideSearchBox();
                } else {
                    showSearchBox();
                }
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
