package com.zhansy.ordermanage.feedback;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.flyco.dialog.listener.OnBtnLeftClickL;
import com.flyco.dialog.listener.OnBtnRightClickL;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.AppManager;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.base.dialog.MyDialog;
import com.zhansy.ordermanage.base.mvp.model.OrderBean;
import com.zhansy.ordermanage.base.mvp.view.IBaseUi;
import com.zhansy.ordermanage.event.OrderDetailEvent;
import com.zhansy.ordermanage.feedback.presenter.FeedbackInputPresenterImpl;
import com.zhansy.ordermanage.feedback.view.FeedbackInputView;
import com.zhansy.ordermanage.order.OrderDetailActivity;
import com.zhansy.ordermanage.utils.CommonUtil;
import com.zhansy.ordermanage.utils.ToastUtil;

import java.io.File;

import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class FeedbackInputActivity extends MVPBaseActivity<FeedbackInputPresenterImpl> implements FeedbackInputView {
    @InjectView(R.id.tv_order_code)
    TextView tv_order_code;
    @InjectView(R.id.et_describe)
    EditText et_describe;
    @InjectView(R.id.iv_icon)
    ImageView iv_icon;
    @InjectView(R.id.spinner_product_issue)
    Spinner spinner_product_issue;

    @OnClick({R.id.btn_back,R.id.btn_send,R.id.rl_upload_icon})
    void OnClick(View v){
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_send:
                describe = et_describe.getText().toString().trim();
                if (describe.isEmpty()){
                    ToastUtil.showToast(this,"商品反馈内容不能为空！");
                }else {
                    MyDialog.showCustomDialog(this, "确定上传商品信息反馈？", new OnBtnRightClickL() {
                        @Override
                        public void onBtnRightClick() {
                            mPresenter.setData(order_code,product_issue , describe,product_code);
                            MyDialog.dimiss();
                        }
                    });
                }
                break;
            case R.id.rl_upload_icon:
                mPresenter.showPhoto();
                //上传图片
                break;
        }
    }
    private String order_code;
    private String product_code;
    private String product_issue="破损";
    private String describe;
    private String fileName;
    private Bitmap head;//Bitmap

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        bean = (OrderBean) getIntent().getSerializableExtra("OrderBean");
        order_code = getIntent().getStringExtra(IBaseUi.KEY_GID);
        product_code = getIntent().getStringExtra("product_code");
        tv_order_code.setText(order_code);
        spinner_product_issue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                product_issue = spinner_product_issue.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_input_feedback;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    mPresenter.cropPhoto(data.getData());
                }else{
                    mPresenter.cancel();
                }
                break;
            case 2:
                if (resultCode ==Activity.RESULT_OK) {
                    File temp = new File(CommonUtil.hostFile
                            + "/issue.jpg");
                    mPresenter.cropPhoto(Uri.fromFile(temp));
                    fileName =temp.toString();
                }else{
                    mPresenter.cancel();
                }
                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if(head!=null){
                        mPresenter.saveSd(head);
                        mPresenter.submitProductImage();
                        iv_icon.setImageBitmap(head);
                        mPresenter.cancel();
                    }
                }
                break;
//            case 4:
//                if(data!=null){
//                    String region=data.getStringExtra("region");
////                    address_txt.setText(region);
//                }
        }
    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected FeedbackInputPresenterImpl createPresenter() {
        return new FeedbackInputPresenterImpl();
    }

    @Override
    public void commitSuccess() {
        AppManager appManager = AppManager.getAppManager();
        appManager.finishActivity(OrderDetailActivity.class);
        MyDialog.showDoubleDialog(this, "提交成功！", "返回", "确定", new OnBtnLeftClickL() {
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
//        EventBus.getDefault().post(new OrderDetailEvent(order_code));
    }

    @Override
    public void commitFailed() {
        MyDialog.showDialog(this, "提交失败");
    }

    @Override
    public void startPictureActivity(Intent i, int j) {
        this.startActivityForResult(i, j);
    }

    @Override
    public void finishActivity() {
        finish();
    }
    @Override
    public Context getPresenterContext() {
        return this;
    }
}
