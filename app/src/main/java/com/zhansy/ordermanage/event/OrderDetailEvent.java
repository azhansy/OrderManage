package com.zhansy.ordermanage.event;

/**
 * Created by ZHANSY on 2016/4/22.
 * 商品反馈完成要更新订单状态
 */
public class OrderDetailEvent {
    String order_code;
    public OrderDetailEvent(String code){
        this.order_code = code;
    }

    public String getOrder_code() {
        return order_code;
    }
}
