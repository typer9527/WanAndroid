package com.yl.wanandroid.service.interfaces;

public interface ErrorListener {
    void onNetError();

    void onTokenInvalid(String errorMsg);

    void onError(String errorMsg);
}
