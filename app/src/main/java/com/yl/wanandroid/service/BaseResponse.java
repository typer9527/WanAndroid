package com.yl.wanandroid.service;

public class BaseResponse<T> {
    private int errorCode; // 0：success -1001: 登录失效
    private String errorMsg;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
