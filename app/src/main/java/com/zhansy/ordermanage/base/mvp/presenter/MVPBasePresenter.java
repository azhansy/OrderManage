package com.zhansy.ordermanage.base.mvp.presenter;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.WindowManager;

import com.zhansy.ordermanage.base.mvp.view.IBaseUi;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 * MVP主持人
 */
public class MVPBasePresenter extends ObserverBasePresenter<IBaseUi> {
    protected ProgressDialog progressDialog;

    public void showProgressDialog(Context context){
        if (progressDialog == null){
            progressDialog = ProgressDialog.show(context, "温馨提醒", "正在请求中...");
            progressDialog.setCanceledOnTouchOutside(true);
        }
        progressDialog.show();
    }
    public void hideProgressDialog(){
        if (progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

//    public void hideInputManager(Activity activity){
//        if (activity == null){
//            return;
//        }
//        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//    }
}
