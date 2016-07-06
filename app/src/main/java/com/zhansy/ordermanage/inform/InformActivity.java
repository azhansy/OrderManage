package com.zhansy.ordermanage.inform;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.db.LitePalUtil;
import com.zhansy.ordermanage.event.InformEvent;
import com.zhansy.ordermanage.inform.adapter.InformAdapter;
import com.zhansy.ordermanage.inform.presenter.InformActivityPresenterImpl;
import com.zhansy.ordermanage.inform.view.InformView;
import com.zhansy.ordermanage.utils.ToastUtil;

import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 * 厂家公告
 */
public class InformActivity extends MVPBaseActivity<InformActivityPresenterImpl> implements InformView {
    @InjectView(R.id.lv_inform)
    ListView lv_inform;
    @InjectView(R.id.rel_no_data)
    RelativeLayout rel_no_data;
    @InjectView(R.id.fab)
    FloatingActionButton fab;

    @OnClick({R.id.btn_back,R.id.fab})
    void OnClick(View v){
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.fab:
                InformDetailActivity.launch(this, InformDetailActivity.class);
                break;
        }
    }

    private int delId;//删除的ID公告

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mPresenter.getData();
        //在要接收消息的页面注册
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getData();
    }
    public void onEventMainThread(InformEvent event){
        mPresenter.getData();
    }
    @Override
    public int getLayoutResource() {
        return R.layout.activity_inform;
    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected InformActivityPresenterImpl createPresenter() {
        return new InformActivityPresenterImpl();
    }

    @Override
    public void setInformAdapter(InformAdapter adapter) {
        lv_inform.setAdapter(adapter);
    }

    @Override
    public ListView getListView() {
        return lv_inform;
    }

    @Override
    public RelativeLayout getRelNoData() {
        return rel_no_data;
    }

    @Override
    public void deleteInformSuccess() {
        ToastUtil.showToast(this,"删除成功");
        mPresenter.getData();//删除成功，重新刷新
        LitePalUtil.delectInformBean(delId);
    }

    @Override
    public void deleteInformFailed() {
        ToastUtil.showToast(this,"删除失败");
    }

    @Override
    public void informDelete(int code) {
        this.delId = code;
        mPresenter.informDelete(code);
    }

    @Override
    public Context getPresenterContext() {
        return this;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //解除注册
        EventBus.getDefault().unregister(this);
    }
}
