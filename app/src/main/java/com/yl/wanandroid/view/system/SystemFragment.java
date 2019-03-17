package com.yl.wanandroid.view.system;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yl.wanandroid.R;
import com.yl.wanandroid.base.BaseMvpFragment;
import com.yl.wanandroid.model.SystemModel;
import com.yl.wanandroid.presenter.SystemPresenter;
import com.yl.wanandroid.service.dto.TreeChild;
import com.yl.wanandroid.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SystemFragment extends BaseMvpFragment<SystemView, SystemPresenter> implements SystemView, OnRefreshListener {
    @BindView(R.id.srl_system)
    SmartRefreshLayout srlSystem;
    @BindView(R.id.rv_system)
    RecyclerView rvSystem;
    private List<TreeChild> trees;
    private SystemAdapter adapter;

    @Override
    protected SystemPresenter initPresenter() {
        return new SystemPresenter(new SystemModel());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_system;
    }

    @Override
    protected void initView(Bundle arguments) {
        rvSystem.setLayoutManager(new LinearLayoutManager(mActivity));
        ViewUtils.addItemDivider(mActivity, ViewUtils.VERTICAL, rvSystem, ViewUtils.DIVIDER_DEFAULT);
    }

    @Override
    protected void initListener() {
        srlSystem.setOnRefreshListener(this);
    }

    @Override
    protected void initData() {
        trees = new ArrayList<>();
        adapter = new SystemAdapter(trees);
        rvSystem.setAdapter(adapter);
        srlSystem.autoRefresh();
    }

    @Override
    public void showSystemTree(List<TreeChild> children) {
        srlSystem.finishRefresh();
        trees.clear();
        trees.addAll(children);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getSystemTree();
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
        srlSystem.finishRefresh();
    }

    @Override
    public void onTokenInvalid(String errorMsg) {
        super.onTokenInvalid(errorMsg);
        srlSystem.finishRefresh();
    }
}
