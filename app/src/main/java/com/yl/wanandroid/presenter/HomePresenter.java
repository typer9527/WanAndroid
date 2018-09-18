package com.yl.wanandroid.presenter;

import com.yl.wanandroid.base.BasePresenter;
import com.yl.wanandroid.model.HomeModel;
import com.yl.wanandroid.service.HttpResponse;
import com.yl.wanandroid.service.dto.Articles;
import com.yl.wanandroid.service.dto.BannerData;
import com.yl.wanandroid.service.interfaces.ResponseListener;
import com.yl.wanandroid.view.home.HomeView;
import com.youth.banner.Banner;

import java.util.List;

public class HomePresenter extends BasePresenter<HomeView> {
    private HomeModel homeModel;

    public HomePresenter(HomeModel homeModel) {
        this.homeModel = homeModel;
    }

    public void getBanners() {
        homeModel.getBanners(new ResponseListener<List<BannerData>>() {
            @Override
            public void onSuccess(HttpResponse<List<BannerData>> response) {
                mView.showBanners(response.getData());
            }
        }, this);
    }

    public void getArticleList(int index, final boolean isRefresh) {
        homeModel.getArticleList(index, new ResponseListener<Articles>() {
            @Override
            public void onSuccess(HttpResponse<Articles> response) {
                mView.showArticleList(response.getData(), isRefresh);
            }
        }, this);
    }

    public void getProjectList(int index, int id, final boolean isRefresh) {
        homeModel.getProjectList(index, id, new ResponseListener<Articles>() {
            @Override
            public void onSuccess(HttpResponse<Articles> response) {
                mView.showArticleList(response.getData(), isRefresh);
            }
        }, this);
    }
}
