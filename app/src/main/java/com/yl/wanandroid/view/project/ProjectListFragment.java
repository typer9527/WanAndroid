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
import com.yl.wanandroid.base.OnItemClickListener;
import com.yl.wanandroid.model.ProjectModel;
import com.yl.wanandroid.presenter.ProjectPresenter;
import com.yl.wanandroid.service.dto.Articles;
import com.yl.wanandroid.service.dto.ProjectCategory;
import com.yl.wanandroid.app.Constant;
import com.yl.wanandroid.utils.ViewUtils;
import com.yl.wanandroid.view.WebActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProjectListFragment extends BaseMvpFragment<ProjectView, ProjectPresenter> implements ProjectView, OnRefreshLoadMoreListener, OnItemClickListener {
    @BindView(R.id.srl_list)
    SmartRefreshLayout srl_project;
    @BindView(R.id.rv_list)
    RecyclerView rv_project;
    private int currentIndex, categoryId;
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
        return new ProjectPresenter(new ProjectModel());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rv_list;
    }

    @Override
    protected void initView(Bundle arguments) {
        categoryId = arguments.getInt(Constant.KEY_CATEGORY_ID, -1);
        rv_project.setLayoutManager(new LinearLayoutManager(mActivity));
        ViewUtils.addItemDivider(mActivity, rv_project, ViewUtils.DIVIDER_DEFAULT);
    }

    @Override
    protected void initListener() {
        srl_project.setOnRefreshLoadMoreListener(this);
    }

    @Override
    protected void initData() {
        currentIndex = 0;
        list = new ArrayList<>();
        adapter = new ProjectAdapter(list);
        adapter.setOnItemClickListener(this);
        rv_project.setAdapter(adapter);
        srl_project.autoRefresh();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getProjectList(++currentIndex, categoryId, false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        currentIndex = 0;
        mPresenter.getProjectList(currentIndex, categoryId, true);
    }

    @Override
    public void onNetError() {
        super.onNetError();
        srl_project.finishRefresh();
        srl_project.finishLoadMore();
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
        srl_project.finishRefresh();
        srl_project.finishLoadMore();
    }

    @Override
    public void showProjectCategory(List<ProjectCategory> list) {

    }

    @Override
    public void showProjectList(Articles data, boolean isRefresh) {
        srl_project.finishRefresh();
        srl_project.finishLoadMore();
        if (isRefresh) list.clear();
        list.addAll(data.getArticles());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(int position) {
        Articles.Article project = list.get(position);
        WebActivity.openWebPage(mActivity, project.getLink(), project.isCollect());
    }
}
