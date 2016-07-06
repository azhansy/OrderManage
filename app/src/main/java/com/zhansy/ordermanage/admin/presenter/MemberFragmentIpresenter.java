package com.zhansy.ordermanage.admin.presenter;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface MemberFragmentIpresenter {

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
