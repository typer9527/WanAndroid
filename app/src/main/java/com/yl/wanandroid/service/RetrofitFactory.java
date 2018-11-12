package com.yl.wanandroid.service;

import android.support.annotation.NonNull;
import android.util.Log;

import com.yl.wanandroid.BuildConfig;
import com.yl.wanandroid.app.Constant;
import com.yl.wanandroid.service.interfaces.APIService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lee on 2018/3/1.
 */

public class RetrofitFactory {
    private static final String TAG = "RetrofitFactory";
    private static final int TIME_OUT = 5;
    private static APIService apiService;
    private static RetrofitFactory retrofitFactory;

    private RetrofitFactory() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String message) {
                Log.e(TAG, "log: " + message);
            }
        });
        logInterceptor.setLevel(BuildConfig.DEBUG ?
                HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        // 可以通过OkHttp来设置一些连接参数
        OkHttpClient.Builder client = new OkHttpClient().newBuilder();
        client.addInterceptor(logInterceptor)
                .cookieJar(AuthCookies.getInstance())
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        // TODO: 18-11-12 研究 Interceptor 和 Retrofit 以优化网络框架
        // 构造 Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(APIService.class);
    }

    public static RetrofitFactory getInstance() {
        if (retrofitFactory == null) {
            synchronized (RetrofitFactory.class) {
                if (retrofitFactory == null)
                    retrofitFactory = new RetrofitFactory();
            }

        }
        return retrofitFactory;
    }

    public APIService getApiService() {
        return apiService;
    }
}
