package com.zhansy.ordermanage.order;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.base.OMApplication;
import com.zhansy.ordermanage.base.widget.XExpandableListView;
import com.zhansy.ordermanage.order.presenter.OrderClassifyPresenterImpl;
import com.zhansy.ordermanage.order.view.OrderClassfyView;
import com.zhansy.ordermanage.order.adapter.ExpandableOrderAdapter;
import com.zhansy.ordermanage.utils.ToastUtil;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 *
 */
public class OrderSearchActivity extends MVPBaseActivity<OrderClassifyPresenterImpl> implements OrderClassfyView,XExpandableListView.IXListViewListener{
    @InjectView(R.id.et_key)
    EditText et_key;
    @InjectView(R.id.spinner_order_type)
    Spinner spinner_order_type;
    @InjectView(R.id.xelv_order_classify)
    XExpandableListView xelv_order_classify;
    @InjectView(R.id.rel_no_data)
    RelativeLayout rel_no_data;

    @OnClick({R.id.btn_back,R.id.biv_sousuo})
    void OnClick(View v){
        switch (v.getId()){
            case R.id.biv_sousuo:
                String key_word = et_key.getText().toString().trim();
                if (!TextUtils.isEmpty(key_word)){
                    //进行搜索请求
//                    ProductAdapter adapter = new ProductAdapter(this);
//                    adapter.setList(null);
//                    lv_search.setAdapter(adapter);
                    mPresenter.getOrderClassfyList(getSearchKey(),key_word);
                }else {
                    ToastUtil.showToast(this,"请输入关键词进行搜索");
                    return;
                }
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }
    private int positionSelect = 0;
    private String  key = "order_code";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        xelv_order_classify.setPullRefreshEnable(true);
        xelv_order_classify.setPullLoadEnable(true);
        xelv_order_classify.setXListViewListener(this);
        initSpinner();
        spinner_order_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positionSelect = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initSpinner() {
        if (OMApplication.getInstance().getCurrentUser().isManage()){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,OMApplication.getInstance().getResources().getStringArray(R.array.search_order_admin));
            //设置下拉列表的风格
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_order_type.setAdapter(adapter);
        }
    }

    private String getSearchKey(){
        switch (positionSelect){
            case 0:
                key = "order_code";
                break;
            case 1:
                key = "order_type";
                break;
            case 2:
                key = "input_user";
                break;
            case 3:
                key = "username";
                break;
            default:
                key = "order_code";
                break;
        }
        return key;
    }
    @Override
    public int getLayoutResource() {
        return R.layout.activity_order_search;
    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected OrderClassifyPresenterImpl createPresenter() {
        return new OrderClassifyPresenterImpl();
    }

    @Override
    public void onRefresh() {
        mPresenter.Refresh("");
    }

    @Override
    public void onLoadMore() {
        mPresenter.Load("");
    }

    @Override
    public XExpandableListView getXExpandableListView() {
        return xelv_order_classify;
    }

    @Override
    public void setAdapter(ExpandableOrderAdapter adapter) {
        xelv_order_classify.setAdapter(adapter);
    }

    @Override
    public RelativeLayout getNoDataView() {
        return rel_no_data;
    }

    @Override
    public void orderDeleteSuccess() {

    }

    @Override
    public void orderDeleteFailed() {

    }

    @Override
    public Context getPresenterContext() {
        return this;
    }
}
