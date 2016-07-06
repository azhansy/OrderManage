package com.zhansy.ordermanage.chart.presenter;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;

import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.chart.business.MpChartBusiness;
import com.zhansy.ordermanage.chart.controller.MpChartController;
import com.zhansy.ordermanage.chart.view.MpChartView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by ZHANSY on 2016/4/12.
 */
public class MpChartPresenterImpl extends MVPBasePresenter implements MpChartIPresenter {
//    private ProgressDialog progressDialog;
    private MpChartController chartController;
    private MyHandler myHandler = new MyHandler(this);

    public MpChartPresenterImpl() {
        chartController = new MpChartController(getContext(),myHandler);
    }

    @Override
    public void getData(String key) {
        showProgressDialog(getContext());
        chartController.getdata(key);

    }

    public static class MyHandler extends Handler{
        WeakReference<MpChartIPresenter> presenter;
        public MyHandler(MpChartIPresenter presenter){
            this.presenter = new WeakReference<MpChartIPresenter>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (presenter != null && presenter.get() != null) {
                MpChartPresenterImpl p = (MpChartPresenterImpl) presenter.get();
                p.handleMessage(msg);
            }
        }
    }
    public void handleMessage(Message msg) {

        if(msg.what== MpChartBusiness.STATE_CHART_SUCCESS){
            MpChartView iview = getActualUi();
            if (iview == null){
                return;
            }
//            List<ChartBean> list = (List<ChartBean>) msg.obj;
//            iview.callbackSuccess(list);
        }
        if(msg.what==MpChartBusiness.STATE_CHART_ERROR){
            MpChartView iview = getActualUi();
            if (iview == null){
                return;
            }
            iview.callbackFailed();
        }
        hideProgressDialog();
    }
}
