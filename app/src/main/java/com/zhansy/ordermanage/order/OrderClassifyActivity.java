package com.zhansy.ordermanage.order;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.base.widget.XExpandableListView;
import com.zhansy.ordermanage.order.view.OrderClassfyView;
import com.zhansy.ordermanage.order.adapter.ExpandableOrderAdapter;
import com.zhansy.ordermanage.order.presenter.OrderClassifyPresenterImpl;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 *
 * 订单分类状态
 */
public class OrderClassifyActivity extends MVPBaseActivity<OrderClassifyPresenterImpl> implements OrderClassfyView, XExpandableListView.IXListViewListener{
//    private ProgressDialog progressDialog;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.list_top_textview)
    TextView list_top_textview;
    @InjectView(R.id.rel_no_data)
    RelativeLayout rel_no_data;
    @InjectView(R.id.order_list_top)
    RelativeLayout order_list_top;
    @InjectView(R.id.xelv_ordxer_classify)
    XExpandableListView xelv_order_classify;
    private Dialog orderDialog;
    int [] p = new int[2];
    int statusBarHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Intent intent = getIntent();
//        String key = intent.getStringExtra(IBaseUi.KEY_GID);  //传过来的title
//        if (progressDialog == null) {
//            progressDialog = ProgressDialog.show(this, "提示", "数据请求中......");
//        }
//        progressDialog.setCancelable(true);//progressDialog可以取消
//        setTitleName(key);
        xelv_order_classify.setXListViewListener(this);
        xelv_order_classify.setPullRefreshEnable(true);
        xelv_order_classify.setPullLoadEnable(true);
//        mPresenter.getOrderClassfyList("order_type","");
        xelv_order_classify.post(new Runnable() {
            @Override
            public void run() {
                xelv_order_classify.getLocationOnScreen(p);
                Rect frame = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
                statusBarHeight = frame.top;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getOrderClassfyList("order_type","");//
    }

    @OnClick({R.id.btn_back,R.id.order_list_top})
    void OnClick(View v){
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.order_list_top:
                showOrderDialog();
                break;
        }
    }
    /**
     * 智能排序
     */
    public void showOrderDialog(){
        if(orderDialog==null){
            this.orderDialog = new AlertDialog.Builder(this,R.style.add_dialog).create();
            this.orderDialog.setCanceledOnTouchOutside(true);

            LayoutInflater mInflater= LayoutInflater.from(this);
            final Window window = this.orderDialog.getWindow();
            window.setGravity(Gravity.TOP);  //此处可以设置dialog显示的位置
            window.getAttributes().x = p[0];
            window.getAttributes().y = p[1]-statusBarHeight;


            this.orderDialog.show();
            final View addView = mInflater.inflate(R.layout.order_dialog, null);
            addView.findViewById(R.id.course_0).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list_top_textview.setText("全部订单");
                    mPresenter.getOrderClassfyList("order_type","");
                    orderDialog.dismiss();
                }
            });
            addView.findViewById(R.id.course_1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list_top_textview.setText("未审核");
                    mPresenter.getOrderClassfyList("order_type","未审核");
                    orderDialog.dismiss();
                }
            });
            addView.findViewById(R.id.course_2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     list_top_textview.setText("备货中");
                    mPresenter.getOrderClassfyList("order_type","备货中");
                    orderDialog.dismiss();
                }
            });
            addView.findViewById(R.id.course_3).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list_top_textview.setText("已发货");
                    mPresenter.getOrderClassfyList("order_type","已发货");
                    orderDialog.dismiss();
                }
            });
            addView.findViewById(R.id.course_4).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list_top_textview.setText("已确认");
                    mPresenter.getOrderClassfyList("order_type","已确认");
                    orderDialog.dismiss();
                }
            });
//            addView.findViewById(R.id.course_5).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    list_top_textview.setText("已完成");
//                    mPresenter.getOrderClassfyList("order_type","已完成");
//                    orderDialog.dismiss();
//                }
//            });
            this.orderDialog.setContentView(addView);
            window.setLayout(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        }else
            orderDialog.show();
    }
    @Override
    public int getLayoutResource() {
        return R.layout.activity_order_classify;
    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected OrderClassifyPresenterImpl createPresenter() {
        return new OrderClassifyPresenterImpl();
    }


    @Override
    public XExpandableListView getXExpandableListView() {
        return xelv_order_classify;
    }

    @Override
    public void setAdapter(ExpandableOrderAdapter adapter) {
        xelv_order_classify.setAdapter(adapter);
//        int getCount = elv_order_classify.getCount();
//        for (int i = 0; i < getCount; i++) {
//            elv_order_classify.expandGroup(i);
//        }
//        if (progressDialog != null){
//            progressDialog.dismiss();
//        }
        //ExpandableListView不会折叠
//        elv_order_classify.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
//                return true;
//            }
//        });
    }

    @Override
    public RelativeLayout getNoDataView() {
        return rel_no_data;
    }

    @Override
    public void orderDeleteSuccess() {

    }

    @Override
    public void orderDeleteFailed() {

    }


    @Override
    public Context getPresenterContext() {
        return this;
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
