//package com.zhansy.ordermanage.me.adapter;
//
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//
//import com.zhansy.ordermanage.R;
//import com.zhansy.ordermanage.base.OMApplication;
////import com.zhansy.ordermanage.order.CompleteFragment;
////import com.zhansy.ordermanage.me.fragment.NoConfirmFragment;
////import com.zhansy.ordermanage.me.fragment.NoEvaluateFragment;
////import com.zhansy.ordermanage.me.fragment.NodeliveryFragment;
////import com.zhansy.ordermanage.me.fragment.NopayFragment;
////import com.zhansy.ordermanage.me.fragment.OrderAllFragment;
//
///**
// * Created by ZHANSY
// * 邮箱：azhansy@hotmail.com
// * 我-全部订单 -我的订单Page 适配器
// */
//public class OrderListPageAdapter extends FragmentPagerAdapter {
//
//    private String status = "user";
//
//    String[] title = {
//            OMApplication.getInstance().getString(R.string.tv_all_order),
//            OMApplication.getInstance().getString(R.string.tv_nopayment),
//            OMApplication.getInstance().getString(R.string.tv_nodelivery),
//            OMApplication.getInstance().getString(R.string.tv_noconfirm),
//            OMApplication.getInstance().getString(R.string.tv_noevaluate),
//            OMApplication.getInstance().getString(R.string.tv_complete)
//    };
//    String[] titleAdmin = {
//            OMApplication.getInstance().getString(R.string.tv_all_order),
//            OMApplication.getInstance().getString(R.string.tv_nopayment),
//            OMApplication.getInstance().getString(R.string.tv_nodelivery),
//            OMApplication.getInstance().getString(R.string.tv_noconfirm),
//            OMApplication.getInstance().getString(R.string.tv_noevaluate),
//            OMApplication.getInstance().getString(R.string.tv_complete)
//    };
//    OrderAllFragment orderAllFragment;
//    NopayFragment nopayFragment;
//    NodeliveryFragment nodeliveryFragment;
//    NoConfirmFragment noConfirmFragment;
//    NoEvaluateFragment noEvaluateFragment;
//    CompleteFragment completeFragment;
//
//    public OrderListPageAdapter(FragmentManager fm,String status) {
//        super(fm);
//        this.status = status;
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        switch (position){
//            case 0:
//                orderAllFragment = OrderAllFragment.getInstance(status);
//                return orderAllFragment;
//            case 1:
//                nopayFragment =  NopayFragment.getInstance(status);
//                return nopayFragment;
//            case 2:
//                nodeliveryFragment = NodeliveryFragment.getInstance(status);
//                return nodeliveryFragment;
//            case 3:
//                noConfirmFragment = NoConfirmFragment.getInstance(status);
//                return noConfirmFragment;
//            case 4:
//                noEvaluateFragment = NoEvaluateFragment.getInstance(status);
//                return noEvaluateFragment;
//            case 5:
//                completeFragment = CompleteFragment.getInstance(status);
//                return completeFragment;
//            default:
//                return null;
//
//        }
//    }
//
//    @Override
//    public int getCount() {
//        if (status.equals("admin")){
//            return titleAdmin.length;
//        }else {
//            return title.length;
//        }
//    }
//
//    @Override
//    public CharSequence getPageTitle(int position) {
//        if (status.equals("admin")){
//            return titleAdmin[position];
//        }else {
//            return title[position];
//        }
//    }
//}
