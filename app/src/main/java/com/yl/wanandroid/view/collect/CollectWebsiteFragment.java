package com.yl.wanandroid.view.collect;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yl.wanandroid.R;
import com.yl.wanandroid.base.BaseMvpFragment;
import com.yl.wanandroid.base.OnItemClickListener;
import com.yl.wanandroid.presenter.CollectPresenter;
import com.yl.wanandroid.service.dto.Articles;
import com.yl.wanandroid.service.dto.CollectItem;
import com.yl.wanandroid.service.dto.Website;
import com.yl.wanandroid.utils.ViewUtils;
import com.yl.wanandroid.view.WebActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CollectWebsiteFragment extends BaseMvpFragment<CollectView, CollectPresenter> implements CollectView, OnRefreshListener, OnItemClickListener {
    @BindView(R.id.srl_list)
    SmartRefreshLayout srlList;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
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
        rvList.setLayoutManager(new LinearLayoutManager(mActivity));
        ViewUtils.addItemDivider(mActivity, ViewUtils.VERTICAL, rvList, R.drawable.shape_rv_divider);
        ViewUtils.addItemDivider(mActivity, ViewUtils.HORIZONTAL, rvList, R.drawable.shape_rv_horizontal_divider);
    }

    @Override
    protected void initListener() {
        srlList.setEnableLoadMore(false);
        srlList.setOnRefreshListener(this);
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

    }

    @Override
    public void showCollectWebsites(List<Website> list) {
        srlList.finishRefresh();
        srlList.finishLoadMore();
        this.list.clear();
        this.list.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getCollectedWebsites();
    }

    @Override
    public void onClick(View view, int position) {
        CollectItem item = list.get(position);
        WebActivity.openWebPage(mActivity, "http://" + item.getLink(), view.isSelected(),
                item.getItemOriginId(), item.getItemId());
    }
}
