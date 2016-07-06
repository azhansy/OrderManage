package com.zhansy.ordermanage.user.view;

import android.content.Intent;

import com.zhansy.ordermanage.base.mvp.view.IBaseUi;
import com.zhansy.ordermanage.base.widget.XListView;
import com.zhansy.ordermanage.order.adapter.FeedBackAdapter;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface UserView extends IBaseUi {

    void upLoadUserIcon();
    void upLoadUserIconFailed();
    void userRegister();
    void userRegisterFailed();

    void startPictureActivity(Intent i, int j);
}
