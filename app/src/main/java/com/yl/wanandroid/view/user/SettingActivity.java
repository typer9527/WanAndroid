package com.yl.wanandroid.view.user;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yl.wanandroid.R;
import com.yl.wanandroid.app.Constant;
import com.yl.wanandroid.base.BaseMvpActivity;
import com.yl.wanandroid.presenter.UserPresenter;
import com.yl.wanandroid.utils.AppUtils;
import com.yl.wanandroid.utils.PrefsUtils;
import com.yl.wanandroid.utils.ToastUtils;
import com.yl.wanandroid.view.IntroActivity;

import butterknife.BindView;

public class SettingActivity extends BaseMvpActivity<UserView, UserPresenter> implements UserView, View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_clean_cache)
    TextView tvCleanCache;
    @BindView(R.id.tv_help_and_callback)
    TextView tvHelpAndCallback;
    @BindView(R.id.tv_about)
    TextView tvAbout;
    @BindView(R.id.rl_sign_out)
    RelativeLayout rlSignOut;
    @BindView(R.id.tv_app_info)
    TextView tvAppInfo;


    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        if (!PrefsUtils.getBoolean(this, Constant.KEY_IS_LOGON)) {
            rlSignOut.setVisibility(View.GONE);
        }
        tvAppInfo.setText(getString(R.string.label_app_info,
                AppUtils.getName(this), AppUtils.getVersionName(this)));
    }

    @Override
    public void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvCleanCache.setOnClickListener(this);
        tvHelpAndCallback.setOnClickListener(this);
        tvAbout.setOnClickListener(this);
        rlSignOut.setOnClickListener(this);
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
        PrefsUtils.setBoolean(this, Constant.KEY_IS_LOGON, false);
        PrefsUtils.setString(this, Constant.KEY_USER_NAME, null);
        PrefsUtils.setString(this, Constant.KEY_LOCAL_COOKIE, null);
        dismissProgressDialog();
        ToastUtils.showShort(this, getString(R.string.label_has_signed_out));
        Intent intent = new Intent(this, IntroActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_clean_cache:
                break;
            case R.id.tv_help_and_callback:
                break;
            case R.id.tv_about:
                break;
            case R.id.rl_sign_out:
                showProgressDialog(getString(R.string.label_on_signing_out));
                mPresenter.signOut();
                break;
            default:
        }
    }
}
