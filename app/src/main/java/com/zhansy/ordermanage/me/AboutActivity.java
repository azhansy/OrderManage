package com.zhansy.ordermanage.me;

import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.base.widget.BaseImageView;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class AboutActivity extends MVPBaseActivity {
    @InjectView(R.id.btn_back)
    BaseImageView btn_back;

    @OnClick(R.id.btn_back)
    void OnClick(){
        finish();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_me_about;
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
