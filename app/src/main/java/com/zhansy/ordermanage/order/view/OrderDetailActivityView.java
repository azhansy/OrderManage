package com.zhansy.ordermanage.order.view;

import com.zhansy.ordermanage.base.mvp.view.IBaseUi;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface OrderDetailActivityView extends IBaseUi{

    void setAdminCheck(boolean b);

    void operSuccess(String tip);
    void operFailed(String tip);

    void downLoadSuccess(String path);
    void downLoadFailed();

}
