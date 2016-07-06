package com.zhansy.ordermanage.chart.controller;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.loopj.android.http.RequestParams;
import com.zhansy.ordermanage.base.DisplayCallback;
import com.zhansy.ordermanage.chart.business.MpChartBusiness;

/**
 * Created by ZHANSY on 2016/4/12.
 */
public class MpChartController {
    private Context mContext;
    private Handler mHandler;
    private MpChartBusiness mBusiness = MpChartBusiness.getInstance();

    public MpChartController(Context context,Handler handler){
        this.mContext = context;
        this.mHandler = handler;
    }
    private DisplayCallback callback = new DisplayCallback() {
        @Override
        public void displayResult(int key, Object param) {
            if (mHandler != null) {
                Message msg = mHandler.obtainMessage();
                msg.what = key;
                msg.obj = param;
                mHandler.handleMessage(msg);

            }
        }
    };

    public void getdata(String key){
        RequestParams params=new RequestParams();
        params.add("action","product_code");
        params.add("name",key);
        mBusiness.getdata(params,callback);
    }
}
