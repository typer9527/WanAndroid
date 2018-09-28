package com.yl.wanandroid.view;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yl.wanandroid.R;
import com.yl.wanandroid.base.BaseActivity;
import com.yl.wanandroid.base.ViewPagerAdapter;
import com.yl.wanandroid.view.collect.CollectArticleFragment;
import com.yl.wanandroid.view.collect.CollectWebsiteFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class CollectActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tl_collect)
    TabLayout tlCollect;
    @BindView(R.id.vp_collect)
    ViewPager vpCollect;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void initView() {
        String[] titles = {getString(R.string.label_article), getString(R.string.label_website)};
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new CollectArticleFragment());
        fragments.add(new CollectWebsiteFragment());
        vpCollect.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments, Arrays.asList(titles)));
        tlCollect.setupWithViewPager(vpCollect);
    }

    @Override
    protected void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
