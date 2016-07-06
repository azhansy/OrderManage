package com.zhansy.ordermanage.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.base.mvp.view.IBaseUi;

import butterknife.ButterKnife;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public abstract class  MVPBaseFragment<Presenter extends MVPBasePresenter> extends Fragment {

    protected Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (usePresenter()) {
            mPresenter = createPresenter();
            mPresenter.attach(getBaseUi());
        }
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int resLayoutId = getLayoutResource();
        if (resLayoutId != 0) {
            View view = inflater.inflate(resLayoutId, container, false);
            ButterKnife.inject(this, view);
            return view;
        }
        return null;
    }

    public abstract int getLayoutResource();

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detach(getBaseUi());
            mPresenter = null;
        }
        super.onDestroy();
    }



    /**
     * 是否需要使用Presenter
     */
    protected abstract boolean usePresenter();

    protected abstract Presenter createPresenter();

    /**
     * 如果需要使用Presenter，这里也需要返回相应的Ui对象
     * MVPBaseFragment implement Ui
     */
    @SuppressWarnings(value = "unchecked")
    protected <Ui extends IBaseUi> Ui getBaseUi() {
        return (Ui) this;
    }
}
