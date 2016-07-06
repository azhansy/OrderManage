package com.zhansy.ordermanage.alert.presenter;

import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.zhansy.ordermanage.alert.business.NiticeActivityBusiness;
import com.zhansy.ordermanage.alert.controller.NoticeActivityController;
import com.zhansy.ordermanage.base.mvp.model.AlertLowBean;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.db.LitePalUtil;
import com.zhansy.ordermanage.alert.adapter.NoticeActivityAdapter;
import com.zhansy.ordermanage.alert.view.NoticeActivityView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class NoticeActivityPresenterImpl extends MVPBasePresenter implements NoticeActivityIpresenter {

    private NoticeActivityController controller;
    private NoticeActivityAdapter adapter;
    private NoticeActivityHanlder hanlder = new NoticeActivityHanlder(this);
    private List<AlertLowBean> alertLowBeanList = new ArrayList<AlertLowBean>();

    public NoticeActivityPresenterImpl(){
        controller = new NoticeActivityController(getContext(),hanlder);
        adapter= new NoticeActivityAdapter(getContext());
    }
    @Override
    public void getData() {
        controller.getdata();
    }

    @Override
    public void delectNoticebean(int id) {
        AlertLowBean alertLowBean= (AlertLowBean)adapter.getItem(id);
        LitePalUtil.delectNoticeBeanList(alertLowBean.getAlert_id());
        adapter.remove(alertLowBean);
//        orderController.getdata();
    }

    public static class NoticeActivityHanlder extends Handler{
        private WeakReference<NoticeActivityIpresenter> presenter;

        public NoticeActivityHanlder(NoticeActivityIpresenter ipresenter){
            this.presenter = new WeakReference<NoticeActivityIpresenter>(ipresenter);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            NoticeActivityPresenterImpl p = (NoticeActivityPresenterImpl) presenter.get();
            p.handleMessage(msg);
        }
    }

    private void handleMessage(Message msg) {
        if (msg.what == NiticeActivityBusiness.STATE_NITICE_DATA_SUCCESS){
            NoticeActivityView iview = getActualUi();
            if (iview == null)return;
            iview.getRelNoData().setVisibility(View.GONE);
            iview.getListView().setVisibility(View.VISIBLE);
           List<AlertLowBean> alertLowBeanList =  ( List<AlertLowBean>) msg.obj;
//            alertLowBeanList = LitePalUtil.getNoticeBeanList();
            adapter.addList(alertLowBeanList);
            adapter.notifyDataSetChanged();
            iview.setNoticeActivityAdapter(adapter);
            iview.setNotify(alertLowBeanList.get(0).getContent());//通知栏
//            iview.setdelectTip(true);
        }
        if (msg.what == NiticeActivityBusiness.STATE_NITICE_DATA_ERROR){
            NoticeActivityView iview = getActualUi();
            if (iview == null)return;
            if (adapter.getCount() == 0){
                iview.getRelNoData().setVisibility(View.VISIBLE);
                iview.getListView().setVisibility(View.GONE);
            }else {
                iview.setNoticeActivityAdapter(adapter);
            }
        }
    }
}
