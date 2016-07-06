package com.zhansy.ordermanage.admin;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.base.widget.PagerSlidingTabStrip;
import com.zhansy.ordermanage.admin.adapter.AdminPagerFragmentAdapter;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 * 管理员后台
 */
public class AdminBackStageActivity extends MVPBaseActivity {
    @InjectView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @InjectView(R.id.vp_admin)
    ViewPager vp_admin;

    @OnClick(R.id.btn_back)
    void OnClick(){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AdminPagerFragmentAdapter adapter = new AdminPagerFragmentAdapter(getSupportFragmentManager());
        vp_admin.setAdapter(adapter);
        vp_admin.setOffscreenPageLimit(2);
        tabs.setViewPager(vp_admin);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_backstage;
    }

    @Override
    protected boolean usePresenter() {
        return false;
    }

    @Override
    protected MVPBasePresenter createPresenter() {
        return null;
    }
}
