package com.zhansy.ordermanage.taste.presenter;

import com.zhansy.ordermanage.base.mvp.model.TasteReturnBean;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface TasteReturnIpresenter {
    void getData();
    void commitTasteMassege(TasteReturnBean bean);
    void deleteTaste(String id);
}
