package com.yl.wanandroid.view.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yl.wanandroid.R;
import com.yl.wanandroid.service.dto.Articles;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleHolder> {
    private List<Articles.Article> list;
    private Context mContext;

    ArticleAdapter(List<Articles.Article> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_article, parent, false);
        return new ArticleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleHolder holder, int position) {
        Articles.Article article = list.get(position);
        RequestOptions options = new RequestOptions()
                .centerCrop().error(R.drawable.ic_android).placeholder(R.drawable.ic_android);
        if (!TextUtils.isEmpty(article.getEnvelopePic()))
            Glide.with(mContext).load(article.getEnvelopePic()).apply(options).into(holder.ivCover);
        holder.tvTitle.setText(Html.fromHtml(article.getTitle()));
        holder.tvAuthorAndTime.setText(mContext.getString(R.string.label_author_and_time, article.getAuthor(), article.getNiceDate()));
        holder.tvCategory.setText(mContext.getString(R.string.label_mark_category, article.getSuperChapterName(), article.getChapterName()));
        holder.ivCollect.setImageResource(article.isCollect() ?
                R.drawable.ic_collected : R.drawable.ic_not_collected);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ArticleHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_author_and_time)
        TextView tvAuthorAndTime;
        @BindView(R.id.tv_category)
        TextView tvCategory;
        @BindView(R.id.iv_collect)
        ImageView ivCollect;

        ArticleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
