package com.yl.wanandroid.view.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yl.wanandroid.R;
import com.yl.wanandroid.base.BaseMvpFragment;
import com.yl.wanandroid.base.OnItemClickListener;
import com.yl.wanandroid.presenter.HomePresenter;
import com.yl.wanandroid.service.dto.Articles;
import com.yl.wanandroid.service.dto.BannerData;
import com.yl.wanandroid.utils.ViewUtils;
import com.yl.wanandroid.view.AgentWebActivity;
import com.yl.wanandroid.view.WebActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseMvpFragment<HomeView, HomePresenter> implements HomeView, OnRefreshLoadMoreListener, OnItemClickListener, OnBannerListener {
    @BindView(R.id.abl_home)
    AppBarLayout ablHome;
    @BindView(R.id.banner_home)
    Banner bannerHome;
    @BindView(R.id.srl_home)
    SmartRefreshLayout srlHome;
    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    @BindView(R.id.fab_to_top)
    FloatingActionButton fabToTop;
    private int totalPage, currentIndex;
    private ArrayList<Articles.Article> list;
    private List<BannerData> banners;
    private ArticleAdapter adapter;

    @Override
    public HomePresenter initPresenter() {
        return new HomePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(Bundle arguments) {
        bannerHome.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        bannerHome.setImageLoader(new BannerLoader());
        rvHome.setLayoutManager(new LinearLayoutManager(mActivity));
        ViewUtils.addItemDivider(mActivity, ViewUtils.VERTICAL, rvHome, R.drawable.shape_rv_divider);
    }

    @Override
    protected void initListener() {
        bannerHome.setOnBannerListener(this);
        srlHome.setOnRefreshLoadMoreListener(this);
        fabToTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvHome.smoothScrollToPosition(0);
                ablHome.setExpanded(true, true);
            }
        });
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        banners = new ArrayList<>();
        adapter = new ArticleAdapter(list);
        adapter.setOnItemClickListener(this);
        bannerHome.setImages(new ArrayList<>());
        bannerHome.start();
        rvHome.setAdapter(adapter);
        currentIndex = 0;
        srlHome.autoRefresh();
    }

    @Override
    public void showArticleList(Articles articles, boolean isRefresh) {
        srlHome.finishRefresh();
        srlHome.finishLoadMore();
        totalPage = articles.getPageCount();
        if (isRefresh) list.clear();
        list.addAll(articles.getArticles());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showBanners(List<BannerData> banners) {
        List<String> imageUrls = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (BannerData banner : banners) {
            imageUrls.add(banner.getImagePath());
            titles.add(banner.getTitle());
        }
        this.banners.clear();
        this.banners.addAll(banners);
        bannerHome.update(imageUrls, titles);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (++currentIndex < totalPage)
            mPresenter.getArticleList(currentIndex, false);
        else {
            showMsg(getString(R.string.label_no_more_data));
            srlHome.finishLoadMore(0, true, true);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        currentIndex = 0;
        mPresenter.getArticleList(currentIndex, true);
        mPresenter.getBanners();
    }

    @Override
    public void onNetError() {
        super.onNetError();
        srlHome.finishRefresh();
        srlHome.finishLoadMore();
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
        srlHome.finishRefresh();
        srlHome.finishLoadMore();
    }

    @Override
    public void onClick(View view, int position) {
        Articles.Article article = list.get(position);
        WebActivity.openWebPage(mActivity, article.getLink(), view.isSelected(), article.getOriginId(), article.getId());
    }

    @Override
    public void OnBannerClick(int position) {
        AgentWebActivity.openExternalUrl(mActivity, banners.get(position).getUrl());
    }

    class BannerLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }
}
