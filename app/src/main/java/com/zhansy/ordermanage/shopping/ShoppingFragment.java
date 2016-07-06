//package com.zhansy.ordermanage.shopping;
//
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.widget.RelativeLayout;
//
//import com.zhansy.ordermanage.R;
//import com.zhansy.ordermanage.base.MVPBaseFragment;
//import com.zhansy.ordermanage.base.widget.XListView;
//import com.zhansy.ordermanage.index.adapter.ProductAdapter;
//import com.zhansy.ordermanage.shopping.presenter.ShoppingFragmentPresenterImpl;
//import com.zhansy.ordermanage.shopping.view.ShoppingFragmentView;
//
///**
// * Created by ZHANSY
// * 邮箱：azhansy@hotmail.com
// */
//public class ShoppingFragment extends MVPBaseFragment<ShoppingFragmentPresenterImpl> implements ShoppingFragmentView,XListView.IXListViewListener {
//
////    @InjectView(R.id.xlv_product)
////    XListView xlv_product;
////    @InjectView(R.id.notice_number)
////    TextView notice_number;
////    @InjectView(R.id.rel_no_data)
////    RelativeLayout rel_no_data;
////    @OnClick(R.id.rel_notice)
////    void OnClick(View v){
////        switch (v.getId()){
////            case R.id.rel_notice:
////                OrderClassifyActivity.launch(getActivity(),InformActivity.class);
////        }
////    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mPresenter.getData();
////        xlv_product.setPullRefreshEnable(true);
////        xlv_product.setPullLoadEnable(true);
////        xlv_product.setXListViewListener(this);
//    }
//
//    @Override
//    public int getLayoutResource() {
//        return R.layout.fragment_shopping;
//    }
//
//    @Override
//    protected boolean usePresenter() {
//        return true;
//    }
//
//    @Override
//    protected ShoppingFragmentPresenterImpl createPresenter() {
//        return new ShoppingFragmentPresenterImpl();
//    }
//
//
//    public static ShoppingFragment newInstance() {
//        ShoppingFragment fragment = new ShoppingFragment();
//        return fragment;
//    }
//
//    @Override
//    public void setProductAdapter(ProductAdapter adapter) {
////        xlv_product.setAdapter(adapter);
//    }
//
//    @Override
//    public XListView getProductXListView() {
//        return null;
//    }
//
//    @Override
//    public void setNoticeNumber(String number) {
////        notice_number.setText(number);
//    }
//
//    @Override
//    public void setEmptyTip() {
//    }
//
//    @Override
//    public RelativeLayout getNoDataView() {
//        return null;
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
