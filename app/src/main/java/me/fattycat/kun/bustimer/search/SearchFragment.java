package me.fattycat.kun.bustimer.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.fattycat.kun.bustimer.R;

/**
 * Author: Kelvinkun
 * Date: 16/6/30
 */

public class SearchFragment extends Fragment implements SearchContract.SearchView {

    private SearchContract.SearchPresenter searchPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_fragment_search, container, false);
        return rootView;
    }

    @Override
    public void setPresenter(SearchContract.SearchPresenter presenter) {
        this.searchPresenter = presenter;
    }
}
