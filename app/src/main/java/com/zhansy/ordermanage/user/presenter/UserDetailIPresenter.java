package com.zhansy.ordermanage.user.presenter;

import com.zhansy.ordermanage.base.mvp.model.UserBean;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface UserDetailIPresenter {
    void deleteUser(String code);
    void updateUser(UserBean bean);
}
