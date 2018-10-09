package com.yl.wanandroid.view.collect;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yl.wanandroid.R;
import com.yl.wanandroid.base.BaseRVAdapter;
import com.yl.wanandroid.base.OnItemClickListener;
import com.yl.wanandroid.service.dto.Articles;

import java.util.List;

public class CollectAdapter extends BaseRVAdapter<CollectHolder> {
    private List<Articles.Article> list;
    private Context mContext;

    CollectAdapter(List<Articles.Article> list) {
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CollectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_collect, parent, false);
        return new CollectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CollectHolder holder, int position) {
        final Articles.Article article = list.get(position);
        holder.tvTitle.setText(Html.fromHtml(article.getTitle()));
        holder.tvDes.setText(mContext.getString(R.string.label_author, article.getAuthor()));
        holder.ivCollect.setSelected(true);
        holder.ivCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectFunction.newInstance().handleArticleCollect(holder.ivCollect, article);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(holder.ivCollect, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
