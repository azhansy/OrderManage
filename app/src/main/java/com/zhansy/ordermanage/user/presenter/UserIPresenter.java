package com.zhansy.ordermanage.user.presenter;

import android.graphics.Bitmap;
import android.net.Uri;

import com.zhansy.ordermanage.base.mvp.model.UserBean;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface UserIPresenter {
    void getData();
    void upLoadIcon();
    void userRegisterCommit(UserBean userBean);
    void userUpdate(UserBean userBean);

    /**
     * 上传头像
     */
    void submitHead();

    /**
     * 弹出对话框
     */
    void showPhoto();


    /**
     * 裁剪
     */
    void cropPhoto(Uri uri);

    /**
     * 保存SD卡
     * @param bitmap
     */
    void saveSd(Bitmap bitmap);
    /**
     * 取消对话框
     */
    void cancel();

    void Refresh(String status);
    void Load(String status);
}
