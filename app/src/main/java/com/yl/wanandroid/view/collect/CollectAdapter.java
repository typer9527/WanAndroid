package com.yl.wanandroid.view.collect;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yl.wanandroid.R;
import com.yl.wanandroid.service.dto.Articles;

import java.util.List;

public class CollectAdapter extends RecyclerView.Adapter<CollectHolder> {
    private List<Articles.Article> list;
    private Context mContext;

    CollectAdapter(List<Articles.Article> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public CollectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_collect, parent, false);
        return new CollectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectHolder holder, int position) {
        Articles.Article article = list.get(position);
        holder.tvTitle.setText(Html.fromHtml(article.getTitle()));
        holder.tvDes.setText(mContext.getString(R.string.label_author, article.getAuthor()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
