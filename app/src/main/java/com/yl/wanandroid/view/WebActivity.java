package com.yl.wanandroid.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import com.yl.wanandroid.R;
import com.yl.wanandroid.app.Constant;
import com.yl.wanandroid.base.BaseActivity;

import butterknife.BindView;

public class WebActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {
    @BindView(R.id.tb_web)
    Toolbar tbWeb;
    @BindView(R.id.wv_web)
    WebView wvWeb;

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

    @Override
    public void initView() {
        tbWeb.inflateMenu(R.menu.menu_toolbar_web);
        boolean isCollected = getIntent().getBooleanExtra(Constant.KEY_IS_COLLECTED, false);
        if (isCollected)
            tbWeb.getMenu().findItem(R.id.item_collect).setIcon(R.drawable.ic_collected_white);
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
    }

    @Override
    public void initData() {
        String url = getIntent().getStringExtra(Constant.KEY_WEB_URL);
        wvWeb.loadUrl(url);
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
}
