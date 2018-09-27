package com.yl.wanandroid.view.user;

import android.content.Intent;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yl.wanandroid.R;
import com.yl.wanandroid.base.BaseMvpActivity;
import com.yl.wanandroid.presenter.UserPresenter;

import butterknife.BindView;

public class RegisterActivity extends BaseMvpActivity<UserView, UserPresenter> implements UserView, TextWatcher, View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_psw)
    EditText etPsw;
    @BindView(R.id.et_confirm_psw)
    EditText etConfirmPsw;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        btnRegister.setEnabled(false);
    }

    @Override
    public void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        etName.addTextChangedListener(this);
        etPsw.addTextChangedListener(this);
        etConfirmPsw.addTextChangedListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(etName.getText()) || TextUtils.isEmpty(etPsw.getText()) ||
                TextUtils.isEmpty(etConfirmPsw.getText())) {
            btnRegister.setEnabled(false);
        } else {
            btnRegister.setEnabled(true);
        }
    }

    @Override
    public UserPresenter initPresenter() {
        return new UserPresenter();
    }

    @Override
    public void onLoginOrRegisterSucceed() {
        dismissProgressDialog();
        Snackbar.make(btnRegister, "注册成功", Snackbar.LENGTH_LONG).setAction("登录", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        }).addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                finish();
            }
        }).show();
    }

    @Override
    public void onSignOutSucceed() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_register) {
            showProgressDialog("");
            String name = etName.getText().toString();
            String psw = etPsw.getText().toString();
            String confirmPsw = etConfirmPsw.getText().toString();
            mPresenter.register(name, psw, confirmPsw);
        }
    }
}
