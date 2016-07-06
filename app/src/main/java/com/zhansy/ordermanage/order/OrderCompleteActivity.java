package com.zhansy.ordermanage.order;

import android.content.Intent;
import android.os.Bundle;

import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.base.mvp.view.IBaseUi;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class OrderCompleteActivity extends MVPBaseActivity {
    @Override
    public int getLayoutResource() {
        return R.layout.activity_base;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String key = intent.getStringExtra(IBaseUi.KEY_GID);  //传过来的title
        getSupportFragmentManager().beginTransaction().add(R.id.container, CompleteFragment.getInstance(key)).commit();
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
