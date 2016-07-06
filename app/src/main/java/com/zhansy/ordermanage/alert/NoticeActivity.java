package com.zhansy.ordermanage.alert;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

import com.flyco.dialog.listener.OnBtnRightClickL;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.alert.adapter.NoticeActivityAdapter;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.alert.presenter.NoticeActivityPresenterImpl;
import com.zhansy.ordermanage.alert.view.NoticeActivityView;
import com.zhansy.ordermanage.base.dialog.MyDialog;
import com.zhansy.ordermanage.utils.CommonUtil;
import com.zhansy.ordermanage.utils.NotificationUtil;
import com.zhansy.ordermanage.utils.ToastUtil;

import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemLongClick;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class NoticeActivity extends MVPBaseActivity<NoticeActivityPresenterImpl> implements NoticeActivityView {
    @InjectView(R.id.lv_notice)
    ListView lv_notice;
    @InjectView(R.id.cb_stock_alerm)
    CheckBox cb_stock_alerm;
    @InjectView(R.id.rel_no_data)
    View rel_no_data;

    @OnClick(R.id.btn_back)
    void OnClick(){
        finish();
    }

    @OnItemLongClick(R.id.lv_notice)
    boolean onItemLongClick(int position){
//        mPresenter.delectNoticebean(position);
        dialog(position);
        return true;
    }
    @OnCheckedChanged(R.id.cb_stock_alerm)
    void OnCheckedChanged(){
        if (cb_stock_alerm.isChecked()){
            editor.putBoolean("lowstock", true);
            editor.commit();
        }else {
            editor.putBoolean("lowstock", false);
            editor.commit();
        }
    }
    public SharedPreferences.Editor editor;
    public SharedPreferences sp;
    private void dialog(final int position){
//        AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器
//        builder.setTitle("提示"); //设置标题
//        builder.setMessage("是否确认删除?"); //设置内容
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                mPresenter.delectNoticebean(position);
//                dialog.dismiss(); //关闭dialog
//            }
//        });
//        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        //参数都设置完成了，创建并显示出来
//        builder.create().show();
        MyDialog.showCustomDialog(this, "是否确定删除？", new OnBtnRightClickL() {
            @Override
            public void onBtnRightClick() {
                mPresenter.delectNoticebean(position);
                MyDialog.dimiss();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = CommonUtil.getSharedPreferences();
        editor = sp.edit();
        boolean deBoolean = sp.getBoolean("lowstock",true);
        cb_stock_alerm.setChecked(deBoolean);
        if (deBoolean){
            mPresenter.getData();
        }
//        NotificationUtil.getAlertNotification(this);
    }
    @Override
    public int getLayoutResource() {
        return R.layout.activity_alert;
    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected NoticeActivityPresenterImpl createPresenter() {
        return new NoticeActivityPresenterImpl();
    }

    @Override
    public void setNoticeActivityAdapter(NoticeActivityAdapter adapter) {
        lv_notice.setAdapter(adapter);
    }

    @Override
    public void setdelectTip(boolean b) {
        if (b){
            ToastUtil.showToast(this,"删除成功");
        }else {
            ToastUtil.showToast(this,"删除失败");
        }
    }

    @Override
    public View getRelNoData() {
        return rel_no_data;
    }

    @Override
    public ListView getListView() {
        return lv_notice;
    }

    @Override
    public void setNotify(String tip) {
        NotificationUtil.getAlertNotification(this,tip);
    }

    @Override
    public Context getPresenterContext() {
        return this;
    }
}
