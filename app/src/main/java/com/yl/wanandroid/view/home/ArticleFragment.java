package com.yl.wanandroid.view.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yl.wanandroid.R;
import com.yl.wanandroid.base.BaseMvpFragment;
import com.yl.wanandroid.model.HomeModel;
import com.yl.wanandroid.presenter.HomePresenter;
import com.yl.wanandroid.service.dto.Articles;

import java.util.ArrayList;

import butterknife.BindView;

public class ArticleFragment extends BaseMvpFragment<HomeView, HomePresenter> implements HomeView {
    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    private int currentIndex;
    private ArrayList<Articles.Article> list;
    private ArticleAdapter adapter;

    @Override
    public HomePresenter initPresenter() {
        return new HomePresenter(new HomeModel());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_article;
    }

    @Override
    protected void initView(Bundle arguments) {
        rvHome.setLayoutManager(new LinearLayoutManager(mActivity));
    }

    @Override
    protected void initData() {
        currentIndex = 0;
        list = new ArrayList<>();
        adapter = new ArticleAdapter(list);
        rvHome.setAdapter(adapter);
        mPresenter.getArticleList(currentIndex);
    }

    @Override
    public void showArticleList(Articles articles) {
        list.addAll(articles.getArticles());
        adapter.notifyDataSetChanged();
    }
}
