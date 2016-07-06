package com.zhansy.ordermanage.alert.business;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.DisplayCallback;
import com.zhansy.ordermanage.base.mvp.model.AlertLowBean;
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
public class NiticeActivityBusiness {
    public static final int STATE_NITICE_DATA_SUCCESS = 0x0111;//请求低库存报警成功
    public static final int STATE_NITICE_DATA_ERROR = 0x0112; //请求低库存报警失败
    public static NiticeActivityBusiness getInstance() {
        NiticeActivityBusiness instance = new NiticeActivityBusiness();
        return instance;
    }

    public void getdata(RequestParams params, final DisplayCallback callback) {
        String url = CommonUtil.appendRequesturl(R.string.api_low_alert_search);
        HttpUtil.get(url, params,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonUtil.showLog("请求失败："+errorResponse);
                callback.displayResult(STATE_NITICE_DATA_ERROR, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                CommonUtil.showLog("请求成功："+response);
                BaseBean baseBean = new BaseBean(response);
                if (baseBean.isSuccess()){
                    List<AlertLowBean> list = new ArrayList<>();
                    JSONArray array = baseBean.getList();
                    for (int i=0; i<array.length(); i++){
                        try {
                            JSONObject object = array.getJSONObject(i);
                            AlertLowBean alertLowBean = new AlertLowBean(object);
                            if (alertLowBean.save()){
                                list.add(alertLowBean);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (list.size()>0){
                        callback.displayResult(STATE_NITICE_DATA_SUCCESS, list);
                    }else {
                        callback.displayResult(STATE_NITICE_DATA_ERROR, "暂无更新");
                    }
                }else {
                    callback.displayResult(STATE_NITICE_DATA_ERROR, baseBean.getMsg());
                }
            }
        });
    }
}
