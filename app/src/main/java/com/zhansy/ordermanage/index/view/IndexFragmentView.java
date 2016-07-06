package com.zhansy.ordermanage.index.view;

import android.widget.RelativeLayout;

import com.zhansy.ordermanage.base.mvp.view.IBaseUi;
import com.zhansy.ordermanage.base.widget.XListView;
import com.zhansy.ordermanage.index.adapter.ProductAdapter;
import com.zhansy.ordermanage.index.adapter.ProductOptionAdapter;


/**
 *
 */
public interface IndexFragmentView extends IBaseUi{

    void setCateAdapter(ProductOptionAdapter adapter);
    void setChildCateAdapter(ProductOptionAdapter adapter);
    void showCourseDialog();
    void setCate(String cate);

    /**
     * @param adapter 设置产品列表适配器
     */
    void setProductAdapter(ProductAdapter adapter);

    /**
     * @return
     */
    XListView getProductXListView();
    RelativeLayout getNoDataView();
    /**
     * @return ViewPager
     */
//    ViewPager getViewPager();

    /**
     * @return  GridView
     */
//    GridView getGridVIew();

    /**
     * @return ListView厂家
     */
//    ListView getListView();



}
