package com.yl.wanandroid.view.project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yl.wanandroid.R;
import com.yl.wanandroid.base.BaseMvpFragment;
import com.yl.wanandroid.model.HomeModel;
import com.yl.wanandroid.presenter.HomePresenter;
import com.yl.wanandroid.service.dto.Articles;
import com.yl.wanandroid.service.dto.BannerData;
import com.yl.wanandroid.utils.ViewUtils;
import com.yl.wanandroid.view.home.HomeView;

import java.util.List;

import butterknife.BindView;

public class ProjectListFragment extends BaseMvpFragment<HomeView, HomePresenter> implements HomeView, OnRefreshLoadMoreListener {
    @BindView(R.id.srl_home)
    SmartRefreshLayout srlHome;
    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    private int currentIndex;

    @Override
    public HomePresenter initPresenter() {
        return new HomePresenter(new HomeModel());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(Bundle arguments) {
        rvHome.setLayoutManager(new LinearLayoutManager(mActivity));
        ViewUtils.addItemDivider(mActivity, rvHome, R.drawable.shape_rv_divider);
    }

    @Override
    protected void initListener() {
        srlHome.setOnRefreshLoadMoreListener(this);
    }

    @Override
    protected void initData() {
        currentIndex = 0;
    }

    @Override
    public void showArticleList(Articles articles, boolean isRefresh) {
        srlHome.finishRefresh();
        srlHome.finishLoadMore();
    }

    @Override
    public void showBanners(List<BannerData> data) {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getProjectList(++currentIndex, 0, false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        currentIndex = 0;
        mPresenter.getProjectList(currentIndex, 0, true);
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
}
