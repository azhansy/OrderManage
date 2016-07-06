package com.zhansy.ordermanage.index.presenter;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import com.zhansy.ordermanage.order.business.OrderBusiness;
import com.zhansy.ordermanage.base.mvp.model.ProductBean;
import com.zhansy.ordermanage.base.mvp.model.ProductOptionsBean;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.product.ProductDetailActivity;
import com.zhansy.ordermanage.index.adapter.ProductAdapter;
import com.zhansy.ordermanage.index.adapter.ProductOptionAdapter;
import com.zhansy.ordermanage.index.view.IndexFragmentView;
import com.zhansy.ordermanage.order.controller.OrderController;
import com.zhansy.ordermanage.utils.ToastUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class IndexFragmentPresenterImpl extends MVPBasePresenter implements  IndexFragmentPresenter,ProductOptionAdapter.GetPosion {
    /**
     * 判断是否是点击显示商品类别
     */
    private boolean isClickshowcate = false;
    private int catePosion,chileCatePosion;
    private int page = 0;

    private ProductAdapter productAdapter;
    private OrderController controller;
    private Handler mHandler = new MyHandler(this);
    private List<ProductBean> productBeanList = new ArrayList<ProductBean>();

    private List<ProductOptionsBean> cateList;
    private ProductOptionAdapter firstAdapter,secondAdapter;
    private String productType = "";

    public  IndexFragmentPresenterImpl(){
        firstAdapter = new ProductOptionAdapter(getContext());
        secondAdapter = new ProductOptionAdapter(getContext());
        productAdapter = new ProductAdapter(getContext());
        controller = new OrderController(getContext(), mHandler);
        firstAdapter.setGet(this);
        secondAdapter.setGet(this);
    }

    @Override
    public void Refresh(String status) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 0;
                IndexFragmentView iview = getActualUi();
                if(iview == null){
                    return;
                }
                iview.getProductXListView().stopRefresh();
                iview.getProductXListView().setRefreshTime(new Date());
                controller.getProductList(page,productType);
            }
        }, 500);
    }

    @Override
    public void Load(String status) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page=page+1;
                IndexFragmentView iview = getActualUi();
                if(iview == null){
                    return;
                }
                iview.getProductXListView().stopLoadMore();
                controller.getProductList(page,productType);
            }
        }, 500);
    }

    @Override
    public void getProductType() {
        controller.getProductType();
    }

    @Override
    public void getData() {
        page = 0;
        productType = "";
        controller.getProductList(page,productType);
        showProgressDialog(getContext());
    }

    @Override
    public void getCouse() {
        showProgressDialog(getContext());
        page = 0;
        controller.getProductList(page,productType);
    }

    @Override
    public void setCatePosition(int position) {
        catePosion = position;
        firstAdapter.notifyDataSetChanged();
        chileCatePosion = 0;
        secondAdapter.setList(cateList.get(position).getChild_list());
        secondAdapter.notifyDataSetChanged();
    }

    @Override
    public void setChileCatePosition(int position) {
        chileCatePosion = position;
        secondAdapter.notifyDataSetChanged();
        IndexFragmentView iview = getActualUi();
        if(iview == null){
            return;
        }
        iview.setCate(((ProductOptionsBean) secondAdapter.getItem(position)).getType_name());
        productType = ((ProductOptionsBean)secondAdapter.getItem(position)).getType_code();
        getCouse();
    }

    @Override
    public void getallCourse() {
        page = 0;
        IndexFragmentView iview = getActualUi();
        if(iview == null){
            return;
        }
        setCatePosition(0);
    }

    @Override
    public void showCates() {
        isClickshowcate = true;
        IndexFragmentView iview = getActualUi();
        if(iview == null){
            return;
        }
        iview.setCateAdapter(firstAdapter);
        iview.setChildCateAdapter(secondAdapter);
        iview.showCourseDialog();
    }

    @Override
    public int getLine() {
        return catePosion;
    }

    @Override
    public int getChileCateLine() {
        return chileCatePosion;
    }


    public static class MyHandler extends Handler {
        private WeakReference<IndexFragmentPresenter> presenter;

        public MyHandler(IndexFragmentPresenter presenter) {
            this.presenter = new WeakReference<IndexFragmentPresenter>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (presenter != null && presenter.get() != null) {
                IndexFragmentPresenterImpl p = (IndexFragmentPresenterImpl) presenter.get();
                p.handleMessage(msg);
            }
        }
    }

    private void handleMessage(Message msg) {
        if (msg.what == OrderBusiness.STATE_PRODUCT_LIST_DATA_SUCCESS) {
            IndexFragmentView iview = getActualUi();
            if (iview == null) return;
            productBeanList = (List<ProductBean>) msg.obj;
            iview.getNoDataView().setVisibility(View.GONE);
            iview.getProductXListView().setVisibility(View.VISIBLE);
            if(page==0) {
                productAdapter.setList(productBeanList);
                iview.setProductAdapter(productAdapter);
            }
            else {
                productAdapter.addList(productBeanList);
            }
            if(productBeanList.size()<10){
                iview.getProductXListView().setPullLoadEnable(false);
            }else{
                iview.getProductXListView().setPullLoadEnable(true);
            }
            productAdapter.notifyDataSetChanged();

            iview.getProductXListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ProductBean bean = productAdapter.getList().get(position-1);
                    Intent intent = new Intent(getContext(), ProductDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ProductBean",bean);
                    intent.putExtras(bundle);
                    getContext().startActivity(intent);
                }
            });
//            getProductType();//请求成功后，获取产品的类型
        }
        if (msg.what == OrderBusiness.STATE_PRODUCT_LIST_DATA_ERROR){
            IndexFragmentView iview = getActualUi();
            if (iview == null) return;
            iview.getProductXListView().setVisibility(View.GONE);
            iview.getNoDataView().setVisibility(View.VISIBLE);
            iview.getNoDataView().setVisibility(View.VISIBLE);
            iview.getProductXListView().setVisibility(View.GONE);
        }
        if (msg.what == OrderBusiness.STATE_PRODUCT_TYPE_SUCCESS){
            IndexFragmentView iview = getActualUi();
            if (iview == null) return;
//            ToastUtil.showToast(getContext(),"商品分类查询成功");
            cateList = (List<ProductOptionsBean>) msg.obj;
            firstAdapter.setList(cateList);
            secondAdapter.setList(cateList.get(0).getChild_list());
            iview.setCateAdapter(firstAdapter);
            iview.setChildCateAdapter(secondAdapter);
            if(isClickshowcate){
                showCates();
            }
        }
        if (msg.what == OrderBusiness.STATE_PRODUCT_TYPE_ERROR) {
            IndexFragmentView iview = getActualUi();
            if (iview == null) return;
            ToastUtil.showToast(getContext(),"服务器异常，获取类型失败");
        }
        hideProgressDialog();
    }
}
