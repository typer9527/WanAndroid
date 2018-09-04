package com.yl.wanandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsUtils {
    private static final String FILE_NAME = "shared_prefs_data";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static boolean getBoolean(Context context, String key) {
        return getSharedPreferences(context).getBoolean(key, false);
    }
}
