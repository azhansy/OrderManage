package com.zhansy.ordermanage.index;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.base.widget.BaseImageView;
import com.zhansy.ordermanage.base.widget.XListView;
import com.zhansy.ordermanage.index.adapter.ProductAdapter;
import com.zhansy.ordermanage.index.presenter.SearchActivityPresenterImpl;
import com.zhansy.ordermanage.index.view.SearchProductView;
import com.zhansy.ordermanage.utils.ToastUtil;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class SearchActivity extends MVPBaseActivity<SearchActivityPresenterImpl> implements SearchProductView,XListView.IXListViewListener{
    @InjectView(R.id.biv_sousuo)
    BaseImageView biv_sousuo;
    @InjectView(R.id.rel_no_data)
    RelativeLayout rel_no_data;
    @InjectView(R.id.xlv_search)
    XListView xlv_search;
    @InjectView(R.id.et_search_content)
    EditText et_search_content;

    @OnClick({R.id.btn_back,R.id.biv_sousuo})
    void OnClick(View v){
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.biv_sousuo:
                String key_word = et_search_content.getText().toString().trim();
                if (!TextUtils.isEmpty(key_word)){
                    //进行搜索请求
//                    ProductAdapter adapter = new ProductAdapter(this);
//                    adapter.setList(null);
//                    lv_search.setAdapter(adapter);
                    mPresenter.triggerSearch("product_name",key_word);
                }else {
                    ToastUtil.showToast(this,"请输入产品名称进行搜索");
                    return;
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        xlv_search.setPullRefreshEnable(true);
        xlv_search.setPullLoadEnable(true);
        xlv_search.setXListViewListener(this);
        mPresenter.getProduct10();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_search;
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
    public void setSearchProductAdapter(ProductAdapter adapter) {
        xlv_search.setAdapter(adapter);
    }

    @Override
    public void searchFail() {
        ToastUtil.showToast(this,"暂无相关数据，请重新查询");
//        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromInputMethod(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void searchSuccess() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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
