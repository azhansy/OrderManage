package com.zhansy.ordermanage.feedback.presenter;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import com.flyco.dialog.listener.OnBtnRightClickL;
import com.zhansy.ordermanage.base.dialog.MyDialog;
import com.zhansy.ordermanage.base.mvp.model.FeedBackBean;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.feedback.business.FeedBackBusiness;
import com.zhansy.ordermanage.feedback.controller.FeedBackController;
import com.zhansy.ordermanage.feedback.view.FeedBackView;
import com.zhansy.ordermanage.order.adapter.FeedBackAdapter;
import com.zhansy.ordermanage.utils.ToastUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class FeedbackPresenterImpl extends MVPBasePresenter implements FeedbackIPresenter {
    private FeedBackAdapter adapter;
    private FeedBackController controller;
    private MyHandler mHandler = new MyHandler(this);
    private int page = 0;
    private String key="product_code";
    private String key_word="";

    public FeedbackPresenterImpl(){
        adapter = new FeedBackAdapter(getContext());
        controller = new FeedBackController(getContext(),mHandler);
    }
    @Override
    public void getData(String key,String key_word) {
        page=0;
        this.key_word = key_word;
        this.key = key;
        controller.getdata(page,key,key_word);
        showProgressDialog(getContext());
    }

    @Override
    public void deleteFeedBack(String msg_id) {
        controller.deleteFeedback(msg_id);
    }

    @Override
    public void Refresh(String status) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 0;
                FeedBackView orderClassfyView = getActualUi();
                if (orderClassfyView == null)
                    return;
                orderClassfyView.getXListView().stopRefresh();
                orderClassfyView.getXListView().setRefreshTime(new Date());
                controller.getdata(page,key,key_word);
            }
        }, 500);
    }

    @Override
    public void Load(String status) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = page + 1 ;
                FeedBackView orderClassfyView = getActualUi();
                if (orderClassfyView == null)
                    return;
                orderClassfyView.getXListView().stopLoadMore();
                controller.getdata(page,key,key_word);
            }
        }, 500);
    }

    public static  class MyHandler extends Handler {
        WeakReference<FeedbackIPresenter> presenter;
        public  MyHandler(FeedbackIPresenter presenter){
            this.presenter = new WeakReference<FeedbackIPresenter>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(presenter!=null&&presenter.get()!=null){
                FeedbackPresenterImpl p= (FeedbackPresenterImpl) presenter.get();
                p.handleMessage(msg);
            }
        }
    }
    public void handleMessage(Message msg) {

        if(msg.what== FeedBackBusiness.STATE_PRODUCT_FEEDBACK_DATA_SUCCESS){
            FeedBackView iview = getActualUi();
            if (iview == null){
                return;
            }
            List<FeedBackBean> list = new ArrayList<>();
            list = (List<FeedBackBean>) msg.obj;

            iview.getXListView().setVisibility(View.VISIBLE);
            iview.getNoDataView().setVisibility(View.GONE);
            iview.getXListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    final FeedBackBean backBean = (FeedBackBean) adapter.getItem(position-1);//xListView要减1
                    MyDialog.showCustomDialog(getContext(), "是否删除此商品反馈信息？", new OnBtnRightClickL() {
                        @Override
                        public void onBtnRightClick() {
                            deleteFeedBack(backBean.getMsg_id());
                            MyDialog.dimiss();
                        }
                    });
                    return false;
                }
            });
            if(page==0) {
                adapter.setList(list);
                iview.setFeedBackAdapter(adapter);
            }
            else {
                adapter.addList(list);
            }
            if(list.size()<10){
                iview.getXListView().setPullLoadEnable(false);
            }else{
                iview.getXListView().setPullLoadEnable(true);
            }
            adapter.notifyDataSetChanged();
        }
        if(msg.what==FeedBackBusiness.STATE_PRODUCT_FEEDBACK_DATA_ERROR){
            FeedBackView iview = getActualUi();
            if (iview == null){
                return;
            }
            iview.getXListView().setVisibility(View.GONE);
            iview.getNoDataView().setVisibility(View.VISIBLE);
        }
        if(msg.what==FeedBackBusiness.DELETE_PRODUCT_FEEDBACK_DATA_SUCCESS){
            FeedBackView iview = getActualUi();
            if (iview == null){
                return;
            }
            adapter.notifyDataSetChanged();
            iview.deleteSuccess();
        }
        if(msg.what==FeedBackBusiness.DELETE_PRODUCT_FEEDBACK_DATA_ERROR){
            FeedBackView iview = getActualUi();
            if (iview == null){
                return;
            }
            iview.deleteFailed();
        }
        hideProgressDialog();
    }
}
