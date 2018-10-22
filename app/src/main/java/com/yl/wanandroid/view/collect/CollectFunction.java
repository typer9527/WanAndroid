package com.yl.wanandroid.view.collect;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;

import com.yl.wanandroid.R;
import com.yl.wanandroid.app.Constant;
import com.yl.wanandroid.model.CollectModel;
import com.yl.wanandroid.service.HttpResponse;
import com.yl.wanandroid.service.dto.CollectItem;
import com.yl.wanandroid.service.dto.EmptyData;
import com.yl.wanandroid.service.interfaces.ErrorListener;
import com.yl.wanandroid.service.interfaces.ResponseListener;
import com.yl.wanandroid.utils.PrefsUtils;
import com.yl.wanandroid.utils.ToastUtils;
import com.yl.wanandroid.view.user.LoginActivity;

public class CollectFunction implements ErrorListener, ResponseListener<EmptyData> {
    private Context mContext;
    private ImageView ivCollect;
    private final CollectModel model;
    private MenuItem item;
    private boolean isCollected;

    private CollectFunction() {
        model = new CollectModel();
    }

    public static CollectFunction newInstance() {
        return new CollectFunction();
    }

    public void handleArticleCollect(ImageView ivCollect, CollectItem item) {
        this.ivCollect = ivCollect;
        isCollected = ivCollect.isSelected();
        mContext = ivCollect.getContext();
        collectOrRevoke(item.getItemOriginId(), item.getItemId());
    }

    private void collectOrRevoke(int originId, int id) {
        if (isCollected) {
            if (originId == 0) {
                model.revokeListArticle(id, this, this);
            } else {
                model.revokeCollectArticle(id, originId, this, this);
            }
        } else {
            model.collectArticle(originId == 0 ? id : originId, this, this);
        }
    }

    public void handleArticleCollect(Context context, MenuItem item, Intent intent) {
        this.item = item;
        this.isCollected = item.isChecked();
        mContext = context;
        collectOrRevoke(intent.getIntExtra(Constant.KEY_ORIGIN_ID, 0),
                intent.getIntExtra(Constant.KEY_ID, 0));
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
        if (TextUtils.isEmpty(PrefsUtils.getString(mContext, Constant.KEY_LOCAL_COOKIE))) {
            showMsg(mContext.getString(R.string.label_login_first));
        } else {
            showMsg(mContext.getString(R.string.label_login_expired));
        }
        mContext.startActivity(new Intent(mContext, LoginActivity.class));
    }

    @Override
    public void onError(String errorMsg) {
        showMsg(errorMsg);
    }

    @Override
    public void onSuccess(HttpResponse<EmptyData> response) {
        if (ivCollect != null)
            ivCollect.setSelected(!ivCollect.isSelected());
        if (item != null) {
            item.setChecked(!item.isChecked());
            item.setIcon(item.isChecked() ? R.drawable.ic_collected_white : R.drawable.ic_not_collected_white);
        }
    }
}
