package com.zhansy.ordermanage.order;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.base.mvp.view.IBaseUi;
import com.zhansy.ordermanage.base.widget.XListView;
import com.zhansy.ordermanage.feedback.presenter.FeedbackPresenterImpl;
import com.zhansy.ordermanage.feedback.view.FeedBackView;
import com.zhansy.ordermanage.order.adapter.FeedBackAdapter;
import com.zhansy.ordermanage.utils.ToastUtil;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 * 商品信息-反馈 管理
 */
public class FeedbackAdminActivity extends MVPBaseActivity<FeedbackPresenterImpl> implements FeedBackView,XListView.IXListViewListener {
    @InjectView(R.id.xlv_feedback)
    XListView xlv_feedback;
    @InjectView(R.id.rel_no_data)
    RelativeLayout rel_no_data;
    @InjectView(R.id.et_key)
    EditText et_key;
    @InjectView(R.id.spinner_feedback_type)
    Spinner spinner_feedback_type;

    @OnClick({R.id.btn_back,R.id.biv_sousuo})
    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.biv_sousuo:
//                ToastUtil.showToast(this,"还没实现查询");
                String key_word = et_key.getText().toString().trim();
                if (key_word.isEmpty()){
                    ToastUtil.showToast(this,"请输入查询信息");
                }else {
                    mPresenter.getData(getSearchKey(),key_word);
                }
                break;
        }
    }


    private int positionSelect = 0;
    private String key="product_code";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        productCode = getIntent().getStringExtra(IBaseUi.KEY_GID);
        mPresenter.getData(key,"");
        xlv_feedback.setPullRefreshEnable(true);
        xlv_feedback.setPullLoadEnable(true);
        xlv_feedback.setXListViewListener(this);
        spinner_feedback_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                key = spinner_feedback_type.getSelectedItem().toString();
                positionSelect = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private String getSearchKey(){
        switch (positionSelect){
            case 0:
                key = "product_name";
                break;
            case 1:
                key = "product_code";
                break;
            case 2:
                key = "order_code";
                break;
            case 3:
                key = "input_user";
                break;
            default:
                key = "product_name";
                break;
        }
        return key;
    }
    @Override
    public int getLayoutResource() {
        return R.layout.activity_order_operation;
    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected FeedbackPresenterImpl createPresenter() {
        return new FeedbackPresenterImpl();
    }

    @Override
    public void setFeedBackAdapter(FeedBackAdapter adapter) {
        xlv_feedback.setAdapter(adapter);
    }

    @Override
    public XListView getXListView() {
        return xlv_feedback;
    }

    @Override
    public View getNoDataView() {
        return rel_no_data;
    }

    @Override
    public void deleteSuccess() {
        ToastUtil.showToast(this,"删除成功");
        mPresenter.getData(key,"");
    }

    @Override
    public void deleteFailed() {
        ToastUtil.showToast(this,"删除失败");
    }

    @Override
    public Context getPresenterContext() {
        return this;
    }

    @Override
    public void onRefresh() {
        mPresenter.Refresh("");
    }

    @Override
    public void onLoadMore() {
        mPresenter.Load("");
    }
}
