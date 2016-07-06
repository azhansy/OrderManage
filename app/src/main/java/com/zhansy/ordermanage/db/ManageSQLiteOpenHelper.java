package com.zhansy.ordermanage.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 *
 * 数据库帮助类
 */
public class ManageSQLiteOpenHelper extends SQLiteOpenHelper{
    public static final String TB_NAME = "ordermanage.db";
    public static final int TB_VERSION = 1;
    //用户表--
    String sqlUser = "create table if not exists User(" +
            "_id integer primary key,name varchar(20)," +
            "user_type integer,imgurl varchar(100)," +
            "username varchar(20),password varchar(20)," +
            "phone_number varchar(20),user_address varchar(50),company varchar(50));";
    //产品表--
    String sqlProduct = "create table if not exists Product(" +
            "_id integer primary key,code varchar(20)," +
            "generation varchar(20),name varchar(20)," +
            "price varchar(20),in_number varchar(20)," +
            "in_time varchar(20),out_number varchar(20)," +
            "out_time varchar(20),input_user varchar(20)," +
            "imgurl varchar(50),stock integer,remark varchar(20));";
    //订单表--
    String sqlOrder = "create table if not exists ProductOrder(" +
            "_id integer primary key,order_code varchar(20)," +
            "trade_code varchar(20),product_id integer," +
            "userName varchar(20),product_number integer," +
            "createtime varchar(20),postcode integer," +
            "order_type varchar(20),remark varchar(20)," +
            "tatalMoney varchar(50));";

    public ManageSQLiteOpenHelper(Context context) {
        super(context, TB_NAME, null, TB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlUser);// 创建User表
        db.execSQL(sqlProduct);// 创建产品表
        db.execSQL(sqlOrder);// 创建订单表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
