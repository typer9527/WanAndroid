package com.yl.wanandroid.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.yl.wanandroid.utils.ToastUtils;

/**
 * MVP模式-BaseMvpFragment
 */
public abstract class BaseMvpFragment<V extends BaseView, P extends BasePresenter<V>> extends BaseFragment implements BaseView {
    private static final String TAG = "BaseMvpFragment";
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

    public abstract P initPresenter();

    @Override
    public void onNetError() {
        ToastUtils.showShort(mActivity, "网络异常");
    }

    @Override
    public void onTokenInvalid(String errorMsg) {
        Log.e(TAG, "onTokenInvalid: " + errorMsg);
        ToastUtils.showShort(mActivity, "登录过期");
    }

    @Override
    public void onError(String errorMsg) {
        ToastUtils.showShort(mActivity, errorMsg);
    }
}
