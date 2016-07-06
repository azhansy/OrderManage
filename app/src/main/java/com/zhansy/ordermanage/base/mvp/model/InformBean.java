package com.zhansy.ordermanage.base.mvp.model;

import org.json.JSONObject;
import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 * 厂家公告
 */
public class InformBean extends DataSupport implements Serializable{
    private int id;
    @Column(unique = true,nullable = false)
    private int inform_id;
    private String content;
    private String createtime;

    public InformBean(JSONObject object){
//        this.id = object.optInt("id");
        this.inform_id = object.optInt("id");
        this.content = object.optString("content");
        this.createtime = object.optString("createtime");
    }
    public int getId() {
        return id;
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

    public String getCreatetime() {
        return createtime;
    }

    public int getInform_id() {
        return inform_id;
    }

    public void setInform_id(int inform_id) {
        this.inform_id = inform_id;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}
