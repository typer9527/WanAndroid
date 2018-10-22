package com.yl.wanandroid.view.collect;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yl.wanandroid.R;
import com.yl.wanandroid.base.BaseMvpFragment;
import com.yl.wanandroid.base.OnItemClickListener;
import com.yl.wanandroid.presenter.CollectPresenter;
import com.yl.wanandroid.service.dto.Articles;
import com.yl.wanandroid.service.dto.CollectItem;
import com.yl.wanandroid.utils.ViewUtils;
import com.yl.wanandroid.view.WebActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CollectArticleFragment extends BaseMvpFragment<CollectView, CollectPresenter> implements CollectView, OnRefreshLoadMoreListener, OnItemClickListener {
    @BindView(R.id.srl_list)
    SmartRefreshLayout srlList;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private int totalPage, currentIndex;
    private List<CollectItem> list;
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
        rvList.setLayoutManager(new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL));
        ViewUtils.addItemDivider(mActivity, ViewUtils.VERTICAL, rvList, R.drawable.shape_rv_divider);
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
        adapter.setOnItemClickListener(this);
        rvList.setAdapter(adapter);
        srlList.autoRefresh();
    }

    @Override
    public void showCollectArticles(Articles data, boolean isRefresh) {
        srlList.finishRefresh();
        srlList.finishLoadMore();
        totalPage = data.getPageCount();
        if (isRefresh) list.clear();
        list.addAll(data.getArticles());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showCollectWebsites(List<Articles.Article> list) {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (++currentIndex < totalPage)
            mPresenter.getCollectedArticleList(currentIndex, false);
        else {
            showMsg(getString(R.string.label_no_more_data));
            srlList.finishLoadMore(0, true, true);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        currentIndex = 0;
        mPresenter.getCollectedArticleList(currentIndex, true);
    }

    @Override
    public void onClick(View view, int position) {
        CollectItem item = list.get(position);
        WebActivity.openWebPage(mActivity, item.getLink(), view.isSelected(),
                item.getItemOriginId(), item.getItemId());
    }
}
