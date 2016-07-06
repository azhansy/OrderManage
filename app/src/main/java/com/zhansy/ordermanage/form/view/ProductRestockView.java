package com.zhansy.ordermanage.form.view;


import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.zhansy.ordermanage.base.mvp.view.IBaseUi;
import com.zhansy.ordermanage.base.widget.XListView;
import com.zhansy.ordermanage.form.adapter.ProductRestockAdapter;

/**
 * 进货订单的显示 UI 接口
 */
public interface ProductRestockView extends IBaseUi {

    /**
     * @param adapter 进货单列表的适配器
     */
    void setProductRestockAdapter(ProductRestockAdapter adapter);

    /**
     * @return ListView ID
     */
    XListView getXListView();


    RelativeLayout getNoDataView();
    /**
     * @return 获取标题栏 全选id
     */
    CheckBox getCheckBox();

    /**
     * 设置全选按钮是否全选
     */
    void setTitleCheckBox(boolean b);

    void onFinishActivity();
}
