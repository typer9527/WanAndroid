package com.yl.wanandroid.presenter;

import com.yl.wanandroid.base.BasePresenter;
import com.yl.wanandroid.model.UserModel;
import com.yl.wanandroid.service.HttpResponse;
import com.yl.wanandroid.service.dto.EmptyData;
import com.yl.wanandroid.service.interfaces.ResponseListener;
import com.yl.wanandroid.view.user.UserView;

public class UserPresenter extends BasePresenter<UserView> {
    private final UserModel model;

    public UserPresenter() {
        this.model = new UserModel();
    }

    public void login(String username, String password) {
        model.login(username, password, new ResponseListener<EmptyData>() {
            @Override
            public void onSuccess(HttpResponse<EmptyData> response) {
                mView.onLoginOrRegisterSucceed();
            }
        }, this);
    }

    public void register(String username, String password, String confirmPassword) {
        model.register(username, password, confirmPassword, new ResponseListener<EmptyData>() {
            @Override
            public void onSuccess(HttpResponse<EmptyData> response) {
                mView.onLoginOrRegisterSucceed();
            }
        }, this);
    }

    public void signOut() {
        model.signOut(new ResponseListener<EmptyData>() {
            @Override
            public void onSuccess(HttpResponse<EmptyData> response) {
                mView.onSignOutSucceed();
            }
        }, this);
    }
}
