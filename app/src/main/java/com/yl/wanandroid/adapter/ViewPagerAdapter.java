package com.yl.wanandroid.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * ViewPager+Fragment的适配器
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mBaseFragmentList;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> mBaseFragmentList) {
        super(fm);
        this.mBaseFragmentList = mBaseFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mBaseFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mBaseFragmentList.size();
    }
}
