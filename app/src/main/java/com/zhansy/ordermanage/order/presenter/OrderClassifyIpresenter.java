package com.zhansy.ordermanage.order.presenter;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface OrderClassifyIpresenter {
    /**
     * 获取分类订单数据
     * @param key    待付款。。。关键字
     */
    void getOrderClassfyList(String key,String key_word);

    void orderDelete(String code);
    void deleteAllOrder();
    /**
     * 刷新
     */
    void Refresh(String status);

    /**
     * 加载
     */
    void Load(String status);
}
