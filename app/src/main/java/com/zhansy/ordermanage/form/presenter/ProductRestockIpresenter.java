package com.zhansy.ordermanage.form.presenter;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface ProductRestockIpresenter {
    /**
     * 获取出货单列表
     */
    void getRestockData(String status);

    /**
     * 点击全选按钮
     */
    void btnCheckBoxAll();
    void createOrder();
    /**
     * 刷新
     */
    public void Refresh(String status);

    /**
     * 加载
     */
    public void Load(String status);
}
