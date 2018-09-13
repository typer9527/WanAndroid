package com.yl.wanandroid.view.home;

import com.yl.wanandroid.base.BaseView;
import com.yl.wanandroid.service.dto.Articles;

public interface HomeView extends BaseView {
    void showArticleList(Articles articles);
}
