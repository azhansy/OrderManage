package com.zhansy.ordermanage.inform.business;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.DisplayCallback;
import com.zhansy.ordermanage.base.mvp.model.BaseBean;
import com.zhansy.ordermanage.base.mvp.model.InformBean;
import com.zhansy.ordermanage.db.LitePalUtil;
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
public class InformBusiness {
    public static final int STATE_INFORM_DATA_SUCCESS = 0x001111;
    public static final int STATE_INFORM_DATA_ERROR = 0x001112;
    public static final int DELETE_INFORM_DATA_SUCCESS = 0x001113;
    public static final int DELETE_INFORM_DATA_ERROR = 0x001114;
    public static final int CREATE_INFORM_DATA_SUCCESS = 0x001115;
    public static final int CREATE_INFORM_DATA_ERROR = 0x001116;
    public static InformBusiness instance;
    public static InformBusiness getInstance() {
        if (instance == null)
            instance= new InformBusiness();
        return instance;
    }

    public void getInformData(RequestParams params, final DisplayCallback callback) {
        String url = CommonUtil.appendRequesturl(R.string.api_inform_search);
        HttpUtil.get(url, params,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonUtil.showLog("请求失败："+errorResponse);
                callback.displayResult(STATE_INFORM_DATA_ERROR, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                CommonUtil.showLog("请求成功："+response);
                BaseBean baseBean = new BaseBean(response);
                if (baseBean.isSuccess()) {
                    List<InformBean> list = new ArrayList<InformBean>();
                    JSONArray array = baseBean.getList();
                    try {
                        LitePalUtil.delectInformBeanList();
                        for (int i=0;i<array.length();i++) {
                            InformBean bean = new InformBean(array.getJSONObject(i));
                            list.add(bean);
                            bean.save();
                        }
                        callback.displayResult(STATE_INFORM_DATA_SUCCESS, list);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callback.displayResult(STATE_INFORM_DATA_ERROR, e.getMessage());
                    }
                }else {
                    callback.displayResult(STATE_INFORM_DATA_ERROR, response);
                }
            }
        });
    }
    public void deleteInform(RequestParams params, final DisplayCallback callback) {
        String url = CommonUtil.appendRequesturl(R.string.api_inform_delete);
        HttpUtil.get(url, params,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonUtil.showLog("请求失败："+errorResponse);
                callback.displayResult(DELETE_INFORM_DATA_ERROR, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                CommonUtil.showLog("请求成功："+response);
                BaseBean baseBean = new BaseBean(response);
                if (baseBean.isSuccess()) {
                        callback.displayResult(DELETE_INFORM_DATA_SUCCESS, baseBean.getMsg());
                }else {
                    callback.displayResult(DELETE_INFORM_DATA_ERROR, response);
                }
            }
        });
    }
    public void createInform(RequestParams params, final DisplayCallback callback) {
        String url = CommonUtil.appendRequesturl(R.string.api_inform_create);
        HttpUtil.get(url, params,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonUtil.showLog("请求失败："+errorResponse);
                callback.displayResult(CREATE_INFORM_DATA_ERROR, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                CommonUtil.showLog("请求成功："+response);
                BaseBean baseBean = new BaseBean(response);
                if (baseBean.isSuccess()) {
                        callback.displayResult(CREATE_INFORM_DATA_SUCCESS, baseBean.getMsg());
                }else {
                    callback.displayResult(CREATE_INFORM_DATA_ERROR, response);
                }
            }
        });
    }
}
