package com.yl.wanandroid.view;

import android.content.Intent;
import android.os.Handler;

import com.yl.wanandroid.R;
import com.yl.wanandroid.base.BaseActivity;
import com.yl.wanandroid.utils.Constant;
import com.yl.wanandroid.utils.PrefsUtils;

public class SplashActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {

    }

    public void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                launchApp();
            }
        }, 1400);
    }

    private void launchApp() {
        if (PrefsUtils.getBoolean(this, Constant.KEY_IS_LOGON)) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, IntroActivity.class));
        }
        finish();
    }
}
