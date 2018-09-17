package com.yl.wanandroid.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.yl.wanandroid.R;

import java.lang.reflect.Field;

public class ViewUtils {
    private static final String TAG = "ViewUtils";
    public static final int DIVIDER_DEFAULT = -1;

    // 利用反射关闭BottomNavigationView的Shift动画
    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // 为RecyclerView添加分割线
    public static void addItemDivider(Context context, RecyclerView recyclerView, int drawable) {
        DividerItemDecoration divider = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        if (drawable == DIVIDER_DEFAULT) {
            recyclerView.addItemDecoration(divider);
        } else {
            Drawable drawableDivider = ContextCompat.getDrawable(context, R.drawable.shape_rv_divider);
            if (drawableDivider != null) {
                divider.setDrawable(drawableDivider);
                recyclerView.addItemDecoration(divider);
            } else {
                Log.e(TAG, "addItemDivider: ");
            }
        }
    }
}
