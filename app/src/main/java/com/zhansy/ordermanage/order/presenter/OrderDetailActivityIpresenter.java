package com.zhansy.ordermanage.order.presenter;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface OrderDetailActivityIpresenter {

    void setData();

    /**
     * 取消删除订单
     */
    void deleteOrder(String code);
    void downloadOrderPDF(String orderCode);
    void downloadOrderExcel(String orderCode);
}
