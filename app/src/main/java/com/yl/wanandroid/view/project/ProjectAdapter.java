package com.yl.wanandroid.view.project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectHolder> {
    private List<Articles.Article> list;
    private Context mContext;

    ProjectAdapter(List<Articles.Article> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ProjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_project, parent, false);
        return new ProjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectHolder holder, int position) {
        Articles.Article article = list.get(position);
        RequestOptions options = new RequestOptions()
                .error(R.drawable.ic_android).placeholder(R.drawable.ic_android);
        Glide.with(mContext).load(R.mipmap.ic_launcher_round).apply(options.circleCrop()).into(holder.ivAvatar);
        if (!TextUtils.isEmpty(article.getEnvelopePic()))
            Glide.with(mContext).load(article.getEnvelopePic()).apply(options.centerCrop()).into(holder.ivCover);
        holder.tvTitle.setText(article.getTitle());
        holder.tvDes.setText(article.getDesc().trim());
        holder.tvAuthorAndTime.setText(mContext.getString(R.string.label_author_and_time, article.getAuthor(), article.getNiceDate()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ProjectHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_avatar)
        ImageView ivAvatar;
        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_author_and_time)
        TextView tvAuthorAndTime;
        @BindView(R.id.tv_des)
        TextView tvDes;

        ProjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
