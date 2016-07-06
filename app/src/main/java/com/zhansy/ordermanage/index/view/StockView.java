package com.zhansy.ordermanage.index.view;


import android.widget.RelativeLayout;

import com.zhansy.ordermanage.base.mvp.view.IBaseUi;
import com.zhansy.ordermanage.base.widget.XListView;
import com.zhansy.ordermanage.index.adapter.StockAdapter;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface StockView extends IBaseUi{

    void setStockAdapter(StockAdapter adapter);
    void searchFail();

    XListView getProductAdapterXListView();

    RelativeLayout getNoDataView();

}
