package com.zhansy.ordermanage.taste.business;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.DisplayCallback;
import com.zhansy.ordermanage.base.mvp.model.BaseBean;
import com.zhansy.ordermanage.base.mvp.model.TasteReturnBean;
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
public class TasteReturnBusiness {
    public static final int CREATE_USER_TASTE_DATA_SUCCESS = 0x000011;
    public static final int CREATE_USER_TASTE_DATA_ERROR = 0x000012;
    public static final int DELETE_GET_TASTE_DATA_SUCCESS = 0x000013;
    public static final int DELETE_GET_TASTE_DATA_ERROR = 0x000014;
    public static final int SEARCH_GET_TASTE_DATA_SUCCESS = 0x000015;
    public static final int SERACH_GET_TASTE_DATA_ERROR = 0x000016;
    public static TasteReturnBusiness instance;
    public static TasteReturnBusiness getInstance() {
        if (instance == null)
            instance= new TasteReturnBusiness();
        return instance;
    }

    public void createTaste(RequestParams params, final DisplayCallback callback) {
        String url = CommonUtil.appendRequesturl(R.string.api_taste_create);
        HttpUtil.get(url, params,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonUtil.showLog("请求失败："+errorResponse);
                callback.displayResult(CREATE_USER_TASTE_DATA_ERROR, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                CommonUtil.showLog("请求成功："+response);
                BaseBean baseBean = new BaseBean(response);
                if (baseBean.isSuccess()){
                    callback.displayResult(CREATE_USER_TASTE_DATA_SUCCESS, baseBean.getMsg());
                }else {
                    callback.displayResult(CREATE_USER_TASTE_DATA_ERROR, baseBean.getMsg());
                }
            }
        });
    }
    public void searchTaste(RequestParams params, final DisplayCallback callback) {
        String url = CommonUtil.appendRequesturl(R.string.api_taste_search);
        HttpUtil.get(url, params,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonUtil.showLog("请求失败："+errorResponse);
                callback.displayResult(SERACH_GET_TASTE_DATA_ERROR, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                CommonUtil.showLog("请求成功："+response);
                BaseBean baseBean = new BaseBean(response);
                if (baseBean.isSuccess()){
                    try {
                        List<TasteReturnBean> list = new ArrayList<TasteReturnBean>();
                        JSONArray array = baseBean.getList();
                        for (int i=0;i<array.length();i++){
                            TasteReturnBean bean = new TasteReturnBean(array.getJSONObject(i));
                            list.add(bean);
                        }
                        callback.displayResult(SEARCH_GET_TASTE_DATA_SUCCESS, list);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callback.displayResult(SERACH_GET_TASTE_DATA_ERROR, e.getMessage());
                    }
                }else {
                    callback.displayResult(SERACH_GET_TASTE_DATA_ERROR, response);
                }
            }
        });
    }
    public void deleteTaste(RequestParams params, final DisplayCallback callback) {
        String url = CommonUtil.appendRequesturl(R.string.api_taste_delete);
        HttpUtil.get(url, params,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonUtil.showLog("请求失败："+errorResponse);
                callback.displayResult(DELETE_GET_TASTE_DATA_ERROR, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                CommonUtil.showLog("请求成功："+response);
                BaseBean baseBean = new BaseBean(response);
                if (baseBean.isSuccess()){
                    callback.displayResult(DELETE_GET_TASTE_DATA_SUCCESS, baseBean.getMsg());
                }else {
                    callback.displayResult(DELETE_GET_TASTE_DATA_ERROR, response);
                }
            }
        });
    }
}
