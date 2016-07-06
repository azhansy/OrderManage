package com.zhansy.ordermanage.taste.controller;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.loopj.android.http.RequestParams;
import com.zhansy.ordermanage.base.DisplayCallback;
import com.zhansy.ordermanage.base.mvp.model.TasteReturnBean;
import com.zhansy.ordermanage.taste.business.TasteReturnBusiness;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class TasteReturnController {
    private Handler mHandler;
    private Context mContext;
    private TasteReturnBusiness mBusiness = TasteReturnBusiness.getInstance();
    public TasteReturnController(Context mContext, Handler mHandler){
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

    public void tasteCommit(TasteReturnBean bean){
        RequestParams params=new RequestParams();
        params.put("content",bean.getContent());
        params.put("phone",bean.getPhone());
        mBusiness.createTaste(params,callback);
    }
    public void deleteTaste(String id){
        RequestParams params=new RequestParams();
        params.put("id",id);
        mBusiness.deleteTaste(params,callback);
    }

    public void getData( ){
        RequestParams params=new RequestParams();
        mBusiness.searchTaste(params,callback);
    }

}
