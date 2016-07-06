package com.zhansy.ordermanage.user.presenter;

import android.os.Handler;
import android.os.Message;

import com.zhansy.ordermanage.base.mvp.model.UserBean;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.user.business.UserBusiness;
import com.zhansy.ordermanage.user.controller.UserController;
import com.zhansy.ordermanage.user.view.UserDetailView;
import java.lang.ref.WeakReference;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class UserDetailPresenterImpl extends MVPBasePresenter implements UserDetailIPresenter {
    private UserController controller;
    private MyHandler mHandler = new MyHandler(this);

    public UserDetailPresenterImpl(){
        controller = new UserController(getContext(),mHandler);
    }

    @Override
    public void deleteUser(String code) {
        controller.userDelete(code);
    }

    @Override
    public void updateUser(UserBean bean) {
        controller.userUpdate(bean);
    }

    public static  class MyHandler extends Handler {
        WeakReference<UserDetailIPresenter> presenter;
        public  MyHandler(UserDetailIPresenter presenter){
            this.presenter = new WeakReference<UserDetailIPresenter>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(presenter!=null&&presenter.get()!=null){
                UserDetailPresenterImpl p= (UserDetailPresenterImpl) presenter.get();
                p.handleMessage(msg);
            }
        }
    }
    public void handleMessage(Message msg) {

        if (msg.what == UserBusiness.STATE_USER_UPDATE_HEAD_DATA_SUCCESS) {
            UserDetailView iview = getActualUi();
            if (iview == null) {
                return;
            }
            iview.UserUpdateSuccess();
        }
        if (msg.what == UserBusiness.STATE_USER_UPDATE_HEAD_DATA_ERROR) {
            UserDetailView iview = getActualUi();
            if (iview == null) {
                return;
            }
            iview.UserUpdateFailed();
        }
        if (msg.what == UserBusiness.STATE_USER_UPDATE_DATA_SUCCESS) {
            UserDetailView iview = getActualUi();
            if (iview == null) {
                return;
            }
            iview.UserUpdateSuccess();
        }
        if (msg.what == UserBusiness.STATE_USER_UPDATE_DATA_ERROR) {
            UserDetailView iview = getActualUi();
            if (iview == null) {
                return;
            }
            iview.UserUpdateFailed();
        }
        if (msg.what == UserBusiness.STATE_USER_DELETE_DATA_SUCCESS) {
            UserDetailView iview1 = getActualUi();
            if (iview1 == null) {
                return;
            }
            iview1.UserDeleteSuccess();
        }
        if (msg.what == UserBusiness.STATE_USER_DELETE_DATA_ERROR) {
            UserDetailView iview1 = getActualUi();
            if (iview1 == null) {
                return;
            }
            iview1.UserDeleteFailed();
        }
    }
}
