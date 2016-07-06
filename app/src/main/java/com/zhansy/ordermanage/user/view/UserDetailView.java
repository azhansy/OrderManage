package com.zhansy.ordermanage.user.view;

import com.zhansy.ordermanage.base.mvp.view.IBaseUi;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface UserDetailView extends IBaseUi{
    void UserDeleteSuccess();
    void UserDeleteFailed();
    void UserUpdateSuccess();
    void UserUpdateFailed();

}
