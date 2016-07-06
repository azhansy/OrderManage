package com.zhansy.ordermanage.order.controller;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.loopj.android.http.RequestParams;
import com.zhansy.ordermanage.base.DisplayCallback;
import com.zhansy.ordermanage.base.OMApplication;
import com.zhansy.ordermanage.order.business.OrderBusiness;
import com.zhansy.ordermanage.base.mvp.model.ProductBean;

import java.util.List;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class OrderController {
    private Handler mHandler;
    private Context mContext;
    private OrderBusiness mBusiness = OrderBusiness.getInstance();
    public OrderController(Context mContext, Handler mHandler){
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
     * 登录
     * @param username
     * @param password
     */
    public void goToLogin(String username, String password){
        RequestParams params=new RequestParams();
        params.put("username",username);
        params.put("password",password);
        mBusiness.goToLogin(mContext,params,callback);
    }
    /**
     */
    public void updateProductById(int id){
//        RequestParams params=new RequestParams();
//        mBusiness.updateProductById(mContext,params,callback);
    }

    /**
     * 产品 种类
     */
    public void getProductType(){
        mBusiness.getProductType(callback);
    }

    /**
     * @param key_word 根据关键词搜索产品
     *            action=product_name&name=a
     */
    public void getKeySearch(int page,String key,String key_word){
        RequestParams params = new RequestParams();
        params.put("page",page);
        params.put("action",key);
//        params.put("action","product_name");
        params.put("name",key_word);
        mBusiness.getProductList(mContext,params,callback);
    }

    public void getProduct10(){
        mBusiness.getProduct10List(callback);
    }

    /**
     * 查询订单列表
     * @param page
     * @param status
     */
//    public void getOrderList(int page,String status){
//        RequestParams params=new RequestParams();
//        params.add("page",String.valueOf(page));
//        params.add("order_type",status);
//        mBusiness.getOrderList(mContext,params,callback);
//    }
//    /**
//     * 查询本地订单列表
//     * @param page
//     * @param status
//     */
//    public void getOrderLocalList(int page,String status){
//        mBusiness.getOrderLocalList(page,status,callback);
//    }
    /**
     * 查询产品列表
     * @param page action=product_name&name=a
     * @param productType
     */
    public void getProductList(int page,String productType){
        RequestParams params=new RequestParams();
        params.add("page",String.valueOf(page));
        params.add("name",productType); //关键词
        params.add("action","type_code");//根据 产品类型 查询
        mBusiness.getProductList(mContext,params,callback);
    }
    /**
     * 查询 本地产品列表
     * @param page
     * @param status
     */
    public void getCurrentUserProductList(int page,String status){
//        RequestParams params=new RequestParams();
//        params.add("page",String.valueOf(page));
//        params.add("status",status);
        mBusiness.getCurrentProductList(mContext,page,callback);
    }
    /**
     * 查询状态列表
     * @param page
     * @param key
     */
    public void getOrderClassifyList(int page,String key,String key_word){
        RequestParams params = new RequestParams();
        params.add("page",String.valueOf(page));
        params.add("action",key);
        params.add("name",key_word);
//        params.add("order_type",key);
        if (!OMApplication.getInstance().getCurrentUser().isManage()){//不是管理员就只查自己的订单
            params.add("user_code",OMApplication.getInstance().getCurrentUser().getUser_code());
        }
        mBusiness.getOrderList(mContext,params,callback);
    }

    /**
     * 检查更新
     */
    public void getAppUpdateVersion() {
        mBusiness.getAppUpdateVersion(mContext,callback);
    }

    /**
     * 下载pdf
     */
    public void upLoadPDF(String code){
        RequestParams params = new RequestParams();
        params.put("order_code",code);
        String fileName = code+".pdf";
        try {
            mBusiness.downloadFile(mContext,fileName,params,callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 下载Excel
     */
    public void upLoadExcel(String code){
        RequestParams params = new RequestParams();
        params.put("order_code",code);
        String fileName = code+".xls";
        try {
            mBusiness.downloadExcel(mContext,fileName,params,callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 下载app
     */
    public void upLoadApp(){
        try {
            mBusiness.downloadApp(mContext,callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteOrder(String code) {
        RequestParams params = new RequestParams();
        params.put("order_code",code);
        mBusiness.deleteOrder(mContext, params, callback);
    }

    public void updateOrder(String code,String status,String remark){
        RequestParams params = new RequestParams();
        params.put("order_code",code);
        params.put("order_type",status);
        params.put("describtion",remark);
        params.put("input_user", OMApplication.getInstance().getCurrentUser().getUsername());
        mBusiness.updateOrder(mContext,params,callback);
    }
    public void createOrder(List<ProductBean> list){
        RequestParams params = new RequestParams();
//        params.put("order_type",orderBean.getOrder_type());
//        params.put("remark",orderBean.getRemark());
        StringBuffer product_code = new StringBuffer();
        StringBuffer product_num = new StringBuffer();
        for (int i=0; i<list.size();i++){
            product_code = product_code.append(list.get(i).getProduct_code()+",");
            product_num = product_num.append(list.get(i).getOut_number()+",");
        }
        product_code = product_code.deleteCharAt(product_code.length() - 1);
        product_num = product_num.deleteCharAt(product_num.length() - 1);
        params.add("product_code",product_code.toString());
        params.add("product_num",product_num.toString());
        params.add("input_user", OMApplication.getInstance().getCurrentUser().getUsername());
        params.add("postcode",OMApplication.getInstance().getCurrentUser().getPostcode());
        params.add("remark",OMApplication.getInstance().getCurrentUser().getUsername());//备注
        params.add("address",OMApplication.getInstance().getCurrentUser().getUser_address());
        mBusiness.createOrder(mContext,params,callback);
    }
}
