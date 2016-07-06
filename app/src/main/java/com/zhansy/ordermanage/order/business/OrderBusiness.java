package com.zhansy.ordermanage.order.business;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.DisplayCallback;
import com.zhansy.ordermanage.base.mvp.model.BaseBean;
import com.zhansy.ordermanage.base.mvp.model.CurrentUserBean;
import com.zhansy.ordermanage.base.mvp.model.DownLoadBean;
import com.zhansy.ordermanage.base.mvp.model.OrderBean;
import com.zhansy.ordermanage.base.mvp.model.ProductBean;
import com.zhansy.ordermanage.base.mvp.model.ProductOptionsBean;
import com.zhansy.ordermanage.db.LitePalUtil;
import com.zhansy.ordermanage.utils.CommonUtil;
import com.zhansy.ordermanage.utils.HttpUtil;
import com.zhansy.ordermanage.utils.NotificationUtil;
import com.zhansy.ordermanage.utils.TimeUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 *
 * 订单请求总类
 */
public class OrderBusiness {

    public static final int STATE_ORDER_LIST_DATA_SUCCESS = 0x100;//请求订单列表成功
    public static final int STATE_ORDER_LIST_DATA_ERROR = 0x101; //请求订单列表失败
//    public static final int STATE_ORDER_LIST_DATA_CLEAR = 0x102;

    public static final int STATE_UPDATE_VERSION_SUCCESS = 0x103;//检查版本成功
    public static final int STATE_UPDATE_VERSION_ERROR = 0x104; //检查版本失败

    public static final int STATE_PRODUCT_CURRENT_LIST_DATA_SUCCESS = 0x105;//获取进货单的产品
    public static final int STATE_PRODUCT_CURRENT_LIST_DATA_ERROR = 0x106;  //获取进货单的产品

    public static final int STATE_PRODUCT_TYPE_SUCCESS = 0x107; //获取产品类型成功
    public static final int STATE_PRODUCT_TYPE_ERROR = 0x108; //获取产品类型失败

    public static final int STATE_PRODUCT_LIST_DATA_SUCCESS = 0x109;//请求产品列表成功
    public static final int STATE_PRODUCT_LIST_DATA_ERROR = 0x110; //请求产品列表失败

//    public static final int GET_COMPANY_USER_LIST_DATA_SUCCESS = 0x111;//请求用户会员列表成功
//    public static final int GET_COMPANY_USER_LIST_DATA_ERROR = 0x112; //请求用户会员列表失败

    public static final int STATE_PRODUCT_UPDATE_DATA_SUCCESS = 0x113;//请求用户会员列表成功
    public static final int STATE_PRODUCT_UPDATE_DATA_ERROR = 0x114; //请求用户会员列表失败

    public static final int STATE_LOGIN_SUCCESS = 0x115;//登录成功
    public static final int STATE_LOGIN_ERROR = 0x116; //登录失败
    public static final int STATE_LOGIN_ERROR_PC = 0x117; //登录失败
    public static final int DELETE_ORDER_SUCCESS = 0x118;//删除订单成功
    public static final int DELETE_ORDER_ERROR = 0x119; //删除订单失败
    public static final int UPDATE_ORDER_SUCCESS = 0x120;//更新订单成功
    public static final int UPDATE_ORDER_ERROR = 0x121; //更新订单失败
    public static final int CREATE_ORDER_SUCCESS = 0x122;//新增订单成功
    public static final int CREATE_ORDER_ERROR = 0x123; //新增订单失败
    public static final int DOWNLOAD_ORDER_SUCCESS = 0x124;//新增订单成功
    public static final int DOWNLOAD_ORDER_ERROR = 0x125; //新增订单失败



    public static OrderBusiness getInstance() {
        OrderBusiness instance = new OrderBusiness();
        return instance;
    }
    /**
     * 登录
     *
     * @param mContext
     * @param params
     * @param callback
     */
    public void goToLogin(Context mContext,RequestParams params,final DisplayCallback callback){
//        http://172.27.10.20:85/OrderManage/OrderManage/index.php/Home/Index/doLogin?username=admin&password=admin
        String url = CommonUtil.appendRequesturl(R.string.api_login);
        HttpUtil.get(url,params, new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("test", "请求失败："+errorResponse);
                callback.displayResult(STATE_LOGIN_ERROR_PC, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("test", "请求成功："+response);
                BaseBean baseBean = new BaseBean(response);
                if (baseBean.isSuccess()){
                    try {
                        CurrentUserBean userBean = new CurrentUserBean(baseBean.getList().getJSONObject(0));
                        LitePalUtil.deleteCurrentUser();
                        if (userBean.save()){
                            CommonUtil.showLog("可以插入当前用户");
                        }else{
                            CommonUtil.showLog("插入当前用户失败");
                        }
                        callback.displayResult(STATE_LOGIN_SUCCESS, userBean.getUser_type());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    callback.displayResult(STATE_LOGIN_ERROR, baseBean.getMsg());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("test", "请求失败："+responseString);
                callback.displayResult(STATE_LOGIN_ERROR_PC, responseString);
            }

        });

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
                }

            }
        });
    }
    /**
     * 请求订单数据列表
     *
     * @param mContext
     * @param params
     * @param callback
     */
    public void getOrderList(Context mContext, RequestParams params, final DisplayCallback callback){
//        http://172.27.10.20:85/OrderManage/index.php/Home/Order/toSearch?order_type=已确认&page=2
        String url = CommonUtil.appendRequesturl(R.string.api_order);
        HttpUtil.get(url, params,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonUtil.showLog("getOrderList请求失败："+errorResponse);
                callback.displayResult(STATE_ORDER_LIST_DATA_ERROR, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                CommonUtil.showLog("getOrderList请求成功："+response);
                BaseBean baseBean = new BaseBean(response);
                if (baseBean.isSuccess()){
                    List<OrderBean> orderBeanList = new ArrayList<OrderBean>();
                    JSONArray array = baseBean.getList();
                    for (int i=0; i<array.length(); i++){
                        try {
                            JSONObject object = array.getJSONObject(i);
                            OrderBean orderBean = new OrderBean(object);
                            orderBeanList.add(orderBean);
//                        orderBean.save();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    callback.displayResult(STATE_ORDER_LIST_DATA_SUCCESS, orderBeanList);
                }
                else {
                    callback.displayResult(STATE_ORDER_LIST_DATA_ERROR, baseBean.getMsg());
                }
            }
        });
    }
    /**
     * 请求 产品 数据列表，分页查询
     *
     * @param mContext
     * @param params
     * @param callback
     */
    public void getProductList(Context mContext, RequestParams params, final DisplayCallback callback){
        String url = CommonUtil.appendRequesturl(R.string.api_product);
        HttpUtil.get(url, params,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonUtil.showLog("getProductList请求失败："+errorResponse);
                callback.displayResult(STATE_PRODUCT_LIST_DATA_ERROR, errorResponse);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                CommonUtil.showLog("getProductList请求成功："+response);
                BaseBean baseBean = new BaseBean(response);
                if (baseBean.isSuccess()){
                    List<ProductBean> productBeanList = new ArrayList<ProductBean>();
                    JSONArray array = baseBean.getList();
                    for (int i=0; i<array.length(); i++){
                        try {
                            JSONObject object = array.getJSONObject(i);
                            ProductBean productBean = new ProductBean(object);
                            productBeanList.add(productBean);
//                        productBean.save();//查询的产品加入数据库,要时刻更新数据库，不能加
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    callback.displayResult(STATE_PRODUCT_LIST_DATA_SUCCESS, productBeanList);
                }
                else {
                    callback.displayResult(STATE_PRODUCT_LIST_DATA_ERROR, baseBean.getMsg());
                }

            }
        });
    }
    //十大热卖商品
    public void getProduct10List(final DisplayCallback callback){
        String url = CommonUtil.appendRequesturl(R.string.api_product_10);
        HttpUtil.get(url,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonUtil.showLog("getProductList请求失败："+errorResponse);
                callback.displayResult(STATE_PRODUCT_LIST_DATA_ERROR, errorResponse);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                CommonUtil.showLog("getProductList请求成功："+response);
                BaseBean baseBean = new BaseBean(response);
                if (baseBean.isSuccess()){
                    List<ProductBean> productBeanList = new ArrayList<ProductBean>();
                    JSONArray array = baseBean.getList();
                    for (int i=0; i<array.length(); i++){
                        try {
                            JSONObject object = array.getJSONObject(i);
                            ProductBean productBean = new ProductBean(object);
                            productBeanList.add(productBean);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    callback.displayResult(STATE_PRODUCT_LIST_DATA_SUCCESS, productBeanList);
                }
                else {
                    callback.displayResult(STATE_PRODUCT_LIST_DATA_ERROR, baseBean.getMsg());
                }

            }
        });
    }
    /**
     * 查询 本地商品 分页查询
     *
     * @param page
     * @param callback
     */
    public void getCurrentProductList(Context context,int page,final DisplayCallback callback){
        List<ProductBean> productBeanList = new ArrayList<ProductBean>();
        productBeanList = LitePalUtil.getCurrentProductBean(page);
        if (productBeanList.size()>0){
            callback.displayResult(STATE_PRODUCT_CURRENT_LIST_DATA_SUCCESS, productBeanList);
        }else {
            callback.displayResult(STATE_PRODUCT_CURRENT_LIST_DATA_ERROR, "");
        }
    }
    /**
     * 检查更新
     * @param mContext
     * @param callback
     */
    public void getAppUpdateVersion(final Context mContext, final DisplayCallback callback){
        String url = CommonUtil.appendRequesturl(R.string.api_version);
//        String url = "http://172.27.10.20:85/OrderManage/index.php/Home/PDFExport/toPrintPDF/order_code/56ee4f5eb67e7";
        HttpUtil.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonUtil.showLog("getAppUpdateVersion请求失败："+errorResponse);
                callback.displayResult(STATE_UPDATE_VERSION_ERROR, "请求失败");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                CommonUtil.showLog("getAppUpdateVersion请求成功："+response);
                BaseBean baseBean = new BaseBean(response);
                if (baseBean.isSuccess()) {
                    //解析版本号是否大于当前版本
                    String versionName = CommonUtil.getAppVersionName(mContext);
                    callback.displayResult(STATE_UPDATE_VERSION_SUCCESS, response);
                }else {
                    callback.displayResult(STATE_UPDATE_VERSION_ERROR, "暂无更新");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                callback.displayResult(STATE_UPDATE_VERSION_ERROR, "请求失败");
            }
        });
    }

    /**删除订单
     * @param mContext
     * @param params
     * @param callback
     */
    public void deleteOrder(Context mContext, RequestParams params, final DisplayCallback callback) {
        String url = CommonUtil.appendRequesturl(R.string.api_delete_order);
        HttpUtil.get(url, params,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonUtil.showLog("deleteOrder请求失败："+errorResponse);
                callback.displayResult(DELETE_ORDER_ERROR, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                CommonUtil.showLog("deleteOrder请求成功：" + response);
                BaseBean baseBean = new BaseBean(response);
                if (baseBean.isSuccess()) {
                    callback.displayResult(DELETE_ORDER_SUCCESS, baseBean.getMsg());
                }else {
                    callback.displayResult(DELETE_ORDER_ERROR, baseBean.getMsg());
                }
            }
        });
    }

    /**更新订单
     * @param mContext
     * @param params
     * @param callback
     */
    public void updateOrder(Context mContext, RequestParams params, final DisplayCallback callback) {
        String url = CommonUtil.appendRequesturl(R.string.api_update_order);
        HttpUtil.get(url, params,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonUtil.showLog("updateOrder请求失败："+errorResponse);
                callback.displayResult(UPDATE_ORDER_ERROR, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                CommonUtil.showLog("updateOrder请求成功："+response);
                BaseBean baseBean = new BaseBean(response);
                if (baseBean.isSuccess()){
                    callback.displayResult(UPDATE_ORDER_SUCCESS, baseBean.getMsg());
                }else {
                    callback.displayResult(UPDATE_ORDER_ERROR, response);
                }
            }
        });
    }
    /**新增订单
     * @param mContext
     * @param params
     * @param callback
     */
    public void createOrder(Context mContext, RequestParams params, final DisplayCallback callback) {
        String url = CommonUtil.appendRequesturl(R.string.api_create_order);
        HttpUtil.get(url, params,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                CommonUtil.showLog("createOrder请求失败："+errorResponse);
                callback.displayResult(CREATE_ORDER_ERROR, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                CommonUtil.showLog("createOrder请求成功："+response);
                BaseBean baseBean = new BaseBean(response);
                if (baseBean.isSuccess()){
                        callback.displayResult(CREATE_ORDER_SUCCESS, baseBean.getMsg());
                }else {
                    callback.displayResult(CREATE_ORDER_ERROR, baseBean.getMsg());
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
    public void uploadFile(String filePath,final Context mContext, RequestParams params, final DisplayCallback callback) throws Exception{
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

    /**
     * 下载 pdf 文件
     * @param mContext
     * @param params
     * @param callback
     * @throws Exception
     */
    public void downloadFile(final Context mContext, final String fileName, RequestParams params, final DisplayCallback callback) throws Exception{
        String url = CommonUtil.appendRequesturl(R.string.api_upLoad_order);
        final String pathName = CommonUtil.hostFile+"/"+fileName ;
        String[] allowedContentTypes = new String[] { "application/octet-stream", "application/pdf","image/png", "image/jpeg"};
        HttpUtil.downloadFile(url,params, new BinaryHttpResponseHandler(allowedContentTypes){
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                // TODO Auto-generated method stub
                // 下载成功后需要做的工作
                Log.e("binaryData:", "共下载了：" + bytes.length+"路径"+pathName);
                NotificationUtil.getDownloadNotification(mContext,"下载成功，点击查看");//通知栏

                DownLoadBean downLoadBean = new DownLoadBean();
                downLoadBean.setName(fileName);
                downLoadBean.setPath(pathName);
                downLoadBean.setType("PDF");
                downLoadBean.setTime(TimeUtil.getStandardFullTime(System.currentTimeMillis()));
                downLoadBean.save();
                File file = new File(pathName);

                try {
                    // 若存在则删除
                    if (file.exists()) {
                        file.delete();
                    }
                    new File( CommonUtil.hostFile).mkdir();//新建文件夹
                    file.createNewFile();  // 创建文件

                    OutputStream stream = new FileOutputStream(file);
                    stream.write(bytes);
                    stream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }finally {

                }
                callback.displayResult(DOWNLOAD_ORDER_SUCCESS, pathName);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//                ToastUtil.showToast(mContext,"下载失败");
                callback.displayResult(DOWNLOAD_ORDER_ERROR, "下载失败");
                NotificationUtil.getDownloadNotification(mContext,"下载失败");//通知栏
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                super.onProgress(bytesWritten, totalSize);
                int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
                // 下载进度显示
                Log.e("下载 Progress>>>>>", bytesWritten + " / " + totalSize);
            }
        });
    }
    /**
     * 下载 Excel 文件
     * @param mContext
     * @param params
     * @param callback
     * @throws Exception
     */
    public void downloadExcel(final Context mContext, final String fileName, RequestParams params, final DisplayCallback callback) throws Exception{
        String url = CommonUtil.appendRequesturl(R.string.api_upLoad_order_excel);
        final String pathName = CommonUtil.hostFile+"/"+fileName/*+".pdf"*/;
        String[] allowedContentTypes = new String[] { "application/octet-stream", "application/download","image/png", "image/jpeg"};
        HttpUtil.downloadFile(url,params, new BinaryHttpResponseHandler(allowedContentTypes){
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
//                String tempPath = Environment.getExternalStorageDirectory().getPath() + "/temp.pdf";
//                String tempPath = Environment.getExternalStorageDirectory().getPath() + "/temp.jpeg";
                // TODO Auto-generated method stub
                // 下载成功后需要做的工作
                Log.e("binaryData:", "共下载了：" + bytes.length+"路径"+pathName);
                NotificationUtil.getDownloadNotification(mContext,"下载成功，点击查看");//通知栏
                //
//                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0,
//                        bytes.length);
                DownLoadBean downLoadBean = new DownLoadBean();
                downLoadBean.setName(fileName);
                downLoadBean.setPath(pathName);
                downLoadBean.setType("Excel");
                downLoadBean.setTime(TimeUtil.getStandardFullTime(System.currentTimeMillis()));
                downLoadBean.save();
                File file = new File(pathName);

                // 压缩格式
//                Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
                int quality=100;
                try {
                    // 若存在则删除
                    if (file.exists()) {
                        file.delete();
                    }
                    new File( CommonUtil.hostFile).mkdir();//新建文件夹
                    file.createNewFile();  // 创建文件

                    OutputStream stream = new FileOutputStream(file);
                    stream.write(bytes);
                    // 压缩输出
//                    bmp.compress(format, quality, stream);
                    // 关闭
                    stream.close();
//                    Toast.makeText(mContext, "下载成功\n" + pathName, Toast.LENGTH_LONG).show();

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }finally {

                }
                callback.displayResult(DOWNLOAD_ORDER_SUCCESS, pathName);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//                ToastUtil.showToast(mContext,"下载失败");
                callback.displayResult(DOWNLOAD_ORDER_ERROR, "下载失败");
                NotificationUtil.getDownloadNotification(mContext,"订单下载失败");//通知栏
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                super.onProgress(bytesWritten, totalSize);
                int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
                // 下载进度显示
                Log.e("下载 Progress>>>>>", bytesWritten + " / " + totalSize);
            }
        });
    }
    /**
     * 下载 app文件
     * @param mContext
     * @param callback
     * @throws Exception
     */
    public void downloadApp(final Context mContext, final DisplayCallback callback) throws Exception{
        String url = CommonUtil.appendRequesturl(R.string.api_app_download);
        final String pathName = CommonUtil.hostFile+"/"+"order.apk";
        String[] allowedContentTypes = new String[] { "application/octet-stream", "application/download","image/png", "image/jpeg"};
        HttpUtil.downloadApp(url,new BinaryHttpResponseHandler(allowedContentTypes){
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
//                String tempPath = Environment.getExternalStorageDirectory().getPath() + "/temp.pdf";
//                String tempPath = Environment.getExternalStorageDirectory().getPath() + "/temp.jpeg";
                // TODO Auto-generated method stub
                // 下载成功后需要做的工作
                Log.e("binaryData:", "共下载了：" + bytes.length+"路径"+pathName);
                NotificationUtil.getDownloadNotification(mContext,"下载成功，点击查看");//通知栏
                //
//                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0,
//                        bytes.length);
                DownLoadBean downLoadBean = new DownLoadBean();
                downLoadBean.setName("order.apk");
                downLoadBean.setPath(pathName);
                downLoadBean.setType("app");
                downLoadBean.setTime(TimeUtil.getStandardFullTime(System.currentTimeMillis()));
                downLoadBean.save();
                File file = new File(pathName);

                // 压缩格式
//                Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
                int quality=100;
                try {
                    // 若存在则删除
                    if (file.exists()) {
                        file.delete();
                    }
                    new File( CommonUtil.hostFile).mkdir();//新建文件夹
                    file.createNewFile();  // 创建文件

                    OutputStream stream = new FileOutputStream(file);
                    stream.write(bytes);
                    // 压缩输出
//                    bmp.compress(format, quality, stream);
                    // 关闭
                    stream.close();
//                    Toast.makeText(mContext, "下载成功\n" + pathName, Toast.LENGTH_LONG).show();

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }finally {

                }
                callback.displayResult(STATE_UPDATE_VERSION_SUCCESS, pathName);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//                ToastUtil.showToast(mContext,"下载失败");
                callback.displayResult(STATE_UPDATE_VERSION_ERROR, "下载失败");
                NotificationUtil.getDownloadNotification(mContext,"更新下载失败");//通知栏
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                super.onProgress(bytesWritten, totalSize);
                int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
                // 下载进度显示
                Log.e("下载 Progress>>>>>", bytesWritten + " / " + totalSize);
            }
        });
    }
}
