package com.zhansy.ordermanage.taste.view;

import android.widget.ListView;
import android.widget.RelativeLayout;

import com.zhansy.ordermanage.base.mvp.view.IBaseUi;
import com.zhansy.ordermanage.base.widget.XExpandableListView;
import com.zhansy.ordermanage.order.adapter.ExpandableOrderAdapter;
import com.zhansy.ordermanage.taste.adapter.TasteAdapter;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface TasteView extends IBaseUi{

    ListView getListView();

    void setAdapter(TasteAdapter adapter);

    RelativeLayout getNoDataView();


//    void tasteDeleteSuccess();
//    void tasteDeleteFailed();

}
