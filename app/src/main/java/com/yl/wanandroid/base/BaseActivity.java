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
    private ProgressDialog mProgressDialog;

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
    protected abstract int getLayoutId();

    protected abstract void initView();

    protected void initData() {

    }

    protected void initListener() {

    }

    protected void showProgressDialog(String msg) {
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

    protected void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    protected void showNetError() {
        dismissProgressDialog();
        ToastUtils.showShort(this, getString(R.string.label_net_error));
    }

    void showTokenInvalid(String errorMsg) {
        dismissProgressDialog();
        ToastUtils.showShort(this, getString(R.string.label_login_expired));
    }

    void showError(String errorMsg) {
        dismissProgressDialog();
        ToastUtils.showShort(this, errorMsg);
    }

    protected void showMsg(String msg) {
        ToastUtils.showShort(this, msg);
    }
}

