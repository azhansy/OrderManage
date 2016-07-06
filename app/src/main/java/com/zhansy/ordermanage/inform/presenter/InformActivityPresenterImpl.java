package com.zhansy.ordermanage.inform.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import com.flyco.dialog.listener.OnBtnRightClickL;
import com.zhansy.ordermanage.admin.AddOrderAdminActivity;
import com.zhansy.ordermanage.base.dialog.MyDialog;
import com.zhansy.ordermanage.base.mvp.model.InformBean;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.base.mvp.view.IBaseUi;
import com.zhansy.ordermanage.inform.InformDetailActivity;
import com.zhansy.ordermanage.inform.adapter.InformAdapter;
import com.zhansy.ordermanage.inform.business.InformBusiness;
import com.zhansy.ordermanage.inform.controller.InformController;
import com.zhansy.ordermanage.inform.view.InformView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHANSY on 2016/4/11.
 */
public class InformActivityPresenterImpl extends MVPBasePresenter implements InformActivityIPresenter{

    @Override
    public void getData() {
        controller.getData();
//        showProgressDialog(getContext());
    }

    @Override
    public void informDelete(int code) {
        showProgressDialog(getContext());
        controller.informDelete(code);
    }

    @Override
    public void informCreate(String info) {
        showProgressDialog(getContext());
        controller.informCreate(info);
    }

    public InformActivityPresenterImpl(){
        controller = new InformController(getContext(),mHandler);
        informAdapter = new InformAdapter(getContext());
    }
    private InformController controller;
    private InformAdapter informAdapter;
    private MyHandler mHandler = new MyHandler(this);
    public static  class MyHandler extends Handler {
        WeakReference<InformActivityIPresenter> presenter;
        public  MyHandler(InformActivityIPresenter presenter){
            this.presenter = new WeakReference<InformActivityIPresenter>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(presenter!=null&&presenter.get()!=null){
                InformActivityPresenterImpl p= (InformActivityPresenterImpl) presenter.get();
                p.handleMessage(msg);
            }
        }
    }
    public void handleMessage(Message msg) {

        if(msg.what== InformBusiness.STATE_INFORM_DATA_SUCCESS){
            final InformView iview = getActualUi();
            if (iview == null){
                return;
            }
            List<InformBean> list = new ArrayList<>();
            list = (List<InformBean>) msg.obj;
            informAdapter.setList(list);
            iview.setInformAdapter(informAdapter);
            iview.getListView().setVisibility(View.VISIBLE);
            iview.getRelNoData().setVisibility(View.GONE);
            iview.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getContext(), InformDetailActivity.class);
                    Bundle bundle = new Bundle();
                    InformBean bean = (InformBean) informAdapter.getItem(position);
                    bundle.putSerializable("InformBean",bean);
                    intent.putExtras(bundle);
                    getContext().startActivity(intent);
                }
            });
            iview.getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    final InformBean bean = (InformBean) informAdapter.getItem(position);
                    MyDialog.showCustomDialog(getContext(), "确定删除此公告？", new OnBtnRightClickL() {
                        @Override
                        public void onBtnRightClick() {
                            iview.informDelete(bean.getInform_id());
                            MyDialog.dimiss();
                        }
                    });
                    return false;
                }
            });
        }
        if(msg.what== InformBusiness.STATE_INFORM_DATA_ERROR){
            InformView iview = getActualUi();
            if (iview == null){
                return;
            }
            iview.getListView().setVisibility(View.GONE);
            iview.getRelNoData().setVisibility(View.VISIBLE);
        }
        if(msg.what== InformBusiness.DELETE_INFORM_DATA_SUCCESS){
            InformView iview = getActualUi();
            if (iview == null){
                return;
            }
            iview.deleteInformSuccess();
        }
        if(msg.what== InformBusiness.DELETE_INFORM_DATA_ERROR){
            InformView iview = getActualUi();
            if (iview == null){
                return;
            }
            iview.deleteInformFailed();
        }
        if(msg.what== InformBusiness.CREATE_INFORM_DATA_SUCCESS){
            InformView iview = getActualUi();
            if (iview == null){
                return;
            }
            iview.deleteInformSuccess();
        }
        if(msg.what== InformBusiness.CREATE_INFORM_DATA_ERROR){
            InformView iview = getActualUi();
            if (iview == null){
                return;
            }
            iview.deleteInformFailed();
        }
        hideProgressDialog();
    }
}
