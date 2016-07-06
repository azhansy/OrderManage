package com.zhansy.ordermanage.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.dialog.listener.OnBtnRightClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.base.OMApplication;
import com.zhansy.ordermanage.base.dialog.MyDialog;
import com.zhansy.ordermanage.base.mvp.model.OrderBean;
import com.zhansy.ordermanage.base.mvp.view.IBaseUi;
import com.zhansy.ordermanage.admin.AddOrderAdminActivity;
import com.zhansy.ordermanage.db.LitePalUtil;
import com.zhansy.ordermanage.download.DownLoadActivity;
import com.zhansy.ordermanage.feedback.FeedbackInputActivity;
import com.zhansy.ordermanage.order.presenter.OrderDetailActivityPresenterImpl;
import com.zhansy.ordermanage.order.view.OrderDetailActivityView;
import com.zhansy.ordermanage.utils.CommonUtil;
import com.zhansy.ordermanage.utils.ToastUtil;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class OrderDetailActivity extends MVPBaseActivity<OrderDetailActivityPresenterImpl> implements OrderDetailActivityView {

    private OrderBean bean;
    private String product_code;

    @InjectView(R.id.order_code)
    TextView order_code;
    @InjectView(R.id.download_btn)
    Button download_btn;
    @InjectView(R.id.order_userName)
    TextView order_userName;
    @InjectView(R.id.order_address)
    TextView order_address;
    @InjectView(R.id.postcode)
    TextView postcode;
    @InjectView(R.id.pay_mode)
    TextView pay_mode;
    @InjectView(R.id.order_type)
    TextView order_type;
    @InjectView(R.id.total_price)
    TextView total_price;
    @InjectView(R.id.input_user)
    TextView input_user;
    @InjectView(R.id.tv_remack)
    TextView tv_remack;
    @InjectView(R.id.cancel_order)
    TextView cancel_order;
    @InjectView(R.id.agree_order)
    TextView agree_order;
    @InjectView(R.id.check_admin)
    RelativeLayout check_admin;
    @InjectView(R.id.btn_product_feedback)
    TextView btn_product_feedback;

    @OnClick({R.id.btn_back,R.id.cancel_order,R.id.agree_order,R.id.download_btn,R.id.btn_product_feedback})
    void OnClick(View v){
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.cancel_order:
                MyDialog.showCustomDialog(this, "确定删除订单？", new OnBtnRightClickL() {
                    @Override
                    public void onBtnRightClick() {
                        mPresenter.deleteOrder(bean.getOrder_code());
                        MyDialog.dimiss();
                    }
                });
//                finish();
                break;
            case R.id.download_btn:
                if (LitePalUtil.downLoadBeanExist(bean.getOrder_code())) {
                    MyDialog.showCustomDialog(OrderDetailActivity.this, "文件已下载，前往下载目录查看？", new OnBtnRightClickL() {
                            @Override
                            public void onBtnRightClick() {
                                DownLoadActivity.launch(OrderDetailActivity.this, DownLoadActivity.class);
                                MyDialog.dimiss();
                            }
                     });
                    return;
                }

                if (normalListDialog == null){
                    normalListDialog = MyDialog.showNormalListDialog(this,"请选择下载类型", new String[]{"下载PDF订单", "下载Excel订单","取消"});
//                    normalListDialog.show();
                }else {
                    normalListDialog.show(R.style.myDialogAnim);
                }
                normalListDialog.setOnOperItemClickL(new OnOperItemClickL() {
                    @Override
                    public void onOperItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        if (position == 0){
                            download_btn.setEnabled(false);//下载中不可点击
                            mPresenter.downloadOrderPDF(bean.getOrder_code());
                            normalListDialog.dismiss();
                        }else if (position == 1){
                            download_btn.setEnabled(false);//下载中不可点击
                            mPresenter.downloadOrderExcel(bean.getOrder_code());//下载Excel
                            normalListDialog.dismiss();
                        }else {
                            normalListDialog.dismiss();
                        }
                    }
                });
                break;
            case R.id.agree_order:
//                mPresenter.setData();
                Intent intent = new Intent(this, AddOrderAdminActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("OrderBean",bean);
                intent.putExtras(bundle);
                intent.putExtra(IBaseUi.KEY_GID, "update");
                this.startActivity(intent);
                break;
            case R.id.btn_product_feedback:
                //进入商品反馈页面
//                FeedbackInputActivity.launch(this, FeedbackInputActivity.class);
                Intent intent1 = new Intent(this, FeedbackInputActivity.class);
//                Bundle bundle1 = new Bundle();
//                bundle1.putSerializable("Feedback",bean);
//                intent1.putExtras(bundle1);
                intent1.putExtra(IBaseUi.KEY_GID, bean.getOrder_code());
                intent1.putExtra("product_code", product_code);
                this.startActivity(intent1);
                break;
        }
    }

    private NormalListDialog normalListDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bean = (OrderBean) getIntent().getSerializableExtra("OrderBean");
        product_code = getIntent().getStringExtra("product_code"); //
        if(OMApplication.getInstance().getCurrentUser().isManage()){
            check_admin.setVisibility(View.VISIBLE);
        }else {
            check_admin.setVisibility(View.GONE);
        }
        if (bean != null){
            CommonUtil.showLog("传过来的orderbean "+bean.toString());
            CommonUtil.showLog("传过来的product_code "+ product_code);
            setInit(bean);
        }
    }

    public void setInit(OrderBean bean){
        order_code.setText(bean.getOrder_code());
        order_userName.setText(bean.getUserName());
        order_address.setText(bean.getAddress());
        postcode.setText(bean.getPostcode());
        pay_mode.setText(bean.getPay_mode());
        total_price.setText(bean.getTotal_price()+"");
        tv_remack.setText(bean.getRemark());
        input_user.setText(bean.getInput_user());
//        if(typeAdmin.equals("admin")){
            order_type.setText(bean.getOrder_type());
//        }else {
//            order_type.setText(bean.getOrder_type());
//        }
        if (!OMApplication.getInstance().getCurrentUser().isManage()
                && bean.getOrder_type().equals(OMApplication.getInstance().getResources().getString(R.string.tv_noevaluate))) {
            btn_product_feedback.setVisibility(View.VISIBLE);
        }else {
            btn_product_feedback.setVisibility(View.GONE);
        }

    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected OrderDetailActivityPresenterImpl createPresenter() {
        return new OrderDetailActivityPresenterImpl();
    }

    @Override
    public void setAdminCheck(boolean b) {
        if (b){
            ToastUtil.showToast(OrderDetailActivity.this,"更新成功！");
            finish();
        }else {
            ToastUtil.showToast(OrderDetailActivity.this,"更新失败，请稍后再试！");
        }
    }

    @Override
    public void operSuccess(String tip) {
        ToastUtil.showToast(this,tip);
        finish();
    }

    @Override
    public void operFailed(String tip) {
        ToastUtil.showToast(this,tip);
    }

    @Override
    public void downLoadSuccess(String path) {
        download_btn.setEnabled(true);//下载完成可以点击
        MyDialog.showCustomDialog(this, "下载成功，查看已下载订单？\n 下载路径："+path, new OnBtnRightClickL() {
            @Override
            public void onBtnRightClick() {
                DownLoadActivity.launch(OrderDetailActivity.this, DownLoadActivity.class);
//                MyDialog.dimiss();
            }
        });
    }

    @Override
    public void downLoadFailed() {
        download_btn.setEnabled(true);//下载完成可以点击
        ToastUtil.showToast(this,"下载失败，请稍后再试。");
    }


    @Override
    public Context getPresenterContext() {
        return this;
    }



}
