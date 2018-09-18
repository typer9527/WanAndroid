package com.yl.wanandroid.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yl.wanandroid.utils.Constant;
import com.yl.wanandroid.utils.PrefsUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * 用来管理用户登录后返回的Cookie信息
 */
public class AuthCookies implements CookieJar {
    @SuppressLint("StaticFieldLeak")
    private static final AuthCookies mAuthCookies = new AuthCookies();
    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();
    private static final String TAG = "AuthCookies";
    private Context context;

    private AuthCookies() {
    }

    public static AuthCookies getInstance() {
        return mAuthCookies;
    }

    public void init(Context context) {
        this.context = context;
    }

    @Override
    public void saveFromResponse(@NonNull HttpUrl url, @NonNull List<Cookie> cookies) {
        cookieStore.put(url, cookies);
        if (url.uri().getPath().equals("/user/login")) {
            // 保存含有登录信息的Cookies
            String cookiesJson = new Gson().toJson(cookieStore.get(url));
            PrefsUtils.setString(context, Constant.KEY_LOCAL_COOKIE, cookiesJson);
        }
    }

    @Override
    public List<Cookie> loadForRequest(@NonNull HttpUrl url) {
        String cookieJson = PrefsUtils.getString(context, Constant.KEY_LOCAL_COOKIE);
        ArrayList<Cookie> cookies = new ArrayList<>();
        if (cookieJson != null) {
            List<Cookie> authCookies = new Gson().fromJson(cookieJson, new TypeToken<List<Cookie>>() {
            }.getType());
            cookies.addAll(authCookies);
        }
        return cookies;
    }
}
