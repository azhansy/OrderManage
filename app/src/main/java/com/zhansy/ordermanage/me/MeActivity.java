package com.zhansy.ordermanage.me;

import android.os.Bundle;

import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class MeActivity extends MVPBaseActivity {

    @Override
    public int getLayoutResource() {
        return R.layout.activity_base;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().add(R.id.container, MeFragment.newInstance()).commit();
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
