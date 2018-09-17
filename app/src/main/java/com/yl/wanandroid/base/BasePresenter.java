package com.yl.wanandroid.base;

import com.yl.wanandroid.service.interfaces.ErrorListener;

public abstract class BasePresenter<V extends BaseView> implements ErrorListener {

    protected V mView;

    public void attach(V view) {
        this.mView = view;
    }

    public void detach() {
        this.mView = null;
    }

    @Override
    public void onTokenInvalid(String errorMsg) {
        mView.onTokenInvalid(errorMsg);
    }

    @Override
    public void onError(String errorMsg) {
        mView.onError(errorMsg);
    }

    @Override
    public void onNetError() {
        mView.onNetError();
    }
}
