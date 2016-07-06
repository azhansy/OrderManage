package com.zhansy.ordermanage.order.controller;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.zhansy.ordermanage.base.DisplayCallback;
import com.zhansy.ordermanage.order.business.OrderBusiness;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class OrderListController {
    private Handler mHandler;
    private Context mContext;
    private OrderBusiness mBusiness = OrderBusiness.getInstance();
    public OrderListController(Context mContext, Handler mHandler){
        this.mContext = mContext;
        this.mHandler = mHandler;
    }

    private DisplayCallback callback = new DisplayCallback() {

        @Override
        public void displayResult(int key, Object result) {
            if (mHandler != null) {
                Message msg = mHandler.obtainMessage();
                msg.what = key;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        }
    };

//    public void getOrderList(int page,String status){
//        RequestParams params=new RequestParams();
//        params.add("page",String.valueOf(page));
//        params.add("status",status);
//        mBusiness.getProductList(mContext,params,callback);
//    }
}
