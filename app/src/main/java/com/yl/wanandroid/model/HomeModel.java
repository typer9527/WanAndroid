package com.yl.wanandroid.model;

import com.yl.wanandroid.base.BaseModel;
import com.yl.wanandroid.service.dto.Articles;
import com.yl.wanandroid.service.interfaces.ErrorListener;
import com.yl.wanandroid.service.interfaces.ResponseListener;

public class HomeModel extends BaseModel {

    public void getArticleList(int index, ResponseListener<Articles> listener, ErrorListener errorListener) {
        rxService.add(apiService.getArticleList(index), listener, errorListener);
    }
}
