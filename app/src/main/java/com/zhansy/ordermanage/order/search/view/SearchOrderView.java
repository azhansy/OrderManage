package com.zhansy.ordermanage.order.search.view;


import android.widget.RelativeLayout;

import com.zhansy.ordermanage.base.mvp.view.IBaseUi;
import com.zhansy.ordermanage.base.widget.XExpandableListView;
import com.zhansy.ordermanage.order.adapter.ExpandableOrderAdapter;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface SearchOrderView extends IBaseUi{
    void searchFail();

    XExpandableListView getXExpandableListView();

    void setAdapter(ExpandableOrderAdapter adapter);

    RelativeLayout getNoDataView();

}
