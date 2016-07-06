//package com.zhansy.ordermanage.admin.presenter;
//
//import android.os.Handler;
//import android.os.Message;
//
//import com.zhansy.ordermanage.base.mvp.model.UserBean;
//import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
//import com.zhansy.ordermanage.admin.adapter.MemberAdapter;
//import com.zhansy.ordermanage.admin.view.MemberAdminFragmentView;
//import com.zhansy.ordermanage.order.business.OrderBusiness;
//import com.zhansy.ordermanage.order.controller.OrderController;
//
//import java.lang.ref.WeakReference;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by ZHANSY
// * 邮箱：azhansy@hotmail.com
// */
//public class MemberFragmentPresenterImpl extends MVPBasePresenter implements MemberFragmentIpresenter{
//    private List<UserBean> userBeanList = new ArrayList<UserBean>();
//    private MyHandler mHandler = new MyHandler(this);
//    private OrderController controller;
//    private int page=1;
//
//    public MemberFragmentPresenterImpl(){
//        controller = new OrderController(getContext(), mHandler);
//    }
//    @Override
//    public void getData() {
//        controller.getCompanyUserList();
//    }
//
//    @Override
//    public void Refresh(String status) {
//
//    }
//
//    @Override
//    public void Load(String status) {
//
//    }
//
//    public static  class MyHandler extends Handler {
//        WeakReference<MemberFragmentIpresenter> presenter;
//        public  MyHandler(MemberFragmentIpresenter presenter){
//            this.presenter = new WeakReference<MemberFragmentIpresenter>(presenter);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if(presenter!=null&&presenter.get()!=null){
//                MemberFragmentPresenterImpl p= (MemberFragmentPresenterImpl) presenter.get();
//                p.handleMessage(msg);
//            }
//        }
//    }
//    public void handleMessage(Message msg) {
//
//        if(msg.what== OrderBusiness.GET_COMPANY_USER_LIST_DATA_SUCCESS){
//            MemberAdminFragmentView iview = getActualUi();
//            if (iview == null)
//                return;
//            MemberAdapter adapter = new MemberAdapter(getContext());
//            userBeanList = (List<UserBean>) msg.obj;
//            adapter.setList(userBeanList);
//            iview.setMemberAdapter(adapter);
//        }
//        if(msg.what==OrderBusiness.GET_COMPANY_USER_LIST_DATA_ERROR){
//
//        }
//    }
//}
