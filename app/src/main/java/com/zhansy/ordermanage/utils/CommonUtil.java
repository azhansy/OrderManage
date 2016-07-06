package com.zhansy.ordermanage.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.OMApplication;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class CommonUtil {

    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
//            versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }
    public static boolean hasSDcard(){
        String sdStatus = Environment.getExternalStorageState();
        if (sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return true;
        }
        return false;
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 记住选中状态
     * @param key
     * @param s
     * @return
     */
    public static int getSelectionSpinner(String key,String s){
        if (key.equals("order_type")) {
            String[] strings = OMApplication.getInstance().getResources().getStringArray(R.array.order_type);
            for (int i = 0; i < strings.length; i++) {
                if (s.equals(strings[i])) {
                    return i;
                }
            }
        }/*else if (key.equals("pay_mode")){
            String[] strings = OMApplication.getInstance().getResources().getStringArray(R.array.pay_mode);
            for (int i = 0; i < strings.length; i++) {
                if (s.equals(strings[i].toString())) {
                    return i;
                }
            }
        }*/
        return 0;
    }
    /**
     * @param response 打印日记
     */
    public static void showLog(String response){
        Log.d("ZHANSY",response);
    }

    /**
     * @return 返回本地 SharedPreferences
     */
    public static SharedPreferences getSharedPreferences(){
        SharedPreferences sp = OMApplication.getInstance().getSharedPreferences("zhansy", OMApplication.getInstance().MODE_APPEND);
        return  sp;

    }

    /**
     * 服务器 主机地址
     */
    public static final String host = OMApplication.getInstance().getString(R.string.host);
    /**
     * 下载路径 地址  内存卡
     */
    public static final String hostFile = Environment.getExternalStorageDirectory().getPath() + "/azhansy";
    /**
     * 组装请求URL
     *
     * @param stringID
     * @return 请求地址
     */
    public static String appendRequesturl(int... stringID){
        int[] ids = stringID;
        StringBuffer sb = new StringBuffer();
        sb.append(host);
        for(int resourse : ids){
            sb.append(OMApplication.getInstance().getString(resourse));
        }
        return sb.toString();
    }

    public static void openFile(File file){

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType (uri, "application/pdf");
        OMApplication.getInstance().startActivity(intent);
    }
}
