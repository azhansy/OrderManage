package com.zhansy.ordermanage.db;

import android.text.TextUtils;
import android.util.Log;

import com.zhansy.ordermanage.base.mvp.model.AlertLowBean;
import com.zhansy.ordermanage.base.mvp.model.CurrentUserBean;
import com.zhansy.ordermanage.base.mvp.model.DownLoadBean;
import com.zhansy.ordermanage.base.mvp.model.FeedBackBean;
import com.zhansy.ordermanage.base.mvp.model.InformBean;
import com.zhansy.ordermanage.base.mvp.model.OrderBean;
import com.zhansy.ordermanage.base.mvp.model.ProductBean;
import com.zhansy.ordermanage.base.mvp.model.UserBean;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 *
 * 数据库操作的工具类
 *
 */
public class LitePalUtil {

    public static void exit(){
        DataSupport.deleteAll(CurrentUserBean.class);//删除当前用户
//        DataSupport.deleteAll(UserBean.class);//删除当前用户
//        DataSupport.deleteAll(OrderBean.class);//删除当前用户
    }

    public static void deleteProduct(int position){
        DataSupport.delete(ProductBean.class, position);
    }
    public static void deleteAllProduct(){
        DataSupport.deleteAll(ProductBean.class);
    }

    /**
     * @return 查询厂家公告
     */
    public static List<InformBean> getNoticeBeanList(){
        List<InformBean> list = new ArrayList<InformBean>();
        list = DataSupport.findAll(InformBean.class);
        Log.d("LitePalUtil", "查询厂家公告：" + list.toString());
        return list;
    }

    /**
     * @return 根据ID 删除厂家公告id
     */
    public static int delectInformBean(int id){
        int i = DataSupport.deleteAll(InformBean.class,"inform_id = ?",""+id);
        Log.d("LitePalUtil", "删除 厂家公告：" + i);
        return i;
    }
    /**
     * @return 根据ID 删除厂家公告数据库所有信息
     */
    public static int delectInformBeanList(){
        int i = DataSupport.deleteAll(InformBean.class);
        Log.d("LitePalUtil", "删除 厂家公告所有信息：" + i);
        return i;
    }

    /**
     * @return 获取下载的列表
     */
    public static List<DownLoadBean> getDownLoadBeanList(){
        List<DownLoadBean> list = new ArrayList<>();
        list = DataSupport.findAll(DownLoadBean.class);
        return list;
    }
    /**
     *  删除下载的列表
     */
    public static int deletetDownLoadBean(int id){
        int i = DataSupport.delete(DownLoadBean.class,id);
        Log.d("LitePalUtil", "删除 查询警报：" + i);
        return i;
    }

    public static boolean downLoadBeanExist(String name) {
        List<DownLoadBean> list =  DataSupport.where("name == ?", name+".pdf").find(DownLoadBean.class);
        if (list == null || list.size() == 0){
            List<DownLoadBean> list1 =  DataSupport.where("name == ?", name+".xls").find(DownLoadBean.class);
            if (list1 == null || list1.size() == 0){
                return false;
            }else {
                return true;
            }
        }else {
            return true;
        }
    }

//    /**
//     * @param id    根据产品的ID
//     * @return  查询反馈产品信息
//     */
//    public static List<FeedBackBean> getFeedBackBeanList(String id,int page){
////        ProductBean productBean = DataSupport.find(ProductBean.class, id, true);
////        List<FeedBackBean> list = productBean.getFeedBackBeanList();
////        list = DataSupport.findAll(FeedBackBean.class);
//        List<FeedBackBean> list = new ArrayList<FeedBackBean>();
//        if (page == 0){
//            list =  DataSupport.where("productbean_id == ?", id).find(FeedBackBean.class);
//        }else {
//            list =  DataSupport.where("productbean_id == ?", id).limit(10).offset(10*(page-1)).find(FeedBackBean.class);
//        }
//        Log.d("LitePalUtil", "查询单个产品的反馈信息所有数据：" + list.toString());
//        return list;
//    }

    /**
     * @return 根据ID 删除消息警报
     */
    public static int delectNoticeBeanList(int id){
        int i = DataSupport.deleteAll(AlertLowBean.class,"alert_id = ?",""+id);
        Log.d("LitePalUtil", "删除 查询警报：" + i);
        return i;
    }

    /**
     * @return 获取库存警报列表
     */
    public static List<AlertLowBean> getAlertLowBeanList(){
        List<AlertLowBean> list = new ArrayList<>();
        list = DataSupport.findAll(AlertLowBean.class);
        return list;
    }

    /**
     * @return 用户的所有数据
     */
    public static List<UserBean> getAllUserBean(){
        List<UserBean> userBeanList = DataSupport.limit(10).offset(10).find(UserBean.class);//
        Log.d("LitePalUtil", "查询所有用户的数据：" + userBeanList.toString());
        return userBeanList;
    }
    /**
     * @return 查询当前用户
     */
    public static CurrentUserBean getCurrentUser(){
        CurrentUserBean user = DataSupport.findFirst(CurrentUserBean.class);
        if (user == null) user = new CurrentUserBean();
        Log.d("LitePalUtil", "当前用户信息：" + user.toString());
        return user;
    }
    /**
     * @return 删除当前用户
     */
    public static void deleteCurrentUser(){
        int i = DataSupport.deleteAll(CurrentUserBean.class);
        Log.d("LitePalUtil", "删除当前用户条数：" + i);
    }
    /**
     * @return 订单的所有数据
     */
    public static List<OrderBean> getPageOrderBean(int page){
        List<OrderBean> orderBeanList = DataSupport.limit(10).offset(10*(page-1)).find(OrderBean.class);
        Log.d("LitePalUtil", "查询订单的所有数据：" + orderBeanList.toString());
        return orderBeanList;
    }

    /**
     * @return  产品的所有数据
     */
    public static List<ProductBean> getCurrentProductBean(int page){
        List<ProductBean> productBeanList = DataSupport.limit(10).offset(10*(page-1)).find(ProductBean.class);
        Log.d("LitePalUtil", "查询进货单产品的所有数据：" + productBeanList.toString());
        return productBeanList;
    }
    /**
     * @return  产品 查询 产品数据
     */
    public static List<ProductBean> getProductBeanList(){
        List<ProductBean> productBeanList = DataSupport.findAll(ProductBean.class);
        return productBeanList;
    }

    /**
     * @return  根据关键词（产品名称） 查询 产品的所有数据
     */
    public static List<ProductBean> getKeySearchPageProductBean(int page, String key){
        List<ProductBean> productBeanList = DataSupport.where("product_name like ?", "%"+key+"%").limit(10).offset(10*(page-1)).find(ProductBean.class);
        Log.d("LitePalUtil", "搜索关键词产品的所有数据：" + productBeanList.toString());
        return productBeanList;
    }

    /**
     * @param queryString 待付款、待发货、待。。。
     * @return List 符合条件的 订单集合
     */
    public static List<OrderBean> QueryOrderStatusList(String queryString){
        List<OrderBean> orderBeanList = null;
        if (TextUtils.isEmpty(queryString)){
           orderBeanList = DataSupport.limit(10).offset(10).find(OrderBean.class);
        }else {
            orderBeanList = DataSupport.where("order_type == ?", queryString).limit(10).offset(10).find(OrderBean.class);
        }
        if (orderBeanList == null){
            return new ArrayList<OrderBean>();
        }
        Log.d("LitePalUtil", "查询订单符合条件的查询数据：" +orderBeanList.size()+"  "+ orderBeanList.toString());
        return orderBeanList;
    }
    /**
     * @param page        分页
     * @param queryString 待付款、待发货、待。。。
     * @return List 符合条件的 订单集合 包括订单 关联 的产品集合
     */
    public static List<OrderBean> QueryOrderStatusListWithProductBean(int page,String queryString){
        List<OrderBean> orderBeanList = new ArrayList<OrderBean>();
        if (TextUtils.isEmpty(queryString)){
            orderBeanList = DataSupport.limit(10).offset(10*(page-1)).find(OrderBean.class,true);
        }else {
            orderBeanList = DataSupport.where("order_type == ?", queryString).limit(10).offset(10*(page-1)).find(OrderBean.class,true);
        }
        Log.d("LitePalUtil", "查询订单和产品"+"查询条件为："+queryString+"符合条件的查询数据：" + orderBeanList.toString());
        return orderBeanList;
    }

    /**
     * @param queryString 待付款、待发货、待。。。
     * @return 数量
     * int result = DataSupport.where("commentcount = ?", "0").count(News.class);
     */
    public static int QueryOrderStatusNumber(String queryString){
//        int number = QueryOrderStatusList(queryString).size();
        int number = DataSupport.where("order_type == ?", queryString).count(OrderBean.class);
        return number;
    }

}
