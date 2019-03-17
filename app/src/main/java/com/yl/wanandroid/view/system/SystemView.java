package com.yl.wanandroid.view.system;

import com.yl.wanandroid.base.BaseView;
import com.yl.wanandroid.service.dto.Articles;
import com.yl.wanandroid.service.dto.ProjectCategory;
import com.yl.wanandroid.service.dto.TreeChild;

import java.util.List;

public interface SystemView extends BaseView {
    void showSystemTree(List<TreeChild> children);
}
