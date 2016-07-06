package com.zhansy.ordermanage.feedback.controller;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.loopj.android.http.RequestParams;
import com.zhansy.ordermanage.base.DisplayCallback;
import com.zhansy.ordermanage.base.mvp.model.FeedBackBean;
import com.zhansy.ordermanage.feedback.business.FeedBackBusiness;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class FeedBackController {
    private Handler mHandler;
    private Context mContext;
    private FeedBackBusiness mBusiness = FeedBackBusiness.getInstance();
    public FeedBackController(Context mContext, Handler mHandler){
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

    public void createFeedback(String order_code, String product_issue, String describe,String product_code){
        RequestParams params=new RequestParams();
        params.add("order_code",order_code);
        params.add("product_issue",product_issue);
        params.add("describe",describe);
        params.add("product_code",product_code);
        mBusiness.createFeedback(params,callback);
    }
    public void getdata(int page,String key,String key_word){
        RequestParams params=new RequestParams();
        params.add("page",String.valueOf(page));
        params.add("action",key);
        params.add("name",key_word);
        mBusiness.getdata(params,callback);
    }
    public void deleteFeedback(String msg_id){
        RequestParams params=new RequestParams();
        params.add("msg_id",msg_id);
        mBusiness.deleteFeedback(params,callback);
    }
}
