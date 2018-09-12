package com.yl.wanandroid.service;

import android.support.annotation.NonNull;

import com.yl.wanandroid.utils.Constant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Lee on 2018/3/1.
 */

public class RetrofitFactory {
    private static final int TIME_OUT = 5;
    private static APIService apiService;
    private static RetrofitFactory retrofitFactory;

    private RetrofitFactory() {
        /*
         * 构造函数私有化
         * 并在构造函数中进行retrofit的初始化
         */
        OkHttpClient client = new OkHttpClient();
        client.newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain.request();
                return chain.proceed(request);
            }
        }).connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        /*
         * 由于retrofit底层的实现是通过okhttp实现的，所以可以通过okHttp来设置一些连接参数
         * 如超时等
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
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
