package com.zhansy.ordermanage.me.presenter;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface UpdatePwdPresenter {

    /**
     * @return 判断修改密码是否符合条件
     */
    boolean getEditTextString();

    void updatePwd();
}
