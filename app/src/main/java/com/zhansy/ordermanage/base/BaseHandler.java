package com.zhansy.ordermanage.base;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by ZHANSY on 2016/4/19.
 * 使用弱引用 Handler 防止内存溢出
 */
public abstract class BaseHandler<T> extends Handler {

    private WeakReference<T> weakReference;

    public BaseHandler(T t) {
        this.weakReference = new WeakReference<>(t);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        T t = weakReference.get();
        if(t==null) return;
        handleMessageProcess(msg,t);
    }

    public abstract void handleMessageProcess(Message msg,T t);
}
