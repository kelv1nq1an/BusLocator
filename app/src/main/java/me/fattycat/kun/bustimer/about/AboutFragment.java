package me.fattycat.kun.bustimer.about;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.fattycat.kun.bustimer.R;

/**
 * Author: Kelvinkun
 * Date: 16/7/1
 */

public class AboutFragment extends Fragment implements AboutContract.View {

    @BindView(R.id.about_app_version)
    TextView aboutAppVersion;
    private AboutContract.Presenter aboutContractPresenter;
    private Unbinder unbinder;
    private String localAppVersion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_fragment_about, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        localAppVersion = getVersionName();
        if (localAppVersion != null) {
            aboutAppVersion.setText(localAppVersion);
        } else {
            aboutAppVersion.setVisibility(View.GONE);
        }

        new AboutPresenter(this);
        aboutContractPresenter.getLatestVersion();
        return rootView;
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("About");
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("About");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(AboutContract.Presenter presenter) {
        this.aboutContractPresenter = presenter;
    }

    @OnClick(R.id.about_app_version)
    public void goToMarket() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse("market://details?id=" + "me.fattycat.kun.bustimer");
        intent.setData(content_url);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException anfe) {
            Uri web_url = Uri.parse("http://fir.im/bustimer");
            Intent webIntent = new Intent(Intent.ACTION_VIEW, web_url);
            startActivity(webIntent);
        }
    }

    private String getVersionName() {
        PackageManager packageManager = getActivity().getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packInfo != null) {
            return packInfo.versionName;
        } else {
            return "";
        }
    }

    @Override
    public void onGetLatestVersion(String appVersion) {
        float localVersion = Float.valueOf(localAppVersion);
        float latestVersion = Float.valueOf(appVersion);
        if (latestVersion > localVersion) {
            Toast.makeText(getActivity(), "有新版本可以升级", Toast.LENGTH_SHORT).show();
            aboutAppVersion.setText(String.format("%s (点此升级到版本%s)", localAppVersion, appVersion));
        }
    }
}
