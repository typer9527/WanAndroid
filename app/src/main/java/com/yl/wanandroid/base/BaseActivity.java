package com.yl.wanandroid.base;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.yl.wanandroid.R;
import com.yl.wanandroid.utils.ToastUtils;

import butterknife.ButterKnife;

/**
 * Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initBasePresenter();
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
        initCreate(savedInstanceState);
    }

    public void initCreate(Bundle savedInstanceState) {

    }

    public void initBasePresenter() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    @LayoutRes
    public abstract int getLayoutId();

    public abstract void initView();

    public void initData() {

    }

    public void initListener() {

    }

    public void showProgressDialog(String msg) {
        if (mProgressDialog == null) {
            if (TextUtils.isEmpty(msg)) {
                mProgressDialog = ProgressDialog.show(this, "", getString(R.string.label_loading), true, false);
                return;
            }
            mProgressDialog = ProgressDialog.show(this, "", msg, true, false);
        } else {
            mProgressDialog.show();
        }
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    public void showNetError() {
        dismissProgressDialog();
        ToastUtils.showShort(this, getString(R.string.label_net_error));
    }

    public void showTokenInvalid(String errorMsg) {
        dismissProgressDialog();
        ToastUtils.showShort(this, getString(R.string.label_login_expired));
    }

    public void showError(String errorMsg) {
        dismissProgressDialog();
        ToastUtils.showShort(this, errorMsg);
    }
}

