package com.zhansy.ordermanage.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhansy.ordermanage.MainActivity;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.admin.MainAdminActivity;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.base.OMApplication;
import com.zhansy.ordermanage.base.widget.RoundedImageView;
import com.zhansy.ordermanage.login.presenter.LoginPresenterImpl;
import com.zhansy.ordermanage.login.view.LoginActivityView;
import com.zhansy.ordermanage.user.UserActivity;
import com.zhansy.ordermanage.utils.CommonUtil;
import com.zhansy.ordermanage.utils.HttpUtil;
import com.zhansy.ordermanage.utils.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class LoginActivity extends MVPBaseActivity<LoginPresenterImpl> implements LoginActivityView{

    @InjectView(R.id.biv_user_icon)
    ImageView biv_user_icon;
    @InjectView(R.id.et_username)
    EditText et_username;
    @InjectView(R.id.et_passwd)
    EditText et_passwd;
    @InjectView(R.id.cb_login)
    CheckBox cb_login;
    @InjectView(R.id.btn_login)
    Button btn_login;
    @InjectView(R.id.tv_youke)
    TextView tv_youke;
    @InjectView(R.id.tv_register)
    TextView tv_register;

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private boolean rememberPswd;
    private String username;
    private String password;

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUserMessage();
    }

    /**
     * 显示手机本地的用户信息
     */
    private void setUserMessage(){
        sp = CommonUtil.getSharedPreferences();
        editor = sp.edit();
        rememberPswd = sp.getBoolean("rememberPswd", false); //记住密码
        cb_login.setChecked(rememberPswd);
        String userName = sp.getString("username","");
        if(userName.isEmpty()) {
           return;
        }else {
            et_username.setText(userName);
            String userIcon = sp.getString("usericon","");
            ImageLoader.getInstance().displayImage(userIcon,biv_user_icon);
        }
        if (rememberPswd){
            String password = sp.getString("password","");
            et_passwd.setText(password);
        }
    }
    @OnClick({R.id.btn_login,R.id.tv_youke,R.id.tv_register})
    void OnClick(View v){
        switch (v.getId()){
            case R.id.btn_login:
                if(!HttpUtil.isNetworkConnected(this)){
                    ToastUtil.showToast(this,"登陆失败，当前无网络！");
                    break;
                }
                username = et_username.getText().toString().trim();
                password = et_passwd.getText().toString().trim();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                    ToastUtil.showToast(this,"账号或密码不能为空");
                    return;
                }
                if (progressDialog == null) {
                    progressDialog = ProgressDialog.show(this, "温馨提示", "正在登陆......");
                    progressDialog.setCancelable(true);
                }else {
                    progressDialog.show();
                }
                mPresenter.gotoLogin(username,password);
                break;
            case R.id.tv_register:
                UserActivity.launch(this, UserActivity.class,"create");
                 break;
            case R.id.tv_youke:
                MainActivity.launch(this,MainActivity.class);
                finish();
                break;
        }
    }

    @OnCheckedChanged(R.id.cb_login)
    void OnCheckedChanged(){
        if (cb_login.isChecked()){
            editor.putBoolean("rememberPswd", true);
            editor.commit();
        }else {
            editor.putBoolean("rememberPswd", false);
            editor.putString("password", "");
            editor.commit();
        }
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected LoginPresenterImpl createPresenter() {
        return new LoginPresenterImpl();
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            exitBy2Click(); //调用双击退出函数
        }
        return false;
    }
    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            ToastUtil.showToast(this, "再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            getAppManager().ExitApp(this);
        }
    }

    @Override
    public Context getPresenterContext() {
        return this;
    }

    @Override
    public void loginSuccess(String userType) {
        if (progressDialog != null){
            progressDialog.dismiss();
        }
        ToastUtil.showToast(this,"登陆成功！");
        rememberPswd = sp.getBoolean("rememberPswd", true); //记住密码
        if (rememberPswd){
            editor = sp.edit();
            editor.putString("username", username);
            editor.putString("password", password);
            editor.putString("usericon", OMApplication.getInstance().getCurrentUser().getImgurl());
            editor.commit();
        }
        if (OMApplication.getInstance().getCurrentUser().isManage()){
            MainAdminActivity.launch(this,MainAdminActivity.class,"success");
        }else {
            MainActivity.launch(this,MainActivity.class,"success");
        }
        finish();
    }

    @Override
    public void loginFail() {
        if (progressDialog != null){
            progressDialog.dismiss();
        }
        ToastUtil.showToast(this,"登陆失败，用户名或密码不正确");
    }

    @Override
    public void loginFailPC() {
        if (progressDialog != null){
            progressDialog.dismiss();
        }
        ToastUtil.showToast(this,"网络异常，请稍后再试");
    }
}
