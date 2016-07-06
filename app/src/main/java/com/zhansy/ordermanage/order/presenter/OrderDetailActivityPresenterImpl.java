package com.zhansy.ordermanage.order.presenter;

import android.os.Handler;
import android.os.Message;

import com.flyco.dialog.listener.OnBtnRightClickL;
import com.zhansy.ordermanage.order.business.OrderBusiness;
import com.zhansy.ordermanage.base.dialog.MyDialog;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.download.DownLoadActivity;
import com.zhansy.ordermanage.order.controller.OrderController;
import com.zhansy.ordermanage.order.view.OrderDetailActivityView;

import java.lang.ref.WeakReference;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class OrderDetailActivityPresenterImpl extends MVPBasePresenter implements OrderDetailActivityIpresenter {
    private OrderController controller;
    private OrderDetailActivityHandler myHandler = new OrderDetailActivityHandler(this);

    public OrderDetailActivityPresenterImpl(){
        controller = new OrderController(getContext(), myHandler);
    }
    @Override
    public void setData() {
//        controller.getTestBaidu();
    }

    @Override
    public void deleteOrder(String code) {
        controller.deleteOrder(code);
    }

    @Override
    public void downloadOrderPDF(String orderCode) {
        controller.upLoadPDF(orderCode);
    }

    @Override
    public void downloadOrderExcel(String orderCode) {
        controller.upLoadExcel(orderCode);
    }

    public static  class OrderDetailActivityHandler extends Handler {
        WeakReference<OrderDetailActivityIpresenter> presenter;
        public  OrderDetailActivityHandler(OrderDetailActivityIpresenter presenter){
            this.presenter = new WeakReference<OrderDetailActivityIpresenter>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(presenter!=null&&presenter.get()!=null){
                OrderDetailActivityPresenterImpl p= (OrderDetailActivityPresenterImpl) presenter.get();
                p.handleMessage(msg);
            }
        }
    }
    public void handleMessage(Message msg) {

        if(msg.what== OrderBusiness.DELETE_ORDER_SUCCESS){
            OrderDetailActivityView iview = getActualUi();
           if (iview == null){
               return;
           }
            String s = (String) msg.obj;
            iview.operSuccess(s);

        }
        if(msg.what==OrderBusiness.DELETE_ORDER_ERROR){
            OrderDetailActivityView iview = getActualUi();
            if (iview == null){
                return;
            }
            String s = (String) msg.obj;
            iview.operFailed(s);
        }

        if(msg.what== OrderBusiness.DOWNLOAD_ORDER_SUCCESS){
            OrderDetailActivityView iview = getActualUi();
            if (iview == null){
                return;
            }
            String pathName = (String) msg.obj;
           iview.downLoadSuccess(pathName);
        }
        if(msg.what== OrderBusiness.DOWNLOAD_ORDER_ERROR){
            OrderDetailActivityView iview = getActualUi();
            if (iview == null){
                return;
            }
            iview.downLoadFailed();
//            String s = (String) msg.obj;
//            iview.showTip(s);
        }
    }
}
