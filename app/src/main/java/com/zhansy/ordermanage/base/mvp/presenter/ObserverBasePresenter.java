package com.zhansy.ordermanage.base.mvp.presenter;

import android.content.Context;
import android.os.Message;


import com.zhansy.ordermanage.base.OMApplication;
import com.zhansy.ordermanage.base.mvp.view.ObserverPresenterBaseUi;

import java.lang.ref.WeakReference;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public abstract class ObserverBasePresenter<T extends ObserverPresenterBaseUi> implements ObserverPresenter<T> {

    private WeakReference<T> mObserverUi;//弱引用

    @Override
    public void attach(T ui) {
        if (ui != null) {
            mObserverUi = new WeakReference<>(ui);
        }
    }

    @Override
    public void detach(T ui) {
        if (mObserverUi != null) {
            mObserverUi.clear();
            mObserverUi = null;
        }
    }

    protected Context getContext() {
        Context context = null;
        T ui = getObserverUi();
        if (ui != null) {
            context = ui.getPresenterContext();
        }
        if (context == null) {
            context = OMApplication.getInstance().getApplicationContext();
        }
        return context;
    }

    protected T getObserverUi() {
        return mObserverUi == null ? null : mObserverUi.get();
    }

    protected boolean isUiAttached() {
        return mObserverUi != null && mObserverUi.get() != null;
    }

    protected <AU extends T> AU getActualUi() {
        if (!isUiAttached()) {
            return null;
        }

        ObserverPresenterBaseUi ui = getObserverUi();
        AU au;
        try {
            au = (AU) ui;
        } catch (Exception e) {
            au = null;
        }
        return au;
    }


}
