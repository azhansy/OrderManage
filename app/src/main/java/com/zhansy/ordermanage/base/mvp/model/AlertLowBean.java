package com.zhansy.ordermanage.base.mvp.model;

import com.zhansy.ordermanage.utils.CommonUtil;

import org.json.JSONObject;
import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 *
 * 低库存报警
 */
public class AlertLowBean extends DataSupport implements Serializable {

    @Column(nullable = false,unique = true)
    private int alert_id;
    private int status;
    private String user_code;
    private String createtime;
    private String product_code;
    private String product_name;
    private String content;
    private UserBean userBean = new UserBean();

    public AlertLowBean(JSONObject obj){
        this.alert_id = obj.optInt("alert_id");
        this.user_code = obj.optString("user_code");
        this.product_name = obj.optString("product_name");
        this.createtime = obj.optString("createtime");
        this.content = obj.optString("content");
        this.status = obj.optInt("status");
        this.product_code = obj.optString("product_code");
    }

    public int getAlert_id() {
        return alert_id;
    }

    public void setAlert_id(int alert_id) {
        this.alert_id = alert_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    @Override
    public String toString() {
        return "AlertLowBean{" +
                "content='" + content + '\'' +
                ", alert_id=" + alert_id +
                ", status=" + status +
                ", user_code='" + user_code + '\'' +
                ", createtime='" + createtime + '\'' +
                ", product_code='" + product_code + '\'' +
                '}';
    }
}
