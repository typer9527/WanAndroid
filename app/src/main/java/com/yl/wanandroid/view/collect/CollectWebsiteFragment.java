package com.yl.wanandroid.view.collect;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yl.wanandroid.R;
import com.yl.wanandroid.base.BaseMvpFragment;
import com.yl.wanandroid.presenter.CollectPresenter;

import butterknife.BindView;

public class CollectWebsiteFragment extends BaseMvpFragment<CollectView, CollectPresenter> implements CollectView {
    @BindView(R.id.srl_list)
    SmartRefreshLayout srlList;
    @BindView(R.id.rv_list)
    RecyclerView rvList;

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

    }
}
