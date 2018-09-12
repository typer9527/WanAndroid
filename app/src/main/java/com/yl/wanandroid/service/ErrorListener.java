package com.yl.wanandroid.service;

public interface ErrorListener {
    void onNetError();

    void onTokenInvalid(String errorMsg);

    void onError(String errorMsg);
}
