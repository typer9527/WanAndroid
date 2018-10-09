package com.yl.wanandroid.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * MVP模式-BaseMvpFragment
 */
public abstract class BaseMvpFragment<V extends BaseView, P extends BasePresenter<V>> extends BaseFragment implements BaseView {
    protected P mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPresenter = initPresenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.attach((V) this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detach();
        }
    }

    protected abstract P initPresenter();

    @Override
    public void onNetError() {
        showNetError();
    }

    @Override
    public void onTokenInvalid(String errorMsg) {
        showTokenInvalid(errorMsg);
    }

    @Override
    public void onError(String errorMsg) {
        showError(errorMsg);
    }
}
