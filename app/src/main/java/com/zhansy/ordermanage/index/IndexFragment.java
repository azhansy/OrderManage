package com.zhansy.ordermanage.index;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseFragment;
import com.zhansy.ordermanage.base.OMApplication;
import com.zhansy.ordermanage.base.widget.XListView;
import com.zhansy.ordermanage.index.adapter.ProductAdapter;
import com.zhansy.ordermanage.index.adapter.ProductOptionAdapter;
import com.zhansy.ordermanage.index.presenter.IndexFragmentPresenterImpl;
import com.zhansy.ordermanage.index.view.IndexFragmentView;
import com.zhansy.ordermanage.utils.CommonUtil;



import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class IndexFragment extends MVPBaseFragment<IndexFragmentPresenterImpl> implements XListView.IXListViewListener,IndexFragmentView{

    @InjectView(R.id.xlv_product)
    XListView xlv_product;
    @InjectView(R.id.rel_no_data)
    RelativeLayout rel_no_data;
    @InjectView(R.id.product_list_type_textview)
    TextView product_list_type_textview;
    @InjectView(R.id.product_list_all_textview)
    TextView product_list_all_textview;

    @OnClick({R.id.biv_sousuo,R.id.rl_product_classify,R.id.rl_product_all,R.id.btn_back})
    void OnClick(View v){
        switch (v.getId()){
            case R.id.biv_sousuo:
                SearchActivity.launch(getActivity(),SearchActivity.class,"iv_sousuo");
                break;
            case R.id.rl_product_classify:
                showCourseDialog();
                product_list_type_textview.setTextColor(this.getResources().getColor(R.color.bg_title));
                product_list_all_textview.setTextColor(this.getResources().getColor(R.color.course_list_value_color));
                break;
            case R.id.rl_product_all:
                mPresenter.getData();
                product_list_type_textview.setTextColor(this.getResources().getColor(R.color.course_list_value_color));
                product_list_all_textview.setTextColor(this.getResources().getColor(R.color.bg_title));
                break;
            case R.id.btn_back:
                getActivity().finish();
                break;
        }
    }

    private ProductOptionAdapter cateAdapter,chileCateAdapter;
    private Dialog courseDialog;
    int [] p = new int[2];
    int statusBarHeight;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mPresenter.getProductType();//请求成功后，获取产品的类型
        mPresenter.getData();
        xlv_product.setPullLoadEnable(true);
        xlv_product.setPullRefreshEnable(true);
        xlv_product.setXListViewListener(this);
        xlv_product.post(new Runnable() {
            @Override
            public void run() {
                xlv_product.getLocationOnScreen(p);
                Rect frame = new Rect();
                getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
                statusBarHeight = frame.top;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getProductType();//请求成功后，获取产品的类型
//        mPresenter.getData();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_index;
    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected IndexFragmentPresenterImpl createPresenter() {
        return new IndexFragmentPresenterImpl();
    }


    public static IndexFragment newInstance() {
        IndexFragment fragment = new IndexFragment();
        return fragment;
    }


    @Override
    public Context getPresenterContext() {
        return getActivity();
    }

    @Override
    public void setCateAdapter(ProductOptionAdapter adapter) {
        cateAdapter = adapter;
    }

    @Override
    public void setChildCateAdapter(ProductOptionAdapter adapter) {
        chileCateAdapter = adapter;
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
        product_list_type_textview.setText(cate);
    }

    @Override
    public void setProductAdapter(ProductAdapter adapter) {
        xlv_product.setAdapter(adapter);
    }

    @Override
    public XListView getProductXListView() {
        return xlv_product;
    }

    @Override
    public RelativeLayout getNoDataView() {
        return rel_no_data;
    }

    @Override
    public void onRefresh() {
        mPresenter.Refresh("");
    }

    @Override
    public void onLoadMore() {
        mPresenter.Load("");
    }
}
