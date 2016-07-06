package com.zhansy.ordermanage.product;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.dialog.listener.OnBtnRightClickL;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseFragment;
import com.zhansy.ordermanage.base.OMApplication;
import com.zhansy.ordermanage.base.dialog.MyDialog;
import com.zhansy.ordermanage.base.mvp.model.ProductBean;
import com.zhansy.ordermanage.index.adapter.ProductOptionAdapter;
import com.zhansy.ordermanage.product.presenter.ProductInputPresenterImpl;
import com.zhansy.ordermanage.product.view.ProductInputView;
import com.zhansy.ordermanage.utils.CommonUtil;
import com.zhansy.ordermanage.utils.ToastUtil;

import java.io.File;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ZHANSY on 2016/4/12.
 */
public class ProductUpdateFragment extends MVPBaseFragment<ProductInputPresenterImpl> implements ProductInputView{

    @InjectView(R.id.et_product_name)
    EditText et_product_name;
    @InjectView(R.id.et_product_code)
    TextView et_product_code;
    @InjectView(R.id.et_generation)
    EditText et_generation;
    @InjectView(R.id.et_product_stock)
    EditText et_product_stock;
    @InjectView(R.id.et_product_price)
    EditText et_product_price;
    @InjectView(R.id.product_remark)
    EditText product_remark;

    @InjectView(R.id.et_classify)
    TextView et_classify;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.rl_title)
    RelativeLayout rl_title;
    @InjectView(R.id.rel_delete)
    RelativeLayout rel_delete;
    @InjectView(R.id.iv_product_icon)
    ImageView iv_product_icon;

    @OnClick({R.id.btn_back,R.id.rl_upload_icon,R.id.rel_update,R.id.rel_delete,R.id.rel_classify})
    void OnClick(View v){
        switch (v.getId()) {
            case R.id.btn_back:
                getActivity().finish();
                break;
            case R.id.rl_upload_icon:
                mPresenter.showPhoto();
                break;
            case R.id.rel_update:
                if(getInputMessage()){
                    MyDialog.showCustomDialog(getActivity(), "确定更新该商品？", new OnBtnRightClickL() {
                        @Override
                        public void onBtnRightClick() {
                            mPresenter.productUpdate(bean);
                            MyDialog.dimiss();
                        }
                    });
                }else {
                    ToastUtil.showToast(getActivity(),"请按要求录入商品！");
                }
                break;
            case R.id.rel_delete:
                MyDialog.showCustomDialog(getActivity(), "确定删除该商品？", new OnBtnRightClickL() {
                    @Override
                    public void onBtnRightClick() {
                        //删除该商品
                        mPresenter.productDelete(bean.getProduct_id()+"");
                        MyDialog.dimiss();
                    }
                });
                break;
            case R.id.rel_classify:
                showCourseDialog();
                break;
        }
    }
    private ProductOptionAdapter cateAdapter,chileCateAdapter;
    private Dialog courseDialog;
    int [] p = new int[2];
    int statusBarHeight;

    public static ProductBean bean;
    private String fileName;
    private Bitmap head;//ͷBitmap
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        mPresenter.getProductType();
        et_classify.post(new Runnable() {
            @Override
            public void run() {
                et_classify.getLocationOnScreen(p);
                Rect frame = new Rect();
                getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
                statusBarHeight = frame.top;
            }
        });

    }

    private void init() {
        et_product_name.setText(bean.getProduct_name());
        et_product_code.setText(bean.getProduct_code());
        et_generation.setText(bean.getGeneration());
        et_product_stock.setText(bean.getStock()+"");
        product_remark.setText(bean.getRemark());
        et_product_price.setText(bean.getPrice()+"");
        tv_title.setText("商品详情管理");
        rel_delete.setVisibility(View.VISIBLE);
        ImageLoader.getInstance().displayImage(bean.getImgurl(), iv_product_icon);
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
                            + "/product.jpg");
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
                        mPresenter.submitProductImage();
                        iv_product_icon.setImageBitmap(head);
                        mPresenter.cancel();
                    }
                }
                break;
            case 4:
                if(data!=null){
                    String region=data.getStringExtra("region");
//                    address_txt.setText(region);
                }
        }
    }
    /**
     * @return 输入的信息是否为空
     */
    private boolean getInputMessage(){
        if (!et_product_name.getText().toString().equals("")
//                && !et_product_code.getText().toString().equals("")
                && !et_classify.getText().toString().equals("")
                && !et_generation.getText().toString().equals("")
//                && !product_remark.getText().toString().equals("")
                && !et_product_stock.getText().toString().equals("")){
            bean.setProduct_name(et_product_name.getText().toString());
//            bean.setProduct_code(et_product_code.getText().toString());
            bean.setGeneration(et_generation.getText().toString());
            bean.setRemark(product_remark.getText().toString());
            bean.setPrice(Float.valueOf(et_product_price.getText().toString()));
            bean.setStock(Integer.valueOf(et_product_stock.getText().toString()));
            return true;
        }
        return false;
    }
    /**
     * 分类 产品
     */
    @Override
    public void showCourseDialog(){
        if(courseDialog==null){
            this.courseDialog = new AlertDialog.Builder(getActivity(),R.style.add_dialog).create();
            this.courseDialog.setCanceledOnTouchOutside(true);

            LayoutInflater mInflater= LayoutInflater.from(getActivity());
            final Window window = this.courseDialog.getWindow();
            window.setGravity(Gravity.TOP);  //此处可以设置dialog显示的位置
            window.getAttributes().x = p[0];
            window.getAttributes().y = p[1]-statusBarHeight;


            this.courseDialog.show();
            final View addView = mInflater.inflate(R.layout.couse_dialog, null);
            ListView listViewCate = (ListView) addView.findViewById(R.id.course_cate_lv);
            ListView listViewChileCate = (ListView) addView.findViewById(R.id.course_cate_child_lv);
            listViewCate.setAdapter(cateAdapter);
            listViewChileCate.setAdapter(chileCateAdapter);
            listViewCate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mPresenter.setCatePosition(position);
                }
            });
            listViewChileCate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mPresenter.setChileCatePosition(position);
                    courseDialog.dismiss();
                }
            });
            this.courseDialog.setContentView(addView);
            window.setLayout(RadioGroup.LayoutParams.MATCH_PARENT, CommonUtil.dip2px(OMApplication.getInstance(),330));
        }else
            courseDialog.show();
    }

    @Override
    public void setCate(String cate) {
        et_classify.setText(cate);
    }

    @Override
    public void startPictureActivity(Intent i, int j) {
        getActivity().startActivityForResult(i, j);
    }

    @Override
    public void finishActivity() {
        getActivity().finish();
    }

    public static ProductUpdateFragment getInstance(ProductBean productBean){
        bean = productBean;
        return  new ProductUpdateFragment();
    }
    @Override
    public int getLayoutResource() {
        return R.layout.fragment_product_input;
    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected ProductInputPresenterImpl createPresenter() {
        return new ProductInputPresenterImpl();
    }

    @Override
    public void commitSuccess() {
        ToastUtil.showToast(getActivity(),"操作成功");
        finishActivity();
//        MyDialog.showCustomDialog(getActivity(), "操作成功", new OnBtnRightClickL() {
//            @Override
//            public void onBtnRightClick() {
////                MyDialog.dimiss();
//                getActivity().finish();
//            }
//        });
    }

    @Override
    public void commitFailed() {
        ToastUtil.showToast(getActivity(),"操作失败");
    }

    @Override
    public void setCateAdapter(ProductOptionAdapter adapter) {
        cateAdapter = adapter;
    }

    @Override
    public void setChildCateAdapter(ProductOptionAdapter adapter) {
        chileCateAdapter = adapter;
    }

    @Override
    public Context getPresenterContext() {
        return getActivity();
    }
}
