package com.yl.wanandroid.view.project;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Html;

import com.yl.wanandroid.R;
import com.yl.wanandroid.adapter.ViewPagerAdapter;
import com.yl.wanandroid.base.BaseMvpFragment;
import com.yl.wanandroid.model.ProjectModel;
import com.yl.wanandroid.presenter.ProjectPresenter;
import com.yl.wanandroid.service.dto.Articles;
import com.yl.wanandroid.service.dto.ProjectCategory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProjectFragment extends BaseMvpFragment<ProjectView, ProjectPresenter> implements ProjectView {
    @BindView(R.id.tb_project)
    Toolbar tbProject;
    @BindView(R.id.tl_project)
    TabLayout tlProject;
    @BindView(R.id.vp_project)
    ViewPager vpProject;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initView(Bundle arguments) {
        tbProject.inflateMenu(R.menu.menu_toolbar_home);
        tlProject.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    protected void initData() {
        mPresenter.getProjectCategory();
    }

    @Override
    public ProjectPresenter initPresenter() {
        return new ProjectPresenter(new ProjectModel());
    }

    @Override
    public void showProjectCategory(List<ProjectCategory> list) {
        List<String> categories = new ArrayList<>();
        List<Fragment> fragments = new ArrayList<>();
        for (ProjectCategory category : list) {
            categories.add(Html.fromHtml(category.getName()).toString());
            fragments.add(ProjectListFragment.newInstance(category.getId()));
        }
        vpProject.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), fragments, categories));
        tlProject.setupWithViewPager(vpProject);
    }

    @Override
    public void showProjectList(Articles data, boolean isRefresh) {

    }
}
