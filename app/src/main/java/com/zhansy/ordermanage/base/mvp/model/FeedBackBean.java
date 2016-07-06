package com.zhansy.ordermanage.base.mvp.model;

import com.zhansy.ordermanage.utils.CommonUtil;

import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class FeedBackBean extends DataSupport implements Serializable {
    private String msg_id;
    private String product_code;
    private String order_code;
    private String username;
    private String product_issue;
    private String status;
    private String imgurl;
    private String createtime;
    private String input_user;
    private String describe;
    private String product_name;

    public FeedBackBean(JSONObject obj){
        this.msg_id = obj.optString("msg_id");
        this.product_code = obj.optString("product_code");
        this.order_code = obj.optString("order_code");
        this.username = obj.optString("username");
        this.product_issue = obj.optString("product_issue");
        this.status = obj.optString("status");
        this.imgurl = CommonUtil.host + obj.optString("imgurl");
        this.createtime = obj.optString("createtime");
        this.input_user = obj.optString("input_user");
        this.describe = obj.optString("describe");
        this.product_name = obj.optString("product_name");
    }
    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProduct_issue() {
        return product_issue;
    }

    public void setProduct_issue(String product_issue) {
        this.product_issue = product_issue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }


    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getInput_user() {
        return input_user;
    }

    public void setInput_user(String input_user) {
        this.input_user = input_user;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    @Override
    public String toString() {
        return "FeedBackBean{" +
                "msg_id='" + msg_id + '\'' +
                ", product_code='" + product_code + '\'' +
                ", order_code='" + order_code + '\'' +
                ", username='" + username + '\'' +
                ", product_issue='" + product_issue + '\'' +
                ", status='" + status + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", createtime=" + createtime +
                ", input_user=" + input_user +
                ", describe='" + describe + '\'' +
                ", product_name='" + product_name + '\'' +
                '}';
    }
}
