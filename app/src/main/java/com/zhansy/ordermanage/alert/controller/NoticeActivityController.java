package com.zhansy.ordermanage.alert.controller;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.loopj.android.http.RequestParams;
import com.zhansy.ordermanage.alert.business.NiticeActivityBusiness;
import com.zhansy.ordermanage.base.DisplayCallback;
import com.zhansy.ordermanage.feedback.business.FeedBackBusiness;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class NoticeActivityController {
    private Handler mHandler;
    private Context mContext;
    private NiticeActivityBusiness mBusiness = NiticeActivityBusiness.getInstance();
    public NoticeActivityController(Context mContext, Handler mHandler){
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

    public void getdata(){
        RequestParams params=new RequestParams();
        mBusiness.getdata(params,callback);
    }
}
