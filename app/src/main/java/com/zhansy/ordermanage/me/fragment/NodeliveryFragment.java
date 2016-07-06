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
//public class NodeliveryFragment extends MVPBaseFragment<OrderClassifyPresenterImpl> implements OrderClassfyView ,XExpandableListView.IXListViewListener{
//
//    @InjectView(R.id.xelv_nodelivery)
//    XExpandableListView xelv_nodelivery;
//    @InjectView(R.id.rel_no_data)
//    RelativeLayout rel_no_data;
//    public static String typeAdmin;
//    public static NodeliveryFragment getInstance(String s){
//        typeAdmin = s;
//        return  new NodeliveryFragment();
//    }
//    @Override
//    public int getLayoutResource() {
//        return R.layout.fragment_nodelivery;
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mPresenter.setTypeAdmin(typeAdmin);
//        mPresenter.getOrderClassfyList(OMApplication.getInstance().getString(R.string.tv_nodelivery));
//
//        xelv_nodelivery.setPullRefreshEnable(true);
//        xelv_nodelivery.setPullLoadEnable(true);
//        xelv_nodelivery.setXListViewListener(this);
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
//        return xelv_nodelivery;
//    }
//
//    @Override
//    public void setAdapter(ExpandableOrderAdapter adapter) {
//        xelv_nodelivery.setAdapter(adapter);
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
