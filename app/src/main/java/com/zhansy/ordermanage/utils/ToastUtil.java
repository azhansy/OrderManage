package com.zhansy.ordermanage.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * ToastUtil
 * 方便统一整个应用的提示时间和样式的统一
 *
 */
public class ToastUtil {
	public static void showToast(Context context,String msg){
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
}
