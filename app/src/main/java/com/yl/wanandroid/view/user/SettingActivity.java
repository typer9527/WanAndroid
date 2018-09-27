package com.yl.wanandroid.view.user;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yl.wanandroid.R;
import com.yl.wanandroid.base.BaseMvpActivity;
import com.yl.wanandroid.presenter.UserPresenter;

import butterknife.BindView;

public class SettingActivity extends BaseMvpActivity<UserView, UserPresenter> implements UserView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public UserPresenter initPresenter() {
        return new UserPresenter();
    }

    @Override
    public void onLoginOrRegisterSucceed() {

    }

    @Override
    public void onSignOutSucceed() {

    }
}
