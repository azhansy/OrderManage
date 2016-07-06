package com.zhansy.ordermanage.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 *
 * 网络请求工具类
 */
public class HttpUtil {

    /**
     * 检测网络是否可用
     * @return true or false
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }
        return false;
    }

    private static AsyncHttpClient client = new AsyncHttpClient();
    static {
        client.setTimeout(10 * 1000); //默认超过10秒为超时
//        setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true)
    }
    //用一个完整url获取一个string对象
    public static void get(String urlString, AsyncHttpResponseHandler res) {
        client.get(urlString, res);
    }
    //url里面带参数，获取一个String对象
    public static void get(String urlString, RequestParams params, AsyncHttpResponseHandler res) {
        client.get(urlString, params, res);
    }
    //有上下文，url里面带参数，获取一个String对象
    public static void get(Context mContext, String urlString, RequestParams params, AsyncHttpResponseHandler res) {
        client.get(mContext,urlString, params, res);
    }
    //不带参数，获取json对象或者数组
    public static void get(String urlString, JsonHttpResponseHandler res){
        client.get(urlString, res);
    }
    //带参数，获取json对象或者数组
    public static void get(String urlString, RequestParams params, JsonHttpResponseHandler res){
        client.get(urlString, params, res);
    }
    //下载数据使用，会返回byte数据
    public static void get(String uString, BinaryHttpResponseHandler bHandler){
        client.get(uString, bHandler);
    }

    public static AsyncHttpClient getAsyncHttpClient() {
        return client;
    }

    //带参数，获取json对象或者数组
    public static void post(String urlString, RequestParams params, JsonHttpResponseHandler res){
        client.post(urlString, params, res);
    }

    //下载文件
    public static void downloadFile(String urlString, RequestParams params,BinaryHttpResponseHandler res) throws Exception{
        client.get(urlString,params, res);
    }
    //下载app文件
    public static void downloadApp(String urlString,BinaryHttpResponseHandler res) throws Exception{
        client.get(urlString,res);
    }
    //测试下载文件
    public static void downloadFile(String urlString, BinaryHttpResponseHandler res) throws Exception{
        client.get(urlString, res);
    }
    //上传文件
    public static void uploadFile(String urlString,  RequestParams params,AsyncHttpResponseHandler res) throws Exception{
        client.post(urlString, params, res);
    }
}
