package com.yl.wanandroid.view.collect;

import com.yl.wanandroid.base.BaseView;
import com.yl.wanandroid.service.dto.Articles;

import java.util.List;

public interface CollectView extends BaseView {
    void showCollectArticles(Articles data, boolean isRefresh);

    void showCollectWebsites(List<Articles.Article> list);
}
