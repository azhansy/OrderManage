package com.zhansy.ordermanage.base.mvp.model;

import org.json.JSONObject;
import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2016/4/12.
 */
public class ChartBean extends DataSupport {
    private int id;
    private String name;
    private float number;//圆饼占的百分比

    public ChartBean(String name,float number){
        this.name = name;
        this.number = number;
    }
    public ChartBean(JSONObject object){
//        this.name = name;
//        this.number = number;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getNumber() {
        return number;
    }

    public void setNumber(float number) {
        this.number = number;
    }
}
