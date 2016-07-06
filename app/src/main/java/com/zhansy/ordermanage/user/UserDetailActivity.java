package com.zhansy.ordermanage.user;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.dialog.listener.OnBtnRightClickL;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.base.OMApplication;
import com.zhansy.ordermanage.base.dialog.MyDialog;
import com.zhansy.ordermanage.base.mvp.model.CurrentUserBean;
import com.zhansy.ordermanage.base.mvp.model.UserBean;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.user.presenter.UserDetailPresenterImpl;
import com.zhansy.ordermanage.user.view.UserDetailView;
import com.zhansy.ordermanage.utils.ToastUtil;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class UserDetailActivity extends MVPBaseActivity<UserDetailPresenterImpl> implements UserDetailView {
    @InjectView(R.id.ic_icon)
    ImageView ic_icon;
    @InjectView(R.id.et_user_name)
    EditText et_user_name;
    @InjectView(R.id.et_phone_number)
    EditText et_phone_number;
    @InjectView(R.id.et_company)
    EditText et_company;
    @InjectView(R.id.et_user_address)
    EditText et_user_address;
    @InjectView(R.id.et_postcode)
    EditText et_postcode;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_manage)
    LinearLayout ll_manage;
    @InjectView(R.id.btn_delete_user)
    Button btn_delete_user;
    @InjectView(R.id.btn_manage)
    Button btn_manage;

    @OnClick({R.id.btn_back,R.id.btn_delete_user,R.id.btn_manage})
    void OnClick(View v){
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_delete_user:
                MyDialog.showCustomDialog(this, "确定删除此用户?", new OnBtnRightClickL() {
                    @Override
                    public void onBtnRightClick() {
                        //执行删除用户操作
                        mPresenter.deleteUser(bean.getUser_code());
                        MyDialog.dimiss();
                    }
                });
                break;
            case R.id.btn_manage:
                if (bean.isManage()){
                    MyDialog.showCustomDialog(this, "确定取消该管理员?", new OnBtnRightClickL() {
                        @Override
                        public void onBtnRightClick() {
                            //执行修改用户操作
                            bean.setUser_type("普通会员");
                            mPresenter.updateUser(bean);
                            MyDialog.dimiss();
                        }
                    });
                }else {
                    MyDialog.showCustomDialog(this, "设置该用户为管理员?", new OnBtnRightClickL() {
                        @Override
                        public void onBtnRightClick() {
                            //执行修改用户操作
                            bean.setUser_type("订单管理员");
                            mPresenter.updateUser(bean);
                            MyDialog.dimiss();
                        }
                    });
                }

                break;
        }
    }

    private UserBean bean;
    private CurrentUserBean currUser;//当前登录用户
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bean = (UserBean) getIntent().getSerializableExtra("UserBean");
        init();
    }

    private void init() {
        currUser = OMApplication.getInstance().getCurrentUser();
        if (bean != null){
            ImageLoader.getInstance().displayImage(bean.getImgurl(), ic_icon);
            et_user_name.setText(bean.getUsername());
            et_phone_number.setText(bean.getPhone_number());
            et_company.setText(bean.getCompany());
            et_user_address.setText(bean.getUser_address());
            et_postcode.setText(bean.getPostcode());
            tv_title.setText(bean.getUsername()+"的详细信息");
            if (currUser.isSuperManage() && !bean.isSuperManage()){
                btn_delete_user.setVisibility(View.VISIBLE);
                btn_manage.setVisibility(View.VISIBLE);
                if (bean.isManage()){
                    btn_manage.setText("取消该管理员");
                }else {
                    btn_manage.setText("设置为管理员");
                }
            }else if (currUser.isManage() && !(currUser.getUser_code().equals(bean.getUser_code())) && !bean.isManage()){
                btn_delete_user.setVisibility(View.VISIBLE);
                btn_manage.setVisibility(View.GONE);
            }else {
                btn_delete_user.setVisibility(View.GONE);
                btn_manage.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_user_detail;
    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected UserDetailPresenterImpl createPresenter() {
        return new UserDetailPresenterImpl();
    }

    @Override
    public void UserDeleteSuccess() {
        ToastUtil.showToast(this,"会员删除成功");
        finish();
    }

    @Override
    public void UserDeleteFailed() {
        ToastUtil.showToast(this,"会员删除失败，请稍后再试！");
    }

    @Override
    public void UserUpdateSuccess() {
        ToastUtil.showToast(this,"操作成功！");
//        init();
        //刷新会员列表
        //..
    }

    @Override
    public void UserUpdateFailed() {
        ToastUtil.showToast(this,"操作失败！");
    }

    @Override
    public Context getPresenterContext() {
        return this;
    }
}
