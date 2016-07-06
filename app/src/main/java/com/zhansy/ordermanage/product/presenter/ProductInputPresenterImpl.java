package com.zhansy.ordermanage.product.presenter;

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
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.mvp.model.ProductBean;
import com.zhansy.ordermanage.base.mvp.model.ProductOptionsBean;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.index.adapter.ProductOptionAdapter;
import com.zhansy.ordermanage.index.view.IndexFragmentView;
import com.zhansy.ordermanage.product.business.ProductBusiness;
import com.zhansy.ordermanage.product.controller.ProductController;
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
 * Created by Administrator on 2016/4/12.
 */
public class ProductInputPresenterImpl extends MVPBasePresenter implements ProductInputPresenter ,ProductOptionAdapter.GetPosion{
    /**
     * 判断是否是点击显示商品类别
     */
    private boolean isClickshowcate = false;
    private int catePosion,chileCatePosion;
//    private int page = 0;

//    private ProductAdapter productAdapter;
    private ProductController controller;
    private Handler mHandler = new MyHandler(this);
    private List<ProductBean> productBeanList = new ArrayList<ProductBean>();

    private List<ProductOptionsBean> cateList;
    private ProductOptionAdapter firstAdapter,secondAdapter;
    private String productType = "11110";

    private LayoutInflater mInflater;
    private AlertDialog builder;
    private TextView picture_txt;       //拍照上传
    private TextView photo_txt;         //相册选择
    private TextView cancel_txt;        //取消
    private static String path= CommonUtil.hostFile;
    private String fileName;
    private boolean hasImage=false;

    public  ProductInputPresenterImpl(){
        firstAdapter = new ProductOptionAdapter(getContext());
        secondAdapter = new ProductOptionAdapter(getContext());
//        productAdapter = new ProductAdapter(getContext());
        controller = new ProductController(getContext(), mHandler);
        firstAdapter.setGet(this);
        secondAdapter.setGet(this);
        this.mInflater= LayoutInflater.from(getContext());
        hasImage=false;
    }
    @Override
    public void getProductType() {
        controller.getProductType();
    }

    @Override
    public void getData() {

    }

    @Override
    public void getCouse() {
//        controller.
    }

    @Override
    public void setCatePosition(int position) {
        catePosion = position;
        firstAdapter.notifyDataSetChanged();
        chileCatePosion = 0;
        secondAdapter.setList(cateList.get(position).getChild_list());
        secondAdapter.notifyDataSetChanged();
    }

    @Override
    public void setChileCatePosition(int position) {
        chileCatePosion = position;
        secondAdapter.notifyDataSetChanged();
        ProductInputView iview = getActualUi();
        if(iview == null){
            return;
        }
        iview.setCate(((ProductOptionsBean) firstAdapter.getItem(catePosion)).getType_name()+"/"+((ProductOptionsBean) secondAdapter.getItem(position)).getType_name());//显示选中的类别
        productType = ((ProductOptionsBean)secondAdapter.getItem(position)).getType_code();//显示选中的类别编码
//        getCouse();
    }

    @Override
    public void getallCourse() {
        IndexFragmentView iview = getActualUi();
        if(iview == null){
            return;
        }
        setCatePosition(0);
    }

    @Override
    public void showCates() {
        isClickshowcate = true;
        IndexFragmentView iview = getActualUi();
        if(iview == null){
            return;
        }
        iview.setCateAdapter(firstAdapter);
        iview.setChildCateAdapter(secondAdapter);
        iview.showCourseDialog();
    }

    @Override
    public void submitProductImage() {
//        RequestParams params=new RequestParams();
//        try {
//            params.put("imgurl", new File(fileName));
//            controller.saveProductimgUrl(params);
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        hasImage = true;
    }
    @Override
    public void setProductData(ProductBean bean) {
        bean.setType_code(productType);
        if (hasImage){
            controller.inputProduct(bean,new File(fileName));
            hasImage = false;
        }else {
            controller.inputProduct(bean);
        }
    }
    @Override
    public void productUpdate(ProductBean bean){
        controller.updateProduct(bean);
    }

    @Override
    public void productDelete(String code) {
        controller.deleteProduct(code);
    }

    @Override
    public void showPhoto() {
        final ProductInputView view = getActualUi();
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
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path ,"product.jpg")));
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
        final ProductInputView view = getActualUi();
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
        fileName =path+"/product.jpg";//图片名字
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

    @Override
    public int getLine() {
        return catePosion;
    }

    @Override
    public int getChileCateLine() {
        return chileCatePosion;
    }



    public static class MyHandler extends Handler {
        private WeakReference<ProductInputPresenter> presenter;

        public MyHandler(ProductInputPresenter presenter) {
            this.presenter = new WeakReference<ProductInputPresenter>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (presenter != null && presenter.get() != null) {
                ProductInputPresenterImpl p = (ProductInputPresenterImpl) presenter.get();
                p.handleMessage(msg);
            }
        }
    }

    private void handleMessage(Message msg) {
        if (msg.what == ProductBusiness.STATE_PRODUCT_UPDATE_SUCCESS) {
            ProductInputView iview = getActualUi();
            if (iview == null) return;
            iview.commitSuccess();
        }
        if (msg.what == ProductBusiness.STATE_PRODUCT_UPDATE_ERROR) {
            ProductInputView iview = getActualUi();
            if (iview == null) return;
            iview.commitFailed();
        }
        if (msg.what == ProductBusiness.STATE_PRODUCT_DELETE_SUCCESS) {
            ProductInputView iview = getActualUi();
            if (iview == null) return;
            iview.commitSuccess();
            iview.finishActivity();

        }
        if (msg.what == ProductBusiness.STATE_PRODUCT_DELETE_ERROR) {
            ProductInputView iview = getActualUi();
            if (iview == null) return;
            iview.commitFailed();
        }
        if (msg.what == ProductBusiness.STATE_PRODUCT_CREATE_SUCCESS){
            ProductInputView iview = getActualUi();
            if (iview == null) return;
            iview.commitSuccess();
        }
        if (msg.what == ProductBusiness.STATE_PRODUCT_CREATE_ERROR){
            ProductInputView iview = getActualUi();
            if (iview == null) return;
            iview.commitFailed();
        }
        if (msg.what == ProductBusiness.STATE_PRODUCT_TYPE_SUCCESS){
            ProductInputView iview = getActualUi();
            if (iview == null) return;
            cateList = (List<ProductOptionsBean>) msg.obj;
            firstAdapter.setList(cateList);
            secondAdapter.setList(cateList.get(0).getChild_list());
            iview.setCateAdapter(firstAdapter);
            iview.setChildCateAdapter(secondAdapter);
            if(isClickshowcate){
                showCates();
            }
        }
        if (msg.what == ProductBusiness.STATE_PRODUCT_TYPE_ERROR) {
            ProductInputView iview = getActualUi();
            if (iview == null) return;
            ToastUtil.showToast(getContext(),"服务器异常，获取类型失败");
        }
    }
}
