package com.yl.wanandroid.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yl.wanandroid.R;
import com.yl.wanandroid.base.BaseActivity;
import com.yl.wanandroid.utils.ToastUtils;

import butterknife.BindView;

public class LoginActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.et_login_name)
    EditText etLoginName;
    @BindView(R.id.et_login_psw)
    EditText etLoginPsw;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        tvRegister.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        btnLogin.setEnabled(false);
    }

    @Override
    public void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        etLoginName.addTextChangedListener(this);
        etLoginPsw.addTextChangedListener(this);
        tvRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(etLoginName.getText()) || TextUtils.isEmpty(etLoginPsw.getText())) {
            btnLogin.setEnabled(false);
        } else {
            btnLogin.setEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.btn_login:
                showProgressDialog(getString(R.string.label_logining));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgressDialog();
                        ToastUtils.showShort(LoginActivity.this, "登录失败");
                    }
                }, 2000);
                break;
            default:
        }
    }
}
