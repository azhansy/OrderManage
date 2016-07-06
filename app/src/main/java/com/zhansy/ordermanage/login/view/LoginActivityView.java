package com.zhansy.ordermanage.login.view;


import com.zhansy.ordermanage.base.mvp.view.IBaseUi;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface LoginActivityView extends IBaseUi{

    void loginSuccess(String userType);//用户类型

    void loginFail();//登录失败

    void loginFailPC();
//    void setDBData();

}
