package com.yl.wanandroid.app;

import android.app.Application;

import com.yl.wanandroid.service.AuthCookies;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AuthCookies.getInstance().init(this);
    }
}
