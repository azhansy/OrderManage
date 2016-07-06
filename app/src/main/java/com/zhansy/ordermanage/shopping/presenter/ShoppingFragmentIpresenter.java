package com.zhansy.ordermanage.shopping.presenter;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface ShoppingFragmentIpresenter {

    void getData();
    /**
     * 刷新
     */
    void Refresh(String status);

    /**
     * 加载
     */
    void Load(String status);

}
