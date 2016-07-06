package com.zhansy.ordermanage.base.mvp.model;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class DownLoadBean extends DataSupport{
    private int id;
    @Column(unique = true,nullable = false)
    private String name;
    private String path;
    private String time;
    private String type;//pdf  excel

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DownLoadBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
