//package com.zhansy.ordermanage.shopping.business;
//
//import android.util.Log;
//
//import com.loopj.android.http.TextHttpResponseHandler;
//import com.zhansy.ordermanage.utils.CommonUtil;
//import com.zhansy.ordermanage.utils.HttpUtil;
//
//import org.apache.http.Header;
//
///**
// * Created by ZHANSY
// * 邮箱：azhansy@hotmail.com
// */
//public class DiscoveryBusiness {
//
//    public static void getDiscoveryData(){
//        HttpUtil.get(CommonUtil.host, new TextHttpResponseHandler() {
//            @Override
//            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                Log.d("ZHANSY", "失败：" + s);
//            }
//
//            @Override
//            public void onSuccess(int i, Header[] headers, String s) {
//                Log.d("ZHANSY", "成功：" + s);
//            }
//        });
//    }
//}
//
