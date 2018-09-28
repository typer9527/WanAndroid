package com.yl.wanandroid.base;

import com.yl.wanandroid.service.RetrofitFactory;
import com.yl.wanandroid.service.RxService;
import com.yl.wanandroid.service.interfaces.APIService;

public class BaseModel {
    protected final RxService rxService;
    protected final APIService apiService;

    protected BaseModel() {
        this.rxService = new RxService();
        this.apiService = RetrofitFactory.getInstance().getApiService();
    }
}
