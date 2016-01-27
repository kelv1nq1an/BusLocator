package me.fattycat.kun.buslocator.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import me.fattycat.kun.buslocator.api.BusApi;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Author: Kelvinkun
 * Time: 16/1/27
 * Description:
 */
public class BaseActivity extends AppCompatActivity {
    public Retrofit mBusRetrofit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBusRetrofit = new Retrofit.Builder()
                .baseUrl(BusApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
