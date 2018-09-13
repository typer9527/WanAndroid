package com.yl.wanandroid.view.user;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yl.wanandroid.R;
import com.yl.wanandroid.base.BaseActivity;

import butterknife.BindView;

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
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
}
