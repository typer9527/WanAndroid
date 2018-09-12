package com.yl.wanandroid.view;

import com.yl.wanandroid.base.BaseView;
import com.yl.wanandroid.service.Articles;

public interface HomeView extends BaseView {
    void showArticleList(Articles articles);
}
