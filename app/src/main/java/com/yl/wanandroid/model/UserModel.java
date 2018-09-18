package com.yl.wanandroid.model;

import com.yl.wanandroid.base.BaseModel;
import com.yl.wanandroid.presenter.UserPresenter;
import com.yl.wanandroid.service.dto.EmptyData;
import com.yl.wanandroid.service.interfaces.ErrorListener;
import com.yl.wanandroid.service.interfaces.ResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class UserModel extends BaseModel {
    public void login(String username, String password, ResponseListener<EmptyData> listener, ErrorListener errorListener) {
        rxService.add(apiService.loginByPsw(username,password), listener, errorListener);
    }

    public void register(String username, String password, String confirmPassword, ResponseListener<EmptyData> listener, ErrorListener errorListener) {
        rxService.add(apiService.register(username, password, confirmPassword), listener, errorListener);
    }
}
