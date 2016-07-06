package com.zhansy.ordermanage.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnBtnLeftClickL;
import com.flyco.dialog.listener.OnBtnRightClickL;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseFragment;
import com.zhansy.ordermanage.base.dialog.MyDialog;
import com.zhansy.ordermanage.base.mvp.model.UserBean;
import com.zhansy.ordermanage.user.presenter.UserPresenterImpl;
import com.zhansy.ordermanage.user.view.UserView;
import com.zhansy.ordermanage.utils.PattenUtil;
import com.zhansy.ordermanage.utils.ToastUtil;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class UserFragment extends MVPBaseFragment<UserPresenterImpl> implements UserView {
    @InjectView(R.id.et_user_name)
    EditText et_user_name;
    @InjectView(R.id.et_passwd)
    EditText et_passwd;
    @InjectView(R.id.et_postcode)
    EditText et_postcode;
    @InjectView(R.id.et_phone_number)
    EditText et_phone_number;
    @InjectView(R.id.et_company)
    EditText et_company;
    @InjectView(R.id.et_user_address)
    EditText et_user_address;
    @InjectView(R.id.iv_icon)
    ImageView iv_icon;
    @InjectView(R.id.rl_upload_icon)
    View rl_upload_icon;
    @InjectView(R.id.rel_register)
    View rel_register;

    @OnClick({R.id.btn_back,R.id.rel_register,R.id.rl_upload_icon})
    void OnClick(View v){
        switch (v.getId()){
            case R.id.btn_back:
                getActivity().finish();
                break;
            case R.id.rel_register:
                if (!PattenUtil.validateMobileNO(et_phone_number.getText().toString().trim())){
                    ToastUtil.showToast(getActivity(),"请输入正确的11位手机号！");
                    return;
                }
                if (!PattenUtil.validatePostcode(et_postcode.getText().toString().trim())){
                    ToastUtil.showToast(getActivity(),"请输入6位有效的邮政编码!");
                    return;
                }
                if (getInputMessage()){
                    MyDialog.showCustomDialog(getActivity(), "确定提交用户信息？", new OnBtnRightClickL() {
                        @Override
                        public void onBtnRightClick() {
                            mPresenter.userRegisterCommit(userBean);
                            MyDialog.dimiss();
                        }
                    });
                }else {
                    ToastUtil.showToast(getActivity(),"请按要求填写注册信息");
                }
                break;
            case R.id.rl_upload_icon:
                break;
        }
    }

    private static UserFragment instance;
    private UserBean userBean;
    public static UserFragment getInstance(){
        if (instance == null){
            instance = new UserFragment();
        }
        return instance;
    }

    /**
     * @return 输入的信息是否为空
     */
    private boolean getInputMessage(){
        userBean = new UserBean();
        if (!et_user_name.getText().toString().equals("") && !et_passwd.getText().toString().equals("")
                && !et_phone_number.getText().toString().equals("") && !et_company.getText().toString().equals("")
                && !et_user_address.getText().toString().equals("")&& !et_postcode.getText().toString().equals("")){
            userBean.setUsername(et_user_name.getText().toString());
            userBean.setPassword(et_passwd.getText().toString());
            userBean.setPhone_number(et_phone_number.getText().toString());
            userBean.setCompany(et_company.getText().toString());
            userBean.setUser_address(et_user_address.getText().toString());
            userBean.setPostcode(et_postcode.getText().toString());
            return true;
        }
        return false;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_user_register;
    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected UserPresenterImpl createPresenter() {
        return new UserPresenterImpl();
    }

    @Override
    public void upLoadUserIcon() {

    }

    @Override
    public void upLoadUserIconFailed() {

    }

    @Override
    public void userRegister() {
        MyDialog.showDoubleDialog(getActivity(), "注册成功！", "返回登录", "确定", new OnBtnLeftClickL() {
            @Override
            public void onBtnLeftClick() {
                getActivity().finish();
            }
        }, new OnBtnRightClickL() {
            @Override
            public void onBtnRightClick() {
                MyDialog.dimiss();
            }
        });
    }

    @Override
    public void userRegisterFailed() {
        MyDialog.showDialog(getActivity(), "注册失败，请稍后再试！");
    }

    @Override
    public void startPictureActivity(Intent i, int j) {
        getActivity().startActivityForResult(i, j);
    }

    @Override
    public Context getPresenterContext() {
        return getActivity();
    }
}
