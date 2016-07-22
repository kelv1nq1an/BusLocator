package me.fattycat.kun.bustimer.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVAnalytics;

import me.fattycat.kun.bustimer.R;

/**
 * Author: Kelvinkun
 * Date: 16/7/1
 */

public class AboutFragment extends Fragment implements AboutContract.AboutView {

    private AboutContract.AboutPresenter aboutPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_fragment_about, container, false);
        return rootView;
    }

    public void onResume() {
        super.onResume();
        AVAnalytics.onFragmentStart("AboutFragment");
    }

    public void onPause() {
        super.onPause();
        AVAnalytics.onFragmentEnd("AboutFragment");
    }

    @Override
    public void setPresenter(AboutContract.AboutPresenter presenter) {
        this.aboutPresenter = presenter;
    }

}
