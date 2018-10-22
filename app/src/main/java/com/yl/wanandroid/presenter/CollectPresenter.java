package com.yl.wanandroid.presenter;

import com.yl.wanandroid.base.BasePresenter;
import com.yl.wanandroid.model.CollectModel;
import com.yl.wanandroid.service.HttpResponse;
import com.yl.wanandroid.service.dto.Articles;
import com.yl.wanandroid.service.dto.Website;
import com.yl.wanandroid.service.interfaces.ResponseListener;
import com.yl.wanandroid.view.collect.CollectView;

import java.util.List;

public class CollectPresenter extends BasePresenter<CollectView> {
    private final CollectModel model;

    public CollectPresenter() {
        this.model = new CollectModel();
    }

    public void getCollectedArticleList(final int index, final boolean isRefresh) {
        model.getCollectedArticles(index, new ResponseListener<Articles>() {
            @Override
            public void onSuccess(HttpResponse<Articles> response) {
                mView.showCollectArticles(response.getData(), isRefresh);
            }
        }, this);
    }

    public void getCollectedWebsites() {
        model.getCollectedWebsites(new ResponseListener<List<Website>>() {
            @Override
            public void onSuccess(HttpResponse<List<Website>> response) {
                mView.showCollectWebsites(response.getData());
            }
        }, this);
    }
}
