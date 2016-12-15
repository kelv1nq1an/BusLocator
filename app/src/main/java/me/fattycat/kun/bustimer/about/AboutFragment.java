package me.fattycat.kun.bustimer.about;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.fattycat.kun.bustimer.R;
import me.fattycat.kun.bustimer.model.AppInfoEntity;

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
    private int localVersionCode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_fragment_about, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        localAppVersion = getPackageInfo() == null ? null : getPackageInfo().versionName;
        localVersionCode = getPackageInfo() != null ? getPackageInfo().versionCode : 0;
        if (localAppVersion != null) {
            aboutAppVersion.setText(localAppVersion);
        } else {
            aboutAppVersion.setVisibility(View.GONE);
        }

        new AboutPresenter(this);
        aboutContractPresenter.getLatestVersion();
        return rootView;
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
        /*Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse("market://details?id=" + "me.fattycat.kun.bustimer");
        intent.setData(content_url);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException anfe) {

        }*/
        Uri web_url = Uri.parse("http://fir.im/bustimer");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, web_url);
        startActivity(webIntent);
    }

    private PackageInfo getPackageInfo() {
        PackageManager packageManager = getActivity().getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo;
    }

    @Override
    public void onGetLatestVersion(AppInfoEntity appVersion) {
        int latestVersion = Integer.valueOf(appVersion.getVersion());
        if (latestVersion > localVersionCode) {
            Toast.makeText(getActivity(), "有新版本可以升级", Toast.LENGTH_SHORT).show();
            aboutAppVersion.setText(String.format("%s\n(点此升级到版本%s)", localAppVersion, appVersion.getVersionShort()));
            aboutAppVersion.setTextColor(ContextCompat.getColor(getActivity(), R.color.red_200));
        }
    }
}
