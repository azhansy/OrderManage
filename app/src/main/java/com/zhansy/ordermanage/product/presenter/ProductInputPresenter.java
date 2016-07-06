package com.zhansy.ordermanage.product.presenter;

import android.graphics.Bitmap;
import android.net.Uri;

import com.zhansy.ordermanage.base.mvp.model.ProductBean;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface ProductInputPresenter {
    void getProductType();
    void productUpdate(ProductBean bean);
    void productDelete(String code);
    void setProductData(ProductBean bean);
    /**
     * 请求数据
     */
     void getData();
    void getCouse();
    void setCatePosition(int position);
    void setChileCatePosition(int position);
    void getallCourse();
    void showCates();

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
