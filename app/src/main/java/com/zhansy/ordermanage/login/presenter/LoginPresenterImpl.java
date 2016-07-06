package com.zhansy.ordermanage.login.presenter;

import android.os.Handler;
import android.os.Message;

import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.inform.business.InformBusiness;
import com.zhansy.ordermanage.inform.controller.InformController;
import com.zhansy.ordermanage.login.view.LoginActivityView;
import com.zhansy.ordermanage.order.business.OrderBusiness;
import com.zhansy.ordermanage.order.controller.OrderController;

import java.lang.ref.WeakReference;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class LoginPresenterImpl extends MVPBasePresenter implements LoginPresenter {
    private OrderController orderController;
    private InformController informController;//公告
    private LoginPresenterImplHandler handler = new LoginPresenterImplHandler(this);

    public LoginPresenterImpl() {
        orderController = new OrderController(getContext(), handler);
        informController = new InformController(getContext(), handler);
    }

    @Override
    public void gotoLogin(String username, String password) {
        orderController.goToLogin(username,password);
    }

    @Override
    public void getUserDataToSQL() {

    }

    public static class LoginPresenterImplHandler extends Handler {
        private WeakReference<LoginPresenter> presenter;

        public LoginPresenterImplHandler(LoginPresenter presenter) {
            this.presenter = new WeakReference<LoginPresenter>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (presenter != null && presenter.get() != null) {
                LoginPresenterImpl p = (LoginPresenterImpl) presenter.get();
                p.handleMessage(msg);
            }
        }
    }
    private void handleMessage(Message msg) {
        if (msg.what == OrderBusiness.STATE_LOGIN_SUCCESS) {
            LoginActivityView iview = getActualUi();
            if (iview == null) return;
            String userType = (String) msg.obj;
            iview.loginSuccess(userType);
            informController.getData();
        }
        if (msg.what == OrderBusiness.STATE_LOGIN_ERROR){
            LoginActivityView iview = getActualUi();
            if (iview == null) return;
            iview.loginFail();
        }
        if (msg.what == OrderBusiness.STATE_LOGIN_ERROR_PC){
            LoginActivityView iview = getActualUi();
            if (iview == null) return;
            iview.loginFailPC();
        }
//        if (msg.what == InformBusiness.STATE_INFORM_DATA_SUCCESS) {
//
//        }
    }
}
