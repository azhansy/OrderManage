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
//public class NoEvaluateFragment extends MVPBaseFragment<OrderClassifyPresenterImpl> implements OrderClassfyView ,XExpandableListView.IXListViewListener{
//
//    @InjectView(R.id.xelv_evaluate)
//    XExpandableListView xelv_evaluate;
//    @InjectView(R.id.rel_no_data)
//    RelativeLayout rel_no_data;
//    public static String typeAdmin;
//    public static NoEvaluateFragment getInstance(String s){
//        typeAdmin = s;
//        return  new NoEvaluateFragment();
//    }
//
//    @Override
//    public int getLayoutResource() {
//        return R.layout.fragment_noevaluate;
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mPresenter.setTypeAdmin(typeAdmin);
//        mPresenter.getOrderClassfyList(OMApplication.getInstance().getString(R.string.tv_noevaluate));
//        xelv_evaluate.setPullRefreshEnable(true);
//        xelv_evaluate.setPullLoadEnable(true);
//        xelv_evaluate.setXListViewListener(this);
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
//        return xelv_evaluate;
//    }
//
//    @Override
//    public void setAdapter(ExpandableOrderAdapter adapter) {
//        xelv_evaluate.setAdapter(adapter);
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
