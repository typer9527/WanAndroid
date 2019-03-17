package com.yl.wanandroid.view.system;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yl.wanandroid.R;
import com.yl.wanandroid.base.BaseRVAdapter;
import com.yl.wanandroid.service.dto.Articles;
import com.yl.wanandroid.service.dto.TreeChild;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class SystemAdapter extends BaseRVAdapter<SystemAdapter.SystemHolder> {
    private final List<TreeChild> list;
    private Context mContext;

    SystemAdapter(List<TreeChild> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public SystemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_system_tree, parent, false);
        return new SystemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SystemHolder holder, int position) {
        final TreeChild child = list.get(position);
        holder.tvTitle.setText(Html.fromHtml(child.getName()));
        holder.tflSubTitles.setAdapter(new TagAdapter<TreeChild>(child.getChildren()) {
            @Override
            public View getView(FlowLayout parent, int position, TreeChild treeChild) {
                TextView tv = (TextView) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_flow_tag, parent, false);
                tv.setText(Html.fromHtml(treeChild.getName()));
                return tv;
            }
        });
        holder.tflSubTitles.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                int id = child.getChildren().get(position).getId();
                if (listener != null)
                    listener.onClick(view, id);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SystemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tfl_sub_titles)
        TagFlowLayout tflSubTitles;

        SystemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
