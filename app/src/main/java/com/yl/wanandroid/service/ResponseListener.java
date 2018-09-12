package com.yl.wanandroid.service;

public interface ResponseListener<T> {
    void onSuccess(T data);
}
