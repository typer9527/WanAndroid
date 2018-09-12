package com.yl.wanandroid.base;

import com.yl.wanandroid.service.ErrorListener;

public abstract class BasePresenter<V extends BaseView> implements ErrorListener {

    protected V view;

    public void attach(V view) {
        this.view = view;
    }

    public void detach() {
        this.view = null;
    }

    @Override
    public void onTokenInvalid(String errorMsg) {
        view.onTokenInvalid(errorMsg);
    }

    @Override
    public void onError(String errorMsg) {
        view.onError(errorMsg);
    }

    @Override
    public void onNetError() {
        view.onNetError();
    }
}
