package com.zhansy.ordermanage.index.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import com.zhansy.ordermanage.base.mvp.model.ProductBean;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.product.ProductDetailActivity;
import com.zhansy.ordermanage.index.adapter.ProductAdapter;
import com.zhansy.ordermanage.index.adapter.StockAdapter;
import com.zhansy.ordermanage.index.view.SearchProductView;
import com.zhansy.ordermanage.order.business.OrderBusiness;
import com.zhansy.ordermanage.order.controller.OrderController;
import com.zhansy.ordermanage.index.view.StockView;
import com.zhansy.ordermanage.product.ProductInputActivitity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class SearchActivityPresenterImpl extends MVPBasePresenter implements SearchActivityIpresenter {
    private List<ProductBean> productBeanList = new ArrayList<ProductBean>();
    private MyHandler mHandler = new MyHandler(this);
    private String key="product_name"; //根据什么来查
    private String key_word="";//关键词
    private OrderController controller;
    private ProductAdapter adapter;
    private StockAdapter stockAdapter;
    private int page=0;

    public SearchActivityPresenterImpl(){
        controller = new OrderController(getContext(), mHandler);
        adapter = new ProductAdapter(getContext());
        stockAdapter = new StockAdapter(getContext());
    }
    @Override
    public void triggerSearch(String key,String key_word) {
        this.key = key;
        this.key_word=key_word;
        controller.getKeySearch(page,key,key_word);
    }

    @Override
    public void getProduct10() {
        controller.getProduct10();
    }

    @Override
    public void Refresh(final String status) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 0;
                try {
                    SearchProductView iview = getActualUi();
                    if(iview == null){
                        return;
                    }
                    iview.getProductAdapterXListView().stopRefresh();
                    iview.getProductAdapterXListView().setRefreshTime(new Date());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    StockView iview1 = getActualUi();
                    if(iview1 == null){
                        return;
                    }
                    iview1.getProductAdapterXListView().stopRefresh();
                    iview1.getProductAdapterXListView().setRefreshTime(new Date());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                controller.getKeySearch(page,key,key_word);
            }
        }, 500);
    }

    @Override
    public void Load(final String status) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page=page+1;
                try {
                    SearchProductView iview = getActualUi();
                    if(iview == null){
                        return;
                    }
                    iview = getActualUi();
                    iview.getProductAdapterXListView().stopLoadMore();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    StockView iview1 = getActualUi();
                    if(iview1 == null){
                        return;
                    }
                    iview1 = getActualUi();
                    iview1.getProductAdapterXListView().stopLoadMore();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                controller.getKeySearch(page,key,key_word);
            }
        }, 500);
    }

    public static  class MyHandler extends Handler {
        WeakReference<SearchActivityIpresenter> presenter;
        public  MyHandler(SearchActivityIpresenter presenter){
            this.presenter = new WeakReference<SearchActivityIpresenter>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(presenter!=null&&presenter.get()!=null){
                SearchActivityPresenterImpl p= (SearchActivityPresenterImpl) presenter.get();
                p.handleMessage(msg);
            }
        }
    }
    public void handleMessage(Message msg) {

        if(msg.what== OrderBusiness.STATE_PRODUCT_LIST_DATA_SUCCESS){
            SearchProductView iview = null;
            try {
                iview = getActualUi();
                if (iview != null) {
                    productBeanList = (List<ProductBean>) msg.obj;
                    iview.getProductAdapterXListView().setVisibility(View.VISIBLE);
                    iview.getNoDataView().setVisibility(View.GONE);
                    iview.searchSuccess();
                    if (page == 0) {
                        adapter.setList(productBeanList);
                        iview.setSearchProductAdapter(adapter);
                    } else {
                        adapter.addList(productBeanList);
                    }
                    if (productBeanList.size() < 10) {
                        iview.getProductAdapterXListView().setPullLoadEnable(false);
                    } else {
                        iview.getProductAdapterXListView().setPullLoadEnable(true);
                    }
                    adapter.notifyDataSetChanged();

                    iview.getProductAdapterXListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ProductBean bean = productBeanList.get(position - 1);//XListView计算时多一个头部刷新了，所以要减一
                            Intent intent = new Intent(getContext(), ProductDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("ProductBean", bean);
                            intent.putExtras(bundle);
                            getContext().startActivity(intent);
                        }
                    });
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                StockView iview1 = getActualUi();
                if (iview1 != null) {
                    productBeanList = (List<ProductBean>) msg.obj;
                    iview1.getProductAdapterXListView().setVisibility(View.VISIBLE);
                    iview1.getNoDataView().setVisibility(View.GONE);
                    if (page == 0) {
                        stockAdapter.setList(productBeanList);
                        iview1.setStockAdapter(stockAdapter);
                    } else {
                        stockAdapter.addList(productBeanList);
                    }
                    if (productBeanList.size() < 10) {
                        iview1.getProductAdapterXListView().setPullLoadEnable(false);
                    } else {
                        iview1.getProductAdapterXListView().setPullLoadEnable(true);
                    }
                    iview1.getProductAdapterXListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ProductBean bean = productBeanList.get(position - 1);//XListView计算时多一个头部刷新了，所以要减一
                            Intent intent = new Intent(getContext(), ProductInputActivitity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("ProductBean", bean);
                            intent.putExtras(bundle);
                            getContext().startActivity(intent);
                        }
                    });
                    stockAdapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if(msg.what==OrderBusiness.STATE_PRODUCT_LIST_DATA_ERROR){
            try {
                SearchProductView iview = getActualUi();
                if (iview != null){
                    iview.getProductAdapterXListView().setVisibility(View.GONE);
                    iview.getNoDataView().setVisibility(View.VISIBLE);
                    iview.searchFail();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            StockView iview1 = null;
            try {
                iview1 = getActualUi();
                if (iview1 != null){
                    iview1.getProductAdapterXListView().setVisibility(View.GONE);
                    iview1.getNoDataView().setVisibility(View.VISIBLE);
                    iview1.searchFail();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
