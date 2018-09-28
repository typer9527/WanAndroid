package com.yl.wanandroid.base;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * ViewPager+Fragment的适配器
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mBaseFragmentList;
    private List<String> titles;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> mBaseFragmentList) {
        super(fm);
        this.mBaseFragmentList = mBaseFragmentList;
    }

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> mBaseFragmentList, List<String> titles) {
        super(fm);
        this.mBaseFragmentList = mBaseFragmentList;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mBaseFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mBaseFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
