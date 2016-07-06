package com.zhansy.ordermanage.taste;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.taste.adapter.TasteAdapter;
import com.zhansy.ordermanage.taste.presenter.TasteReturnPresenterImpl;
import com.zhansy.ordermanage.taste.view.TasteView;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 * 查看用户建议
 */
public class TasteActivity extends MVPBaseActivity<TasteReturnPresenterImpl> implements TasteView {
    @InjectView(R.id.lv_taste)
    ListView lv_taste;
    @InjectView(R.id.rel_no_data)
    RelativeLayout rel_no_data;
    @OnClick(R.id.btn_back)
    void OnClick(){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.getData();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_taste;
    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected TasteReturnPresenterImpl createPresenter() {
        return new TasteReturnPresenterImpl();
    }

    @Override
    public Context getPresenterContext() {
        return this;
    }

    @Override
    public ListView getListView() {
        return lv_taste;
    }

    @Override
    public void setAdapter(TasteAdapter adapter) {
        lv_taste.setAdapter(adapter);
    }

    @Override
    public RelativeLayout getNoDataView() {
        return rel_no_data;
    }
}
