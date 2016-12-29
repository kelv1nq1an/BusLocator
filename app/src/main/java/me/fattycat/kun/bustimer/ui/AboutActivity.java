package me.fattycat.kun.bustimer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fattycat.kun.bustimer.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.about_share_image_view)
    public void share() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "港城公交 -- 一款能查询张家港实时公交、收藏常用线路、到站提醒等的实用工具，欢迎下载使用哦~ http://www.wandoujia.com/apps/me.fattycat.kun.bustimer");
        shareIntent.setType("text/plain");
        startActivity(Intent.createChooser(shareIntent, "分享 港城公交"));
    }

    @OnClick(R.id.about_close_image_view)
    public void close() {
        finish();
    }
}
