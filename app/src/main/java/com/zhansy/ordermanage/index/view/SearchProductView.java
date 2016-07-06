package com.zhansy.ordermanage.index.view;


import android.widget.RelativeLayout;

import com.zhansy.ordermanage.base.mvp.view.IBaseUi;
import com.zhansy.ordermanage.base.widget.XListView;
import com.zhansy.ordermanage.index.adapter.ProductAdapter;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface SearchProductView extends IBaseUi{

    void setSearchProductAdapter(ProductAdapter adapter);
    void searchFail();
    void searchSuccess();

    XListView getProductAdapterXListView();

    RelativeLayout getNoDataView();

}
