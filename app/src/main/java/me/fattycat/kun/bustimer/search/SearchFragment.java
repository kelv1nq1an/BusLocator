package me.fattycat.kun.bustimer.search;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.fattycat.kun.bustimer.Http.BusTimerApi;
import me.fattycat.kun.bustimer.R;
import me.fattycat.kun.bustimer.favourite.LineListAdapter;
import me.fattycat.kun.bustimer.model.LineEntity;
import me.fattycat.kun.bustimer.model.LineListEntity;

/**
 * Author: Kelvinkun
 * Date: 16/6/30
 */

public class SearchFragment extends Fragment implements SearchContract.View {

    @BindView(R.id.fragment_search_floating_search_view)
    FloatingSearchView fragmentSearchFloatingSearchView;
    @BindView(R.id.fragment_search_floating_line_list)
    RecyclerView fragmentSearchFloatingLineList;
    private SearchContract.Presenter searchContractPresenter;
    private List<LineListEntity.ResultEntity.LinesEntity> linesResultList;
    private LineListAdapter lineListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_fragment_search, container, false);
        ButterKnife.bind(this, rootView);

        fragmentSearchFloatingSearchView.setShowSearchKey(false);
        fragmentSearchFloatingSearchView.setLeftActionMode(FloatingSearchView.LEFT_ACTION_MODE_SHOW_SEARCH);
        fragmentSearchFloatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                doSearchBusLine(newQuery);
            }
        });

        fragmentSearchFloatingSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                doOnSearch(searchSuggestion.getBody());
            }

            @Override
            public void onSearchAction(String currentQuery) {
                //doOnSearch(currentQuery);
            }
        });

        fragmentSearchFloatingSearchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(View suggestionView, ImageView leftIcon, TextView textView, SearchSuggestion item, int itemPosition) {
                leftIcon.setImageResource(R.drawable.ic_directions_bus_black_24dp);
                leftIcon.setColorFilter(R.color.grey_500, PorterDuff.Mode.MULTIPLY);
            }
        });

        lineListAdapter = new LineListAdapter(getActivity(), null);
        fragmentSearchFloatingLineList.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentSearchFloatingLineList.setAdapter(lineListAdapter);

        // TODO: 16/7/7 设定默认键盘为数字键盘
        SearchPresenter searchPresenter = new SearchPresenter(this);
        return rootView;
    }

    private void doOnSearch(String searchString) {
        if (linesResultList == null || TextUtils.isEmpty(searchString)) {
            return;
        }
        lineListAdapter.clearData();
        for (LineListEntity.ResultEntity.LinesEntity line : linesResultList) {
            if (TextUtils.equals(line.getRunPathName(), searchString)) {
                // TODO: 16/7/8 合并请求
                searchContractPresenter.searchLineInfo(line.getRunPathId(), BusTimerApi.FLAG_LINE_GO);
                searchContractPresenter.searchLineInfo(line.getRunPathId(), BusTimerApi.FLAG_LINE_BACK);
                return;
            }
        }
    }

    private void doSearchBusLine(String lineName) {
        if (lineName.isEmpty()) {
            clearSuggestions();
        } else {
            fragmentSearchFloatingSearchView.showProgress();
            searchContractPresenter.searchBusLine(lineName);
        }
    }

    private void clearSuggestions() {
        fragmentSearchFloatingSearchView.hideProgress();
        fragmentSearchFloatingSearchView.swapSuggestions(new ArrayList<SearchSuggestion>());
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        this.searchContractPresenter = presenter;
    }

    @Override
    public void onLinesSearchSuccess(List<LineListEntity.ResultEntity.LinesEntity> lines) {
        linesResultList = lines;

        List<LinesSearchSuggestion> linesSearchSuggestions = new ArrayList<>();
        for (LineListEntity.ResultEntity.LinesEntity line : lines) {
            linesSearchSuggestions.add(new LinesSearchSuggestion(line.getRunPathName()));
        }

        fragmentSearchFloatingSearchView.hideProgress();
        fragmentSearchFloatingSearchView.swapSuggestions(linesSearchSuggestions);
    }

    @Override
    public void onLinesSearchFailed() {
        clearSuggestions();
    }

    @Override
    public void onLineInfoSearchSuccess(LineEntity line) {
        lineListAdapter.addData(line);
    }

    @Override
    public void onLineInfoSearchFailed() {
        lineListAdapter.clearData();
    }

    public boolean isSearchBarFocused() {
        return fragmentSearchFloatingSearchView.isSearchBarFocused();
    }

    public void clearSearchBarFocus() {
        fragmentSearchFloatingSearchView.clearSearchFocus();
    }
}