package com.zhansy.ordermanage.taste;

import android.os.Bundle;
import android.widget.TextView;

import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.base.mvp.model.InformBean;
import com.zhansy.ordermanage.base.mvp.model.TasteReturnBean;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 * 用户建议信息表
 */
public class TasteDetailActivity extends MVPBaseActivity{
    @InjectView(R.id.return_back_editetext)
    TextView return_back_editetext;
    @InjectView(R.id.edit_phone_number)
    TextView edit_phone_number;

    @OnClick(R.id.btn_back)
    void OnClick(){
        finish();
    }

    private TasteReturnBean bean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bean = (TasteReturnBean) getIntent().getSerializableExtra("TasteReturnBean");
        if (bean != null){
            return_back_editetext.setText(bean.getContent()+"\n"+bean.getCreatetime());
            edit_phone_number.setText(bean.getPhone());

        }
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_taste_detail;
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
