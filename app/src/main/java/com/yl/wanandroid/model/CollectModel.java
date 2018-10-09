package com.yl.wanandroid.model;

import com.yl.wanandroid.base.BaseModel;
import com.yl.wanandroid.service.dto.Articles;
import com.yl.wanandroid.service.dto.EmptyData;
import com.yl.wanandroid.service.interfaces.ErrorListener;
import com.yl.wanandroid.service.interfaces.ResponseListener;

public class CollectModel extends BaseModel {
    public void getCollectedArticles(int index, ResponseListener<Articles> listener, ErrorListener errorListener) {
        rxService.add(apiService.getCollectedArticles(index), listener, errorListener);
    }

    public void collectArticle(int index, ResponseListener<EmptyData> listener, ErrorListener errorListener) {
        rxService.add(apiService.collectArticle(index), listener, errorListener);
    }

    public void revokeCollectArticle(int id, int originId, ResponseListener<EmptyData> listener, ErrorListener errorListener) {
        rxService.add(apiService.revokeCollectArticle(id, originId), listener, errorListener);
    }

    public void revokeListArticle(int id, ResponseListener<EmptyData> listener, ErrorListener errorListener) {
        rxService.add(apiService.revokeCollectArticle(id), listener, errorListener);
    }
}
