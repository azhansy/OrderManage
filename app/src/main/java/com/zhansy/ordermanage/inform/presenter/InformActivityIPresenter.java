package com.zhansy.ordermanage.inform.presenter;

import com.zhansy.ordermanage.base.mvp.model.InformBean;

/**
 * Created by Administrator on 2016/4/11.
 */
public interface InformActivityIPresenter {
    void getData();
    void informDelete(int code);
    void informCreate(String info);
}
