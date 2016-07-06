package com.zhansy.ordermanage.feedback;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.base.mvp.model.FeedBackBean;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.base.mvp.view.IBaseUi;
import com.zhansy.ordermanage.base.widget.XListView;
import com.zhansy.ordermanage.db.LitePalUtil;
import com.zhansy.ordermanage.feedback.presenter.FeedbackPresenterImpl;
import com.zhansy.ordermanage.feedback.view.FeedBackView;
import com.zhansy.ordermanage.order.adapter.FeedBackAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 *
 * 商品信息反馈
 */
public class FeedbackActivity extends MVPBaseActivity<FeedbackPresenterImpl> implements FeedBackView,XListView.IXListViewListener{
    @InjectView(R.id.xlv_feedback)
    XListView xlv_feedback;

    @InjectView(R.id.rel_no_data)
    View rel_no_data;

    @OnClick(R.id.btn_back)
    void OnClick() {
        finish();
    }

    private FeedBackAdapter feedBackAdapter;

    private String productCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productCode = getIntent().getStringExtra(IBaseUi.KEY_GID);
        mPresenter.getData("product_code",productCode);
        xlv_feedback.setPullRefreshEnable(true);
        xlv_feedback.setPullLoadEnable(true);
        xlv_feedback.setXListViewListener(this);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_feedback;
    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected FeedbackPresenterImpl createPresenter() {
        return new FeedbackPresenterImpl();
    }

    @Override
    public void setFeedBackAdapter(FeedBackAdapter adapter) {
        xlv_feedback.setAdapter(adapter);
    }

    @Override
    public XListView getXListView() {
        return xlv_feedback;
    }

    @Override
    public View getNoDataView() {
        return rel_no_data;
    }

    @Override
    public void deleteSuccess() {

    }

    @Override
    public void deleteFailed() {

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
