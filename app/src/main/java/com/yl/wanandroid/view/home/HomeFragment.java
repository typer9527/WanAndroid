package com.yl.wanandroid.view.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.yl.wanandroid.R;
import com.yl.wanandroid.adapter.ViewPagerAdapter;
import com.yl.wanandroid.base.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseFragment {
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
        String[] tabTitles = new String[]{"关注", getString(R.string.label_article), getString(R.string.label_project)};
        List<Fragment> fragments = new ArrayList<>();
        for (String ignored : tabTitles) {
            fragments.add(new Fragment());
        }
        vpHome.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), fragments, Arrays.asList(tabTitles)));
        tlHome.setupWithViewPager(vpHome);
    }
}
