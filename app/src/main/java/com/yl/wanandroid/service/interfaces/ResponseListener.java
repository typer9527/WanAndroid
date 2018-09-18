package com.yl.wanandroid.service.interfaces;

import com.yl.wanandroid.service.HttpResponse;

public interface ResponseListener<T> {
    void onSuccess(HttpResponse<T> response);
}
