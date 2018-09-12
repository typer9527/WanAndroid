package com.yl.wanandroid.model;

import com.yl.wanandroid.service.Articles;
import com.yl.wanandroid.service.ErrorListener;
import com.yl.wanandroid.service.ResponseListener;
import com.yl.wanandroid.service.RetrofitFactory;
import com.yl.wanandroid.service.RxService;

public class HomeModel {

    public void getArticleList(int index, ResponseListener<Articles> listener, ErrorListener errorListener) {
        new RxService().add(RetrofitFactory.getInstance().getApiService().getArticleList(index), listener, errorListener);
    }
}
