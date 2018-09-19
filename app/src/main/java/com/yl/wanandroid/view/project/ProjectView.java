package com.yl.wanandroid.view.project;

import com.yl.wanandroid.base.BaseView;
import com.yl.wanandroid.service.dto.Articles;
import com.yl.wanandroid.service.dto.ProjectCategory;

import java.util.List;

public interface ProjectView extends BaseView {
    void showProjectCategory(List<ProjectCategory> list);

    void showProjectList(Articles data, boolean isRefresh);
}
