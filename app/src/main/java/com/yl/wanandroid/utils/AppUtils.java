package com.yl.wanandroid.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.yl.wanandroid.R;

import java.io.File;
import java.math.BigDecimal;

/**
 * 获取应用相关信息的工具类
 */
public class AppUtils {
    private AppUtils() {
        throw new Error("Do not need instantiate!");
    }

    public static String getName(Context context) {
        return context.getString(R.string.app_name);
    }

    public static String getVersionName(Context context) {
        String verName = "";
        try {
            String packageName = context.getPackageName();
            verName = context.getPackageManager().getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    public static int getVersionCode(Context context) {
        int verCode = -1;
        try {
            String packageName = context.getPackageName();
            verCode = context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verCode;
    }

    public static String getCacheSize(Context context) {
        long cacheSize = 0;
        try {
            cacheSize = getFolderSize(context.getCacheDir());
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                cacheSize += getFolderSize(context.getExternalCacheDir());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getFormatSize(cacheSize);
    }

    public static void cleanCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    private static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                // 如果下面还有文件
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    // 格式化缓存大小
    private static String getFormatSize(double size) {
        double kByte = size / 1024;
        if (kByte < 1) {
            return null;
        }
        double mByte = kByte / 1024;
        if (mByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }
        double gByte = mByte / 1024;
        if (gByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(mByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }
        double tBytes = gByte / 1024;
        if (tBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(tBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }
        return dir != null && dir.delete();
    }
}
