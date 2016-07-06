package com.zhansy.ordermanage.me.view;


import com.zhansy.ordermanage.base.mvp.view.IBaseUi;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface MeFragmentView extends IBaseUi {

    /**
     * 设置各状态的订单数量
//     */
//    void setNoPaymentNumber(String noPaymentNumber);
//
//    void setNoDeliveryNumber(String noDeliveryNumber);
//
//    void setNoConfirmNumber(String noConfirmNumber);
//
//    void setNoEvaluate(String noEvaluate);
//
//    void setComplete(String complete);

    /**
     * 显示用户名、公司和头像
     */
    void setUserName(String userName);

    void setUserIcon(String userIcon);

    void setCompany(String company);
}
