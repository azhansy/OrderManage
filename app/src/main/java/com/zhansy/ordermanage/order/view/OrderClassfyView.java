package com.zhansy.ordermanage.order.view;

import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import com.zhansy.ordermanage.base.mvp.view.IBaseUi;
import com.zhansy.ordermanage.base.widget.XExpandableListView;
import com.zhansy.ordermanage.order.adapter.ExpandableOrderAdapter;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface OrderClassfyView extends IBaseUi{

    XExpandableListView getXExpandableListView();

    void setAdapter(ExpandableOrderAdapter adapter);

    RelativeLayout getNoDataView();


    void orderDeleteSuccess();
    void orderDeleteFailed();

}
