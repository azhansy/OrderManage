//package com.zhansy.ordermanage.me.fragment;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.widget.ExpandableListView;
//import android.widget.RelativeLayout;
//
//import com.zhansy.ordermanage.R;
//import com.zhansy.ordermanage.base.MVPBaseFragment;
//import com.zhansy.ordermanage.base.OMApplication;
//import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
//import com.zhansy.ordermanage.base.widget.XExpandableListView;
//import com.zhansy.ordermanage.base.widget.XListView;
//import com.zhansy.ordermanage.order.presenter.OrderClassifyPresenterImpl;
//import com.zhansy.ordermanage.order.view.OrderClassfyView;
//import com.zhansy.ordermanage.order.adapter.ExpandableOrderAdapter;
//
//import butterknife.InjectView;
//
///**
// * Created by ZHANSY
// * 邮箱：azhansy@hotmail.com
// */
//public class OrderAllFragment extends MVPBaseFragment<OrderClassifyPresenterImpl> implements OrderClassfyView ,XExpandableListView.IXListViewListener {
//
//    private  static  String typeAdmin;
//    @InjectView(R.id.xelv_all_order)
//    XExpandableListView xelv_all_order;
//    @InjectView(R.id.rel_no_data)
//    RelativeLayout rel_no_data;
//    public static OrderAllFragment getInstance(String ty){
//        typeAdmin = ty;
//        return  new OrderAllFragment();
//    }
//    @Override
//    public int getLayoutResource() {
//        return R.layout.fragment_all_order;
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mPresenter.setTypeAdmin(typeAdmin);
//        mPresenter.getOrderClassfyList("");
//        xelv_all_order.setPullRefreshEnable(true);
//        xelv_all_order.setPullLoadEnable(true);
//        xelv_all_order.setXListViewListener(this);
//    }
//
//    @Override
//    protected boolean usePresenter() {
//        return true;
//    }
//
//    @Override
//    protected OrderClassifyPresenterImpl createPresenter() {
//        return new OrderClassifyPresenterImpl(getActivity());
//    }
//
//    @Override
//    public XExpandableListView getXExpandableListView() {
//        return xelv_all_order;
//    }
//
//    @Override
//    public void setAdapter(ExpandableOrderAdapter adapter) {
//        xelv_all_order.setAdapter(adapter);
//    }
//
//    @Override
//    public RelativeLayout getNoDataView() {
//        return rel_no_data;
//    }
//
//    @Override
//    public Context getPresenterContext() {
//        return getActivity();
//    }
//
//    @Override
//    public void onRefresh() {
//        mPresenter.Refresh("");
//    }
//
//    @Override
//    public void onLoadMore() {
//        mPresenter.Load("");
//    }
//}
