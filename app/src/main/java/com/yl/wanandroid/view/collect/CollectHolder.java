package com.yl.wanandroid.view.collect;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.wanandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

class CollectHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;

    CollectHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
