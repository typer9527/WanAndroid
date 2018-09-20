package com.yl.wanandroid.base;

import android.util.Log;

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
        showNetError();
    }

    @Override
    public void onTokenInvalid(String errorMsg) {
        Log.e(TAG, "onTokenInvalid: " + errorMsg);
        showTokenInvalid(errorMsg);
    }

    @Override
    public void onError(String errorMsg) {
        showError(errorMsg);
    }
}
