package com.zhansy.ordermanage.login.presenter;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface LoginPresenter {


    /**
     * @param username
     * @param password
     */
    void gotoLogin(String username,String password);

    /**
     * 把用户数据保存包本地数据库
     */
    void getUserDataToSQL();
}
