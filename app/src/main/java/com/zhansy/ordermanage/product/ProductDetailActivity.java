package com.zhansy.ordermanage.product;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.dialog.listener.OnBtnLeftClickL;
import com.flyco.dialog.listener.OnBtnRightClickL;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.base.OMApplication;
import com.zhansy.ordermanage.base.dialog.MyDialog;
import com.zhansy.ordermanage.base.mvp.model.ProductBean;
import com.zhansy.ordermanage.base.widget.BaseImageView;
import com.zhansy.ordermanage.form.FormActivity;
import com.zhansy.ordermanage.index.BuyProductActivity;
import com.zhansy.ordermanage.index.presenter.ProductDetailActivityPresenterImpl;
import com.zhansy.ordermanage.index.view.ProductDetailActivityView;
import com.zhansy.ordermanage.feedback.FeedbackActivity;
import com.zhansy.ordermanage.utils.CommonUtil;
import com.zhansy.ordermanage.utils.ToastUtil;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class ProductDetailActivity extends MVPBaseActivity<ProductDetailActivityPresenterImpl> implements ProductDetailActivityView {
    @InjectView(R.id.product_remark)
    TextView product_remark;
    @InjectView(R.id.product_price)
    TextView product_price;
    @InjectView(R.id.product_name)
    TextView product_name;
    @InjectView(R.id.product_image)
    BaseImageView product_image;
    @InjectView(R.id.tv_product_num)
    TextView tv_product_num;
    @InjectView(R.id.product_stock_number)
    TextView product_stock_number;
    @InjectView(R.id.product_generation)
    TextView product_generation;
    @InjectView(R.id.product_input)
    TextView product_input;
    @InjectView(R.id.rel_product_feedback)
    RelativeLayout rel_product_feedback;

    @OnClick({R.id.btn_back,R.id.btn_add,R.id.btn_reduce,R.id.btn_buy,R.id.rel_product_feedback})
    void OnClick(View v){
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_add:
                try {
                    int number = Integer.parseInt(tv_product_num.getText().toString());
                    if (number >= bean.getStock()){
//                        number = bean.getStock();
                        ToastUtil.showToast(this,"已选择最大库存量！");
                    }else {
                        number = number + 10;
                    }
                    tv_product_num.setText(number+"");
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_reduce:
                try {
                    int number1 = Integer.parseInt(tv_product_num.getText().toString());
                    if (number1<=10){
                        number1 = 10;
                    }else {
                        number1 = number1-10;
                    }
                    tv_product_num.setText(number1+"");
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_buy:
                if (TextUtils.isEmpty(tv_product_num.getText().toString())){
                    ToastUtil.showToast(this,"请输入正确的购买数量！");
                    break;
                }
                Long count = Long.valueOf(tv_product_num.getText().toString());
                if (count > bean.getStock() || count < 0 ){
                    ToastUtil.showToast(this,"请输入正确的购买数量！");
                    break;
                }
                bean.setUser_code(OMApplication.getInstance().getCurrentUser().getUser_code());
//                    bean.setCurrentUserBean(OMApplication.getInstance().getCurrentUser());
                bean.setOut_number(count);
//                    bean.setProduct_code(bean.getProduct_code());
                if (bean.save()){
                        MyDialog.showDoubleDialog(this,"成功加入订货单！", "继续进货", "进入进货单", new OnBtnLeftClickL() {
                            @Override
                            public void onBtnLeftClick() {
                                MyDialog.dimiss();
                                finish();
                            }
                        }, new OnBtnRightClickL() {
                            @Override
                            public void onBtnRightClick() {
                                FormActivity.launch(ProductDetailActivity.this, FormActivity.class);
                                MyDialog.dimiss();
                                finish();
                            }
                        });

                    //加入订货单成功
                }else {
                    //加入订货单失败
                    MyDialog.showDoubleDialog(this,"订货单已有此商品", "重新返回进货", "查看进货单", new OnBtnLeftClickL() {
                        @Override
                        public void onBtnLeftClick() {
                            MyDialog.dimiss();
                            finish();
                        }
                    }, new OnBtnRightClickL() {
                        @Override
                        public void onBtnRightClick() {
                            FormActivity.launch(ProductDetailActivity.this, FormActivity.class);
                            MyDialog.dimiss();
                            finish();
//                                    getAppManager().finishActivity(BuyProductActivity.class);
                        }
                    });
                }
                break;
//            case r.id.product_share:
//                break;
            case R.id.rel_product_feedback:
                FeedbackActivity.launch(this,FeedbackActivity.class,bean.getProduct_code());
                break;
        }

    }

    private ProductBean bean;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED);
        init();
        mPresenter.getData();
    }
    private void init(){
        bean = (ProductBean) getIntent().getSerializableExtra("ProductBean");
        CommonUtil.showLog("传过来的productbean "+bean.toString());
        if (bean != null){
            ImageLoader.getInstance().displayImage(bean.getImgurl(),product_image);
            product_stock_number.setText(String.valueOf(bean.getStock()));
            product_name.setText(bean.getProduct_name());
            product_price.setText(bean.getPrice()+"");
            product_remark.setText(bean.getRemark());
            product_generation.setText(bean.getGeneration());
            product_input.setText(bean.getInput_user());
        }
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected ProductDetailActivityPresenterImpl createPresenter() {
        return new ProductDetailActivityPresenterImpl();
    }

//    @Override
//    public void setFeedBackAdapter(FeedBackAdapter adapter) {
//        lv_feedback.setAdapter(adapter);
//    }

    @Override
    public void setToShoppingCartSuccess() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("成功加入进货单")
//                .setMessage("进入进货单？")
//                .setPositiveButton("进入", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });

        if (progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }
        ToastUtil.showToast(this,"成功加入进货单");
        finish();
    }

    @Override
    public void setToShoppingCartFail() {
        if (progressDialog != null){
            progressDialog.dismiss();
        }
        ToastUtil.showToast(this,"添加失败，请稍后再试");
    }

    @Override
    public Context getPresenterContext() {
        return this;
    }
}
