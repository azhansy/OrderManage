package com.zhansy.ordermanage.admin.presenter;

import android.os.Handler;
import android.os.Message;

import com.zhansy.ordermanage.base.mvp.model.OrderBean;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.admin.view.AddOrderAdminActivityView;
import com.zhansy.ordermanage.order.business.OrderBusiness;
import com.zhansy.ordermanage.order.controller.OrderController;

import java.lang.ref.WeakReference;


/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class AddOrderAdminPresenterImpl extends MVPBasePresenter implements AddOrderAdminIpresenter {
    private OrderController orderController;
    private MyHandler mHandler = new MyHandler(this);

    public AddOrderAdminPresenterImpl(){
        orderController = new OrderController(getContext(),mHandler);
    }

    @Override
    public void createOrderData(OrderBean orderBean) {
//        orderController.createOrder(orderBean);
    }

    @Override
    public void updateOrderData(String code,String status,String remark) {
        orderController.updateOrder(code, status, remark);
    }


    public static  class MyHandler extends Handler {
        WeakReference<AddOrderAdminIpresenter> presenter;

        public MyHandler(AddOrderAdminIpresenter presenter) {
            this.presenter = new WeakReference<AddOrderAdminIpresenter>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (presenter !=  null && presenter.get() != null){
                AddOrderAdminPresenterImpl p = (AddOrderAdminPresenterImpl)presenter.get();
                p.handleMessage(msg);
            }
        }
    }

    private void handleMessage(Message msg) {
        if(msg.what== OrderBusiness.UPDATE_ORDER_SUCCESS){
            AddOrderAdminActivityView iview = getActualUi();
            if (iview == null)
                return;
            iview.showTip(true);
        }
        if(msg.what== OrderBusiness.UPDATE_ORDER_ERROR){
            AddOrderAdminActivityView iview = getActualUi();
            if (iview == null)
                return;
            iview.showTip(false);
        }
        if(msg.what== OrderBusiness.CREATE_ORDER_SUCCESS){
            AddOrderAdminActivityView iview = getActualUi();
            if (iview == null)
                return;
            iview.showTip(true);
        }
        if(msg.what== OrderBusiness.CREATE_ORDER_ERROR){
            AddOrderAdminActivityView iview = getActualUi();
            if (iview == null)
                return;
            iview.showTip(false);
        }
    }
}
