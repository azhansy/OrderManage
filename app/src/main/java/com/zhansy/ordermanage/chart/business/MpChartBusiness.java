package com.zhansy.ordermanage.chart.business;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.DisplayCallback;
import com.zhansy.ordermanage.base.mvp.model.BaseBean;
import com.zhansy.ordermanage.base.mvp.model.ChartBean;
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
 * Created by Administrator on 2016/4/12.
 */
public class MpChartBusiness {
    public static final int STATE_CHART_SUCCESS = 0x0222;//请求统计数
    public static final int STATE_CHART_ERROR = 0x0224; //
    public static MpChartBusiness getInstance(){
        return new MpChartBusiness();
    }
    public void getdata(RequestParams params, final DisplayCallback callback) {
        String url = CommonUtil.appendRequesturl(R.string.api_mp_chart);
        HttpUtil.get(url, params,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonUtil.showLog("请求失败："+errorResponse);
                callback.displayResult(STATE_CHART_ERROR, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                CommonUtil.showLog("请求成功："+response);
                BaseBean baseBean = new BaseBean(response);
                if (baseBean.isSuccess()) {
                    List<ChartBean> list = new ArrayList<>();
                    JSONArray array = baseBean.getList();
                    for (int i = 0; i < array.length(); i++) {
                        try {
                            JSONObject object = array.getJSONObject(i);
                            ChartBean chartBean = new ChartBean(object);
                            list.add(chartBean);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    callback.displayResult(STATE_CHART_SUCCESS, list);
                }else {
                    callback.displayResult(STATE_CHART_SUCCESS, baseBean.getMsg());
                }
            }
        });
    }
}
