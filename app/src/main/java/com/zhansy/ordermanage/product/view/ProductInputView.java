package com.zhansy.ordermanage.product.view;

import android.content.Intent;

import com.zhansy.ordermanage.base.mvp.view.IBaseUi;
import com.zhansy.ordermanage.index.adapter.ProductOptionAdapter;

/**
 * Created by ZHANSY on 2016/4/12.
 */
public interface ProductInputView extends IBaseUi{
    void commitSuccess();
    void commitFailed();
    void setCateAdapter(ProductOptionAdapter adapter);
    void setChildCateAdapter(ProductOptionAdapter adapter);
    void showCourseDialog();
    void setCate(String cate);

    void startPictureActivity(Intent i, int j);

    void finishActivity();
}
