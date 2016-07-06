package com.zhansy.ordermanage.admin.presenter;

import com.zhansy.ordermanage.base.mvp.model.OrderBean;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface AddOrderAdminIpresenter {

    void createOrderData(OrderBean orderBean);

    void updateOrderData(String code,String status,String remark);
}
