package com.zhansy.ordermanage.feedback.business;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.DisplayCallback;
import com.zhansy.ordermanage.base.mvp.model.BaseBean;
import com.zhansy.ordermanage.base.mvp.model.FeedBackBean;
import com.zhansy.ordermanage.utils.CommonUtil;
import com.zhansy.ordermanage.utils.HttpUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class FeedBackBusiness {
    public static final int STATE_PRODUCT_FEEDBACK_DATA_SUCCESS = 0x00021;//请求反馈列表成功
    public static final int STATE_PRODUCT_FEEDBACK_DATA_ERROR = 0x00031; //请求反馈列表失败
    public static final int DELETE_PRODUCT_FEEDBACK_DATA_SUCCESS = 0x00041;//请求反馈列表成功
    public static final int DELETE_PRODUCT_FEEDBACK_DATA_ERROR = 0x00051; //请求反馈列表失败
    public static final int CREATE_PRODUCT_FEEDBACK_DATA_SUCCESS = 0x00061;//请求反馈列表成功
    public static final int CREATE_PRODUCT_FEEDBACK_DATA_ERROR = 0x00071; //请求反馈列表失败
    public static FeedBackBusiness getInstance() {
        FeedBackBusiness instance = new FeedBackBusiness();
        return instance;
    }

    public void getdata(RequestParams params, final DisplayCallback callback) {
        String url = CommonUtil.appendRequesturl(R.string.api_product_feed_back_search);
        HttpUtil.get(url, params,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonUtil.showLog("请求失败："+errorResponse);
                callback.displayResult(STATE_PRODUCT_FEEDBACK_DATA_ERROR, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                CommonUtil.showLog("请求成功："+response);
                BaseBean baseBean = new BaseBean(response);
                if (baseBean.isSuccess()) {
                    try {
                        List<FeedBackBean> list = new ArrayList<>();
                        JSONArray array = baseBean.getList();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            FeedBackBean productBean = new FeedBackBean(object);
                            list.add(productBean);
                        }
                        callback.displayResult(STATE_PRODUCT_FEEDBACK_DATA_SUCCESS, list);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callback.displayResult(STATE_PRODUCT_FEEDBACK_DATA_ERROR, baseBean.getMsg());
                    }
                }else {
                    callback.displayResult(STATE_PRODUCT_FEEDBACK_DATA_ERROR, baseBean.getMsg());
                }
            }
        });
    }
    public void deleteFeedback(RequestParams params, final DisplayCallback callback) {
        String url = CommonUtil.appendRequesturl(R.string.api_product_feed_back_delete);
        HttpUtil.get(url, params,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonUtil.showLog("请求失败："+errorResponse);
                callback.displayResult(DELETE_PRODUCT_FEEDBACK_DATA_ERROR, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                CommonUtil.showLog("请求成功："+response);
                BaseBean baseBean = new BaseBean(response);
                if (baseBean.isSuccess()) {
                    callback.displayResult(DELETE_PRODUCT_FEEDBACK_DATA_SUCCESS, baseBean.getMsg());
                }else {
                    callback.displayResult(DELETE_PRODUCT_FEEDBACK_DATA_ERROR, baseBean.getMsg());
                }
            }
        });
    }
    public void createFeedback(RequestParams params, final DisplayCallback callback) {
        String url = CommonUtil.appendRequesturl(R.string.api_product_feed_back_create);
        HttpUtil.get(url, params,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonUtil.showLog("请求失败："+errorResponse);
                callback.displayResult(CREATE_PRODUCT_FEEDBACK_DATA_ERROR, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                CommonUtil.showLog("请求成功："+response);
                BaseBean baseBean = new BaseBean(response);
                if (baseBean.isSuccess()) {
                    callback.displayResult(CREATE_PRODUCT_FEEDBACK_DATA_SUCCESS, baseBean.getMsg());
                }else {
                    callback.displayResult(CREATE_PRODUCT_FEEDBACK_DATA_ERROR, baseBean.getMsg());
                }
            }
        });
    }
}
