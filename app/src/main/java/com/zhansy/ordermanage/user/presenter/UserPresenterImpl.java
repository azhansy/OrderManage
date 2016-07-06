package com.zhansy.ordermanage.user.presenter;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.TextView;

import com.flyco.dialog.listener.OnBtnRightClickL;
import com.loopj.android.http.RequestParams;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.admin.adapter.MemberAdapter;
import com.zhansy.ordermanage.admin.view.MemberAdminFragmentView;
import com.zhansy.ordermanage.base.OMApplication;
import com.zhansy.ordermanage.base.dialog.MyDialog;
import com.zhansy.ordermanage.base.mvp.model.TasteReturnBean;
import com.zhansy.ordermanage.base.mvp.model.UserBean;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.taste.TasteDetailActivity;
import com.zhansy.ordermanage.user.UserDetailActivity;
import com.zhansy.ordermanage.user.UserUpdateFragment;
import com.zhansy.ordermanage.user.business.UserBusiness;
import com.zhansy.ordermanage.user.controller.UserController;
import com.zhansy.ordermanage.user.view.UserView;
import com.zhansy.ordermanage.utils.CommonUtil;
import com.zhansy.ordermanage.utils.ToastUtil;

import org.litepal.util.LogUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class UserPresenterImpl extends MVPBasePresenter implements UserIPresenter {
    private List<UserBean> userBeanList = new ArrayList<>();
    private UserController controller;
    private MemberAdapter memberAdapter;
    private MyHandler mHandler = new MyHandler(this);
    private int page = 0;
    private boolean loadImage=false;

    private LayoutInflater mInflater;
    private AlertDialog builder;
    private TextView picture_txt;       //拍照上传
    private TextView photo_txt;         //相册选择
    private TextView cancel_txt;        //取消
    private static String path= CommonUtil.hostFile;
    private String fileName;

    public UserPresenterImpl(){
        controller = new UserController(getContext(),mHandler);
        memberAdapter = new MemberAdapter(getContext());
        this.mInflater= LayoutInflater.from(getContext());
    }

    @Override
    public void getData() {
        controller.userSearch(0);
    }

    @Override
    public void upLoadIcon() {

    }
    @Override
    public void Refresh(String status) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 0;
                MemberAdminFragmentView view = getActualUi();
                if (view == null)
                    return;
                view.getXListView().stopRefresh();
                view.getXListView().setRefreshTime(new Date());
                controller.userSearch(page);
            }
        }, 500);
    }

    @Override
    public void Load(String status) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = page + 1 ;
                MemberAdminFragmentView orderClassfyView = getActualUi();
                if (orderClassfyView == null)
                    return;
                orderClassfyView.getXListView().stopLoadMore();
                controller.userSearch(page);
            }
        }, 500);
    }
    @Override
    public void userRegisterCommit(UserBean userBean) {
        controller.userRegisterCommit(userBean);
    }

    @Override
    public void userUpdate(UserBean userBean) {
        if (loadImage){
            //更新头像走多一条请求上传的链接
//            controller.uploadFile(fileName,userBean);
        }
        controller.userUpdate(userBean);
    }

    @Override
    public void submitHead() {
        loadImage= true;//已经修改上传图片
//        RequestParams params=new RequestParams();
//        try {
//            params.put("imgurl", new File(fileName));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        controller.saveHead(params);
//        controller.uploadFile(fileName);
    }

    @Override
    public void showPhoto() {
        final UserView view = getActualUi();
        if (view == null)
            return;
        if (this.builder == null)
            this.builder = new AlertDialog.Builder(getContext()).create();
        this.builder.show();
        Window window = this.builder.getWindow();
        final View addView = mInflater.inflate(R.layout.alert_photo_view, null);
        window.setContentView(addView);
        window.setGravity(Gravity.BOTTOM);
        picture_txt= (TextView) addView.findViewById(R.id.pictures_txt);
        photo_txt= (TextView) addView.findViewById(R.id.photo_txt);
        cancel_txt= (TextView) addView.findViewById(R.id.cancel_txt);
        picture_txt.setOnClickListener(new View.OnClickListener() {		//拍照上传
            @Override
            public void onClick(View arg0) {  //拍照
                // TODO Auto-generated method stub
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path ,"head.jpg")));
                view.startPictureActivity(intent2, 2);//采用ForResult打开
            }
        });
        photo_txt.setOnClickListener(new View.OnClickListener() {		//从相册选择
            @Override
            public void onClick(View arg0) {  //相册
                // TODO Auto-generated method stub
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                view.startPictureActivity(intent1,1);
            }
        });
        cancel_txt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                builder.cancel();
            }
        });
    }

    @Override
    public void cropPhoto(Uri uri) {
        final UserView view = getActualUi();
        if (view == null)
            return;
        Intent intent = new Intent("com.android.camera.action.CROP");//剪切图片
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        view.startPictureActivity(intent, 3);
    }

    @Override
    public void saveSd(Bitmap bitmap) {
//        String sdStatus = Environment.getExternalStorageState();
//        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
//            ToastUtil.showToast(getContext(),"SD卡无效");
//            return;
//        }
        if (!CommonUtil.hasSDcard()){
            ToastUtil.showToast(getContext(),"SD卡无效");
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        fileName =path+"/head.jpg";//图片名字
        try {
            b = new FileOutputStream(fileName);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void cancel() {
        this.builder.cancel();
    }


    public static  class MyHandler extends Handler {
        WeakReference<UserIPresenter> presenter;
        public  MyHandler(UserIPresenter presenter){
            this.presenter = new WeakReference<UserIPresenter>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(presenter!=null&&presenter.get()!=null){
                UserPresenterImpl p= (UserPresenterImpl) presenter.get();
                p.handleMessage(msg);
            }
        }
    }
    public void handleMessage(Message msg) {

        if(msg.what== UserBusiness.STATE_USER_REGISTER_DATA_SUCCESS){
            UserView iview = getActualUi();
            if (iview == null){
                return;
            }
            iview.userRegister();
        }
        if(msg.what== UserBusiness.STATE_USER_REGISTER_DATA_ERROR){
            UserView iview = getActualUi();
            if (iview == null){
                return;
            }
            iview.userRegisterFailed();
        }
        if(msg.what== UserBusiness.STATE_USER_REGISTER_DATA_ERROR_PC){
            UserView iview = getActualUi();
            if (iview == null){
                return;
            }
//            iview.userRegisterFailed();
            ToastUtil.showToast(getContext(),"此用户已经注册，请重新输入用户信息！");
        }
        if(msg.what== UserBusiness.STATE_USER_UPDATE_DATA_SUCCESS){
            UserView iview = getActualUi();
            if (iview == null){
                return;
            }
            iview.userRegister();
        }
        if(msg.what== UserBusiness.STATE_USER_UPDATE_DATA_ERROR){
            UserView iview = getActualUi();
            if (iview == null){
                return;
            }
            iview.userRegisterFailed();
        }
        if(msg.what== UserBusiness.STATE_USER_UPDATE_HEAD_DATA_SUCCESS){
            UserView iview = getActualUi();
            if (iview == null){
                return;
            }
            iview.userRegister();
        }
        if(msg.what== UserBusiness.STATE_USER_UPDATE_HEAD_DATA_ERROR){
            UserView iview = getActualUi();
            if (iview == null){
                return;
            }
            iview.userRegisterFailed();
        }
        if(msg.what== UserBusiness.STATE_USER_SEARCH_DATA_SUCCESS){
            final MemberAdminFragmentView iview = getActualUi();
            if (iview == null){
                return;
            }
            iview.getXListView().setVisibility(View.VISIBLE);
            iview.getNoDataView().setVisibility(View.GONE);
            userBeanList = (List<UserBean>) msg.obj;
            if(page==0) {
                memberAdapter.setList(userBeanList);
                iview.setMemberAdapter(memberAdapter);
            }
            else {
                memberAdapter.addList(userBeanList);
            }
            if(userBeanList.size()<10){
                iview.getXListView().setPullLoadEnable(false);
            }else{
                iview.getXListView().setPullLoadEnable(true);
            }
            iview.getXListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final UserBean bean = memberAdapter.getList().get(position - 1);//XListView计算时多一个头部刷新了，所以要减一
//                    if (bean.isManage()){
//                        MyDialog.showCustomDialog(getContext(), "此用户为管理员，不可对其操作", new OnBtnRightClickL() {
//                            @Override
//                            public void onBtnRightClick() {
//                                MyDialog.dimiss();
//                            }
//                        });
//                    }else {
//                        MyDialog.showCustomDialog(getContext(), "是否删除该会员？", new OnBtnRightClickL() {
//                            @Override
//                            public void onBtnRightClick() {
//                                controller.userDelete(bean.getUser_code());
//                                MyDialog.dimiss();
//                            }
//                        });
//                        iview.getXListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getContext(), UserDetailActivity.class);
                        Bundle bundle = new Bundle();
//                                UserBean bean = (UserBean) memberAdapter.getItem(position);
                        bundle.putSerializable("UserBean",bean);
                        intent.putExtras(bundle);
                        getContext().startActivity(intent);
//                            }
//                        });
//                    }
                }
            });
            memberAdapter.notifyDataSetChanged();
        }
        if(msg.what== UserBusiness.STATE_USER_SEARCH_DATA_ERROR){
            MemberAdminFragmentView iview = getActualUi();
            if (iview == null){
                return;
            }
            iview.getXListView().setVisibility(View.GONE);
            iview.getNoDataView().setVisibility(View.VISIBLE);
        }
        if(msg.what== UserBusiness.STATE_USER_DELETE_DATA_SUCCESS){
            UserView iview = getActualUi();
            if (iview == null){
                return;
            }
            ToastUtil.showToast(getContext(),"会员删除成功");
            memberAdapter.notifyDataSetChanged();
        }
        if(msg.what== UserBusiness.STATE_USER_DELETE_DATA_ERROR){
            UserView iview = getActualUi();
            if (iview == null){
                return;
            }
            ToastUtil.showToast(getContext(),"会员删除失败");
        }
    }
}
