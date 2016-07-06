package com.zhansy.ordermanage.base.mvp.model;

import com.zhansy.ordermanage.utils.CommonUtil;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class BaseBean {
    private int id;
    private String state;
    private String msg;
    private Boolean isSuccess;
    private JSONArray list;

    public BaseBean(JSONObject object){
        this.state = object.optString("state");
        this.msg = object.optString("msg");
        this.isSuccess = "success".equals(state);
        try {
            this.list = object.optJSONArray("list");
        } catch (Exception e) {
            e.printStackTrace();
            CommonUtil.showLog("list为Object"+e.getMessage());
        }
    }
    public int getId() {
        return id;
    }

    public Boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public JSONArray getList() {
        return list;
    }

    public void setList(JSONArray list) {
        this.list = list;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
