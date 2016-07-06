package com.zhansy.ordermanage.form;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.flyco.dialog.listener.OnBtnRightClickL;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseFragment;
import com.zhansy.ordermanage.base.dialog.MyDialog;
import com.zhansy.ordermanage.base.widget.XListView;
import com.zhansy.ordermanage.form.adapter.ProductRestockAdapter;
import com.zhansy.ordermanage.form.presenter.ProductRestockPresonterImpl;
import com.zhansy.ordermanage.form.view.ProductRestockView;

import java.util.Date;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class ProductShoppingFragment extends MVPBaseFragment<ProductRestockPresonterImpl> implements ProductRestockView , XListView.IXListViewListener{

    @InjectView(R.id.xlv_restock)
    XListView xlv_restock;
    @InjectView(R.id.rel_no_data)
    RelativeLayout rel_no_data;
    @InjectView(R.id.cball_choose)
    CheckBox cball_choose;

    @OnClick({R.id.cball_choose,R.id.btn_back,R.id.btn_pay})
    void OnClick(View v){
        switch (v.getId()){
            case R.id.btn_back:
                getActivity().finish();
                break;
            case R.id.cball_choose:
                mPresenter.btnCheckBoxAll();
                break;
            case R.id.btn_pay:
                MyDialog.showCustomDialog(getActivity(), "确定提交该订单？", new OnBtnRightClickL() {
                    @Override
                    public void onBtnRightClick() {
                        mPresenter.createOrder();
                        MyDialog.dimiss();
                    }
                });
                break;
        }
    }
    @Override
    public int getLayoutResource() {
        return R.layout.fragment_order;
    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected ProductRestockPresonterImpl createPresenter() {
        return new ProductRestockPresonterImpl();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.getRestockData("");
        xlv_restock.setPullRefreshEnable(true);
        xlv_restock.setPullLoadEnable(false);
        xlv_restock.setXListViewListener(this);
    }

    public static ProductShoppingFragment newInstance() {
        ProductShoppingFragment fragment = new ProductShoppingFragment();
        return fragment;
    }

    @Override
    public void setProductRestockAdapter(ProductRestockAdapter adapter) {
        xlv_restock.setAdapter(adapter);
    }

    @Override
    public XListView getXListView() {
        return xlv_restock;
    }

    @Override
    public RelativeLayout getNoDataView() {
        return rel_no_data;
    }

    @Override
    public CheckBox getCheckBox() {
        return cball_choose;
    }

    @Override
    public void setTitleCheckBox(boolean b) {
        cball_choose.setChecked(b);
    }

    @Override
    public void onFinishActivity() {
        getActivity().finish();
    }

    @Override
    public Context getPresenterContext() {
        return getActivity();
    }

    @Override
    public void onRefresh() {
//        mPresenter.Refresh("");
        xlv_restock.postDelayed(new Runnable() {
            @Override
            public void run() {
                xlv_restock.stopRefresh();
                xlv_restock.setRefreshTime(new Date());
            }
        }, 500);
    }

    @Override
    public void onLoadMore() {
//        mPresenter.Load("");
    }


}
