package com.zhansy.ordermanage.me;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.flyco.dialog.listener.OnBtnRightClickL;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.base.dialog.MyDialog;
import com.zhansy.ordermanage.me.presenter.MeFragmentPresenterImpl;
import com.zhansy.ordermanage.me.view.UpdateAppVersionView;
import com.zhansy.ordermanage.utils.CommonUtil;
import com.zhansy.ordermanage.utils.ToastUtil;

import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class UpdateVersionActivity extends MVPBaseActivity<MeFragmentPresenterImpl>  implements UpdateAppVersionView {
    public SharedPreferences.Editor editor;
    public SharedPreferences sp;
    ProgressDialog progressDialog;
    @InjectView(R.id.cb_update_version)
    CheckBox cb_update_version;

    @OnCheckedChanged(R.id.cb_update_version)
    void OnCheckedChanged(){
        if (cb_update_version.isChecked()){
            editor.putBoolean("cb_update_version", true);
            editor.commit();
        }else {
            editor.putBoolean("cb_update_version", false);
            editor.commit();
        }
    }
    @OnClick({R.id.btn_back,R.id.rel_update_version})
    void OnClick(View v){
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.rel_update_version:
                if (progressDialog == null) {
                    progressDialog = ProgressDialog.show(this, "检查更新", "数据请求中......");
                }
                progressDialog.setCancelable(true);//progressDialog可以取消
                mPresenter.getAppUpdateVerion();
                break;
        }
    }
    @Override
    public int getLayoutResource() {
        return R.layout.activity_me_update_version;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = CommonUtil.getSharedPreferences();
        editor = sp.edit();
        boolean deBoolean = sp.getBoolean("cb_update_version",true);
        cb_update_version.setChecked(deBoolean);
        if (deBoolean) {
            if (progressDialog == null) {
                progressDialog = ProgressDialog.show(this, "检查更新", "数据请求中......");
            }
            progressDialog.setCancelable(true);//progressDialog可以取消
            mPresenter.getAppUpdateVerion();
        }

    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected MeFragmentPresenterImpl createPresenter() {
        return new MeFragmentPresenterImpl();
    }

    @Override
    public void getUpdateAPPVersionResult(boolean successOrNot) {
        if (progressDialog != null){
            progressDialog.dismiss();
        }
        if(successOrNot){
            MyDialog.showCustomDialog(this, "有新版本更新，是否下载？", new OnBtnRightClickL() {
                @Override
                public void onBtnRightClick() {
                    ToastUtil.showToast(UpdateVersionActivity.this,"执行下载操作。。。");
                    mPresenter.getData();
                }
            });
        }else {
            ToastUtil.showToast(this,"暂无新版本！");
        }
    }
//    public void showDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("是否确认下载更新？");
//        builder.setTitle("有新版本");
//        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                ToastUtil.showToast(UpdateVersionActivity.this,"执行下载操作。。。");
//            }
//        });builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        builder.create().show();
//    }

    @Override
    public Context getPresenterContext() {
        return this;
    }
}
