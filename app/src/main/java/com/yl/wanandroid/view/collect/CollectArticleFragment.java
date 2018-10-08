package com.yl.wanandroid.view.collect;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yl.wanandroid.R;
import com.yl.wanandroid.base.BaseMvpFragment;
import com.yl.wanandroid.presenter.CollectPresenter;
import com.yl.wanandroid.service.dto.Articles;
import com.yl.wanandroid.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CollectArticleFragment extends BaseMvpFragment<CollectView, CollectPresenter> implements CollectView, OnRefreshLoadMoreListener {
    @BindView(R.id.srl_list)
    SmartRefreshLayout srlList;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private int currentIndex;
    private List<Articles.Article> list;
    private CollectAdapter adapter;

    @Override
    protected CollectPresenter initPresenter() {
        return new CollectPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rv_list;
    }

    @Override
    protected void initView(Bundle arguments) {
        ViewUtils.setViewMargin(rvList, 10, 0, 0, 0);
        rvList.setLayoutManager(new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL));
        ViewUtils.addItemDivider(mActivity, ViewUtils.HORIZONTAL, rvList, R.drawable.shape_rv_horizontal_divider);
    }

    @Override
    protected void initListener() {
        srlList.setOnRefreshLoadMoreListener(this);
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        adapter = new CollectAdapter(list);
        rvList.setAdapter(adapter);
        srlList.autoRefresh();
    }

    @Override
    public void showCollectArticles(Articles data, boolean isRefresh) {
        srlList.finishRefresh();
        srlList.finishLoadMore();
        if (isRefresh) list.clear();
        list.addAll(data.getArticles());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRevokeSucceed() {

    }

    @Override
    public void onCollectSucceed() {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getCollectedArticleList(++currentIndex, false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        currentIndex = 0;
        mPresenter.getCollectedArticleList(currentIndex, true);
    }
}
