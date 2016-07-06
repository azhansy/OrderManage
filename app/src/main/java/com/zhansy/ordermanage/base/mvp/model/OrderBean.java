package com.zhansy.ordermanage.base.mvp.model;

import org.json.JSONArray;
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
 */
public class OrderBean extends DataSupport implements Serializable{
    @Column(nullable=false, unique=true)
    private int order_id;
    @Column(nullable = false,unique = true)
    private String order_code;
    private String user_code;
    private String postcode;  //邮政编码
    private String userName;
    private String createtime;//时间
    private String order_type;
    private String remark;
    private double total_price; //总价格
    private String pay_mode;
    private String address;
    private String input_user;
//    private String orderDescribe;
//    private UserBean userBean;
    private List<ProductBean> productBeanList = new ArrayList<ProductBean>();

    public OrderBean() {
    }

    public OrderBean(JSONObject object){
        this.order_id = object.optInt("order_id");
        this.order_code = object.optString("order_code");
        this.user_code = object.optString("user_code");
        this.total_price = object.optDouble("total_price");
        this.createtime = object.optString("createtime");
        this.postcode = object.optString("postcode");
        this.pay_mode = object.optString("pay_mode");
        this.order_type = object.optString("order_type");
        this.input_user = object.optString("input_user");
        this.address = object.optString("address");
        this.remark = object.optString("remark");
        this.userName = object.optString("username");
        JSONArray product = object.optJSONArray("orderdetails");
        if (product!=null) {
            productBeanList.clear();
            for (int i = 0; i < product.length(); i++) {
                try {
                    this.productBeanList.add(new ProductBean(product.getJSONObject(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getInput_user() {
        return input_user;
    }

    public void setInput_user(String input_user) {
        this.input_user = input_user;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }


    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPay_mode() {
        return pay_mode;
    }

    public void setPay_mode(String pay_mode) {
        this.pay_mode = pay_mode;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ProductBean> getProductBeanList() {
        return productBeanList;
    }

    public void setProductBeanList(List<ProductBean> productBeanList) {
        this.productBeanList = productBeanList;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "input_user='" + input_user + '\'' +
                ", order_id=" + order_id +
                ", order_code='" + order_code + '\'' +
                ", user_code='" + user_code + '\'' +
                ", postcode='" + postcode + '\'' +
                ", userName='" + userName + '\'' +
                ", createtime='" + createtime + '\'' +
                ", order_type='" + order_type + '\'' +
                ", remark='" + remark + '\'' +
                ", total_price=" + total_price +
                ", pay_mode='" + pay_mode + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
