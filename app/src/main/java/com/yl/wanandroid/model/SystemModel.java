package com.yl.wanandroid.model;

import com.yl.wanandroid.base.BaseModel;
import com.yl.wanandroid.service.dto.TreeChild;
import com.yl.wanandroid.service.interfaces.ErrorListener;
import com.yl.wanandroid.service.interfaces.ResponseListener;

import java.util.List;

public class SystemModel extends BaseModel {
    public void getSsystemTreeData(ResponseListener<List<TreeChild>> listener, ErrorListener errorListener) {
        rxService.add(apiService.getSystemTree(), listener, errorListener);
    }
}
