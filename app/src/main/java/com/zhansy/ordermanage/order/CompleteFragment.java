package com.zhansy.ordermanage.order;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.dialog.listener.OnBtnRightClickL;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseFragment;
import com.zhansy.ordermanage.base.OMApplication;
import com.zhansy.ordermanage.base.dialog.MyDialog;
import com.zhansy.ordermanage.base.mvp.model.OrderBean;
import com.zhansy.ordermanage.base.widget.XExpandableListView;
import com.zhansy.ordermanage.order.presenter.OrderClassifyPresenterImpl;
import com.zhansy.ordermanage.order.view.OrderClassfyView;
import com.zhansy.ordermanage.order.adapter.ExpandableOrderAdapter;
import com.zhansy.ordermanage.utils.ToastUtil;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class CompleteFragment extends MVPBaseFragment<OrderClassifyPresenterImpl> implements OrderClassfyView  ,XExpandableListView.IXListViewListener{

    @InjectView(R.id.xelv_complete)
    XExpandableListView xelv_complete;
    @InjectView(R.id.rel_no_data)
    RelativeLayout rel_no_data;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.btn_clear_data)
    Button btn_clear_data;

    @OnClick({R.id.btn_back,R.id.btn_clear_data})
    void OnClick(View v){
        switch (v.getId()){
            case R.id.btn_clear_data:
                MyDialog.showCustomDialog(getActivity(), "确定清空已完成订单?", new OnBtnRightClickL() {
                    @Override
                    public void onBtnRightClick() {
                        mPresenter.deleteAllOrder();
                        MyDialog.dimiss();
                    }
                });
                break;
            case R.id.btn_back:
                getActivity().finish();
                break;
        }
    }

    public static String orderType = "lr_noPayment";
    private String tip = "是否取消此订单？";

//    public static String typeAdmin;
    public static CompleteFragment getInstance(String s){
        orderType = s;
        return  new CompleteFragment();
    }
    @Override
    public int getLayoutResource() {
        return R.layout.fragment_complete;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mPresenter.setTypeAdmin(typeAdmin);
        if (orderType.equals("lr_noPayment")){
            if (OMApplication.getInstance().getCurrentUser().isManage()){
                tip = "是否删除此订单？";
                tv_title.setText("待审核订单");
            }else {
                tip = "是否取消此订单？";
                tv_title.setText("可取消订单");
            }
            mPresenter.getOrderClassfyList("order_type",OMApplication.getInstance().getString(R.string.tv_nopayment));
        }else {
            mPresenter.getOrderClassfyList("order_type",OMApplication.getInstance().getString(R.string.tv_complete));
            tip = "是否删除此订单？";
            tv_title.setText("已完成订单");

        }
        xelv_complete.setPullRefreshEnable(true);
        xelv_complete.setPullLoadEnable(true);
        xelv_complete.setXListViewListener(this);
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
    public XExpandableListView getXExpandableListView() {
        return xelv_complete;
    }

    @Override
    public void setAdapter(final ExpandableOrderAdapter adapter) {
        xelv_complete.setAdapter(adapter);
        xelv_complete.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
//                ToastUtil.showToast(getActivity(),"长按"+position);
                MyDialog.showCustomDialog(getActivity(), tip, new OnBtnRightClickL() {
                    @Override
                    public void onBtnRightClick() {
                        OrderBean orderBean = adapter.getmList().get(position-1);
                        mPresenter.orderDelete(orderBean.getOrder_code());
                        MyDialog.dimiss();
//                        adapter.notifyDataSetChanged();
                    }
                });
                return false;
            }
        });
        if (orderType.equals("lr_complete")){//批量删除已完成订单
            btn_clear_data.setVisibility(View.VISIBLE);
        }else {
            btn_clear_data.setVisibility(View.GONE);
        }
    }

    @Override
    public RelativeLayout getNoDataView() {
        return rel_no_data;
    }

    private void refresh(){
        if (orderType.equals("lr_noPayment")){
            mPresenter.getOrderClassfyList("order_type",OMApplication.getInstance().getString(R.string.tv_nopayment));
        }else {
            mPresenter.getOrderClassfyList("order_type",OMApplication.getInstance().getString(R.string.tv_complete));
        }
    }
    @Override
    public void orderDeleteSuccess() {
        ToastUtil.showToast(getActivity(),"操作成功！");
        refresh();
    }

    @Override
    public void orderDeleteFailed() {
        ToastUtil.showToast(getActivity(),"订单操作失败，请稍后再试！");
    }

    @Override
    public Context getPresenterContext() {
        return getActivity();
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
