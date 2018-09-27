package com.yl.wanandroid.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yl.wanandroid.R;
import com.yl.wanandroid.app.Constant;
import com.yl.wanandroid.base.BaseFragment;
import com.yl.wanandroid.utils.PrefsUtils;
import com.yl.wanandroid.view.user.LoginActivity;
import com.yl.wanandroid.view.user.SettingActivity;

import butterknife.BindView;

public class MineFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.ll_user)
    LinearLayout llUser;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.ll_collect)
    LinearLayout llCollect;
    @BindView(R.id.ll_setting)
    LinearLayout llSetting;
    private boolean isLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(Bundle arguments) {
        isLogin = PrefsUtils.getBoolean(mActivity, Constant.KEY_IS_LOGON);
        if (isLogin) {
            tvLogin.setText(PrefsUtils.getString(mActivity, Constant.KEY_USER_NAME));
        }
    }

    @Override
    protected void initListener() {
        llUser.setOnClickListener(this);
        llCollect.setOnClickListener(this);
        llSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_user:
            case R.id.tv_login:
                if (!isLogin) startActivity(new Intent(mActivity, LoginActivity.class));
                break;
            case R.id.ll_collect:
                break;
            case R.id.ll_setting:
                startActivity(new Intent(mActivity, SettingActivity.class));
                break;
            default:
        }
    }
}
