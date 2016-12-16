package me.fattycat.kun.bustimer.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fattycat.kun.bustimer.R;
import me.fattycat.kun.bustimer.data.model.BusGPSModel;
import me.fattycat.kun.bustimer.data.model.LineInfoModel;
import me.fattycat.kun.bustimer.data.model.StationModel;
import me.fattycat.kun.bustimer.ui.favourite.FavouriteActivity;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {
    private static final String EXTRA_KEY      = "RPID";
    private static final String EXTRA_KEY_NAME = "NAME";

    @BindView(R.id.top_bar_title_text_view)
    TextView     topBarTitleTextView;
    @BindView(R.id.detail_favourite_text_view)
    TextView     detailFavouriteTextView;
    @BindView(R.id.detail_favourite_container)
    LinearLayout detailFavouriteContainer;
    @BindView(R.id.detail_first_text_view)
    TextView     detailFirstTextView;
    @BindView(R.id.detail_gap_text_view)
    TextView     detailGapTextView;
    @BindView(R.id.detail_end_text_view)
    TextView     detailEndTextView;

    private String                   rpid;
    private String                   lineName;
    private DetailContract.Presenter presenter;

    public static Intent newIntent(Context context, String rpid, String lineName) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_KEY, rpid);
        intent.putExtra(EXTRA_KEY_NAME, lineName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        rpid = getIntent().getStringExtra(EXTRA_KEY);
        lineName = getIntent().getStringExtra(EXTRA_KEY_NAME);
        topBarTitleTextView.setText(lineName);
        new DetailPresenter(this);
        presenter.getLineInfo(rpid);
        presenter.getStations(rpid);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unSubscribe();
    }

    @OnClick(R.id.top_bar_favourite_image_view)
    public void onFavouriteClick() {
        startActivity(new Intent(this, FavouriteActivity.class));
    }

    @OnClick(R.id.top_bar_info_image_view)
    public void onInfoClick() {

    }

    @Override
    public void onGetLineInfoStart() {

    }

    @Override
    public void onGetLineInfoSuccess(LineInfoModel lineInfoModel) {
        // TODO: 2016/12/16 夜班车
        detailFirstTextView.setText(String.format("首班:%s", lineInfoModel.getStartTime()));
        detailEndTextView.setText(String.format("末班:%s分钟", lineInfoModel.getEndTime()));
        detailGapTextView.setText(String.format("间隔:%s", lineInfoModel.getBusInterval()));
    }

    @Override
    public void onGetLineInfoError() {

    }

    @Override
    public void onGetStationsStart() {

    }

    @Override
    public void onGetStationsSuccess(StationModel stationModel) {

    }

    @Override
    public void onGetStationsError() {

    }

    @Override
    public void onGetBusGPSStart() {

    }

    @Override
    public void onGetBusGPSSuccess(BusGPSModel busGPSModel) {

    }

    @Override
    public void onGetBusGPSError() {

    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
