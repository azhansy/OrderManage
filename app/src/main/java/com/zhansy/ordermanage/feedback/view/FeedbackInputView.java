package com.zhansy.ordermanage.feedback.view;

import android.content.Intent;

import com.zhansy.ordermanage.base.mvp.view.IBaseUi;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface FeedbackInputView extends IBaseUi{
    void commitSuccess();
    void commitFailed();

    void startPictureActivity(Intent i, int j);

    void finishActivity();
}
