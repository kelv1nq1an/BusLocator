package me.fattycat.kun.bustimer.favourite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.fattycat.kun.bustimer.R;

/**
 * Author: Kelvinkun
 * Date: 16/6/30
 */

public class FavouriteFragment extends android.support.v4.app.Fragment implements FavouriteContract.View {

    @BindView(R.id.favouriteRecyclerView)
    RecyclerView favouriteRecyclerView;

    private Unbinder unbinder;
    private FavouriteContract.Presenter favouriteContractPresenter;
    private LineListAdapter lineListAdapter;

    @Nullable
    @Override
    public android.view.View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        android.view.View root = inflater.inflate(R.layout.layout_fragment_favourite, container, false);
        unbinder = ButterKnife.bind(this, root);
        FavouritePresenter favouritePresenter = new FavouritePresenter(this);

        favouriteRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        favouriteContractPresenter.loadLocalData();

        lineListAdapter = new LineListAdapter(getActivity(), null);
        favouriteRecyclerView.setAdapter(lineListAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(@NonNull FavouriteContract.Presenter presenter) {
        this.favouriteContractPresenter = presenter;
    }

    @Override
    public void onDataLoaded() {
        lineListAdapter.setData(null);
    }
}
