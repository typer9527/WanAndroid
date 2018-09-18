package com.yl.wanandroid.view.home;

import com.yl.wanandroid.base.BaseView;
import com.yl.wanandroid.service.dto.Articles;
import com.yl.wanandroid.service.dto.BannerData;
import com.youth.banner.Banner;

import java.util.List;

public interface HomeView extends BaseView {
    void showArticleList(Articles articles, boolean isRefresh);

    void showBanners(List<BannerData> data);
}
