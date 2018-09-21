package com.yl.wanandroid.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yl.wanandroid.R;
import com.yl.wanandroid.app.Constant;
import com.yl.wanandroid.base.BaseActivity;

import butterknife.BindView;

public class WebActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener,
        SwipeRefreshLayout.OnRefreshListener, View.OnLongClickListener {
    @BindView(R.id.tb_web)
    Toolbar tbWeb;
    @BindView(R.id.wv_web)
    WebView wvWeb;
    @BindView(R.id.srl_web)
    SwipeRefreshLayout srlWeb;

    public static void openWebPage(Activity activity, String url, boolean isCollected) {
        Intent intent = new Intent(activity, WebActivity.class);
        intent.putExtra(Constant.KEY_WEB_URL, url);
        intent.putExtra(Constant.KEY_IS_COLLECTED, isCollected);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initView() {
        tbWeb.inflateMenu(R.menu.menu_toolbar_web);
        srlWeb.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        boolean isCollected = getIntent().getBooleanExtra(Constant.KEY_IS_COLLECTED, false);
        if (isCollected)
            tbWeb.getMenu().findItem(R.id.item_collect).setIcon(R.drawable.ic_collected_white);
        wvWeb.getSettings().setJavaScriptEnabled(true);
        wvWeb.setOnLongClickListener(this);
        wvWeb.setWebViewClient(new WebViewClient() {
            private boolean isFailed;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                isFailed = false;
                wvWeb.setVisibility(View.INVISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                WebViewActivity.openExternalUrl(WebActivity.this, url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                isFailed = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                srlWeb.setRefreshing(false);
                if (!isFailed) wvWeb.setVisibility(View.VISIBLE);
                else showNetError();
            }
        });
        wvWeb.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                tbWeb.setTitle(title);
            }
        });
    }

    @Override
    public void initListener() {
        tbWeb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tbWeb.setOnMenuItemClickListener(this);
        srlWeb.setOnRefreshListener(this);
    }

    @Override
    public void initData() {
        srlWeb.setRefreshing(true);
        wvWeb.loadUrl(getIntent().getStringExtra(Constant.KEY_WEB_URL));
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_collect:
                //item.setIcon(R.drawable.ic_collected_white);
                return true;
            case R.id.item_share:
                return true;
        }
        return false;
    }

    @Override
    public void onRefresh() {
        wvWeb.reload();
    }

    @Override
    public boolean onLongClick(View v) {
        // TODO: 2018/9/21 图片识别与保存
        return false;
    }
}
