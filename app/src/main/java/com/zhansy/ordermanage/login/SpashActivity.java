package com.zhansy.ordermanage.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;

import com.zhansy.ordermanage.MainActivity;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.admin.MainAdminActivity;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.base.OMApplication;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.login.LoginActivity;
import com.zhansy.ordermanage.login.presenter.LoginPresenterImpl;
import com.zhansy.ordermanage.login.view.LoginActivityView;
import com.zhansy.ordermanage.utils.CommonUtil;
import com.zhansy.ordermanage.utils.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class SpashActivity extends MVPBaseActivity<LoginPresenterImpl> implements LoginActivityView {

    private SharedPreferences sp;
    private boolean rememberPswd;
    private Long beginTime;
    private int resetTime = 1000 * 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beginTime = System.currentTimeMillis();
        sp = CommonUtil.getSharedPreferences();
        rememberPswd = sp.getBoolean("rememberPswd", false); //记住密码
        if (rememberPswd){
            String userName = sp.getString("username","");
            String userPasswd = sp.getString("password","");
            if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPasswd)){
                timeGotoLogin();
            }else {
                mPresenter.gotoLogin(userName,userPasswd);
            }
        }else {
            timeGotoLogin();
        }
    }

    private void timeGotoLogin(){
        final Intent it = new Intent(this, LoginActivity.class); //你要转向的Activity
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(it); //执行
                finish();
            }
        };
        timer.schedule(task, resetTime); //设计 多少秒 跳转
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_spash;
    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected LoginPresenterImpl createPresenter() {
        return new LoginPresenterImpl();
    }

    @Override
    public void loginSuccess(String userType) {
        final Long time = System.currentTimeMillis()-beginTime;
        if (time < resetTime){
            try {
                Thread.sleep(resetTime-time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        if (userType == ""){
//            MainAdminActivity.launch(this,MainAdminActivity.class,"success");
//        }else {
//            MainActivity.launch(this,MainActivity.class,"success");
//        }
        if (OMApplication.getInstance().getCurrentUser().isManage()){
            MainAdminActivity.launch(this,MainAdminActivity.class,"success");
        }else {
            MainActivity.launch(this,MainActivity.class,"success");
        }
        ToastUtil.showToast(this,"登陆成功！");
        finish();
    }

    @Override
    public void loginFail() {
        final Long time = System.currentTimeMillis()-beginTime;
        if (time < resetTime){
            try {
                Thread.sleep(resetTime-time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ToastUtil.showToast(this,"登陆失败，请重新登录");
        LoginActivity.launch(this,LoginActivity.class,"fail");
        finish();
    }

    @Override
    public void loginFailPC() {
        final Long time = System.currentTimeMillis()-beginTime;
        if (time < resetTime){
            try {
                Thread.sleep(resetTime-time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ToastUtil.showToast(this,"网络异常，请重新登录!");
        LoginActivity.launch(this,LoginActivity.class,"fail");
        finish();
    }

    @Override
    public Context getPresenterContext() {
        return this;
    }
}
