package me.fattycat.kun.bustimer.favourite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.avos.avoscloud.AVAnalytics;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.fattycat.kun.bustimer.R;
import me.fattycat.kun.bustimer.common.LineListAdapter;
import me.fattycat.kun.bustimer.model.LineEntity;
import me.fattycat.kun.bustimer.model.LineEntityWrapper;
import me.fattycat.kun.bustimer.model.LineInfoSerializable;
import me.fattycat.kun.bustimer.util.FavoriteDataUtil;

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

        favouriteRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        lineListAdapter = new LineListAdapter(getActivity(), null);
        favouriteRecyclerView.setAdapter(lineListAdapter);

        new FavouritePresenter(this);
        return root;
    }

    public void onPause() {
        super.onPause();
        AVAnalytics.onFragmentEnd("FavouriteFragment");
    }
    @Override
    public void onResume() {
        super.onResume();
        favouriteContractPresenter.loadLocalData();
        AVAnalytics.onFragmentStart("FavouriteFragment");
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
        List<LineInfoSerializable> allFavoriteLines = FavoriteDataUtil.loadAllFavoriteLine(getActivity());
        if (allFavoriteLines == null || allFavoriteLines.size() == 0) {
            return;
        }
        List<LineEntityWrapper> allFavoriteLineWrappers = new ArrayList<>();
        for (LineInfoSerializable line : allFavoriteLines) {
            LineEntity.ResultEntity resultEntity = new LineEntity.ResultEntity();
            resultEntity.setEndTime1(line.getEndTime1());
            resultEntity.setStartTime(line.getStartTime());
            resultEntity.setStartStation(line.getStartStation());
            resultEntity.setRunFlag(line.getRunFlag());
            resultEntity.setRunPathName(line.getRunPathName());
            resultEntity.setBusInterval(line.getBusInterval());
            resultEntity.setEndStation(line.getEndStation());
            resultEntity.setRunPathId(line.getRunPathId());
            resultEntity.setEndTime(line.getEndTime());
            resultEntity.setStartTime1(line.getStartTime1());
            LineEntity lineEntity = new LineEntity();
            lineEntity.setResult(resultEntity);
            LineEntityWrapper lineEntityWrapper = new LineEntityWrapper(lineEntity, line.getFlag());
            allFavoriteLineWrappers.add(lineEntityWrapper);
        }
        lineListAdapter.setData(allFavoriteLineWrappers);
    }
}
