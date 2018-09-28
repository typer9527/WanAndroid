package com.yl.wanandroid.service;

import android.support.annotation.NonNull;

import com.yl.wanandroid.service.interfaces.APIService;
import com.yl.wanandroid.app.Constant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lee on 2018/3/1.
 */

public class RetrofitFactory {
    //private static final String TAG = "RetrofitFactory";
    private static final int TIME_OUT = 5;
    private static APIService apiService;
    private static RetrofitFactory retrofitFactory;

    private RetrofitFactory() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String message) {
                //Log.e(TAG, "log: " + message);
            }
        });
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        /*
         * 由于retrofit底层的实现是通过OKHttp实现的，所以可以通过OKHttp来设置一些连接参数
         * 如超时等
         */
        OkHttpClient.Builder client = new OkHttpClient().newBuilder();
        client.addInterceptor(logInterceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        Request request = chain.request();
                        return chain.proceed(request);
                    }
                })
                .cookieJar(AuthCookies.getInstance())
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        /*
         * 构造函数私有化
         * 并在构造函数中进行retrofit的初始化
         */
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
