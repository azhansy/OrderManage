package com.zhansy.ordermanage.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.base.mvp.view.IBaseUi;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 * Activity基本类
 */
public abstract class MVPBaseActivity<Presenter extends MVPBasePresenter> extends AppCompatActivity {
    private AppManager appManager = AppManager.getAppManager();
    protected Presenter mPresenter;
    public abstract int getLayoutResource();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutResId = getLayoutResource();
        if (layoutResId != 0) {
            setContentView(layoutResId);
        }
        if (usePresenter()) {
            mPresenter = createPresenter();
            mPresenter.attach(getBaseUi());
        }
        Log.d("ActivityManage", "路径："+this.getPackageName()+"."+this.getClass().getName());
        appManager.addActivity(this);
    }
    public AppManager getAppManager(){
        return appManager;
    }
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.inject(this);
    }
    @Override
    public void onDestroy() {
        ButterKnife.reset(this);
        if (mPresenter != null) {
            mPresenter.detach(getBaseUi());
            mPresenter = null;
        }
        appManager.removeActivity(this);
//        ImageLoader.getInstance().clearMemoryCache();//清除缓存
        super.onDestroy();
    }
    /**
     * 是否需要使用Presenter
     */
    protected abstract boolean usePresenter();

    protected abstract Presenter createPresenter();

    /**
     * 如果需要使用Presenter，这里也需要返回相应的Ui对象
     * MVPBaseActivity implement Ui
     */
    @SuppressWarnings(value = "unchecked")
    protected <Ui extends IBaseUi> Ui getBaseUi() {
        return (Ui) this;
    }

    public static void launch(Context context, Class<? extends MVPBaseActivity> cls) {
        launch(context, cls, null);
    }
    public static void launch(Context context, Class<? extends MVPBaseActivity> cls, String gid) {
        Intent intent = new Intent(context, cls);
        if (!TextUtils.isEmpty(gid)) {
            intent.putExtra(IBaseUi.KEY_GID, gid);
        }
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }
}
