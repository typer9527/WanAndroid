package com.yl.wanandroid.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ViewUtils {
    private static final String TAG = "ViewUtils";
    public static final int VERTICAL = LinearLayout.VERTICAL;
    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int DIVIDER_DEFAULT = -1;

    // 为RecyclerView添加分割线
    public static void addItemDivider(Context context, int orientation, RecyclerView recyclerView, int drawable) {
        DividerItemDecoration divider = new DividerItemDecoration(context, orientation);
        if (drawable == DIVIDER_DEFAULT) {
            recyclerView.addItemDecoration(divider);
        } else {
            Drawable drawableDivider = ContextCompat.getDrawable(context, drawable);
            if (drawableDivider != null) {
                // 为首个item设置上边的padding和左边的margin
                if (orientation == VERTICAL)
                    recyclerView.setPadding(0, drawableDivider.getIntrinsicHeight() / 2, 0, 0);
                if (orientation == HORIZONTAL)
                    setMarginLeft(recyclerView, px2dip(context, drawableDivider.getIntrinsicWidth()));
                divider.setDrawable(drawableDivider);
                recyclerView.addItemDecoration(divider);
            } else {
                Log.e(TAG, "addItemDivider: drawable is null");
            }
        }
    }

    private static void setMarginLeft(View view, int left) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(dip2px(view.getContext(), left), dip2px(view.getContext(), 0),
                    dip2px(view.getContext(), 0), dip2px(view.getContext(), 0));
            view.requestLayout();
        } else {
            Log.e(TAG, "setMarginLeft: fail to set margin");
        }
    }

    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
