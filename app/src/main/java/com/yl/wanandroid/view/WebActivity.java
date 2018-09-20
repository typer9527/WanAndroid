package com.yl.wanandroid.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.yl.wanandroid.R;
import com.yl.wanandroid.app.Constant;
import com.yl.wanandroid.base.BaseActivity;

import butterknife.BindView;

public class WebActivity extends BaseActivity {
    @BindView(R.id.tb_web)
    Toolbar tbWeb;
    @BindView(R.id.wv_web)
    WebView wvWeb;

    public static void openWebPage(Activity activity, String url) {
        Intent intent = new Intent(activity, WebActivity.class);
        intent.putExtra(Constant.KEY_WEB_URL, url);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        String url = getIntent().getStringExtra(Constant.KEY_WEB_URL);
        wvWeb.loadUrl(url);
    }
}
