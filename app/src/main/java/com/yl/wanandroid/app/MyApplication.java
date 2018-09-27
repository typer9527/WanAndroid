package com.yl.wanandroid.app;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.yl.wanandroid.service.AuthCookies;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        AuthCookies.getInstance().init(this);
    }
}
