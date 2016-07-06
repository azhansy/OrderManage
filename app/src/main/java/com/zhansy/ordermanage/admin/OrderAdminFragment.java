//package com.zhansy.ordermanage.admin;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.view.ViewPager;
//
//import com.zhansy.ordermanage.R;
//import com.zhansy.ordermanage.base.MVPBaseFragment;
//import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
//import com.zhansy.ordermanage.base.widget.PagerSlidingTabStrip;
//import com.zhansy.ordermanage.me.adapter.OrderListPageAdapter;
//
//import butterknife.InjectView;
//import butterknife.OnClick;
//
///**
// * Created by ZHANSY
// * 邮箱：azhansy@hotmail.com
// */
//public class OrderAdminFragment extends MVPBaseFragment {
//
//    private OrderListPageAdapter orderListPageAdapter;
//
//    @InjectView(R.id.tabs_order_page)
//    PagerSlidingTabStrip tabs_order_page;
//    @InjectView(R.id.vp_order_page)
//    ViewPager vp_order_page;
//
//    @OnClick(R.id.btn_floating)
//    void OnClick(){
////        ToastUtil.showToast(getActivity(),"跳转到添加订单");
//        AddOrderAdminActivity.launch(getActivity(), AddOrderAdminActivity.class,"create");
//    }
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        orderListPageAdapter = new OrderListPageAdapter(getChildFragmentManager(),"admin");
//        vp_order_page.setAdapter(orderListPageAdapter);
//        vp_order_page.setOffscreenPageLimit(5);//只预加载自己，一个
//        tabs_order_page.setViewPager(vp_order_page);
//    }
//
//    @Override
//    public int getLayoutResource() {
//        return R.layout.fragment_order_manage;
//    }
//
//    @Override
//    protected boolean usePresenter() {
//        return false;
//    }
//
//    @Override
//    protected MVPBasePresenter createPresenter() {
//        return null;
//    }
//}
