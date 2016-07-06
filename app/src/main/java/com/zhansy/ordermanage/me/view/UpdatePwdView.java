package com.zhansy.ordermanage.me.view;

import android.widget.EditText;

import com.zhansy.ordermanage.base.mvp.view.IBaseUi;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface UpdatePwdView extends IBaseUi {

    EditText getOldPswdEditText();
    EditText getNewPswdEditText();
    EditText getPswdEditText();

    void getUpdatePswdResult(boolean successOrNot);
}
