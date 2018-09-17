package com.yl.wanandroid.presenter;

import com.yl.wanandroid.base.BasePresenter;
import com.yl.wanandroid.model.HomeModel;
import com.yl.wanandroid.service.dto.Articles;
import com.yl.wanandroid.service.interfaces.ResponseListener;
import com.yl.wanandroid.view.home.HomeView;

public class HomePresenter extends BasePresenter<HomeView> {
    private HomeModel homeModel;

    public HomePresenter(HomeModel homeModel) {
        this.homeModel = homeModel;
    }

    public void getArticleList(int index) {
        homeModel.getArticleList(index, new ResponseListener<Articles>() {
            @Override
            public void onSuccess(Articles data) { mView.showArticleList(data);
            }
        }, this);
    }
}
