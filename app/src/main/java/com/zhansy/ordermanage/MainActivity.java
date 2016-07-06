package com.zhansy.ordermanage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.base.OMApplication;
import com.zhansy.ordermanage.base.mvp.model.CurrentUserBean;
import com.zhansy.ordermanage.base.mvp.model.InformBean;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.chart.MpChartActivity;
import com.zhansy.ordermanage.db.LitePalUtil;
import com.zhansy.ordermanage.download.DownLoadActivity;
import com.zhansy.ordermanage.form.FormActivity;
import com.zhansy.ordermanage.me.MeActivity;
import com.zhansy.ordermanage.order.OrderClassifyActivity;
import com.zhansy.ordermanage.order.OrderCompleteActivity;
import com.zhansy.ordermanage.taste.TasteReturnActivity;
import com.zhansy.ordermanage.order.OrderSearchActivity;
import com.zhansy.ordermanage.product.ProductSearchActivity;
import com.zhansy.ordermanage.index.BuyProductActivity;
import com.zhansy.ordermanage.alert.NoticeActivity;
import com.zhansy.ordermanage.utils.CommonUtil;
import com.zhansy.ordermanage.utils.ToastUtil;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends MVPBaseActivity {
    @InjectView(R.id.tv_notice)
    TextView tv_notice;
    @InjectView(R.id.biv_user_icon)
    ImageView biv_user_icon;
    @InjectView(R.id.tv_user_name)
    TextView tv_user_name;
    @InjectView(R.id.tv_user_phone)
    TextView tv_user_phone;
    @InjectView(R.id.tv_user_address)
    TextView tv_user_address;
    @InjectView(R.id.tv_user_company)
    TextView tv_user_company;
    @InjectView(R.id.tv_postcode)
    TextView tv_postcode;

    @OnClick({R.id.rl_buy_product,R.id.rl_order_1,R.id.rl_order_2,
            R.id.rl_order_3,R.id.rl_order_complete,R.id.rel_product_alert,
            R.id.rl_order_search,R.id.rl_order_chart,R.id.rl_contract_load,
            R.id.rel_me_setting,R.id.rl_user_advice,R.id.rl_order_download,
            R.id.rl_order_4,R.id.rel_setting})
    void OnClick(View v){
        switch (v.getId()){
            case R.id.rl_buy_product:
                BuyProductActivity.launch(this,BuyProductActivity.class);
                break;
            case R.id.rl_order_1:
//                OrderClassifyActivity.launch(this,OrderClassifyActivity.class,"lr_noPayment");
                FormActivity.launch(this, FormActivity.class);//订货单
                break;
            case R.id.rl_order_2:
                ProductSearchActivity.launch(this,ProductSearchActivity.class);
                break;
            case R.id.rl_order_3:
                OrderClassifyActivity.launch(this,OrderClassifyActivity.class,"lr_noPayment");
                break;
            case R.id.rl_order_4:
//                OrderClassifyActivity.launch(this,OrderClassifyActivity.class,"rl_noevaluate");
                OrderCompleteActivity.launch(this,OrderCompleteActivity.class,"lr_noPayment");//未审核订单
                break;
            case R.id.rl_order_complete:
                OrderCompleteActivity.launch(this,OrderCompleteActivity.class,"lr_complete");//已完成订单
                break;
            case R.id.rel_product_alert:
                NoticeActivity.launch(this,NoticeActivity.class);
                break;
            case R.id.rl_order_search:
                OrderSearchActivity.launch(this,OrderSearchActivity.class);
                break;
            case R.id.rl_order_chart:
                MpChartActivity.launch(this,MpChartActivity.class);
                break;
            case R.id.rel_me_setting:
            case R.id.rel_setting:
                MeActivity.launch(this, MeActivity.class);
                break;
            case R.id.rl_order_download:
                DownLoadActivity.launch(this, DownLoadActivity.class);
                break;
            case R.id.rl_user_advice:
                TasteReturnActivity.launch(this, TasteReturnActivity.class);
                break;
        }
    }
    private Long beginTime;//上一次刷新低库存数据
    private int resetTime = 1000 * 60 * 60;//间隔一个时间再刷新

    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        CurrentUserBean user = OMApplication.getInstance().getCurrentUser();
        ImageLoader.getInstance().displayImage(user.getImgurl(), biv_user_icon);
        tv_user_name.setText(user.getUsername() +"  ("+user.getUser_type()+")");
        tv_user_phone.setText(user.getPhone_number());
        tv_user_address.setText(user.getUser_address());
        tv_user_company.setText(user.getCompany());
        tv_postcode.setText(user.getPostcode());
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateNotice();
//        LowAlert();
    }

    /**
     * 定时请求低库存数据
     */
    private void LowAlert(){
        SharedPreferences sp = CommonUtil.getSharedPreferences();
        SharedPreferences.Editor editor = sp.edit();
        boolean deBoolean = sp.getBoolean("lowstock",true);
        if (deBoolean){
//            beginTime = System.currentTimeMillis();//第一次刷新后的时间
            final Long time = System.currentTimeMillis()-beginTime;
            if (time > resetTime){
                //间隔时间后，执行刷新低库存数据

                //请求成功后，把当前请求时间记住
                beginTime = System.currentTimeMillis();

            }
        }
    }

    /**
     * 更新通知
     */
    private void updateNotice(){
        List<InformBean> informBeanList = new ArrayList<>();
        informBeanList = LitePalUtil.getNoticeBeanList();
        if (informBeanList.size() != 0){
            String content="";
            for (int i=0;i<informBeanList.size();i++){
                content = content + informBeanList.get(i).getContent()+"   ";
            }
            tv_notice.setText(content);
        }
    }

    @Override
    protected boolean usePresenter() {
        return false;
    }

    @Override
    protected MVPBasePresenter createPresenter() {
        return null;
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            exitBy2Click(); //调用双击退出函数
        }
        return false;
    }
    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            ToastUtil.showToast(this, "再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            getAppManager().ExitApp(this);
        }
    }
}
