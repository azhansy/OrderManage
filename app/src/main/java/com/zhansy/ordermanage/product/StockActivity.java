package com.zhansy.ordermanage.product;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.base.widget.BaseImageView;
import com.zhansy.ordermanage.base.widget.XListView;
import com.zhansy.ordermanage.index.adapter.StockAdapter;
import com.zhansy.ordermanage.index.presenter.SearchActivityPresenterImpl;
import com.zhansy.ordermanage.index.view.StockView;
import com.zhansy.ordermanage.utils.ToastUtil;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 *
 */
public class StockActivity extends MVPBaseActivity<SearchActivityPresenterImpl> implements StockView,XListView.IXListViewListener{
    @InjectView(R.id.biv_sousuo)
    BaseImageView biv_sousuo;
    @InjectView(R.id.rel_no_data)
    RelativeLayout rel_no_data;
    @InjectView(R.id.xlv_search)
    XListView xlv_search;
    @InjectView(R.id.et_key)
    EditText et_key;
    @InjectView(R.id.spinner_product_type)
    Spinner spinner_product_type;


    @OnClick({R.id.btn_back,R.id.biv_sousuo})
    void OnClick(View v){
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.biv_sousuo:
                String key_word = et_key.getText().toString().trim();
                if (!TextUtils.isEmpty(key_word)){
                    //进行搜索请求
//                    ProductAdapter adapter = new ProductAdapter(this);
//                    adapter.setList(null);
//                    lv_search.setAdapter(adapter);
                    mPresenter.triggerSearch(getSearchKey(),key_word);
                }else {
                    ToastUtil.showToast(this,"请输入关键词进行搜索");
                    return;
                }
                break;
        }
    }

    private int positionSelect = 0;
    private String  key = "product_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        xlv_search.setPullRefreshEnable(true);
        xlv_search.setPullLoadEnable(true);
        xlv_search.setXListViewListener(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        spinner_product_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positionSelect = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.triggerSearch("","");
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
                key = "generation";
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
        return R.layout.activity_restock;
    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected SearchActivityPresenterImpl createPresenter() {
        return new SearchActivityPresenterImpl();
    }

    @Override
    public void setStockAdapter(StockAdapter adapter) {
        xlv_search.setAdapter(adapter);
    }

    @Override
    public void searchFail() {
        ToastUtil.showToast(this,"暂无相关数据，请重新查询");
//        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromInputMethod(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public XListView getProductAdapterXListView() {
        return xlv_search;
    }

    @Override
    public RelativeLayout getNoDataView() {
        return rel_no_data;
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
