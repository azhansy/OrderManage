package com.zhansy.ordermanage.base.mvp.model;

import com.zhansy.ordermanage.utils.CommonUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class UserBean extends DataSupport implements Serializable {

    @Column(nullable=false, unique=true)
    private int user_id;

    @Column(nullable=false, unique=true)
    private String user_code;
    private String user_type;
    private String username;
    private String actual_name;//真实名字
    private String input_user;//登记人员
    private String imgurl;
    private String password;
    private String phone_number;
    private String user_address;
    private String postcode;//邮政编码
    private String company; //所属公司
    private boolean isManage;//是否为管理员
    private boolean isSuperManage;;//是否为超级管理员
//    private List<ProductBean> productBeanList = new ArrayList<ProductBean>();
//    private List<OrderBean> orderBeanList = new ArrayList<OrderBean>();
//    private List<AlertLowBean> noticeBeanList = new ArrayList<AlertLowBean>();

    public UserBean() {
    }

    public UserBean(JSONObject object){
        this.user_id = object.optInt("user_id");
        this.user_code = object.optString("user_code");
        this.user_type = object.optString("user_type");
        this.username = object.optString("username");
        this.actual_name = object.optString("actual_name");
        this.password = object.optString("password");
        this.phone_number = object.optString("phone_number");
        this.user_address = object.optString("address");
        this.company = object.optString("company");
        this.postcode = object.optString("postcode");
        this.imgurl = CommonUtil.host + object.optString("imgurl");
        this.input_user = object.optString("input_user");
        this.isManage = !("普通会员".equals(user_type) || "普通用户".equals(user_type));//是否为管理员
        this.isSuperManage = "超级管理员".equals(user_type);
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUsername() {
        return username;
    }

    public boolean isSuperManage() {
        return isSuperManage;
    }

    public void setSuperManage(boolean superManage) {
        isSuperManage = superManage;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getActual_name() {
        return actual_name;
    }

    public void setActual_name(String actual_name) {
        this.actual_name = actual_name;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getCompany() {
        return company;
    }
    public boolean isManage() {
        return isManage;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setManage(boolean manage) {
        isManage = manage;
    }
//    public void setCompany(String company) {
//        this.company = company;
//    }
//
//    public List<OrderBean> getOrderBeanList() {
//        return orderBeanList;
//    }
//
//    public void setOrderBeanList(List<OrderBean> orderBeanList) {
//        this.orderBeanList = orderBeanList;
//    }
//
//    public List<AlertLowBean> getNoticeBeanList() {
//        return noticeBeanList;
//    }
//
//    public void setNoticeBeanList(List<AlertLowBean> noticeBeanList) {
//        this.noticeBeanList = noticeBeanList;
//    }
//
//    public List<ProductBean> getProductBeanList() {
//        return productBeanList;
//    }
//
//    public void setProductBeanList(List<ProductBean> productBeanList) {
//        this.productBeanList = productBeanList;
//    }

    @Override
    public String toString() {
        return "UserBean{" +
                "company='" + company + '\'' +
                ", user_id=" + user_id +
                ", user_code='" + user_code + '\'' +
                ", user_type='" + user_type + '\'' +
                ", username='" + username + '\'' +
                ", actual_name='" + actual_name + '\'' +
                ", input_user='" + input_user + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", password='" + password + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", user_address='" + user_address + '\'' +
                '}';
    }
}
