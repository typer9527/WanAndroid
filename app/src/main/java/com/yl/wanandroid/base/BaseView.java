package com.yl.wanandroid.base;

public interface BaseView {
    void onTokenInvalid(String errorMsg);

    void onError(String errorMsg);

    void onNetError();
}
