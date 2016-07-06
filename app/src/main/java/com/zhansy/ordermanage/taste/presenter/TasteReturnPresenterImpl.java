package com.zhansy.ordermanage.taste.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import com.flyco.dialog.listener.OnBtnRightClickL;
import com.zhansy.ordermanage.base.dialog.MyDialog;
import com.zhansy.ordermanage.base.mvp.model.InformBean;
import com.zhansy.ordermanage.base.mvp.model.TasteReturnBean;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.inform.InformDetailActivity;
import com.zhansy.ordermanage.taste.TasteDetailActivity;
import com.zhansy.ordermanage.taste.adapter.TasteAdapter;
import com.zhansy.ordermanage.taste.business.TasteReturnBusiness;
import com.zhansy.ordermanage.taste.controller.TasteReturnController;
import com.zhansy.ordermanage.taste.view.TasteReturnView;
import com.zhansy.ordermanage.taste.view.TasteView;
import com.zhansy.ordermanage.user.view.UserView;
import com.zhansy.ordermanage.utils.ToastUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class TasteReturnPresenterImpl extends MVPBasePresenter implements TasteReturnIpresenter {

    @Override
    public void getData() {
        controller.getData();
        showProgressDialog(getContext());
    }

    @Override
    public void commitTasteMassege(TasteReturnBean bean) {
        controller.tasteCommit(bean);
    }

    @Override
    public void deleteTaste(String id) {
        controller.deleteTaste(id);
    }

    public TasteReturnPresenterImpl(){
        controller = new TasteReturnController(getContext(),mHandler);
        tasteAdapter = new TasteAdapter(getContext());
    }
    private TasteReturnController controller;
    private TasteAdapter tasteAdapter;
    private MyHandler mHandler = new MyHandler(this);
    public static  class MyHandler extends Handler {
        WeakReference<TasteReturnIpresenter> presenter;
        public  MyHandler(TasteReturnIpresenter presenter){
            this.presenter = new WeakReference<TasteReturnIpresenter>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(presenter!=null&&presenter.get()!=null){
                TasteReturnPresenterImpl p= (TasteReturnPresenterImpl) presenter.get();
                p.handleMessage(msg);
            }
        }
    }
    public void handleMessage(Message msg) {

        if(msg.what== TasteReturnBusiness.CREATE_USER_TASTE_DATA_SUCCESS){
            TasteReturnView iview = getActualUi();
            if (iview == null){
                return;
            }
            iview.commitSuccess();
        }
        if(msg.what== TasteReturnBusiness.CREATE_USER_TASTE_DATA_ERROR){
            TasteReturnView iview = getActualUi();
            if (iview == null){
                return;
            }
            iview.commitFailed();
        }
        if(msg.what== TasteReturnBusiness.DELETE_GET_TASTE_DATA_SUCCESS){
//            TasteReturnView iview = getActualUi();
//            if (iview == null){
//                return;
//            }
            ToastUtil.showToast(getContext(),"删除成功");
            getData();//刷新
        }
        if(msg.what== TasteReturnBusiness.DELETE_GET_TASTE_DATA_ERROR){
//            TasteReturnView iview = getActualUi();
//            if (iview == null){
//                return;
//            }
            ToastUtil.showToast(getContext(),"删除失败");
        }
        if(msg.what== TasteReturnBusiness.SEARCH_GET_TASTE_DATA_SUCCESS){
            TasteView iview = getActualUi();
            if (iview == null){
                return;
            }
            iview.getNoDataView().setVisibility(View.GONE);
            iview.getListView().setVisibility(View.VISIBLE);
            List<TasteReturnBean> list = (List<TasteReturnBean>) msg.obj;
            tasteAdapter.setList(list);
            iview.setAdapter(tasteAdapter);
            iview.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getContext(), TasteDetailActivity.class);
                    Bundle bundle = new Bundle();
                    TasteReturnBean bean = (TasteReturnBean) tasteAdapter.getItem(position);
                    bundle.putSerializable("TasteReturnBean",bean);
                    intent.putExtras(bundle);
                    getContext().startActivity(intent);
                }
            });
            iview.getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    MyDialog.showCustomDialog(getContext(), "确定删除该建议？", new OnBtnRightClickL() {
                        @Override
                        public void onBtnRightClick() {
                            TasteReturnBean bean = (TasteReturnBean) tasteAdapter.getItem(position);
                            controller.deleteTaste(bean.getTaste_id()+"");
                            MyDialog.dimiss();
                        }
                    });
                    return false;
                }
            });
        }
        if(msg.what== TasteReturnBusiness.SERACH_GET_TASTE_DATA_ERROR){
            TasteView iview = getActualUi();
            if (iview == null){
                return;
            }
            iview.getNoDataView().setVisibility(View.VISIBLE);
            iview.getListView().setVisibility(View.GONE);
        }
        hideProgressDialog();
    }
}
