package com.zhansy.ordermanage.base.mvp.model;

import org.json.JSONArray;
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
public class ProductOptionsBean extends DataSupport implements Serializable {
    private int id;
    private String type_name;
    @Column(nullable = false,unique = true)
    private String type_code;
    private List<ProductOptionsBean> child_list;

    public ProductOptionsBean(JSONObject job) {
        this.id = job.optInt("id");
        this.type_name = job.optString("type_name");
        this.type_code = job.optString("type_code");

        JSONArray arraycourse = job.optJSONArray("products");
        if (arraycourse != null) {
            child_list = new ArrayList<ProductOptionsBean>();
            if (arraycourse.length() > 0) {
                for (int i = 0; i < arraycourse.length(); i++) {
                    child_list.add(new ProductOptionsBean(arraycourse.optJSONObject(i)));
                }
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getType_code() {
        return type_code;
    }

    public void setType_code(String type_code) {
        this.type_code = type_code;
    }

    public List<ProductOptionsBean> getChild_list() {
        return child_list;
    }

    public void setChild_list(List<ProductOptionsBean> child_list) {
        this.child_list = child_list;
    }
}
