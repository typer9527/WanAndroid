package com.yl.wanandroid.service;

public interface ResponseListener<T> {

    void onTokenInvalid(String errorMsg);

    void onError(String errorMsg);

    void onSuccess(T data);
}
