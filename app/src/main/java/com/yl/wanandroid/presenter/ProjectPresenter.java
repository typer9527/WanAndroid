package com.yl.wanandroid.presenter;

import com.yl.wanandroid.base.BasePresenter;
import com.yl.wanandroid.model.ProjectModel;
import com.yl.wanandroid.service.HttpResponse;
import com.yl.wanandroid.service.dto.Articles;
import com.yl.wanandroid.service.dto.ProjectCategory;
import com.yl.wanandroid.service.interfaces.ResponseListener;
import com.yl.wanandroid.view.project.ProjectView;

import java.util.List;

public class ProjectPresenter extends BasePresenter<ProjectView> {
    private ProjectModel projectModel;

    public ProjectPresenter() {
        this.projectModel = new ProjectModel();
    }

    public void getProjectCategory() {
        projectModel.getProjectCategory(new ResponseListener<List<ProjectCategory>>() {
            @Override
            public void onSuccess(HttpResponse<List<ProjectCategory>> response) {
                mView.showProjectCategory(response.getData());
            }
        }, this);
    }

    public void getProjectList(int index, int id, final boolean isRefresh) {
        projectModel.getProjectList(index, id, new ResponseListener<Articles>() {
            @Override
            public void onSuccess(HttpResponse<Articles> response) {
                mView.showProjectList(response.getData(), isRefresh);
            }
        }, this);
    }
}
