package com.yl.wanandroid.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.yl.wanandroid.R;
import com.yl.wanandroid.adapter.ViewPagerAdapter;
import com.yl.wanandroid.base.BaseActivity;
import com.yl.wanandroid.fragment.HomeFragment;
import com.yl.wanandroid.utils.ViewUtils;
import com.yl.wanandroid.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.bnv_main)
    BottomNavigationView bnvMain;
    @BindView(R.id.vp_main)
    NoScrollViewPager vpMain;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        ViewUtils.disableShiftMode(bnvMain);
    }

    @Override
    public void initListener() {
        bnvMain.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void initData() {
        List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());
        mFragments.add(new Fragment());
        mFragments.add(new Fragment());
        mFragments.add(new Fragment());
        vpMain.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mFragments));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_home:
                vpMain.setCurrentItem(0, false);
                return true;
            case R.id.item_classify:
                vpMain.setCurrentItem(1, false);
                return true;
            case R.id.item_todo:
                vpMain.setCurrentItem(2, false);
                return true;
            case R.id.item_mine:
                vpMain.setCurrentItem(3, false);
                return true;
            default:
        }
        return false;
    }
}
