package me.fattycat.kun.bustimer.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import me.fattycat.kun.bustimer.model.LineListEntity;
import me.fattycat.kun.bustimer.R;

/**
 * Author: Kelvinkun
 * Date: 16/6/30
 */

public class SearchFragment extends Fragment implements SearchContract.View {

    @BindView(R.id.fragment_search_floating_search_view)
    FloatingSearchView fragmentSearchFloatingSearchView;
    private SearchContract.Presenter searchContractPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_fragment_search, container, false);
        ButterKnife.bind(this, rootView);
        fragmentSearchFloatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                fragmentSearchFloatingSearchView.showProgress();
                searchContractPresenter.searchBusLine(oldQuery, newQuery);
            }
        });

        fragmentSearchFloatingSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {

            }
        });

        fragmentSearchFloatingSearchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(View suggestionView, ImageView leftIcon, TextView textView, SearchSuggestion item, int itemPosition) {
                leftIcon.setImageResource(R.drawable.ic_directions_bus_black_24dp);
                leftIcon.setColorFilter(R.color.grey_500, android.graphics.PorterDuff.Mode.MULTIPLY);
            }
        });
        fragmentSearchFloatingSearchView.setLeftActionMode(FloatingSearchView.LEFT_ACTION_MODE_SHOW_SEARCH);

        // TODO: 16/7/7 设定默认键盘为数字键盘
        SearchPresenter searchPresenter = new SearchPresenter(this);
        return rootView;
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        this.searchContractPresenter = presenter;
    }

    @Override
    public void onLinesSearchSuccess(List<LineListEntity.ResultEntity.LinesEntity> lines) {
        List<LinesSearchSuggestion> linesSearchSuggestions = new ArrayList<>();
        for (LineListEntity.ResultEntity.LinesEntity line : lines) {
            linesSearchSuggestions.add(new LinesSearchSuggestion(line.getRunPathName()));
        }

        fragmentSearchFloatingSearchView.hideProgress();
        fragmentSearchFloatingSearchView.swapSuggestions(linesSearchSuggestions);
    }

    public boolean isSearchBarFocused() {
        return fragmentSearchFloatingSearchView.isSearchBarFocused();
    }

    public void clearSearchBarFocus() {
        fragmentSearchFloatingSearchView.clearSearchFocus();
    }
}
