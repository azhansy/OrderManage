package com.zhansy.ordermanage.product.business;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.DisplayCallback;
import com.zhansy.ordermanage.base.mvp.model.BaseBean;
import com.zhansy.ordermanage.base.mvp.model.ProductOptionsBean;
import com.zhansy.ordermanage.utils.CommonUtil;
import com.zhansy.ordermanage.utils.HttpUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/12.
 */
public class ProductBusiness {
    public static final int STATE_PRODUCT_TYPE_SUCCESS = 0x1001; //获取产品类型成功
    public static final int STATE_PRODUCT_TYPE_ERROR = 0x1002; //获取产品类型失败
    public static final int STATE_PRODUCT_CREATE_SUCCESS = 0x1013; //商品录入
    public static final int STATE_PRODUCT_CREATE_ERROR = 0x1014;
    public static final int STATE_PRODUCT_UPDATE_SUCCESS = 0x1023;
    public static final int STATE_PRODUCT_UPDATE_ERROR = 0x1024;
    public static final int STATE_PRODUCT_DELETE_SUCCESS = 0x1033;
    public static final int STATE_PRODUCT_DELETE_ERROR = 0x1034;

    public static ProductBusiness getInstance() {
        ProductBusiness instance = new ProductBusiness();
        return instance;
    }

    /** 产品 分类
     * @param callback
     */
    public void getProductType(final DisplayCallback callback){
        final String url = CommonUtil.appendRequesturl(R.string.api_product_type);
        HttpUtil.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonUtil.showLog("请求失败："+errorResponse);
//                List<ProductOptionsBean> list = new ArrayList<>();
//                list = LitePalUtil.getAllProductOptionsBean();
                callback.displayResult(STATE_PRODUCT_TYPE_ERROR, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                CommonUtil.showLog("请求成功："+response);
                BaseBean baseBean = new BaseBean(response);
                if (baseBean.isSuccess()) {
                    try {
                        List<ProductOptionsBean> list = new ArrayList<>();
                        JSONArray array = baseBean.getList();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            ProductOptionsBean bean = new ProductOptionsBean(object);
                            list.add(bean);
                        }
                        callback.displayResult(STATE_PRODUCT_TYPE_SUCCESS, list);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callback.displayResult(STATE_PRODUCT_TYPE_ERROR, "数据格式出现错误");
                    }
                }else {
                    callback.displayResult(STATE_PRODUCT_TYPE_ERROR, baseBean.getMsg());
                }
            }
        });
    }

    public void createProduct(Context mContext, RequestParams params, final DisplayCallback callback) {
        String url = CommonUtil.appendRequesturl(R.string.api_create_product);
        HttpUtil.get(url, params,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonUtil.showLog("createProduct 请求失败："+errorResponse);
//                callback.displayResult(CREATE_ORDER_ERROR, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                CommonUtil.showLog("createProduct请求成功："+response);
                BaseBean baseBean = new BaseBean(response);
                if (baseBean.isSuccess()) {
                    callback.displayResult(STATE_PRODUCT_CREATE_SUCCESS, baseBean.getMsg());
                }else {
                    callback.displayResult(STATE_PRODUCT_CREATE_SUCCESS, baseBean.getMsg());
                }
            }
        });
    }
    public void updateProduct(Context mContext, RequestParams params, final DisplayCallback callback) {
        String url = CommonUtil.appendRequesturl(R.string.api_update_product);
        HttpUtil.get(url, params,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonUtil.showLog("createProduct 请求失败："+errorResponse);
                callback.displayResult(STATE_PRODUCT_UPDATE_ERROR, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                CommonUtil.showLog("createProduct请求成功："+response);
                BaseBean baseBean = new BaseBean(response);
                if (baseBean.isSuccess()) {
                    callback.displayResult(STATE_PRODUCT_UPDATE_SUCCESS, baseBean.getMsg());
                }else {
                    callback.displayResult(STATE_PRODUCT_UPDATE_ERROR, baseBean.getMsg());
                }
            }
        });
    }
    public void deleteProduct(Context mContext, RequestParams params, final DisplayCallback callback) {
        String url = CommonUtil.appendRequesturl(R.string.api_delete_product);
        HttpUtil.get(url, params,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonUtil.showLog("createProduct 请求失败："+errorResponse);
                callback.displayResult(STATE_PRODUCT_DELETE_ERROR, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                CommonUtil.showLog("createProduct请求成功："+response);
                BaseBean baseBean = new BaseBean(response);
                if (baseBean.isSuccess()) {
                    callback.displayResult(STATE_PRODUCT_DELETE_SUCCESS, baseBean.getMsg());
                }else {
                    callback.displayResult(STATE_PRODUCT_DELETE_ERROR, baseBean.getMsg());
                }
            }
        });
    }
}
