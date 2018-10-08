package com.yl.wanandroid.presenter;

import com.yl.wanandroid.base.BasePresenter;
import com.yl.wanandroid.model.CollectModel;
import com.yl.wanandroid.service.HttpResponse;
import com.yl.wanandroid.service.dto.Articles;
import com.yl.wanandroid.service.dto.EmptyData;
import com.yl.wanandroid.service.interfaces.ResponseListener;
import com.yl.wanandroid.view.collect.CollectView;

public class CollectPresenter extends BasePresenter<CollectView> {
    private final CollectModel model;

    public CollectPresenter() {
        this.model = new CollectModel();
    }

    public void getCollectedArticleList(int index, final boolean isRefresh) {
        model.getCollectedArticles(index, new ResponseListener<Articles>() {
            @Override
            public void onSuccess(HttpResponse<Articles> response) {
                mView.showCollectArticles(response.getData(), isRefresh);
            }
        }, this);
    }

    public void revokeCollectArticle(int id) {
        model.revokeCollectArticle(id, new ResponseListener<EmptyData>() {
            @Override
            public void onSuccess(HttpResponse<EmptyData> response) {
                mView.onRevokeSucceed();
            }
        }, this);
    }

    public void collectArticle(int id) {
        model.collectArticle(id, new ResponseListener<EmptyData>() {
            @Override
            public void onSuccess(HttpResponse<EmptyData> response) {
                mView.onCollectSucceed();
            }
        }, this);
    }
}
