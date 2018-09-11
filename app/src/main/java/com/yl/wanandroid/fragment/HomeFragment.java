package com.yl.wanandroid.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yl.wanandroid.R;
import com.yl.wanandroid.adapter.ViewPagerAdapter;
import com.yl.wanandroid.base.BaseFragment;
import com.yl.wanandroid.service.BaseResponse;
import com.yl.wanandroid.service.HomeTab;
import com.yl.wanandroid.utils.Constant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseFragment {
    private static final String TAG = "HomeFragment";
    @BindView(R.id.tl_home)
    TabLayout tlHome;
    @BindView(R.id.vp_home)
    ViewPager vpHome;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(Bundle arguments) {
        tlHome.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    protected void initData() {
        Log.e(TAG, "initData: " + getLocalJson(Constant.JSON_HOME_TAB));
        BaseResponse<List<HomeTab>> response = new Gson().fromJson(getLocalJson(Constant.JSON_HOME_TAB), new TypeToken<BaseResponse<List<HomeTab>>>() {
        }.getType());
        Log.e(TAG, "initData: " + response.getData().get(0).getHomeTab());
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (HomeTab tab : response.getData()) {
            fragments.add(new Fragment());
            titles.add(tab.getHomeTab());
        }
        vpHome.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), fragments, titles));
        tlHome.setupWithViewPager(vpHome);
    }

    private String getLocalJson(String fileName) {
        String jsonStr = null;
        InputStreamReader reader;
        try {
            reader = new InputStreamReader(getActivity().getAssets().open(fileName), "utf-8");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
            bufferedReader.close();
            jsonStr = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }
}
