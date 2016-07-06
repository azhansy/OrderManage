package com.zhansy.ordermanage.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnBtnRightClickL;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseFragment;
import com.zhansy.ordermanage.base.OMApplication;
import com.zhansy.ordermanage.base.dialog.MyDialog;
import com.zhansy.ordermanage.base.mvp.model.CurrentUserBean;
import com.zhansy.ordermanage.base.mvp.model.UserBean;
import com.zhansy.ordermanage.user.presenter.UserPresenterImpl;
import com.zhansy.ordermanage.user.view.UserView;
import com.zhansy.ordermanage.utils.CommonUtil;
import com.zhansy.ordermanage.utils.PattenUtil;
import com.zhansy.ordermanage.utils.ToastUtil;

import java.io.File;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class UserUpdateFragment extends MVPBaseFragment<UserPresenterImpl> implements UserView {
    @InjectView(R.id.et_user_name)
    EditText et_user_name;
//    @InjectView(R.id.et_passwd)
//    EditText et_passwd;
    @InjectView(R.id.et_phone_number)
    EditText et_phone_number;
    @InjectView(R.id.et_company)
    EditText et_company;
    @InjectView(R.id.et_user_address)
    EditText et_user_address;
    @InjectView(R.id.et_postcode)
    EditText et_postcode;
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
                    ToastUtil.showToast(getActivity(),"请输入正确的手机号！");
                    return;
                }
                if (!PattenUtil.validatePostcode(et_postcode.getText().toString().trim())){
                    ToastUtil.showToast(getActivity(),"请输入6位有效的邮政编码!");
                    return;
                }
                if (getInputMessage()){
                    MyDialog.showCustomDialog(getActivity(), "确定修改个人信息？", new OnBtnRightClickL() {
                        @Override
                        public void onBtnRightClick() {
                            mPresenter.userUpdate(userBean);
                            MyDialog.dimiss();
                        }
                    });
                }else {
                    ToastUtil.showToast(getActivity(),"请按要求填写信息");
                }
                break;
            case R.id.rl_upload_icon:
                mPresenter.showPhoto();//上传照片
                break;
        }
    }
    private String fileName;
    private Bitmap head;//Bitmap
    private static UserUpdateFragment instance;
    private UserBean userBean;
    public static UserUpdateFragment getInstance(){
        if (instance == null){
            instance = new UserUpdateFragment();
        }
        return instance;
    }

    /**
     * @return 输入的信息是否为空
     */
    private boolean getInputMessage(){
        if (!et_user_name.getText().toString().equals("")
                && !et_phone_number.getText().toString().equals("") && !et_company.getText().toString().equals("")
                && !et_user_address.getText().toString().equals("")&& !et_postcode.getText().toString().equals("")){
            userBean.setUsername(et_user_name.getText().toString().trim());
//            userBean.setPassword(et_passwd.getText().toString());
            userBean.setPhone_number(et_phone_number.getText().toString().trim());
            userBean.setCompany(et_company.getText().toString().trim());
            userBean.setUser_address(et_user_address.getText().toString().trim());
            userBean.setUser_address(et_postcode.getText().toString().trim());
            return true;
        }
        return false;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        InitWiew();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    mPresenter.cropPhoto(data.getData());
                }else{
                    mPresenter.cancel();
                }
                break;
            case 2:
                if (resultCode ==Activity.RESULT_OK) {
                    File temp = new File(CommonUtil.hostFile
                            + "/head.jpg");
                    mPresenter.cropPhoto(Uri.fromFile(temp));
                    fileName =temp.toString();
                }else{
                    mPresenter.cancel();
                }
                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if(head!=null){
                        mPresenter.saveSd(head);
                        mPresenter.submitHead();
                        iv_icon.setImageBitmap(head);
                        mPresenter.cancel();
                    }
                }
                break;
//            case 4:
//                if(data!=null){
//                    String region=data.getStringExtra("region");
////                    address_txt.setText(region);
//                }
        }
    }

    private void init() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        userBean = new UserBean();

    }

    private void InitWiew(){
        CurrentUserBean user = OMApplication.getInstance().getCurrentUser();
        ImageLoader.getInstance().displayImage(user.getImgurl(),iv_icon);
        et_user_name.setText(user.getUsername());
        et_phone_number.setText(user.getPhone_number());
        et_company.setText(user.getCompany());
        et_user_address.setText(user.getUser_address());
        et_postcode.setText(user.getPostcode());
    }
    @Override
    public void startPictureActivity(Intent i, int j) {
        getActivity().startActivityForResult(i, j);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_user_update;
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
        MyDialog.showCustomDialog(getActivity(), "更新成功！", new OnBtnRightClickL() {
            @Override
            public void onBtnRightClick() {
                getActivity().finish();
            }
        });
    }

    @Override
    public void userRegisterFailed() {
        MyDialog.showRightDialog(getActivity(), "更新失败，请稍后再试！", new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                MyDialog.dimiss();
//                getActivity().finish();
            }
        });
    }

    @Override
    public Context getPresenterContext() {
        return getActivity();
    }
}
