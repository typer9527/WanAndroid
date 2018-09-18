package com.yl.wanandroid.view.home;

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
import com.yl.wanandroid.service.dto.BannerData;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_BANNER = 0;
    private static final int TYPE_ARTICLE = 1;
    private List<BannerData> banners;
    private List<Articles.Article> list;
    private Context mContext;
    private Banner bannerView;

    ArticleAdapter(List<Articles.Article> list) {
        this.list = list;
    }

    ArticleAdapter(List<BannerData> banners, List<Articles.Article> list) {
        this.banners = banners;
        this.list = list;
    }

    public void refreshBanners(List<BannerData> banners) {
        this.banners.clear();
        this.banners.addAll(banners);
        List<String> imageUrls = new ArrayList<>();
        for (BannerData banner : this.banners) {
            imageUrls.add(banner.getImagePath());
        }
        if (bannerView != null)
            bannerView.update(imageUrls);
    }

    @Override
    public int getItemViewType(int position) {
        if (banners != null && position == 0) return TYPE_BANNER;
        else return TYPE_ARTICLE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view;
        switch (viewType) {
            case TYPE_BANNER:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_banner, parent, false);
                return new BannerHolder(view);
            case TYPE_ARTICLE:
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_article, parent, false);
                return new ArticleHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_BANNER:
                BannerHolder bannerHolder = (BannerHolder) holder;
                bannerHolder.bannerHome.setImageLoader(new BannerLoader());
                bannerView = bannerHolder.bannerHome;
                //refreshBanners(banners);
                break;
            case TYPE_ARTICLE:
            default:
                if (banners != null) position--;
                ArticleHolder articleHolder = (ArticleHolder) holder;
                Articles.Article article = list.get(position);
                RequestOptions options = new RequestOptions()
                        .centerCrop().error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher);
                if (!TextUtils.isEmpty(article.getEnvelopePic()))
                    Glide.with(mContext).load(article.getEnvelopePic()).apply(options).into(articleHolder.ivCover);
                articleHolder.tvTitle.setText(article.getTitle());
                articleHolder.tvAuthorAndTime.setText(mContext.getString(R.string.label_author_and_time, article.getAuthor(), article.getFormatTime()));
                articleHolder.tvCategory.setText(article.getChapterName());
                articleHolder.ivCollect.setImageResource(article.isCollect() ?
                        R.drawable.ic_collected : R.drawable.ic_not_collected);
        }
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

    class BannerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.banner_home)
        Banner bannerHome;

        BannerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class BannerLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }
}
