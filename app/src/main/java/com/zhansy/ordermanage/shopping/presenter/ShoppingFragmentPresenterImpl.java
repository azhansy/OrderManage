package com.zhansy.ordermanage.shopping.presenter;

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
import com.zhansy.ordermanage.order.business.OrderBusiness;
import com.zhansy.ordermanage.order.controller.OrderController;
import com.zhansy.ordermanage.shopping.view.ShoppingFragmentView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class ShoppingFragmentPresenterImpl extends MVPBasePresenter implements ShoppingFragmentIpresenter {


    private List<ProductBean> productBeanList = new ArrayList<ProductBean>();
    private MyHandler mHandler = new MyHandler(this);
    private ProductAdapter adapter;
    private OrderController controller;
    private int page=1;

    public ShoppingFragmentPresenterImpl(){
        controller = new OrderController(getContext(), mHandler);
        adapter = new ProductAdapter(getContext());
    }

    @Override
    public void getData() {
        controller.getCurrentUserProductList(page,"");
    }

    @Override
    public void Refresh(String status) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                ShoppingFragmentView iview = getActualUi();
                if(iview == null){
                    return;
                }
                iview.getProductXListView().stopRefresh();
                iview.getProductXListView().setRefreshTime(new Date());
                controller.getCurrentUserProductList(page,"");
            }
        }, 500);
    }

    @Override
    public void Load(String status) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page=page+1;
                ShoppingFragmentView iview = getActualUi();
                if(iview == null){
                    return;
                }
                iview = getActualUi();
                iview.getProductXListView().stopLoadMore();
                controller.getCurrentUserProductList(page,"");
            }
        }, 500);
    }

    public static  class MyHandler extends Handler {
        WeakReference<ShoppingFragmentIpresenter> presenter;
        public  MyHandler(ShoppingFragmentIpresenter presenter){
            this.presenter = new WeakReference<ShoppingFragmentIpresenter>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(presenter!=null&&presenter.get()!=null){
                ShoppingFragmentPresenterImpl p= (ShoppingFragmentPresenterImpl) presenter.get();
                p.handleMessage(msg);
            }
        }
    }
    public void handleMessage(Message msg) {

        if(msg.what== OrderBusiness.STATE_PRODUCT_LIST_DATA_SUCCESS){
            ShoppingFragmentView iview = getActualUi();
            if (iview == null)
                return;
            iview.getProductXListView().setVisibility(View.VISIBLE);
            iview.getNoDataView().setVisibility(View.GONE);
            productBeanList = (List<ProductBean>) msg.obj;
            if(page==1) {
                adapter.setList(productBeanList);
                iview.setProductAdapter(adapter);
            }
            else {
                adapter.addList(productBeanList);
            }
            if(productBeanList.size()<10){
                iview.getProductXListView().setPullLoadEnable(false);
            }else{
                iview.getProductXListView().setPullLoadEnable(true);
            }
            adapter.notifyDataSetChanged();

            iview.getProductXListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ProductBean bean = productBeanList.get(position-1);
                    Intent intent = new Intent(getContext(), ProductDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ProductBean",bean);
                    intent.putExtras(bundle);
                    getContext().startActivity(intent);
                }
            });
        }
        if(msg.what==OrderBusiness.STATE_PRODUCT_LIST_DATA_ERROR){
            ShoppingFragmentView iview = getActualUi();
            if (iview == null)
                return;
            iview.getProductXListView().setVisibility(View.GONE);
            iview.getNoDataView().setVisibility(View.VISIBLE);
        }
    }
}
