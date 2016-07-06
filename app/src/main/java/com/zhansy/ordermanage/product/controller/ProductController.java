package com.zhansy.ordermanage.product.controller;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.loopj.android.http.RequestParams;
import com.zhansy.ordermanage.base.DisplayCallback;
import com.zhansy.ordermanage.base.mvp.model.ProductBean;
import com.zhansy.ordermanage.product.business.ProductBusiness;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Administrator on 2016/4/12.
 */
public class ProductController  {
    private Handler mHandler;
    private Context mContext;
    private ProductBusiness mBusiness = ProductBusiness.getInstance();
    public ProductController(Context mContext, Handler mHandler){
        this.mContext = mContext;
        this.mHandler = mHandler;
    }

    private DisplayCallback callback = new DisplayCallback() {

        @Override
        public void displayResult(int key, Object result) {
            if (mHandler != null) {
                Message msg = mHandler.obtainMessage();
                msg.what = key;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        }
    };

    /**
     * 产品 种类
     */
    public void getProductType(){
        mBusiness.getProductType(callback);
    }
    public void deleteProduct(String code){
        RequestParams params = new RequestParams();
        params.add("product_id",code);
//        params.add("action","product_id");
//        params.add("name",code);
        mBusiness.deleteProduct(mContext, params, callback);
    }

    public void updateProduct(ProductBean bean) {
        RequestParams params = new RequestParams();
        params.add("product_id",bean.getProduct_id()+"");
        params.add("product_name",bean.getProduct_name());
        params.add("type_code",bean.getType_code());
        params.add("generation",bean.getGeneration());
        params.add("remark",bean.getRemark());
        params.add("price",bean.getPrice()+"");
        params.add("stock",bean.getStock()+"");
        mBusiness.updateProduct(mContext, params, callback);
    }
    public void inputProduct(ProductBean bean) {
        RequestParams params = new RequestParams();
        params.add("product_name",bean.getProduct_name());
        params.add("type_code",bean.getType_code());
        params.add("generation",bean.getGeneration());
        params.add("remark",bean.getRemark());
        params.add("price",bean.getPrice()+"");
        params.add("stock",bean.getStock()+"");
        mBusiness.createProduct(mContext, params, callback);
    }
    public void inputProduct(ProductBean bean, File file) {
        RequestParams params = new RequestParams();
        params.add("product_name",bean.getProduct_name());
        params.add("type_code",bean.getType_code());
        params.add("generation",bean.getGeneration());
        params.add("remark",bean.getRemark());
        params.add("price",bean.getPrice()+"");
        params.add("stock",bean.getStock()+"");
        try {
            params.put("imgurl", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBusiness.createProduct(mContext, params, callback);
    }

    public void saveProductimgUrl(RequestParams params) {

    }
}
