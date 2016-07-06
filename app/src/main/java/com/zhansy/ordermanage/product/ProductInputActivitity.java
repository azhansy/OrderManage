package com.zhansy.ordermanage.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.base.mvp.model.ProductBean;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;

/**
 * Created by ZHNASY on 2016/4/12.
 */
public class ProductInputActivitity extends MVPBaseActivity {
    @Override
    public int getLayoutResource() {
        return R.layout.activity_base;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProductBean bean = (ProductBean) getIntent().getSerializableExtra("ProductBean");
        if (bean != null){
            getSupportFragmentManager().beginTransaction().add(R.id.container, ProductUpdateFragment.getInstance(bean),"ProductUpdateFragment").commit();
        }else {
            getSupportFragmentManager().beginTransaction().add(R.id.container, ProductInputFragment.getInstance(),"ProductInputFragment").commit();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*在这里，我们通过碎片管理器中的Tag，就是每个碎片的名称，来获取对应的fragment*/
        Fragment f = getSupportFragmentManager().findFragmentByTag("ProductInputFragment");
        Fragment f1 = getSupportFragmentManager().findFragmentByTag("ProductUpdateFragment");
        /*然后在碎片中调用重写的onActivityResult方法*/
        if (f!=null){
            f.onActivityResult(requestCode, resultCode, data);
        }
        if (f1 != null){
            f1.onActivityResult(requestCode, resultCode, data);
        }
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
