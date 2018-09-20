package com.yl.wanandroid.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.yl.wanandroid.R;

public class WebViewActivity extends AppCompatActivity {
    private static final String KEY_EXTERNAL_URL = "key_external_url";
    private WebView webView;

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
        webView.loadUrl(startUrl);
    }

    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        webView = findViewById(R.id.web_view);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
