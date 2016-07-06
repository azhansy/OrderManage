package com.zhansy.ordermanage.shopping.view;

import android.widget.RelativeLayout;

import com.zhansy.ordermanage.base.mvp.view.IBaseUi;
import com.zhansy.ordermanage.base.widget.XListView;
import com.zhansy.ordermanage.index.adapter.ProductAdapter;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface ShoppingFragmentView extends IBaseUi {

    /**
     * @param adapter 设置产品列表适配器
     */
    void setProductAdapter(ProductAdapter adapter);

    /**
     * @return
     */
    XListView getProductXListView();

    /**
     * @param number 显示提示数量
     */
    void setNoticeNumber(String number);

    void setEmptyTip();

    RelativeLayout getNoDataView();
}
