package com.yl.wanandroid.view.collect;

import com.yl.wanandroid.base.BaseView;
import com.yl.wanandroid.service.dto.Articles;

public interface CollectView extends BaseView {
    void showCollectArticles(Articles data, boolean isRefresh);

    void onRevokeSucceed();

    void onCollectSucceed();
}
