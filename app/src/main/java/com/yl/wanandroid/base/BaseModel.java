package com.yl.wanandroid.base;

import com.yl.wanandroid.service.RetrofitFactory;
import com.yl.wanandroid.service.RxService;
import com.yl.wanandroid.service.interfaces.APIService;

public class BaseModel {
    protected RxService rxService;
    protected APIService apiService;

    public BaseModel() {
        this.rxService = new RxService();
        this.apiService = RetrofitFactory.getInstance().getApiService();
    }
}
