package com.zhansy.ordermanage.index;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class BuyProductActivity extends MVPBaseActivity {
    private FragmentTransaction fragmentTransaction;
    private Fragment indexFragment;
    @Override
    public int getLayoutResource() {
        return R.layout.activity_buy_product;
    }

    @Override
    protected boolean usePresenter() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentTransaction =  getSupportFragmentManager().beginTransaction();
        indexFragment = IndexFragment.newInstance();
        fragmentTransaction.add(R.id.container,indexFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected MVPBasePresenter createPresenter() {
        return null;
    }
}
