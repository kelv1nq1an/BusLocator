package me.fattycat.kun.bustimer.common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Kelvinkun
 * Date: 16/6/30
 */

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments = new ArrayList<>();

    public TabFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.size() == 0 ? null : fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
