package com.zhansy.ordermanage.me.presenter;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.me.view.UpdatePwdView;
import com.zhansy.ordermanage.user.business.UserBusiness;
import com.zhansy.ordermanage.user.controller.UserController;
import com.zhansy.ordermanage.utils.ToastUtil;

import java.lang.ref.WeakReference;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class UpdatePwdPresenterImpl extends MVPBasePresenter implements UpdatePwdPresenter {
    private String pswd;
    private String oldPswd;//旧密码

    @Override
    public boolean getEditTextString() {
        UpdatePwdView updatePwdView = getActualUi();
        if (updatePwdView == null){
            return false;
        }
        oldPswd = updatePwdView.getOldPswdEditText().getText().toString().trim();
        String newPswd = updatePwdView.getNewPswdEditText().getText().toString().trim();
        pswd = updatePwdView.getPswdEditText().getText().toString().trim();
        if (TextUtils.isEmpty(oldPswd) || TextUtils.isEmpty(newPswd)|| TextUtils.isEmpty(pswd)){
            ToastUtil.showToast(getContext(),"密码不能为空，请重新输入");
            return false;
        }else if (newPswd.equals(pswd)){
            return true;
        }else {
            ToastUtil.showToast(getContext(),"新密码不一致，请重新输入");
            return false;
        }
    }

    @Override
    public void updatePwd() {
        controller.updatePwd(oldPswd,pswd);
    }

    private UserController controller;
    private MyHandler mHandler = new MyHandler(this);

    public UpdatePwdPresenterImpl(){
        controller = new UserController(getContext(),mHandler);
    }
    public static  class MyHandler extends Handler {
        WeakReference<UpdatePwdPresenter> presenter;
        public  MyHandler(UpdatePwdPresenter presenter){
            this.presenter = new WeakReference<UpdatePwdPresenter>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(presenter!=null&&presenter.get()!=null){
                UpdatePwdPresenterImpl p= (UpdatePwdPresenterImpl) presenter.get();
                p.handleMessage(msg);
            }
        }
    }
    public void handleMessage(Message msg) {

        if(msg.what== UserBusiness.STATE_USER_UPDATE_DATA_SUCCESS){
            UpdatePwdView iview = getActualUi();
            if (iview == null){
                return;
            }
            iview.getUpdatePswdResult(true);
        }
        if(msg.what== UserBusiness.STATE_USER_UPDATE_DATA_ERROR){
            UpdatePwdView iview = getActualUi();
            if (iview == null){
                return;
            }
            iview.getUpdatePswdResult(false);
        }
    }
}
