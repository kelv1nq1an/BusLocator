package me.fattycat.kun.bustimer.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.gigamole.navigationtabbar.ntb.NavigationTabBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.fattycat.kun.bustimer.R;

/**
 * Author: Kelvinkun
 * Date: 16/6/28
 */

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.mainViewPager)
    ViewPager mainViewPager;
    @BindView(R.id.mainNavigationTabBar)
    NavigationTabBar mainNavigationTabBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        ButterKnife.bind(this);

        initViewPager();
        initNavigationTabBar();
    }

    private void initViewPager() {

    }

    private void initNavigationTabBar() {
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();

        models.add(
                new NavigationTabBar.Model.Builder(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.ic_favorite_black_24dp, null),
                        ResourcesCompat.getColor(getResources(), R.color.pink_200, null)
                ).title("收藏").build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.ic_search_black_24dp, null),
                        ResourcesCompat.getColor(getResources(), R.color.tabIndicatorSelectedColor, null)
                ).title("搜索").build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.ic_near_me_black_24dp, null),
                        ResourcesCompat.getColor(getResources(), R.color.teal_200, null)
                ).title("关于").build()
        );

        mainNavigationTabBar.setModels(models);
        mainNavigationTabBar.setModelIndex(1);
        //navigationTabBar.setViewPager(viewPager, 2);
        mainNavigationTabBar.setIsTitled(true);
        mainNavigationTabBar.setIsTinted(true);
        mainNavigationTabBar.setIsSwiped(true);
        mainNavigationTabBar.setBgColor(Color.WHITE);
    }

}
