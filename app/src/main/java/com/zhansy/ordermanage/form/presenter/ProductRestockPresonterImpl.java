package com.zhansy.ordermanage.form.presenter;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnBtnRightClickL;
import com.zhansy.ordermanage.base.dialog.MyDialog;
import com.zhansy.ordermanage.base.mvp.model.ProductBean;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.db.LitePalUtil;
import com.zhansy.ordermanage.order.business.OrderBusiness;
import com.zhansy.ordermanage.order.controller.OrderController;
import com.zhansy.ordermanage.form.adapter.ProductRestockAdapter;
import com.zhansy.ordermanage.form.view.ProductRestockView;
import com.zhansy.ordermanage.index.BuyProductActivity;
import com.zhansy.ordermanage.shopping.view.ShoppingFragmentView;
import com.zhansy.ordermanage.utils.ToastUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class ProductRestockPresonterImpl extends MVPBasePresenter implements ProductRestockIpresenter,ProductRestockAdapter.CallBack {

//    private ProgressDialog progressDialog;
    private MyHandler mHandler = new MyHandler(this);
    private OrderController controller;
    private ProductRestockAdapter adapter;
    private List<ProductBean> list=new ArrayList<>();

    public ProductRestockPresonterImpl() {
        controller=new OrderController(getContext(),mHandler);
        adapter = new ProductRestockAdapter(getContext(),this);
    }

    @Override
    public void getRestockData(String status) {
        final ProductRestockView iview = getActualUi();
        if(iview == null){
            return;
        }
        list = LitePalUtil.getProductBeanList();
        if (list == null || list.size() == 0){
            iview.getXListView().setVisibility(View.GONE);
            iview.getNoDataView().setVisibility(View.VISIBLE);
        }else {
            iview.getXListView().setVisibility(View.VISIBLE);
            iview.getNoDataView().setVisibility(View.GONE);
            adapter.setList(list);
            iview.setProductRestockAdapter(adapter);
        }
        iview.getXListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                MyDialog.showCustomDialog(getContext(), "确定移出订货单？", new OnBtnRightClickL() {
                    @Override
                    public void onBtnRightClick() {
                        adapter.remove(position-1);
                        MyDialog.myDialog.dismiss();
                        if (list == null || list.size() == 0) {
                            iview.getXListView().setVisibility(View.GONE);
                            iview.getNoDataView().setVisibility(View.VISIBLE);
                        }
                    }
                });
                return false;
            }
        });
    }

    @Override
    public void btnCheckBoxAll() {
        ProductRestockView iview = getActualUi();
        if(iview == null)return;
        if(iview.getCheckBox().isChecked()){
            adapter.WeatherCheckedAll(true);
        }else {
            adapter.WeatherCheckedAll(false);
        }
    }

    @Override
    public void createOrder() {
        if (adapter.getChoosedProduct().size() == 0){
            MyDialog.showDialog(getContext(), "请选择需要购买的货品");
            //显示先购物
            return;
        }
        showProgressDialog(getContext());
        controller.createOrder(adapter.getList());
    }

    @Override
    public void Refresh(String status) {
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ProductRestockView iview = getActualUi();
//                if(iview == null){
//                    return;
//                }
//                iview.getProductXListView().stopRefresh();
//                iview.getProductXListView().setRefreshTime(new Date());
//                controller.getCurrentUserProductList(page,"");
//            }
//        }, 500);
    }

    @Override
    public void Load(String status) {
    }

    @Override
    public void triggerSetCheck(boolean b) {
        ProductRestockView iview = getActualUi();
        if(iview == null)return;
        iview.getCheckBox().setChecked(b);
    }
    public static  class MyHandler extends Handler {
        WeakReference<ProductRestockIpresenter> presenter;
        public  MyHandler(ProductRestockIpresenter presenter){
            this.presenter = new WeakReference<ProductRestockIpresenter>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(presenter!=null&&presenter.get()!=null){
                ProductRestockPresonterImpl p= (ProductRestockPresonterImpl) presenter.get();
                p.handleMessage(msg);
            }
        }
    }
    public void handleMessage(Message msg) {
        if(msg.what== OrderBusiness.CREATE_ORDER_SUCCESS){
            //创建订单 成功
            final ProductRestockView iview = getActualUi();
            if(iview == null){
                return;
            }
            iview.getXListView().setVisibility(View.GONE);
            iview.getNoDataView().setVisibility(View.VISIBLE);
            MyDialog.showRightDialog(getContext(), "提交成功，继续进货", new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    BuyProductActivity.launch(getContext(), BuyProductActivity.class);
                    MyDialog.dimiss();
                    iview.onFinishActivity();
                }
            });
//            if (productBeanList.size() < 10) {
//                iview1.getProductAdapterXListView().setPullLoadEnable(false);
//            } else {
//                iview1.getProductAdapterXListView().setPullLoadEnable(true);
//            }
//            MyDialog.showDoubleDialog(getContext(),"提交成功", "继续进货", "确定", new OnBtnLeftClickL() {
//                @Override
//                public void onBtnLeftClick() {
//                    BuyProductActivity.launch(getContext(), BuyProductActivity.class);
//                    AppManager.getAppManager().finishActivity(FormActivity.class);
//                }
//            }, new OnBtnRightClickL() {
//                @Override
//                public void onBtnRightClick() {
//
//                }
//            });
            adapter.clear();
        }
        if(msg.what==OrderBusiness.CREATE_ORDER_ERROR){
            ProductRestockView iview = getActualUi();
            if(iview == null){
                return;
            }
            iview.getXListView().setVisibility(View.VISIBLE);
            iview.getNoDataView().setVisibility(View.GONE);
            ToastUtil.showToast(getContext(),"提交失败，请稍后再试");
        }
        hideProgressDialog();
    }
}
