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
import com.yl.wanandroid.service.dto.CollectItem;

import java.util.List;

public class CollectAdapter extends BaseRVAdapter<CollectHolder> {
    private List<CollectItem> list;
    private Context mContext;

    CollectAdapter(List<CollectItem> list) {
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
        final CollectItem item = list.get(position);
        holder.tvTitle.setText(Html.fromHtml(item.getItemTitle()));
        String des = item.isArticle() ? mContext.getString(R.string.label_author, item.getItemDes()) : item.getItemDes();
        holder.tvDes.setText(des);
        holder.ivCollect.setSelected(true);
        holder.ivCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectFunction.newInstance().handleArticleCollect(holder.ivCollect, item);
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
