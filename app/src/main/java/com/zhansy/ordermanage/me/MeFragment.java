package com.zhansy.ordermanage.me;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhansy.ordermanage.base.DataCleanManager;
import com.zhansy.ordermanage.base.OMApplication;
import com.zhansy.ordermanage.base.dialog.MyDialog;
import com.zhansy.ordermanage.base.mvp.model.CurrentUserBean;
import com.zhansy.ordermanage.db.LitePalUtil;
import com.zhansy.ordermanage.login.LoginActivity;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseFragment;
import com.zhansy.ordermanage.admin.AdminBackStageActivity;
import com.zhansy.ordermanage.me.presenter.MeFragmentPresenterImpl;
import com.zhansy.ordermanage.me.view.MeFragmentView;
import com.zhansy.ordermanage.user.UserActivity;
import com.zhansy.ordermanage.utils.CommonUtil;

import org.litepal.crud.DataSupport;

import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 * 我-主页
 */
public class MeFragment extends MVPBaseFragment<MeFragmentPresenterImpl> implements MeFragmentView{

    public SharedPreferences.Editor editor;
    public SharedPreferences sp;

    @InjectView(R.id.mefragment_top_username)
    TextView mefragment_top_username;
    @InjectView(R.id.tv_app_cache)
    TextView tv_app_cache;
    @InjectView(R.id.mefragment_top_company)
    TextView mefragment_top_company;
    @InjectView(R.id.mefragment_top_image_icon)
    ImageView mefragment_top_image_icon;

//    @InjectView(R.id.cb_stock_alerm)
//    CheckBox cb_stock_alerm;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_me;
    }
    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected MeFragmentPresenterImpl createPresenter() {
        return new MeFragmentPresenterImpl();
    }

    public static MeFragment newInstance() {
        MeFragment fragment = new MeFragment();
        return fragment;
    }

//    @OnCheckedChanged(R.id.cb_stock_alerm)
//    void OnCheckedChanged(){
//        if (cb_stock_alerm.isChecked()){
//            editor.putBoolean("lowstock", true);
//            editor.commit();
//        }else {
//            editor.putBoolean("lowstock", false);
//            editor.commit();
//        }
//    }

    private NormalListDialog normalListDialog;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        sp = CommonUtil.getSharedPreferences();
//        editor = sp.edit();
//        boolean deBoolean = sp.getBoolean("lowstock",true);
//        cb_stock_alerm.setChecked(deBoolean);
//        mPresenter.getData();
        init();
    }

    private void init(){
        setUserName(OMApplication.getInstance().getCurrentUser().getUsername());
        setCompany(OMApplication.getInstance().getCurrentUser().getCompany());
        setUserIcon(OMApplication.getInstance().getCurrentUser().getImgurl());
        try {
            tv_app_cache.setText(DataCleanManager.getTotalCacheSize(getActivity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @OnClick({/*R.id.rel_all_order,R.id.lr_noPayment,R.id.rl_noDelivery,
            R.id.lr_noConfirm,*/R.id.btn_back,R.id.app_set_person_data,R.id.rel_about,
            R.id.rel_updatePwd,R.id.rel_login_exit,/*R.id.rel_backstage,*/R.id.rel_check_version,
            R.id.app_set_clear_cache})
    void OnClick(View v){
//        mPresenter.getData();
        switch (v.getId()){
            case R.id.btn_back:
                getActivity().finish();
                break;
            case R.id.app_set_person_data:
                UserActivity.launch(getActivity(),UserActivity.class,"update");
                break;
            case R.id.rel_check_version:
                UpdateVersionActivity.launch(getActivity(),UpdateVersionActivity.class);
                break;
            case R.id.rel_about:
                AboutActivity.launch(getActivity(),AboutActivity.class);
                break;
            case R.id.rel_updatePwd:
                UpdatePwdActivity.launch(getActivity(),UpdatePwdActivity.class);
                break;
            case R.id.rel_login_exit:
                LoginActivity.launch(getActivity(),LoginActivity.class,"rel_login_exit");
                LitePalUtil.exit();//删除当前用户信息
                getActivity().finish();
                break;
             case R.id.app_set_clear_cache:
//                 if (normalListDialog == null){
//                     normalListDialog = MyDialog.showNormalListDialog(getActivity(),"请选择清除本应用的数据", new String[]{"所有的数据", "全部数据库表", "内部所有Cache缓存","清除SharedPreference","取消"});
//                 }else {
//                     normalListDialog.show(R.style.myDialogAnim);
//                 }
//                 normalListDialog.setOnOperItemClickL(new OnOperItemClickL() {
//                     @Override
//                     public void onOperItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                         if (position == 0){
//                             DataCleanManager.cleanApplicationData(getActivity(),CommonUtil.hostFile);
//                             normalListDialog.dismiss();
//                         }else if (position == 1){
//                             DataCleanManager.cleanDatabases(getActivity());
//                             normalListDialog.dismiss();
//                         }else if (position == 2){
//                             DataCleanManager.cleanInternalCache(getActivity());
//                             normalListDialog.dismiss();
//                         }else if (position == 3){
//                             DataCleanManager.cleanSharedPreference(getActivity());
//                             normalListDialog.dismiss();
//                         }else {
//                             normalListDialog.dismiss();
//                         }
//                     }
//                 });
                Intent i = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                String pkg = "com.android.settings";
                String cls = "com.android.settings.applications.InstalledAppDetails";
                i.setComponent(new ComponentName(pkg, cls));
                i.setData(Uri.parse("package:" + getActivity().getPackageName()));
                startActivity(i);
                break;
        }
    }


    @Override
    public Context getPresenterContext() {
        return getActivity();
    }

//
//    @Override
//    public void setNoPaymentNumber(String noPaymentNumber) {
////        tv_noPayment.setText(noPaymentNumber);
//    }
//
//    @Override
//    public void setNoDeliveryNumber(String noDeliveryNumber) {
////        tv_noDelivery.setText(noDeliveryNumber);
//    }
//
//    @Override
//    public void setNoConfirmNumber(String noConfirmNumber) {
////        tv_noConfirm.setText(noConfirmNumber);
//    }
//
//    @Override
//    public void setNoEvaluate(String noEvaluate) {
////        tv_noEvaluate.setText(noEvaluate);
//    }
//
//    @Override
//    public void setComplete(String complete) {
////        tv_complete.setText(complete);
//    }

    @Override
    public void setUserName(String userName) {
        mefragment_top_username.setText(userName);
    }

    @Override
    public void setUserIcon(String userIcon) {
        ImageLoader.getInstance().displayImage(userIcon,mefragment_top_image_icon);
    }

    @Override
    public void setCompany(String company) {
        mefragment_top_company.setText(company);
    }
}
