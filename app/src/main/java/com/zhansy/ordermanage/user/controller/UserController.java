package com.zhansy.ordermanage.user.controller;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.loopj.android.http.RequestParams;
import com.zhansy.ordermanage.base.DisplayCallback;
import com.zhansy.ordermanage.base.mvp.model.UserBean;
import com.zhansy.ordermanage.user.business.UserBusiness;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class UserController {
    private Handler mHandler;
    private Context mContext;
    private UserBusiness mBusiness = UserBusiness.getInstance();
    public UserController(Context mContext, Handler mHandler){
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

    public void userRegisterCommit(UserBean bean){
        RequestParams params=new RequestParams();
        params.put("username",bean.getUsername());
        params.put("password",bean.getPassword());
        params.put("phone_number",bean.getPhone_number());
        params.put("address",bean.getUser_address());
        params.put("company",bean.getCompany());
        params.put("postcode",bean.getPostcode());
        mBusiness.userRegisterCommit(params,callback);
    }
    public void userUpdate(UserBean bean){
        RequestParams params=new RequestParams();
        params.put("user_code",bean.getUser_code());
        params.put("username",bean.getUsername());
        params.put("user_type",bean.getUser_type());
        params.put("phone_number",bean.getPhone_number());
        params.put("address",bean.getUser_address());
        params.put("company",bean.getCompany());
        mBusiness.userUpdate(params,callback);
    }
    public void userSearch(int page){
        RequestParams params=new RequestParams();
        params.put("page", page+1); //后台从1开始查询
        mBusiness.SearchUser(params,callback);
    }
    public void userDelete(String user_code){
        RequestParams params=new RequestParams();
        params.put("user_code",user_code);
        mBusiness.deleteUser(params,callback);
    }

    public void updatePwd(String oldPswd,String pswd){
        RequestParams params=new RequestParams();
        params.put("password",oldPswd);
        params.put("new_password",pswd);
        mBusiness.updatePwd(params,callback);
    }

    public void uploadFile(String path,UserBean bean){
        RequestParams params=new RequestParams();
        params.put("user_code",bean.getUser_code());
        try {
            mBusiness.uploadFile(path,mContext,params,callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
