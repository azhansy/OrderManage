package com.zhansy.ordermanage.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.zhansy.ordermanage.base.mvp.model.UserBean;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class UserDao {
    private ManageSQLiteOpenHelper mOpenHelper;

    public UserDao(Context context){
        mOpenHelper = new ManageSQLiteOpenHelper(context);
    }


    /**
     * 插入数据
     *
     * @param userBean 用户实体类
     */
    public void insert(UserBean userBean){
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        if(db.isOpen()){
            ContentValues values = new ContentValues();
            values.put("id",userBean.getUser_id());
            values.put("user_type",userBean.getUser_type());
            db.insert("User","_id",values);
            Log.i("ZHANSY-UserDao", "插入成功");
            db.close();	// 数据库关闭
        }
    }

    /**
     * @param id 需要删除的id
     */
    public void delete(int id){
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        if(db.isOpen()){
            String whereClause = "_id = ?";
            String[] whereArgs = {id + ""};
            int count = db.delete("User", whereClause, whereArgs);
            Log.i("ZHANSY-UserDao", "删除了: " + count + "行");
            db.close();	// 数据库关闭
        }
    }
    /**
     * 根据id找到记录, 并且修改用户信息
     * @param id
     * @param userBean
     */
    public void update(int id, UserBean userBean) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        if(db.isOpen()) {	// 如果数据库打开, 执行添加的操作
            ContentValues values = new ContentValues();
            values.put("name", userBean.getUsername());
            int count  = db.update("User", values, "_id = ?", new String[]{id + ""});
            Log.i("ZHANSY-UserDao", "修改了: " + count + "行");
            db.close();	// 数据库关闭
        }
    }
    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    public UserBean queryItem(int id) {
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();	// 获得一个只读的数据库对象
        if(db.isOpen()) {
            String[] columns = {"_id", "user_type"};	// 需要的列
            String selection = "_id = ?";	// 选择条件, 给null查询所有
            String[] selectionArgs = {id + ""};	// 选择条件的参数, 会把选择条件中的? 替换成数据中的值
            String groupBy = null;	// 分组语句  group by name
            String having = null;	// 过滤语句
            String orderBy = null;	// 排序

            Cursor cursor = db.query("Person", columns, selection, selectionArgs, groupBy, having, orderBy);

            if(cursor != null && cursor.moveToFirst()) {		// cursor对象不为null, 并且可以移动到第一行
                int _id = cursor.getInt(cursor.getColumnIndex("_id"));
                int user_type = cursor.getInt(cursor.getColumnIndex("user_type"));
                db.close();
//                return new UserBean(_id, user_type, null, null, null, null, null, null);
            }
            db.close();
        }
        return null;
    }

}
