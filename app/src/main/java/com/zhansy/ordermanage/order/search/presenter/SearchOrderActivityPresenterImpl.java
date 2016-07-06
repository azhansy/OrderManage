//package com.zhansy.ordermanage.order.search.presenter;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.view.View;
//import android.widget.AdapterView;
//
//import com.zhansy.ordermanage.order.business.OrderBusiness;
//import com.zhansy.ordermanage.base.mvp.model.ProductBean;
//import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
//import com.zhansy.ordermanage.order.controller.OrderController;
//import com.zhansy.ordermanage.product.ProductDetailActivity;
//import com.zhansy.ordermanage.index.adapter.ProductAdapter;
//import com.zhansy.ordermanage.index.view.SearchProductView;
//
//import java.lang.ref.WeakReference;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * Created by ZHANSY
// * 邮箱：azhansy@hotmail.com
// */
//public class SearchOrderActivityPresenterImpl extends MVPBasePresenter implements SearchOrderActivityIpresenter {
//    private List<ProductBean> productBeanList = new ArrayList<ProductBean>();
//    private MyHandler mHandler = new MyHandler(this);
//    private String key="order_code"; //根据什么来查
//    private String key_word="";//关键词
//    private OrderController controller;
//    private ProductAdapter adapter;
//    private int page=1;
//
//    public SearchOrderActivityPresenterImpl(){
//        controller = new OrderController(getContext(), mHandler);
//        adapter = new ProductAdapter(getContext());
//    }
//    @Override
//    public void triggerSearch(String key,String key_word) {
//        this.key = key;
//        this.key_word=key_word;
//        controller.getKeySearch(page,key,key_word);
//    }
//
//    @Override
//    public void Refresh(final String status) {
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                page = 1;
//                SearchProductView iview = getActualUi();
//                if(iview == null){
//                    return;
//                }
//                iview.getProductAdapterXListView().stopRefresh();
//                iview.getProductAdapterXListView().setRefreshTime(new Date());
//                controller.getKeySearch(page,key,key_word);
//            }
//        }, 500);
//    }
//
//    @Override
//    public void Load(final String status) {
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                page=page+1;
//                SearchProductView iview = getActualUi();
//                if(iview == null){
//                    return;
//                }
//                iview = getActualUi();
//                iview.getProductAdapterXListView().stopLoadMore();
//                controller.getKeySearch(page,key,key_word);
//            }
//        }, 500);
//    }
//
//    public static  class MyHandler extends Handler {
//        WeakReference<SearchOrderActivityIpresenter> presenter;
//        public  MyHandler(SearchOrderActivityIpresenter presenter){
//            this.presenter = new WeakReference<SearchOrderActivityIpresenter>(presenter);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if(presenter!=null&&presenter.get()!=null){
//                SearchOrderActivityPresenterImpl p= (SearchOrderActivityPresenterImpl) presenter.get();
//                p.handleMessage(msg);
//            }
//        }
//    }
//    public void handleMessage(Message msg) {
//
//        if(msg.what== OrderBusiness.STATE_PRODUCT_LIST_DATA_SUCCESS){
//            SearchProductView iview = getActualUi();
//            if (iview == null)
//                return;
//            productBeanList = (List<ProductBean>) msg.obj;
//            iview.getProductAdapterXListView().setVisibility(View.VISIBLE);
//            iview.getNoDataView().setVisibility(View.GONE);
//            if(page==1) {
//                adapter.setList(productBeanList);
//                iview.setSearchProductAdapter(adapter);
//            }
//            else {
//                adapter.addList(productBeanList);
//            }
//            if(productBeanList.size()<10){
//                iview.getProductAdapterXListView().setPullLoadEnable(false);
//            }else{
//                iview.getProductAdapterXListView().setPullLoadEnable(true);
//            }
//            adapter.notifyDataSetChanged();
//
//            iview.getProductAdapterXListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    ProductBean bean = productBeanList.get(position-1);//XListView计算时多一个头部刷新了，所以要减一
//                    Intent intent = new Intent(getContext(), ProductDetailActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("ProductBean",bean);
//                    intent.putExtras(bundle);
//                    getContext().startActivity(intent);
//                }
//            });
//        }
//        if(msg.what==OrderBusiness.STATE_PRODUCT_LIST_DATA_ERROR){
//            SearchProductView iview = getActualUi();
//            if (iview == null)
//                return;
//            iview.getProductAdapterXListView().setVisibility(View.GONE);
//            iview.getNoDataView().setVisibility(View.VISIBLE);
//            iview.searchFail();
//        }
//    }
//}
