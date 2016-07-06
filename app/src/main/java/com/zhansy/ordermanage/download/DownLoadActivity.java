package com.zhansy.ordermanage.download;

import android.os.Bundle;

import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.me.MeFragment;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class DownLoadActivity extends MVPBaseActivity {

    @Override
    public int getLayoutResource() {
        return R.layout.activity_base;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().add(R.id.container, DownLoadFragment.newInstance()).commit();
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
