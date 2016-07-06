package com.zhansy.ordermanage.base.mvp.model;

import com.zhansy.ordermanage.utils.CommonUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 *
 * 产品实体类
 */
public class ProductBean extends DataSupport implements Serializable{
    private int id;
//    @Column(nullable = false,unique = true)
    private int product_id;
    @Column(nullable = false,unique = true)
    private String product_code;
    private String generation; //生产批次
    private String product_name;
    private double price; //单价
    private Long in_number; //进货数量
    private Date in_time; //录入时间
    private Long out_number;//出货数量
    private Date out_time;
    private String input_user;
    private String imgurl;
    private int stock;
    private String createtime;
    private String remark;
    private String user_code = "0";//关联用户
    private List<OrderBean> orderBeanList = new ArrayList<>();
//    private  CurrentUserBean currentUserBean = new CurrentUserBean();

    private boolean isChoosed;  //提交到订单的产品

    private String type_code;

    @Override
    public synchronized int update(long id) {
        CommonUtil.showLog("ProductBean更新成功："+toString());
        return super.update(id);
    }

    @Override
    public synchronized boolean save() {
        CommonUtil.showLog("ProductBean保存成功："+toString());
        return super.save();
    }

    public ProductBean() {
    }

    public ProductBean(JSONObject object){
        this.product_id = object.optInt("product_id");
        this.product_code = object.optString("product_code");
        this.generation = object.optString("generation");
        this.product_name = object.optString("product_name");
        this.price = object.optDouble("price");
        this.stock = object.optInt("stock");
        this.createtime = object.optString("createtime");
        this.input_user = object.optString("input_user");
        this.imgurl = CommonUtil.host + object.optString("imgurl");
        this.remark = object.optString("remark");
        this.type_code = object.optString("type_code");
//        this.in_number = object.optString("in_number");
//        this.in_time = object.optString("in_time");
        this.out_number = object.optLong("product_num");
//        this.out_time = object.optString("out_time");
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getGeneration() {
        return generation;
    }

    public String getType_code() {
        return type_code;
    }

    public void setType_code(String type_code) {
        this.type_code = type_code;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }



    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getIn_number() {
        return in_number;
    }

    public void setIn_number(Long in_number) {
        this.in_number = in_number;
    }

    public Date getIn_time() {
        return in_time;
    }

    public void setIn_time(Date in_time) {
        this.in_time = in_time;
    }

    public Long getOut_number() {
        return out_number;
    }

    public void setOut_number(Long out_number) {
        this.out_number = out_number;
    }

    public Date getOut_time() {
        return out_time;
    }

    public void setOut_time(Date out_time) {
        this.out_time = out_time;
    }

    public String getInput_user() {
        return input_user;
    }

    public void setInput_user(String input_user) {
        this.input_user = input_user;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
//    public CurrentUserBean getCurrentUserBean() {
//        return currentUserBean;
//    }
//
//    public void setCurrentUserBean(CurrentUserBean currentUserBean) {
//        this.currentUserBean = currentUserBean;
//    }

    public List<OrderBean> getOrderBeanList() {
        return orderBeanList;
    }

    public void setOrderBeanList(List<OrderBean> orderBeanList) {
        this.orderBeanList = orderBeanList;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    @Override
    public String toString() {
        return "ProductBean{" +
                "id=" + id +
                ", product_id=" + product_id +
                ", product_code='" + product_code + '\'' +
                ", generation='" + generation + '\'' +
                ", product_name='" + product_name + '\'' +
                ", price=" + price +
                ", in_number=" + in_number +
                ", in_time=" + in_time +
                ", out_number=" + out_number +
                ", out_time=" + out_time +
                ", input_user='" + input_user + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", stock=" + stock +
                ", createtime='" + createtime + '\'' +
                ", remark='" + remark + '\'' +
                ", user_code='" + user_code + '\'' +
                ", isChoosed=" + isChoosed +
                '}';
    }
}
