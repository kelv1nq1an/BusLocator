package me.fattycat.kun.bustimer.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import me.fattycat.kun.bustimer.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author: qk329
 * Date: 2016/12/14
 */

public class RetrofitClient {

    public static Retrofit defaultInstance() {
        Gson gson = new GsonBuilder()
                            .serializeNulls()
                            .setLenient()
                            .setPrettyPrinting()
                            .create();
        return new Retrofit.Builder()
                       .client(defaultOkHttpClient())
                       .baseUrl(BusTimerApi.BASE_URL)
                       .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                       .addConverterFactory(GsonConverterFactory.create(gson))
                       .build();
    }

    private static OkHttpClient defaultOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
                                                         .header("Content-Type", "application/json; charset=utf-8")
                                                         .method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(loggingInterceptor)
                .addInterceptor(headerInterceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        return builder.build();
    }
}
