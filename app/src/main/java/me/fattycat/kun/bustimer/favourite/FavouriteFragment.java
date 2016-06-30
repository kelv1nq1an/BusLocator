package me.fattycat.kun.bustimer.favourite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.fattycat.kun.bustimer.R;

/**
 * Author: Kelvinkun
 * Date: 16/6/30
 */

public class FavouriteFragment extends Fragment implements FavouriteContract.FavouriteView {

    @BindView(R.id.favouriteRecyclerView)
    RecyclerView favouriteRecyclerView;

    private Unbinder unbinder;
    private FavouriteContract.FavouritePresenter favouritePresenter;
    private FavouriteListAdapter favouriteListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.layout_favourite, container, false);
        unbinder = ButterKnife.bind(this, root);

        favouriteRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // FIXME: 16/6/30
        List<String> testList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            testList.add("aaa");
        }

        favouriteListAdapter = new FavouriteListAdapter(getActivity(), testList);
        favouriteRecyclerView.setAdapter(favouriteListAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(@NonNull FavouriteContract.FavouritePresenter presenter) {
        this.favouritePresenter = presenter;
    }
}
