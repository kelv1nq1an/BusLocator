package me.fattycat.kun.bustimer.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gigamole.navigationtabbar.ntb.NavigationTabBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.fattycat.kun.bustimer.R;
import me.fattycat.kun.bustimer.about.AboutFragment;
import me.fattycat.kun.bustimer.common.TabFragmentPagerAdapter;
import me.fattycat.kun.bustimer.favourite.FavouriteFragment;
import me.fattycat.kun.bustimer.search.SearchFragment;

/**
 * Author: Kelvinkun
 * Date: 16/6/28
 */

public class MainActivity extends AppCompatActivity implements MainContract.MainView {

    @BindView(R.id.mainViewPager)
    ViewPager mainViewPager;
    @BindView(R.id.mainNavigationTabBar)
    NavigationTabBar mainNavigationTabBar;
    @BindView(R.id.main_line)
    View mainLine;

    private MainContract.MainPresenter mainPresenter;
    private TabFragmentPagerAdapter mainTabFragmentPagerAdapter;
    private List<Fragment> fragmentList;

    private int[] tabSelectedColor = new int[]{R.color.pink_200, R.color.tabIndicatorSelectedColor, R.color.teal_200};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_main);
        ButterKnife.bind(this);

        initViewPager();
        initNavigationTabBar();
    }

    private void initViewPager() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new FavouriteFragment());
        fragmentList.add(new SearchFragment());
        fragmentList.add(new AboutFragment());
        mainTabFragmentPagerAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);

        mainViewPager.setAdapter(mainTabFragmentPagerAdapter);
    }

    private void initNavigationTabBar() {
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();

        models.add(
                new NavigationTabBar.Model.Builder(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.ic_favorite_black_24dp, null),
                        ResourcesCompat.getColor(getResources(), tabSelectedColor[0], null)
                ).title("收藏").build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.ic_search_black_24dp, null),
                        ResourcesCompat.getColor(getResources(), tabSelectedColor[1], null)
                ).title("搜索").build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.ic_near_me_black_24dp, null),
                        ResourcesCompat.getColor(getResources(), tabSelectedColor[2], null)
                ).title("关于").build()
        );

        mainNavigationTabBar.setModels(models);
        mainNavigationTabBar.setViewPager(mainViewPager, 1);
        mainNavigationTabBar.setIsTitled(false);
        mainNavigationTabBar.setIsTinted(true);
        mainNavigationTabBar.setIsSwiped(true);
        mainNavigationTabBar.setBgColor(Color.WHITE);

        mainNavigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mainLine.setBackgroundColor(ResourcesCompat.getColor(getResources(), tabSelectedColor[position], null));
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void setPresenter(@NonNull MainContract.MainPresenter presenter) {
        this.mainPresenter = presenter;
    }
}
