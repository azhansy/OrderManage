package com.zhansy.ordermanage.admin;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnBtnLeftClickL;
import com.flyco.dialog.listener.OnBtnRightClickL;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.base.dialog.MyDialog;
import com.zhansy.ordermanage.base.mvp.model.OrderBean;
import com.zhansy.ordermanage.base.mvp.view.IBaseUi;
import com.zhansy.ordermanage.admin.presenter.AddOrderAdminPresenterImpl;
import com.zhansy.ordermanage.admin.view.AddOrderAdminActivityView;
import com.zhansy.ordermanage.event.OrderDetailEvent;
import com.zhansy.ordermanage.utils.CommonUtil;

import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class AddOrderAdminActivity extends MVPBaseActivity<AddOrderAdminPresenterImpl> implements AddOrderAdminActivityView {

    @InjectView(R.id.spinner_order_type)
    Spinner spinner_order_type;
    @InjectView(R.id.spinner_pay_mode)
    EditText spinner_pay_mode;

    @InjectView(R.id.order_code)
    EditText order_code;
//    @InjectView(R.id.trade_code)
//    EditText trade_code;
    @InjectView(R.id.userName)
    EditText userName;
    @InjectView(R.id.total_price)
    EditText total_price;
    @InjectView(R.id.postcode)
    EditText postcode;
    @InjectView(R.id.order_address)
    EditText order_address;
    @InjectView(R.id.remark)
    EditText remark;

    @InjectView(R.id.request_order_title)
    TextView request_order_title;
    @InjectView(R.id.request_order_commit)
    TextView request_order_commit;

    private String  orderStatus = "";//订单状态

    @OnClick({R.id.btn_back,R.id.request_order_commit})
    void OnClick(View v){
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.request_order_commit:
                if (request.equals("update")){
                    MyDialog.showCustomDialog(this, "确定提交？", new OnBtnRightClickL() {
                        @Override
                        public void onBtnRightClick() {
                            mPresenter.updateOrderData(bean.getOrder_code(),orderStatus,remark.getText().toString());
                            MyDialog.dimiss();
                        }
                    });
                }/*else {
                    bean.setOrder_type("haha");
                    mPresenter.createOrderData(bean);
                }*/
                break;
        }
    }
    private OrderBean bean = new OrderBean();
    private String request;
//    private String product_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在要接收消息的页面注册
//        EventBus.getDefault().register(this);
        bean = (OrderBean) getIntent().getSerializableExtra("OrderBean");
        if (bean != null) {
            CommonUtil.showLog("传过来的orderbean " + bean.toString());
            setInit(bean);
            orderStatus = bean.getOrder_type();
        }
        request = getIntent().getStringExtra(IBaseUi.KEY_GID); //标记为新建还是更新订单
//        product_code = getIntent().getStringExtra("product_code");//订单里面的商品code
        if (request.equals("update")){
            request_order_title.setText("跟踪订单");
            setEditable(false);
        }else {
            request_order_title.setText("添加订单");
            setEditable(true);
        }

        spinner_order_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                orderStatus = spinner_order_type.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void setInit(OrderBean bean){
        order_code.setText(bean.getOrder_code());
        userName.setText(bean.getUserName());
        order_address.setText(bean.getAddress());
        postcode.setText(bean.getPostcode());
        //判断bean.getPay_mode()
//        spinner_pay_mode.setSelection(CommonUtil.getSelectionSpinner("pay_mode",bean.getPay_mode()));
        if (!bean.getPay_mode().isEmpty()){
            spinner_pay_mode.setText(bean.getPay_mode());
        }
        total_price.setText(bean.getTotal_price()+"");
        remark.setText(bean.getRemark());
        //判断bean.getOrder_type()
        spinner_order_type.setSelection(CommonUtil.getSelectionSpinner("order_type",bean.getOrder_type()));
    }

    private void setEditable(boolean isEdit){
        order_code.setFocusable(isEdit);
//        order_code.setFocusable(isEdit);
        userName.setFocusable(isEdit);
        order_address.setFocusable(isEdit);
        postcode.setFocusable(isEdit);
        spinner_pay_mode.setFocusable(isEdit);
        total_price.setFocusable(isEdit);
//        remark.setFocusable(isEdit);
    }

//    public void onEventMainThread(OrderDetailEvent event){
//        mPresenter.updateOrderData(event.getOrder_code(),"已完成","");
//    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_add_order_admin;
    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected AddOrderAdminPresenterImpl createPresenter() {
        return new AddOrderAdminPresenterImpl();
    }

    @Override
    public void setEditTextContent() {
        setInit(bean);
    }

    @Override
    public void showTip(boolean b) {
        if (!b){
            MyDialog.showRightDialog(this, "提交失败，请检查是否有修改订单内容！", new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    MyDialog.dimiss();
                }
            });
        }else {
            if (request.equals("update")){
                MyDialog.showDoubleDialog(this, "提交成功，审核已通过！", "返回", "确定", new OnBtnLeftClickL() {
                    @Override
                    public void onBtnLeftClick() {
                        finish();
                    }
                }, new OnBtnRightClickL() {
                    @Override
                    public void onBtnRightClick() {
                        MyDialog.dimiss();
                    }
                });
            }else {
                MyDialog.showRightDialog(this, "成功添加订单！", new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        finish();
                    }
                });
            }
        }
    }

    @Override
    public Context getPresenterContext() {
        return this;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解除注册
//        EventBus.getDefault().unregister(this);
    }
}
