package com.zhansy.ordermanage.feedback.presenter;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.dialog.MyDialog;
import com.zhansy.ordermanage.base.mvp.model.FeedBackBean;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.feedback.business.FeedBackBusiness;
import com.zhansy.ordermanage.feedback.controller.FeedBackController;
import com.zhansy.ordermanage.feedback.view.FeedBackView;
import com.zhansy.ordermanage.feedback.view.FeedbackInputView;
import com.zhansy.ordermanage.product.view.ProductInputView;
import com.zhansy.ordermanage.utils.CommonUtil;
import com.zhansy.ordermanage.utils.ToastUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class FeedbackInputPresenterImpl extends MVPBasePresenter implements FeedbackInputIPresenter {
    private LayoutInflater mInflater;
    private AlertDialog builder;
    private TextView picture_txt;       //拍照上传
    private TextView photo_txt;         //相册选择
    private TextView cancel_txt;        //取消
    private static String path= CommonUtil.hostFile;
    private String fileName;
    private boolean hasImage=false;
    private FeedBackController controller;
    private MyHandler mHandler = new MyHandler(this);

    public FeedbackInputPresenterImpl(){
        controller = new FeedBackController(getContext(),mHandler);
        this.mInflater = LayoutInflater.from(getContext());
    }

    @Override
    public void setData(String order_code, String product_issue, String describe,String product_code) {
        controller.createFeedback(order_code, product_issue, describe,product_code);
        showProgressDialog(getContext());
    }

    @Override
    public void submitProductImage() {
        hasImage = true;
    }

    @Override
    public void showPhoto() {
        final FeedbackInputView view = getActualUi();
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
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path ,"issue.jpg")));
                view.startPictureActivity(intent2, 2);//采用ForResult打开
            }
        });
        photo_txt.setOnClickListener(new View.OnClickListener() {		//从相册选择
            @Override
            public void onClick(View arg0) {
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
        final FeedbackInputView view = getActualUi();
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
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            ToastUtil.showToast(getContext(),"SD卡无效");
//            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        //new GetHeadIcon().startRun();
        fileName =path+"/issue.jpg";//图片名字
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
        WeakReference<FeedbackInputIPresenter> presenter;
        public  MyHandler(FeedbackInputIPresenter presenter){
            this.presenter = new WeakReference<FeedbackInputIPresenter>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(presenter!=null&&presenter.get()!=null){
                FeedbackInputPresenterImpl p= (FeedbackInputPresenterImpl) presenter.get();
                p.handleMessage(msg);
            }
        }
    }
    public void handleMessage(Message msg) {

        if(msg.what== FeedBackBusiness.CREATE_PRODUCT_FEEDBACK_DATA_SUCCESS){
            FeedbackInputView iview = getActualUi();
            if (iview == null){
                return;
            }
            iview.commitSuccess();
        }
        if(msg.what==FeedBackBusiness.CREATE_PRODUCT_FEEDBACK_DATA_ERROR){
            FeedbackInputView iview = getActualUi();
            if (iview == null){
                return;
            }
            iview.commitFailed();
        }
        hideProgressDialog();
    }
}
