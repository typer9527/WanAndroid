package com.yl.wanandroid.base;

import android.support.v7.widget.RecyclerView;


public abstract class BaseRVAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {
    protected OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
