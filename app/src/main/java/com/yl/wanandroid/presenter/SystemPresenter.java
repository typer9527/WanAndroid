package com.yl.wanandroid.presenter;

import com.yl.wanandroid.base.BasePresenter;
import com.yl.wanandroid.model.SystemModel;
import com.yl.wanandroid.service.HttpResponse;
import com.yl.wanandroid.service.dto.TreeChild;
import com.yl.wanandroid.service.interfaces.ResponseListener;
import com.yl.wanandroid.view.system.SystemView;

import java.util.List;

public class SystemPresenter extends BasePresenter<SystemView> {
    private SystemModel model;

    public SystemPresenter(SystemModel model) {
        this.model = model;
    }

    public void getSystemTree() {
        model.getSsystemTreeData(new ResponseListener<List<TreeChild>>() {
            @Override
            public void onSuccess(HttpResponse<List<TreeChild>> response) {
                mView.showSystemTree(response.getData());
            }
        }, this);
    }
}
