package com.yl.wanandroid.view;

import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yl.wanandroid.R;
import com.yl.wanandroid.base.BaseActivity;
import com.yl.wanandroid.view.MainActivity;
import com.yl.wanandroid.view.user.LoginActivity;
import com.yl.wanandroid.view.user.RegisterActivity;

import butterknife.BindView;

public class IntroActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.btn_browser)
    Button btnBrowser;
    @BindView(R.id.tv_login)
    TextView tvLogin;

    @Override
    public int getLayoutId() {
        return R.layout.activity_intro;
    }

    @Override
    public void initView() {
        tvLogin.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public void initListener() {
        btnBrowser.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.btn_browser:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.tv_login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            default:
        }
    }
}
