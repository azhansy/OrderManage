package com.zhansy.ordermanage.inform.controller;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.loopj.android.http.RequestParams;
import com.zhansy.ordermanage.base.DisplayCallback;
import com.zhansy.ordermanage.inform.business.InformBusiness;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class InformController {
    private Handler mHandler;
    private Context mContext;
    private InformBusiness mBusiness = InformBusiness.getInstance();
    public InformController(Context mContext, Handler mHandler){
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


    public void getData( ){
        RequestParams params=new RequestParams();
        mBusiness.getInformData(params,callback);
    }
    public void informDelete(int code){
        RequestParams params=new RequestParams();
        params.add("id",code+"");
        mBusiness.deleteInform(params,callback);
    }
    public void informCreate(String info){
        RequestParams params=new RequestParams();
        params.add("content",info);
        mBusiness.createInform(params,callback);
    }

}
