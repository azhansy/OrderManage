package com.zhansy.ordermanage.me.presenter;

import android.os.Handler;
import android.os.Message;

import com.zhansy.ordermanage.base.OMApplication;
import com.zhansy.ordermanage.base.mvp.model.OrderBean;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.order.business.OrderBusiness;
import com.zhansy.ordermanage.order.controller.OrderController;
import com.zhansy.ordermanage.me.view.MeFragmentView;
import com.zhansy.ordermanage.me.view.UpdateAppVersionView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class MeFragmentPresenterImpl extends MVPBasePresenter implements MeFragmentIpresenter {
    private List<OrderBean> orderBeanList = new ArrayList<OrderBean>();
    private OrderController controller;
    private MyHandler mHandler = new MyHandler(this);
    private int page = 1;

    public MeFragmentPresenterImpl(){
        controller = new OrderController(getContext(),mHandler);
    }
    @Override
    public void getData() {
//        controller.getOrderLocalList(page,"");//获取本地数据
//        controller.getOrderList(page,"");//获取订单列表
        //下载app
        controller.upLoadApp();
    }

    @Override
    public void getAppUpdateVerion() {
        controller.getAppUpdateVersion();
//        controller.upLoadApp();
    }


    @Override
    public void setUserHeader() {
        MeFragmentView meFragmentView = getActualUi();
        meFragmentView.setUserName(OMApplication.getInstance().getCurrentUser().getUsername());
        meFragmentView.setCompany(OMApplication.getInstance().getCurrentUser().getCompany());
        meFragmentView.setUserIcon(OMApplication.getInstance().getCurrentUser().getImgurl());
    }
//
//    @Override
//    public void setOrderNumber() {
//        MeFragmentView meFragmentView = getActualUi();
////        meFragmentView.setNoPaymentNumber(LitePalUtil.QueryOrderStatusNumber("待付款")+"");
////        meFragmentView.setNoDeliveryNumber(LitePalUtil.QueryOrderStatusNumber("待发货")+"");
////        meFragmentView.setNoConfirmNumber(LitePalUtil.QueryOrderStatusNumber("待收货")+"");
////        meFragmentView.setNoEvaluate(LitePalUtil.QueryOrderStatusNumber("待评价")+"");
////        meFragmentView.setComplete(LitePalUtil.QueryOrderStatusNumber("已完成")+"");
//    }

//    @Override
//    public void Refresh(String status) {
//
//    }
//
//    @Override
//    public void Load(String status) {
//
//    }

    public static  class MyHandler extends Handler {
        WeakReference<MeFragmentIpresenter> presenter;
        public  MyHandler(MeFragmentIpresenter presenter){
            this.presenter = new WeakReference<MeFragmentIpresenter>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(presenter!=null&&presenter.get()!=null){
                MeFragmentPresenterImpl p= (MeFragmentPresenterImpl) presenter.get();
                p.handleMessage(msg);
            }
        }
    }
    public void handleMessage(Message msg) {
        if(msg.what== OrderBusiness.STATE_ORDER_LIST_DATA_SUCCESS){
            //请求订单列表成功
            orderBeanList= (List<OrderBean>) msg.obj;
//            setOrderNumber();
            setUserHeader();
        }
        if(msg.what==OrderBusiness.STATE_ORDER_LIST_DATA_SUCCESS){

        }
        if (msg.what == OrderBusiness.STATE_UPDATE_VERSION_SUCCESS){
            UpdateAppVersionView updateAppVersionView = getActualUi();
            if (updateAppVersionView == null)
                return;
            updateAppVersionView.getUpdateAPPVersionResult(true);
        }
        if (msg.what == OrderBusiness.STATE_UPDATE_VERSION_ERROR){
            UpdateAppVersionView updateAppVersionView = getActualUi();
            if (updateAppVersionView == null)
                return;
            updateAppVersionView.getUpdateAPPVersionResult(false);
        }
//        if(msg.what==OrderBusiness.STATE_ORDER_NOPAY_LIST_DATA_SUCCESS){
//            OrderClassfyView orderClassfyView = getActualUi();
//            ExpandableOrderAdapter expandableOrderAdapter = new ExpandableOrderAdapter(getContext());
//            orderBeanList= (List<OrderBean>) msg.obj;
//            expandableOrderAdapter.setOrderBeanList(orderBeanList);
//            orderClassfyView.setAdapter(expandableOrderAdapter);
//        }
//        if(msg.what==OrderBusiness.STATE_ORDER_NOPAY_LIST_DATA_ERROR){
//
//        }
    }
}
