package com.zhansy.ordermanage.taste.view;

import com.zhansy.ordermanage.base.mvp.view.IBaseUi;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface TasteReturnView extends IBaseUi {
    void commitSuccess();
    void commitFailed();
}
