package com.zhansy.ordermanage.user.business;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.DisplayCallback;
import com.zhansy.ordermanage.base.mvp.model.BaseBean;
import com.zhansy.ordermanage.base.mvp.model.UserBean;
import com.zhansy.ordermanage.utils.CommonUtil;
import com.zhansy.ordermanage.utils.HttpUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class UserBusiness {
    public static final int STATE_USER_REGISTER_DATA_SUCCESS = 0x00221;
    public static final int STATE_USER_REGISTER_DATA_ERROR = 0x00241;
    public static final int STATE_USER_REGISTER_DATA_ERROR_PC = 0x002411;
    public static final int STATE_USER_UPDATE_DATA_SUCCESS = 0x00251;
    public static final int STATE_USER_UPDATE_DATA_ERROR = 0x00261;
    public static final int STATE_USER_UPDATE_HEAD_DATA_SUCCESS = 0x002151;
    public static final int STATE_USER_UPDATE_HEAD_DATA_ERROR = 0x002161;
    public static final int STATE_USER_DELETE_DATA_SUCCESS = 0x00271;
    public static final int STATE_USER_DELETE_DATA_ERROR = 0x00281;
    public static final int STATE_USER_SEARCH_DATA_SUCCESS = 0x00301;
    public static final int STATE_USER_SEARCH_DATA_ERROR = 0x00311;
    public static UserBusiness getInstance() {
        UserBusiness instance = new UserBusiness();
        return instance;
    }

    public void userRegisterCommit(RequestParams params, final DisplayCallback callback) {
        String url = CommonUtil.appendRequesturl(R.string.api_user_register);
        HttpUtil.get(url, params,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonUtil.showLog("请求失败："+errorResponse);
                callback.displayResult(STATE_USER_REGISTER_DATA_ERROR, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                CommonUtil.showLog("请求成功："+response);
                BaseBean baseBean = new BaseBean(response);
                if (baseBean.isSuccess()){
                    callback.displayResult(STATE_USER_REGISTER_DATA_SUCCESS, response);
                }else {
                    callback.displayResult(STATE_USER_REGISTER_DATA_ERROR_PC, response);
                }
            }
        });
    }
    /**
     * 更新用户
     * @param params
     * @param callback
     */
    public void userUpdate(RequestParams params,final DisplayCallback callback){
        String url=CommonUtil.appendRequesturl(R.string.api_user_update);
        HttpUtil.post(url, params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        callback.displayResult(STATE_USER_UPDATE_HEAD_DATA_ERROR, "保存失败");
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        CommonUtil.showLog("请求成功："+response);
                        BaseBean baseBean = new BaseBean(response);
                        if (baseBean.isSuccess()){
                            callback.displayResult(STATE_USER_UPDATE_HEAD_DATA_SUCCESS, baseBean.getMsg());
                        }else {
                            callback.displayResult(STATE_USER_UPDATE_HEAD_DATA_ERROR, baseBean.getMsg());
                        }
                    }
                }
        );
    }
    public void updatePwd(RequestParams params, final DisplayCallback callback) {
        String url = CommonUtil.appendRequesturl(R.string.api_password_update);
        HttpUtil.get(url, params,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonUtil.showLog("请求失败："+errorResponse);
                callback.displayResult(STATE_USER_UPDATE_DATA_ERROR, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                CommonUtil.showLog("请求成功："+response);
                BaseBean baseBean = new BaseBean(response);
                if (baseBean.isSuccess()) {
                    callback.displayResult(STATE_USER_UPDATE_DATA_SUCCESS, baseBean.getMsg());
                }else {
                    callback.displayResult(STATE_USER_UPDATE_DATA_ERROR, baseBean.getMsg());
                }
            }
        });
    }
//    public void updateUser(RequestParams params, final DisplayCallback callback) {
//        String url = CommonUtil.appendRequesturl(R.string.api_user_update);
//        HttpUtil.get(url, params,new JsonHttpResponseHandler(){
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
//                CommonUtil.showLog("请求失败："+errorResponse);
//                callback.displayResult(STATE_USER_UPDATE_DATA_ERROR, errorResponse);
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
//                CommonUtil.showLog("请求成功："+response);
//                BaseBean baseBean = new BaseBean(response);
//                if (baseBean.isSuccess()) {
//                    callback.displayResult(STATE_USER_UPDATE_DATA_SUCCESS, baseBean.getMsg());
//                }else {
//                    callback.displayResult(STATE_USER_UPDATE_DATA_ERROR, baseBean.getMsg());
//                }
//            }
//        });
//    }
    public void SearchUser(RequestParams params, final DisplayCallback callback) {
        String url = CommonUtil.appendRequesturl(R.string.api_user_search);
        HttpUtil.get(url, params,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonUtil.showLog("请求失败："+errorResponse);
                callback.displayResult(STATE_USER_SEARCH_DATA_ERROR, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                CommonUtil.showLog("请求成功："+response);
                BaseBean baseBean = new BaseBean(response);
                if (baseBean.isSuccess()) {
                    List<UserBean> userBeanList = null;
                    try {
                        userBeanList = new ArrayList<UserBean>();
                        JSONArray array = baseBean.getList();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            UserBean userBean = new UserBean(object);
                            userBeanList.add(userBean);
                        }
                        callback.displayResult(STATE_USER_SEARCH_DATA_SUCCESS, userBeanList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callback.displayResult(STATE_USER_SEARCH_DATA_ERROR, e.getMessage());
                    }
                }else {
                    callback.displayResult(STATE_USER_SEARCH_DATA_ERROR, baseBean.getMsg());
                }

            }
        });
    }

    public void deleteUser(RequestParams params, final DisplayCallback callback) {
        String url = CommonUtil.appendRequesturl(R.string.api_user_delete);
        HttpUtil.get(url, params,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonUtil.showLog("请求失败："+errorResponse);
                callback.displayResult(STATE_USER_DELETE_DATA_ERROR, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                CommonUtil.showLog("请求成功："+response);
                BaseBean baseBean = new BaseBean(response);
                if (baseBean.isSuccess()) {
                    callback.displayResult(STATE_USER_DELETE_DATA_SUCCESS, baseBean.getMsg());
                }else {
                    callback.displayResult(STATE_USER_DELETE_DATA_ERROR, baseBean.getMsg());
                }
            }
        });
    }

    /**
     * 上传文件
     * @param filePath
     * @param mContext
     * @param params
     * @param callback
     * @throws Exception
     */
    public void uploadFile(String filePath, final Context mContext, RequestParams params, final DisplayCallback callback) throws Exception{
        String url = CommonUtil.appendRequesturl(R.string.api_user_update);
//        filePath = Environment.getExternalStorageDirectory().getPath().//Don't use "/sdcard/" here
        File file = new File(filePath);
//        RequestParams params = new RequestParams();
//        params.put("uploadfile", file);
        if (file.exists() && file.length() > 0) {
            HttpUtil.uploadFile(url,params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    // 上传成功后要做的工作
                    Toast.makeText(mContext, "上传成功", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    // 上传失败后要做到工作
                    Toast.makeText(mContext, "上传失败", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onProgress(long bytesWritten, long totalSize) {
                    super.onProgress(bytesWritten, totalSize);
                    int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
                    // 上传进度显示
                    Log.e("上传 Progress>>>>>", bytesWritten + " / " + totalSize);
                }

                @Override
                public void onRetry(int retryNo) {
                    // TODO Auto-generated method stub
                    super.onRetry(retryNo);
                    // 返回重试次数
                }
            });
        } else {
            Toast.makeText(mContext, "文件不存在", Toast.LENGTH_LONG).show();
        }
    }
}
