//package com.zhansy.ordermanage.order.business;
//
//import android.content.Context;
//import android.util.Log;
//
//import com.loopj.android.http.RequestParams;
//import com.loopj.android.http.TextHttpResponseHandler;
//import com.zhansy.ordermanage.R;
//import com.zhansy.ordermanage.base.DisplayCallback;
//import com.zhansy.ordermanage.base.OMApplication;
//import com.zhansy.ordermanage.base.mvp.model.OrderBean;
//import com.zhansy.ordermanage.db.LitePalUtil;
//import com.zhansy.ordermanage.utils.CommonUtil;
//import com.zhansy.ordermanage.utils.HttpUtil;
//
//import org.apache.http.Header;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by ZHANSY
// * 邮箱：azhansy@hotmail.com
// * <p/>
// * 出货订单Business
// */
//public class DeliveryOrderBusiness{
//    public static final int STATE_ORDER_LIST_DATA_SUCCESS = 101;
//    public static final int STATE_ORDER_LIST_DATA_ERROR = 102;
//    public static final int STATE_ORDER_LIST_DATA_CLEAR = 100;
//
//    public static final int STATE_ORDER_DETAIL_SUCCESS = 103;
//    public static final int STATE_ORDER_DETAIL_ERROR = 104;
//
//    public static DeliveryOrderBusiness getInstance() {
//        DeliveryOrderBusiness instance = new DeliveryOrderBusiness();
//        return instance;
//    }
//
//    public void getLocalData(Context mContext, RequestParams params, final DisplayCallback callback) {
////        final ProgressDialog progressDialog = ProgressDialog.show(mContext, "正在下载", "Loading......");
////        progressDialog.setCancelable(true);//progressDialog可以取消
//        InputStream is = OMApplication.getInstance().getResources().openRawResource(R.raw.order);
//        try {
//            byte[] buffer = new byte[is.available()];
//            is.read(buffer);
////            将字符数组转换为UTF-8编码的字符串
//            String json = new String(buffer, "UTF-8");
//            JSONObject jsonObject = new JSONObject(json);
//            JSONObject message = jsonObject.getJSONObject("message");
//            JSONArray array = message.getJSONArray("list");
//            if (array != null) {
//                List<OrderBean> list = new ArrayList<OrderBean>();
//                for (int i = 0; i < array.length(); i++) {
//                    OrderBean orderBean = new OrderBean(array.optJSONObject(i));
//                    list.add(orderBean);
//                }
//                list.clear();
//                list = LitePalUtil.getAllOrderBean();
////                if (progressDialog.isShowing() && progressDialog != null) {
////                    progressDialog.dismiss();
////                }
//                callback.displayResult(STATE_ORDER_LIST_DATA_SUCCESS, list);
//            } else {
//                callback.displayResult(STATE_ORDER_LIST_DATA_ERROR, "获取失败");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 获取出货单的列表数据
//     *
//     * @param mContext 上下文
//     * @param params   参数
//     * @param callback 回调
//     */
//    public void getDeliveryOrderList(Context mContext, RequestParams params, final DisplayCallback callback) {
//        //http://172.27.10.20:8080/app/api/order/delivery ? userName=zhansan & password = lisi
//        String url = CommonUtil.appendRequesturl(R.string.api_order);
//        HttpUtil.get(mContext, url, params,
//                new TextHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, String response) {
//                        try {
//                            JSONObject json = new JSONObject(response);
//                            if (json != null) {
//                                OrderBean orderBean = null;
//                                List<OrderBean> list = new ArrayList<OrderBean>();
//                                JSONArray jsonArray = json.optJSONArray("list");
//                                if (jsonArray != null) {
//                                    for (int i = 0; i < jsonArray.length(); i++) {
//                                        orderBean = new OrderBean(jsonArray.optJSONObject(i));
//                                        list.add(orderBean);
//                                    }
//                                    /**
//                                     * 订单列表
//                                     */
//                                    callback.displayResult(STATE_ORDER_LIST_DATA_SUCCESS, list);
//                                } else {
//                                    callback.displayResult(STATE_ORDER_LIST_DATA_CLEAR, json.optString("info"));
//                                }
//
//                            } else {
//                                callback.displayResult(STATE_ORDER_LIST_DATA_ERROR, "获取失败");
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                        callback.displayResult(STATE_ORDER_LIST_DATA_ERROR, "加载失败！");
//                        Log.d("ZHANSY", "onFailure: " + responseString);
//                    }
//                }
//        );
//    }
//
//}
