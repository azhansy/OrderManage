//package com.zhansy.ordermanage.me;
//
//
//import android.os.Bundle;
//import android.support.v4.view.ViewPager;
//import android.view.View;
//
//import com.zhansy.ordermanage.R;
//import com.zhansy.ordermanage.base.MVPBaseActivity;
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
// * 我 - 我的订单
// */
//public class OrderListPageActivity extends MVPBaseActivity {
//
//    @InjectView(R.id.tabs_order_page)
//    PagerSlidingTabStrip tabs_order_page;
//    @InjectView(R.id.vp_order_page)
//    ViewPager vp_order_page;
//
//    private OrderListPageAdapter orderListPageAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        orderListPageAdapter = new OrderListPageAdapter(getSupportFragmentManager(),"user");
//        vp_order_page.setAdapter(orderListPageAdapter);
//        vp_order_page.setOffscreenPageLimit(5);//预加载5个
//        tabs_order_page.setViewPager(vp_order_page);
//    }
//
//    @OnClick({R.id.btn_back})
//    void OnClick(View v){
//        switch (v.getId()){
//            case R.id.btn_back:
//                finish();
//                break;
//
//        }
//    }
//
//    @Override
//    public int getLayoutResource() {
//        return R.layout.activity_order_list;
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
