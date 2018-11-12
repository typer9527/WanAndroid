package com.yl.wanandroid.view;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.yl.wanandroid.R;
import com.yl.wanandroid.base.BaseActivity;
import com.yl.wanandroid.base.ViewPagerAdapter;
import com.yl.wanandroid.utils.ToastUtils;
import com.yl.wanandroid.view.home.HomeFragment;
import com.yl.wanandroid.view.project.ProjectFragment;
import com.yl.wanandroid.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.bnv_main)
    BottomNavigationView bnvMain;
    @BindView(R.id.vp_main)
    NoScrollViewPager vpMain;
    private long timeMillis = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initListener() {
        bnvMain.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void initData() {
        List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());
        mFragments.add(new ProjectFragment());
        mFragments.add(new Fragment());
        mFragments.add(new MineFragment());
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
            case R.id.item_system:
                vpMain.setCurrentItem(2, false);
                return true;
            case R.id.item_mine:
                vpMain.setCurrentItem(3, false);
                return true;
            default:
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - timeMillis < 1000) {
            super.onBackPressed();
        } else {
            timeMillis = System.currentTimeMillis();
            ToastUtils.showShort(this, getString(R.string.label_press_again_to_exit));
        }
    }
}
