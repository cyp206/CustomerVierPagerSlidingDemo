package com.snow.yiping.customervierpagerslidingdemo.utils;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Constructor;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;



/**
 * Created by Y on 2016/10/26.
 */

public class CommonUtils {
    public static final long ONE_MB = 1048576;
    public static final String EVENT_PARAM_VALUE_NO = "0";

    public static String FormetFileSize(long fileS) {
        String fileSizeString = "";
        String wrongSize = "0KB";
        if (fileS <= 0) {
            return wrongSize;
        }
        if (fileS < 1000) {
            fileSizeString = new DecimalFormat(get3numFormat((double) fileS)).format((double) fileS).replace(",", ".") + "B";
        } else if (fileS < 1024000) {
            fileSizeString = new DecimalFormat(get3numFormat(((double) fileS) / 1024.0d)).format(((double) fileS) / 1024.0d).replace(",", ".") + "KB";
        } else if (fileS < 1048576000) {
            fileSizeString = new DecimalFormat(get3numFormat(((double) fileS) / 1048576.0d)).format(((double) fileS) / 1048576.0d).replace(",", ".") + "MB";
        } else {
            fileSizeString = new DecimalFormat(get3numFormat(((double) fileS) / 1.073741824E9d)).format(((double) fileS) / 1.073741824E9d).replace(",", ".") + "GB";
        }
        return fileSizeString;
    }

    public static String get3numFormat(double num) {
        if (num < 10.0d) {
            return "0.00";
        }
        return num < 100.0d ? "##.0" : "###";
    }


    /**
     * 获取手机内部剩余存储空间
     *
     * @return
     */
    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * 获取手机内部总的存储空间
     *
     * @return
     */
    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }
}
