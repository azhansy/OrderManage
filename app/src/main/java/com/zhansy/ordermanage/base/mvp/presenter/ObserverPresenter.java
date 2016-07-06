package com.zhansy.ordermanage.base.mvp.presenter;


import com.zhansy.ordermanage.base.mvp.view.ObserverPresenterBaseUi;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface ObserverPresenter<T extends ObserverPresenterBaseUi> {

    void attach(T ui);//附加UI

    void detach(T ui);//去除UI

}
