package com.zhansy.ordermanage.me;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.flyco.dialog.listener.OnBtnRightClickL;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.base.dialog.MyDialog;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.base.widget.BaseImageView;
import com.zhansy.ordermanage.login.LoginActivity;
import com.zhansy.ordermanage.me.presenter.UpdatePwdPresenterImpl;
import com.zhansy.ordermanage.me.view.UpdatePwdView;
import com.zhansy.ordermanage.utils.ToastUtil;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class UpdatePwdActivity extends MVPBaseActivity<UpdatePwdPresenterImpl> implements UpdatePwdView {
    @InjectView(R.id.et_old_pswd)
    EditText et_old_pswd;
    @InjectView(R.id.et_new_pswd)
    EditText et_new_pswd;
    @InjectView(R.id.et_pswd)
    EditText et_pswd;

    @OnClick({R.id.btn_back, R.id.btn_login})
    void OnClick(View v){
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_login:
                if (mPresenter.getEditTextString()){
                    MyDialog.showCustomDialog(this, "确定修改密码？", new OnBtnRightClickL() {
                        @Override
                        public void onBtnRightClick() {
                            mPresenter.updatePwd();
                            MyDialog.dimiss();
                        }
                    });
                }
                break;
        }
    }
    @Override
    public int getLayoutResource() {
        return R.layout.activity_me_pwd;
    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected UpdatePwdPresenterImpl createPresenter() {
        return new UpdatePwdPresenterImpl();
    }

    @Override
    public EditText getOldPswdEditText() {
        return et_old_pswd;
    }

    @Override
    public EditText getNewPswdEditText() {
        return et_new_pswd;
    }

    @Override
    public EditText getPswdEditText() {
        return et_pswd;
    }

    @Override
    public void getUpdatePswdResult(boolean successOrNot) {
        if (successOrNot){
            ToastUtil.showToast(this,"修改成功，请重新登录");
            LoginActivity.launch(this,LoginActivity.class);
            finish();
        }else {
            ToastUtil.showToast(this,"修改失败，请重试");
        }
    }

    @Override
    public Context getPresenterContext() {
        return this;
    }
}
