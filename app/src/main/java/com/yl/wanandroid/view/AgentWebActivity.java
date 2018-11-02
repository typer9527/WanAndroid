package com.yl.wanandroid.view;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.just.agentweb.AgentWeb;
import com.yl.wanandroid.R;

import java.net.URISyntaxException;

public class AgentWebActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener, DownloadListener {
    private static final String KEY_EXTERNAL_URL = "key_external_url";
    private AgentWeb mAgentWeb;
    private Toolbar toolbar;

    public static void openExternalUrl(Activity activity, String externalUrl) {
        Intent intent = new Intent(activity, AgentWebActivity.class);
        intent.putExtra(KEY_EXTERNAL_URL, externalUrl);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_web);
        initViews();
        initAgentWeb();
    }

    private void initAgentWeb() {
        WebViewClient webViewClient = new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (isUrlInvalid(url)) openThirdPartyApp(url);
                return isUrlInvalid(url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                showToast("当前网络不可用");
            }

        };
        WebChromeClient chromeClient = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                toolbar.setTitle(title);
                toolbar.setSubtitle(view.getUrl());
            }
        };
        LinearLayout llAgentWeb = findViewById(R.id.ll_agent_web);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        String startUrl = getIntent().getStringExtra(KEY_EXTERNAL_URL);
        AgentWeb.PreAgentWeb ready = AgentWeb.with(this).setAgentWebParent(llAgentWeb, params)
                .useDefaultIndicator(getResources().getColor(R.color.colorGray))
                .setWebViewClient(webViewClient).setWebChromeClient(chromeClient)
                .createAgentWeb().ready();
        if (isUrlInvalid(startUrl)) {
            mAgentWeb = ready.go("");
            openThirdPartyApp(startUrl);
            finish();
        } else {
            mAgentWeb = ready.go(startUrl);
        }
        mAgentWeb.getWebCreator().getWebView().setDownloadListener(this);
        WebSettings settings = mAgentWeb.getAgentWebSettings().getWebSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
    }

    // 启动第三方应用或打开系统应用市场
    private void openThirdPartyApp(String uri) {
        try {
            Intent intent = Intent.parseUri(uri, Intent.URI_INTENT_SCHEME);
            if (getPackageManager().resolveActivity(intent, 0) == null) {
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + intent.getPackage()));
            }
            startActivity(intent);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            showToast("打开第三方应用失败");
        }
    }

    private boolean isUrlInvalid(String startUrl) {
        return !startUrl.startsWith("http://") && !startUrl.startsWith("https://") &&
                !startUrl.startsWith("ftp://") && !startUrl.startsWith("file://");
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.inflateMenu(R.menu.menu_web_view);
        toolbar.setOnMenuItemClickListener(this);
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_refresh:
                mAgentWeb.getUrlLoader().reload();
                return true;
            case R.id.item_copy_link:
                ClipboardManager clipboardManager = (ClipboardManager)
                        getSystemService(Context.CLIPBOARD_SERVICE);
                if (clipboardManager != null) {
                    clipboardManager.setPrimaryClip(
                            ClipData.newPlainText("clip_data", toolbar.getSubtitle()));
                    showToast("链接已复制");
                } else {
                    showToast("复制失败");
                }
                return true;
            case R.id.item_open_in_browser:
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(toolbar.getSubtitle().toString())));
                return true;
        }
        return false;
    }

    @Override
    public void onDownloadStart(final String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("文件下载").setMessage("是否打开浏览器下载链接文件")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showToast("已取消下载");
                        finish();
                    }
                })
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }

    @Override
    public void onBackPressed() {
        if (!mAgentWeb.back()) super.onBackPressed();
    }
}
