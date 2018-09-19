package com.yl.wanandroid.model;

import com.yl.wanandroid.base.BaseModel;
import com.yl.wanandroid.presenter.HomePresenter;
import com.yl.wanandroid.service.dto.Articles;
import com.yl.wanandroid.service.dto.BannerData;
import com.yl.wanandroid.service.interfaces.ErrorListener;
import com.yl.wanandroid.service.interfaces.ResponseListener;
import com.youth.banner.Banner;

import java.util.List;

public class HomeModel extends BaseModel {

    public void getArticleList(int index, ResponseListener<Articles> listener, ErrorListener errorListener) {
        rxService.add(apiService.getArticleList(index), listener, errorListener);
    }

    public void getBanners(ResponseListener<List<BannerData>> listener, ErrorListener errorListener) {
        rxService.add(apiService.getBanners(), listener, errorListener);
    }
}
