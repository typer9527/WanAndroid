package com.yl.wanandroid.base;

import android.util.Log;

import com.yl.wanandroid.R;
import com.yl.wanandroid.utils.ToastUtils;

/**
 * MVP模式-BaseMvpActivity
 */
public abstract class BaseMvpActivity<V extends BaseView, P extends BasePresenter<V>> extends BaseActivity implements BaseView {
    private static final String TAG = "BaseMvpActivity";
    protected P mPresenter;

    @Override
    public void initBasePresenter() {
        mPresenter = initPresenter();
        mPresenter.attach((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detach();
        }
    }

    public abstract P initPresenter();

    @Override
    public void onNetError() {
        dismissProgressDialog();
        ToastUtils.showShort(this, getString(R.string.label_net_error));
    }

    @Override
    public void onTokenInvalid(String errorMsg) {
        Log.e(TAG, "onTokenInvalid: " + errorMsg);
        dismissProgressDialog();
        ToastUtils.showShort(this, getString(R.string.label_login_expired));
    }

    @Override
    public void onError(String errorMsg) {
        dismissProgressDialog();
        ToastUtils.showShort(this, errorMsg);
    }
}
