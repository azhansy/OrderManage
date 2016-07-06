package com.zhansy.ordermanage.base.mvp.model;

import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class TasteReturnBean extends DataSupport implements Serializable {
    private int id;
    private String taste_id;
    private String content;
    private String phone;
    private String createtime;

    public TasteReturnBean(){

    }
    public TasteReturnBean(JSONObject object){
        this.taste_id = object.optString("id");
        this.content = object.optString("content");
        this.phone = object.optString("phone");
        this.createtime = object.optString("createtime");
    }

    public String getTaste_id() {
        return taste_id;
    }

    public void setTaste_id(String taste_id) {
        this.taste_id = taste_id;
    }

    public int getId() {
        return id;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
