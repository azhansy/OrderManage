package com.zhansy.ordermanage.base.mvp.model;

import com.zhansy.ordermanage.utils.CommonUtil;

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
public class CurrentUserBean extends DataSupport implements Serializable {

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
    private String postcode;//邮政编码
    private String user_address;
    private String company; //所属公司

    private boolean isManage;//是否为管理员
    private boolean isSuperManage;
//    private List<ProductBean> productBeanList = new ArrayList<>();
//    private String type0;//未审核数量
//    private String type1; //备货中
//    private String type2;//已发货
//    private String type3;//已确认

    public CurrentUserBean() {
    }

    /** 关联查询
     * @return
     */
    public List<ProductBean> getProductList() {
        return DataSupport.where("user_code = ?", user_code).find(ProductBean.class);
    }
    public CurrentUserBean(JSONObject object){
        this.user_id = object.optInt("user_id");
        this.user_code = object.optString("user_code");
        this.user_type = object.optString("user_type");
        this.username = object.optString("username");
        this.actual_name = object.optString("actual_name");
        this.password = object.optString("password");
        this.phone_number = object.optString("phone_number");
        this.user_address = object.optString("address");
        this.postcode = object.optString("postcode");
        this.company = object.optString("company");
        this.imgurl = CommonUtil.host + object.optString("imgurl");
        this.input_user = object.optString("input_user");

        this.isManage = !("普通会员".equals(user_type) || "普通用户".equals(user_type));//是否为管理员
        this.isSuperManage = "超级管理员".equals(user_type);
//        this.type0 = object.optString("type0");
//        this.type1 = object.optString("type1");
//        this.type2 = object.optString("type2");
//        this.type3 = object.optString("type3");
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

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getUsername() {
        return username;
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

    public boolean isSuperManage() {
        return isSuperManage;
    }

    public void setSuperManage(boolean superManage) {
        isSuperManage = superManage;
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

    public void setCompany(String company) {
        this.company = company;
    }

    public boolean isManage() {
        return isManage;
    }

    public void setManage(boolean manage) {
        isManage = manage;
    }

    //    public List<ProductBean> getProductBeanList() {
//        return productBeanList;
//    }
//
//    public void setProductBeanList(List<ProductBean> productBeanList) {
//        this.productBeanList = productBeanList;
//    }

    //
//    public String getType0() {
//        return type0;
//    }
//
//    public void setType0(String type0) {
//        this.type0 = type0;
//    }
//
//    public String getType1() {
//        return type1;
//    }
//
//    public void setType1(String type1) {
//        this.type1 = type1;
//    }
//
//    public String getType2() {
//        return type2;
//    }
//
//    public void setType2(String type2) {
//        this.type2 = type2;
//    }
//
//    public String getType3() {
//        return type3;
//    }
//
//    public void setType3(String type3) {
//        this.type3 = type3;
//    }


    @Override
    public String toString() {
        return "CurrentUserBean{" +
                "user_id=" + user_id +
                ", user_code='" + user_code + '\'' +
                ", user_type='" + user_type + '\'' +
                ", username='" + username + '\'' +
                ", actual_name='" + actual_name + '\'' +
                ", input_user='" + input_user + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", password='" + password + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", postcode='" + postcode + '\'' +
                ", user_address='" + user_address + '\'' +
                ", company='" + company + '\'' +
                ", isManage=" + isManage +
                ", isSuperManage=" + isSuperManage +
                '}';
    }
}
