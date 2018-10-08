package com.yl.wanandroid.view.collect;

import android.content.Context;
import android.widget.ImageView;

import com.yl.wanandroid.R;
import com.yl.wanandroid.model.CollectModel;
import com.yl.wanandroid.service.HttpResponse;
import com.yl.wanandroid.service.dto.Articles;
import com.yl.wanandroid.service.dto.EmptyData;
import com.yl.wanandroid.service.interfaces.ErrorListener;
import com.yl.wanandroid.service.interfaces.ResponseListener;
import com.yl.wanandroid.utils.ToastUtils;

public class CollectFunction implements ErrorListener, ResponseListener<EmptyData> {
    private Context mContext;
    private ImageView ivCollect;
    private final CollectModel model;

    private CollectFunction() {
        model = new CollectModel();
    }

    public static CollectFunction newInstance() {
        return new CollectFunction();
    }

    public void handleCollectList(ImageView ivCollect, Articles.Article article) {
        this.ivCollect = ivCollect;
        mContext = ivCollect.getContext();
        if (ivCollect.isSelected()) {
            model.revokeCollectArticle(article.getId(), article.getOriginId(), this, this);
        } else {
            model.collectArticle(article.getOriginId(), this, this);
        }
    }

    private void showMsg(String msg) {
        ToastUtils.showShort(mContext, msg);
    }

    @Override
    public void onNetError() {
        showMsg(mContext.getString(R.string.label_net_error));
    }

    @Override
    public void onTokenInvalid(String errorMsg) {
        showMsg(mContext.getString(R.string.label_login_expired));
    }

    @Override
    public void onError(String errorMsg) {
        showMsg(errorMsg);
    }

    @Override
    public void onSuccess(HttpResponse<EmptyData> response) {
        ivCollect.setSelected(!ivCollect.isSelected());
    }
}
