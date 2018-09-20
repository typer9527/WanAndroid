package com.yl.wanandroid.view.user;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yl.wanandroid.R;
import com.yl.wanandroid.base.BaseMvpActivity;
import com.yl.wanandroid.model.UserModel;
import com.yl.wanandroid.presenter.UserPresenter;
import com.yl.wanandroid.app.Constant;
import com.yl.wanandroid.utils.PrefsUtils;
import com.yl.wanandroid.utils.ToastUtils;
import com.yl.wanandroid.view.MainActivity;

import butterknife.BindView;

public class LoginActivity extends BaseMvpActivity<UserView, UserPresenter> implements UserView, View.OnClickListener, TextWatcher {
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
                mPresenter.login(etLoginName.getText().toString(), etLoginPsw.getText().toString());
                showProgressDialog(getString(R.string.label_logining));
                break;
            default:
        }
    }

    @Override
    public UserPresenter initPresenter() {
        return new UserPresenter(new UserModel());
    }

    @Override
    public void onLoginOrRegisterSucceed() {
        PrefsUtils.setBoolean(this, Constant.KEY_IS_LOGON, true);
        dismissProgressDialog();
        ToastUtils.showShort(this, getString(R.string.label_login_succeed));
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
