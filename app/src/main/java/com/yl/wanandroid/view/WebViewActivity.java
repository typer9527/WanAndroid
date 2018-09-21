package com.yl.wanandroid.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yl.wanandroid.R;

public class WebViewActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {
    private static final String KEY_EXTERNAL_URL = "key_external_url";
    private WebView webView;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private ImageButton ibGoBack, ibGoFroward;

    public static void openExternalUrl(Activity activity, String externalUrl) {
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra(KEY_EXTERNAL_URL, externalUrl);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initViews();
        initData();
    }

    private void initData() {
        String startUrl = getIntent().getStringExtra(KEY_EXTERNAL_URL);
        checkWebViewNavBtnEnable();
        webView.loadUrl(startUrl);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        webView = findViewById(R.id.web_view);
        progressBar = findViewById(R.id.progressBar);
        ibGoBack = findViewById(R.id.ib_go_back);
        ibGoFroward = findViewById(R.id.ib_go_forward);
        toolbar.inflateMenu(R.menu.menu_web_view);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressBar.setMax(100);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        webView.setWebViewClient(new WebViewClient() {
            private boolean isFailed;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                isFailed = false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                checkWebViewNavBtnEnable();
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                webView.setVisibility(View.INVISIBLE);
                isFailed = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!isFailed) webView.setVisibility(View.VISIBLE);
                else showToast(getString(R.string.label_net_error));
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                toolbar.setTitle(title);
                toolbar.setSubtitle(view.getUrl());
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if (newProgress == 100) progressBar.setProgress(0);
            }
        });
    }

    private void checkWebViewNavBtnEnable() {
        ibGoBack.setEnabled(webView.canGoBack());
        ibGoFroward.setEnabled(webView.canGoForward());
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_refresh:
                webView.reload();
                return true;
            case R.id.item_copy_link:
                ClipboardManager clipboardManager = (ClipboardManager)
                        getSystemService(Context.CLIPBOARD_SERVICE);
                if (clipboardManager != null) {
                    clipboardManager.setPrimaryClip(
                            ClipData.newPlainText("clip_data", webView.getUrl()));
                    showToast(getString(R.string.label_link_copied));
                } else {
                    showToast(getString(R.string.label_copy_failed));
                }
                return true;
            case R.id.item_open_in_browser:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(webView.getUrl())));
                return true;
        }
        return false;
    }

    public void goBack(View view) {
        webView.goBack();
    }

    public void goForward(View view) {
        webView.goForward();
    }
}
