package com.zhansy.ordermanage.order.presenter;

import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.zhansy.ordermanage.base.mvp.model.OrderBean;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.order.business.OrderBusiness;
import com.zhansy.ordermanage.order.controller.OrderController;
import com.zhansy.ordermanage.order.view.OrderClassfyView;
import com.zhansy.ordermanage.order.adapter.ExpandableOrderAdapter;
import com.zhansy.ordermanage.utils.ToastUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class OrderClassifyPresenterImpl extends MVPBasePresenter implements OrderClassifyIpresenter {

    private String typeAdmin = "user";
    private List<OrderBean> orderBeanList = new ArrayList<OrderBean>();
    private MyHandler mHandler = new MyHandler(this);
    private OrderController controller;
    private ExpandableOrderAdapter expandableOrderAdapter;
    private int page= 0;
    private String key="order_type";
    private String key_word="";

    public OrderClassifyPresenterImpl(){
        controller = new OrderController(getContext(),mHandler);
        expandableOrderAdapter = new ExpandableOrderAdapter(getContext());
    }

    public void setTypeAdmin(String s){
        this.typeAdmin = s;
    }

    @Override
    public void getOrderClassfyList(String key,String key_word) {
        page= 0;
        this.key = key;
        this.key_word = key_word;
        controller.getOrderClassifyList(page,key,key_word);
        showProgressDialog(getContext());
//        if(progressDialog==null){
//            progressDialog= ProgressDialog.show(context,"提示","正在加载......");
//            progressDialog.setCancelable(true);
//            progressDialog.show();
//        }
    }

    @Override
    public void orderDelete(String code) {
        controller.deleteOrder(code);
    }

    @Override
    public void deleteAllOrder() {
        if (expandableOrderAdapter.getmList().size() == 0){
            ToastUtil.showToast(getContext(),"无可删的订单！");
            return;
        }
        StringBuffer codes = new StringBuffer();
        for (OrderBean bean : expandableOrderAdapter.getmList()) {
            codes.append(bean.getOrder_code() + ",");
        }
        codes = codes.deleteCharAt(codes.length() - 1);
        controller.deleteOrder(codes.toString());
    }

    @Override
    public void Refresh(String status) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 0;
                OrderClassfyView orderClassfyView = getActualUi();
                if (orderClassfyView == null)
                    return;
                orderClassfyView.getXExpandableListView().stopRefresh();
                orderClassfyView.getXExpandableListView().setRefreshTime(new Date());
                controller.getOrderClassifyList(page,key,key_word);
            }
        }, 500);
    }

    @Override
    public void Load(String status) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = page + 1 ;
                OrderClassfyView orderClassfyView = getActualUi();
                if (orderClassfyView == null)
                    return;
                orderClassfyView.getXExpandableListView().stopLoadMore();
                controller.getOrderClassifyList(page,key,key_word);
            }
        }, 500);
    }




    public static  class MyHandler extends Handler {
        WeakReference<OrderClassifyIpresenter> presenter;
        public  MyHandler(OrderClassifyIpresenter presenter){
            this.presenter = new WeakReference<OrderClassifyIpresenter>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(presenter!=null&&presenter.get()!=null){
                OrderClassifyPresenterImpl p= (OrderClassifyPresenterImpl) presenter.get();
                p.handleMessage(msg);
            }
        }
    }
    public void handleMessage(Message msg) {

        if(msg.what==OrderBusiness.STATE_ORDER_LIST_DATA_SUCCESS){
            OrderClassfyView iview = getActualUi();
            if (iview == null)
                return;
            iview.getXExpandableListView().setVisibility(View.VISIBLE);
            iview.getNoDataView().setVisibility(View.GONE);
            orderBeanList= (List<OrderBean>) msg.obj;
            expandableOrderAdapter.setTypeAdmin(typeAdmin);
            if(page==0) {
                expandableOrderAdapter.setOrderBeanList(orderBeanList);
                iview.setAdapter(expandableOrderAdapter);
            }
            else {
                expandableOrderAdapter.addList(orderBeanList);
            }
            if(orderBeanList.size()<10){
                iview.getXExpandableListView().setPullLoadEnable(false);
            }else{
                iview.getXExpandableListView().setPullLoadEnable(true);
            }
            expandableOrderAdapter.notifyDataSetChanged();
        }
        if(msg.what==OrderBusiness.STATE_ORDER_LIST_DATA_ERROR){
            OrderClassfyView iview = getActualUi();
            if(iview == null){
                return;
            }
            iview.getXExpandableListView().setVisibility(View.GONE);
            iview.getNoDataView().setVisibility(View.VISIBLE);
        }
        if (msg.what == OrderBusiness.DELETE_ORDER_SUCCESS){
            OrderClassfyView iview = getActualUi();
            if(iview == null){
                return;
            }
            expandableOrderAdapter.notifyDataSetChanged();
            iview.orderDeleteSuccess();
        }
        if (msg.what == OrderBusiness.DELETE_ORDER_ERROR){
            OrderClassfyView iview = getActualUi();
            if(iview == null){
                return;
            }
            iview.orderDeleteFailed();
        }

        hideProgressDialog();
    }
}
