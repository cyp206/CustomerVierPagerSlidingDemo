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

//    public static boolean isNeedShowUpdatePoint(Context context) {
//        int configVersionCode = AppConfig.getConfig(context).getUpdate().getLastVersionCode();
//        int appVersionCode = ApplicationUtils.getInstance(context).getVersionCode();
//        if (configVersionCode <= 0 || appVersionCode <= 0) return false;
//        return configVersionCode > appVersionCode && PrefUtils.getBoolean(context, PrefKey.Navigation.SHOW_POINT_WITHOUT_CLICK, true);
//    }
//
//    public static boolean isNeedUpdate(Context context) {
//        int configVersionCode = AppConfig.getConfig(context).getUpdate().getLastVersionCode();
//        int appVersionCode = ApplicationUtils.getInstance(context).getVersionCode();
//        if (configVersionCode <= 0 || appVersionCode <= 0) return false;
//        return configVersionCode > appVersionCode;
//    }
//
//    public static void goGpOrBooster(Context mContext) {
//        if (mContext == null) return;
//        String boosterPkgName = "mobi.supo.cleaner";
//        if (!TextUtils.isEmpty(AppConfig.getConfig(mContext).getNavigation().getJcPackage())) {
//            boosterPkgName = AppConfig.getConfig(mContext).getNavigation().getJcPackage();
//        }
//        if (PackageUtils.isPackageInstalled(mContext, boosterPkgName)) {
//            PackageManager packageManager = mContext.getPackageManager();
//            Intent intent = packageManager.getLaunchIntentForPackage(boosterPkgName);
//            mContext.startActivity(intent);
//        } else {
//            String clickUrl = PackageUtils.isGooglePlayInstalled(mContext) ? AppConfig.getConfig(mContext).getNavigation().getJcGp() : AppConfig.getConfig(mContext).getNavigation().getJc();
//            Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setData(Uri.parse(clickUrl));
//            try {
//                mContext.startActivity(i);
//            } catch (ActivityNotFoundException e) {
//                //TODO
//            }
//        }
//    }
//
//    public static void goGpBooster(Context mContext) {
//        if (mContext == null) return;
//        String appPackageName = "mobi.supo.cleaner";
//        if (!TextUtils.isEmpty(AppConfig.getConfig(mContext).getNavigation().getJcPackage())) {
//            appPackageName = AppConfig.getConfig(mContext).getNavigation().getJcPackage();
//
//        }
//        try {
//            Intent vIntent = new Intent(Intent.ACTION_MAIN);
//            vIntent.setComponent(new ComponentName(appPackageName, "mobi.yellow.booster.modules.main.MainActivity"));
//            mContext.startActivity(vIntent);
//        } catch (Exception e) {
//            try {
//                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//            } catch (android.content.ActivityNotFoundException anfe) {
//                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//            }
//        }
//
//
//    }
//
//    //跳转security
//    public static void goGpOrSecurity(Context mContext) {
//        if (mContext == null) return;
//        String boosterPkgName = "com.supo.security";
//        if (!TextUtils.isEmpty(AppConfig.getConfig(mContext).getNavigation().getJcPackage())) {
//            boosterPkgName = AppConfig.getConfig(mContext).getNavigation().getJcPackage();
//        }
//        if (PackageUtils.isPackageInstalled(mContext, boosterPkgName)) {
//            PackageManager packageManager = mContext.getPackageManager();
//            Intent intent = packageManager.getLaunchIntentForPackage(boosterPkgName);
//            mContext.startActivity(intent);
//        } else {
//            String clickUrl = PackageUtils.isGooglePlayInstalled(mContext) ? AppConfig.getConfig(mContext).getNavigation().getJcGp() : AppConfig.getConfig(mContext).getNavigation().getJc();
//            Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setData(Uri.parse(clickUrl));
//            try {
//                mContext.startActivity(i);
//            } catch (ActivityNotFoundException e) {
//                //TODO
//            }
//        }
//    }
//
//    /**
//     * 跳转 Security gp无关配置
//     */
//    public static void goGpOrSecurityUnrelatedConfig(Context mContext) {
//        if (mContext == null) return;
//        String appPackageName = "com.supo.security";
//
//        if (PackageUtils.forSps(mContext, appPackageName)) {
//
//            try {
//                Intent vIntent = new Intent(Intent.ACTION_MAIN);
//                ComponentName vComponentName = new ComponentName(appPackageName, "com.yellow.security.activity.MainActivity");
//                vIntent.setComponent(vComponentName);
//                vIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                mContext.startActivity(vIntent);
//            } catch (Exception e) {
//                e.printStackTrace();
//                goSpGp(mContext, appPackageName);
//            }
//
//        } else {
//            goSpGp(mContext, appPackageName);
//        }
//    }
//
//    private static void goSpGp(Context mContext, String appPackageName) {
//        try {
//            Intent vIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));
//            vIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            mContext.startActivity(vIntent);
//        } catch (ActivityNotFoundException anfe) {
//            Intent vIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName));
//            vIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            mContext.startActivity(vIntent);
//        }
//    }
//
//    public static void setFirstDate(Context context) {
//        if (context == null) return;
//        long current = System.currentTimeMillis();
//        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd", Locale.US);
//        Date date = new Date(current);
//        String currentTime = formatter.format(date);
//        PrefUtils.putString(context, PrefKey.OPEN_SUPO_BATTERY_FIRST_TIME, currentTime);
//    }
//
//    public static boolean isFirstDay(Context context) {
//        if (context == null) return false;
//        long current = System.currentTimeMillis();
//        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd", Locale.US);
//        Date date = new Date(current);
//        String currentDate = formatter.format(date);
//        String firstDate = PrefUtils.getString(context, PrefKey.OPEN_SUPO_BATTERY_FIRST_TIME, "");
//        return !(TextUtils.isEmpty(firstDate) || !currentDate.equals(firstDate));
//    }
//
//    public static String FormetStringFileSize(long fileS) {
//        DecimalFormat df = new DecimalFormat("#.00");
//        try {
//            Constructor<DecimalFormat> constructor = DecimalFormat.class.getDeclaredConstructor(String.class, Locale.class);
//            constructor.setAccessible(true);
//            df = constructor.newInstance("#.00", Locale.US);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("FormetDoubleFileSize", "不能创建");
//            df = new DecimalFormat("#.00");
//        }
//        if (fileS <= 0) {
//            return EVENT_PARAM_VALUE_NO;
//        }
//        if (fileS < 1000) {
//            return fileS + "";
//        }
//        if (fileS < 1024000) {
//            return (fileS / 1024) + "";
//        }
//        if (fileS < 1048576000) {
//            return (fileS / ONE_MB) + "";
//        }
//        String s = null;
//        try {
//            s = Double.valueOf(df.format((double) (((float) fileS) / 1.07374182E9f)).replace(",", ".")) + "";
//        } catch (Exception e) {
//            e.printStackTrace();
//            s = df.format((double) (((float) fileS) / 1.07374182E9f)).replace(",", ".") + "";
//        }
//
//        return s;
//
//    }
//
//    public static String FormetUnit(long fileS) {
//        String fileSizeString = "";
//        String wrongSize = "KB";
//        if (fileS <= 0) {
//            return wrongSize;
//        }
//        if (fileS < 1000) {
//            fileSizeString = "B";
//        } else if (fileS < 1024000) {
//            fileSizeString = "KB";
//        } else if (fileS < 1048576000) {
//            fileSizeString = "MB";
//        } else {
//            fileSizeString = "GB";
//        }
//        return fileSizeString;
//    }
//
//
//    public static double FormetDoubleFileSize(long fileS) {
//        DecimalFormat df = new DecimalFormat("#.00");
//        try {
//            Constructor<DecimalFormat> constructor = DecimalFormat.class.getDeclaredConstructor(String.class, Locale.class);
//            constructor.setAccessible(true);
//            df = constructor.newInstance("#.00", Locale.US);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("FormetDoubleFileSize", "不能创建");
//            df = new DecimalFormat("#.00");
//        }
//        if (fileS <= 0) {
//            return 0.0d;
//        }
//        if (fileS < 1000) {
//            return Double.valueOf(df.format((double) fileS).replace(",", ".")).doubleValue();
//        }
//        if (fileS < 1024000) {
//            return Double.valueOf(df.format(((double) fileS) / 1024.0d).replace(",", ".")).doubleValue();
//        }
//        if (fileS >= 1048576000) {
//            return 0.0d;
//        }
//        try {
//            return Double.valueOf(df.format(((double) fileS) / 1048576.0d).replace(",", ".")).doubleValue();
//        } catch (Exception e) {
//            return (double) (((float) fileS) / 1.07374182E9f);
//        }
//    }
//
//
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
//
//    public static void openFaceBook(Context mContext) {
//        PackageManager pm = mContext.getPackageManager();
//        String URL = "https://www.facebook.com/SUPO-Battery-1801808116704698/";
//        Uri uri = Uri.parse(URL);
//        try {
//            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
//            if (applicationInfo.enabled) {
//                uri = Uri.parse("fb://facewebmodal/f?href=" + URL);
//            }
//        } catch (PackageManager.NameNotFoundException ignored) {
//            ignored.printStackTrace();
//        }
//        try {
//            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//            mContext.startActivity(intent);
//        } catch (Exception e) {
//            YbLog.i("CommonUtils", e.getMessage());
//        }
//    }
//
//    /**
//     * 是否显示clean result card
//     *
//     * @param context
//     * @return
//     */
//    public static boolean isShowCleanCard(Context context) {
//        return (System.currentTimeMillis() - PrefUtils.getLong(context, "LAST_TIME", Long.valueOf(0))) > Constants.Common.BATTERY_INFO_CHECK_INTERVAL;
//    }


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
