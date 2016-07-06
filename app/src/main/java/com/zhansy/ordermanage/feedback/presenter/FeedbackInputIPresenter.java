package com.zhansy.ordermanage.feedback.presenter;

import android.graphics.Bitmap;
import android.net.Uri;


/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface FeedbackInputIPresenter {
    /**
     * @param order_code
     * @param product_issue
     * @param describe
     * @param product_code
     */
    void setData(String order_code,String product_issue,String describe,String product_code);


    /**
     * 上传商品图片
     */
    void submitProductImage();

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
}
