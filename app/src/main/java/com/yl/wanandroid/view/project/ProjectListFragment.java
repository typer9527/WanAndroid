package com.yl.wanandroid.view.project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yl.wanandroid.R;
import com.yl.wanandroid.app.Constant;
import com.yl.wanandroid.base.BaseMvpFragment;
import com.yl.wanandroid.base.OnItemClickListener;
import com.yl.wanandroid.presenter.ProjectPresenter;
import com.yl.wanandroid.service.dto.Articles;
import com.yl.wanandroid.service.dto.ProjectCategory;
import com.yl.wanandroid.utils.ViewUtils;
import com.yl.wanandroid.view.WebActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProjectListFragment extends BaseMvpFragment<ProjectView, ProjectPresenter> implements ProjectView, OnRefreshLoadMoreListener, OnItemClickListener {
    @BindView(R.id.srl_list)
    SmartRefreshLayout srlProject;
    @BindView(R.id.rv_list)
    RecyclerView rvProject;
    private int totalPage, currentIndex, categoryId;
    private List<Articles.Article> list;
    private ProjectAdapter adapter;

    public static ProjectListFragment newInstance(int categoryId) {
        Bundle args = new Bundle();
        args.putInt(Constant.KEY_CATEGORY_ID, categoryId);
        ProjectListFragment fragment = new ProjectListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ProjectPresenter initPresenter() {
        return new ProjectPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rv_list;
    }

    @Override
    protected void initView(Bundle arguments) {
        categoryId = arguments.getInt(Constant.KEY_CATEGORY_ID, -1);
        rvProject.setLayoutManager(new LinearLayoutManager(mActivity));
        ViewUtils.addItemDivider(mActivity, ViewUtils.VERTICAL, rvProject, ViewUtils.DIVIDER_DEFAULT);
    }

    @Override
    protected void initListener() {
        srlProject.setOnRefreshLoadMoreListener(this);
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        adapter = new ProjectAdapter(list);
        adapter.setOnItemClickListener(this);
        rvProject.setAdapter(adapter);
        srlProject.autoRefresh();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (++currentIndex <= totalPage)
            mPresenter.getProjectList(currentIndex, categoryId, false);
        else {
            srlProject.finishLoadMore();
            srlProject.setNoMoreData(true);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        currentIndex = 0;
        mPresenter.getProjectList(currentIndex, categoryId, true);
    }

    @Override
    public void onNetError() {
        super.onNetError();
        srlProject.finishRefresh();
        srlProject.finishLoadMore();
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
        srlProject.finishRefresh();
        srlProject.finishLoadMore();
    }

    @Override
    public void showProjectCategory(List<ProjectCategory> list) {

    }

    @Override
    public void showProjectList(Articles data, boolean isRefresh) {
        srlProject.finishRefresh();
        srlProject.finishLoadMore();
        totalPage = data.getPageCount();
        if (isRefresh) list.clear();
        list.addAll(data.getArticles());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view, int position) {
        Articles.Article project = list.get(position);
        WebActivity.openWebPage(mActivity, project.getLink(), project.isCollect(), project.getOriginId(), project.getId());
    }
}
