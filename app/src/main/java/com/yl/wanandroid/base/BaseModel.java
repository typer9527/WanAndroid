package com.yl.wanandroid.base;

import com.yl.wanandroid.service.interfaces.APIService;
import com.yl.wanandroid.service.interfaces.ErrorListener;
import com.yl.wanandroid.service.HttpResponse;
import com.yl.wanandroid.service.interfaces.ResponseListener;
import com.yl.wanandroid.service.RetrofitFactory;
import com.yl.wanandroid.service.RxService;

import io.reactivex.Observable;

public abstract class BaseModel {
    private RxService rxService = new RxService();
    protected APIService apiService = RetrofitFactory.getInstance().getApiService();

    protected <T> void add(Observable<HttpResponse<T>> observable, ResponseListener<T> listener, ErrorListener errorListener) {
        rxService.add(observable, listener, errorListener);
    }
}
