package com.yl.wanandroid.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yl.wanandroid.R;
import com.yl.wanandroid.app.Constant;
import com.yl.wanandroid.utils.PrefsUtils;
import com.yl.wanandroid.utils.ToastUtils;
import com.yl.wanandroid.view.user.LoginActivity;

import butterknife.ButterKnife;

/**
 * Fragment基类
 */
public abstract class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";
    private ProgressDialog mProgressDialog;

    /**
     * 贴附的activity
     */
    protected FragmentActivity mActivity;
    /**
     * 根view
     */
    private View mRootView;
    /**
     * 是否对用户可见
     */
    private boolean isUiVisible; // Fragment是否可见
    private boolean dataPrepared; // 网络数据是否已请求过

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), container, false);
            ButterKnife.bind(this, mRootView);
            initView(getArguments());
            initData();
            onLazyLoad();
            initListener();
        }
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    /**
     * 设置根布局资源id
     */
    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 初始化View
     *
     * @param arguments 接收到的从其他地方传递过来的参数
     */
    protected abstract void initView(Bundle arguments);

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 设置监听事件
     */
    protected void initListener() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isUiVisible = true;
            onLazyLoad();
        } else {
            isUiVisible = false;
        }
    }


    private void onLazyLoad() {
        if (mRootView != null && isUiVisible && !dataPrepared) {
            dataPrepared = true;
            lazyLoad();
            isUiVisible = false;
        }
    }

    protected void lazyLoad() {

    }

    public void showProgressDialog(String msg) {
        if (mProgressDialog == null) {
            if (TextUtils.isEmpty(msg)) {
                mProgressDialog = ProgressDialog.show(mActivity, "", "加载中...", true, true);
            } else {
                mProgressDialog = ProgressDialog.show(mActivity, "", msg, true, true);
            }
        } else {
            mProgressDialog.show();
        }
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    protected void showMsg(String msg) {
        ToastUtils.showShort(mActivity, msg);
    }

    void showTokenInvalid(String errorMsg) {
        Log.e(TAG, "onTokenInvalid: " + errorMsg);
        dismissProgressDialog();
        if (TextUtils.isEmpty(PrefsUtils.getString(mActivity, Constant.KEY_LOCAL_COOKIE))) {
            showMsg(this.getString(R.string.label_login_first));
        } else {
            showMsg(this.getString(R.string.label_login_expired));
        }
        startActivity(new Intent(mActivity, LoginActivity.class));
    }

    void showNetError() {
        dismissProgressDialog();
        ToastUtils.showShort(mActivity, getString(R.string.label_net_error));
    }

    void showError(String errorMsg) {
        dismissProgressDialog();
        ToastUtils.showShort(mActivity, errorMsg);
    }
}
