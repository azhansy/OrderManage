package com.zhansy.ordermanage.inform;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.dialog.listener.OnBtnLeftClickL;
import com.flyco.dialog.listener.OnBtnRightClickL;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.base.dialog.MyDialog;
import com.zhansy.ordermanage.base.mvp.model.InformBean;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.event.InformEvent;
import com.zhansy.ordermanage.inform.adapter.InformAdapter;
import com.zhansy.ordermanage.inform.presenter.InformActivityPresenterImpl;
import com.zhansy.ordermanage.inform.view.InformView;
import com.zhansy.ordermanage.utils.ToastUtil;

import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 * 厂家公告 ，详细信息
 */
public class InformDetailActivity extends MVPBaseActivity<InformActivityPresenterImpl> implements InformView{
    @InjectView(R.id.inform_txt)
    EditText inform_txt;
    @InjectView(R.id.inform_time)
    EditText inform_time;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.ll_time)
    LinearLayout ll_time;
    @InjectView(R.id.btn_inform)
    Button btn_inform;

    @OnClick({R.id.btn_back,R.id.btn_inform})
    void OnClick(View v){
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_inform:
                if (!inform_txt.getText().toString().isEmpty()){
                    MyDialog.showCustomDialog(this, "确定添加该公告？", new OnBtnRightClickL() {
                        @Override
                        public void onBtnRightClick() {
                            String info = inform_txt.getText().toString();
                            mPresenter.informCreate(info);
                        }
                    });
                }else {
                    ToastUtil.showToast(this,"请输入公告信息！");
                }
                break;
        }
    }

    private InformBean bean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bean = (InformBean) getIntent().getSerializableExtra("InformBean");
        if (bean != null){
            inform_txt.setText(bean.getContent());
            inform_time.setText(bean.getCreatetime());
            ll_time.setVisibility(View.VISIBLE);
            btn_inform.setVisibility(View.GONE);
            init(false);
        }else {
            ll_time.setVisibility(View.GONE);
            btn_inform.setVisibility(View.VISIBLE);
            init(true);
            tv_title.setText("添加公告");
        }
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_inform_detail;
    }

    /**
     * @param isEnable 是否初始化
     */
    private void init(boolean isEnable) {
        inform_txt.setEnabled(isEnable);
        inform_time.setEnabled(isEnable);
    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected InformActivityPresenterImpl createPresenter() {
        return new InformActivityPresenterImpl();
    }


    @Override
    public void setInformAdapter(InformAdapter adapter) {

    }

    @Override
    public ListView getListView() {
        return null;
    }

    @Override
    public RelativeLayout getRelNoData() {
        return null;
    }

    @Override
    public void deleteInformSuccess() {
        MyDialog.showDoubleDialog(this, "添加成功！", "返回", "确定", new OnBtnLeftClickL() {
            @Override
            public void onBtnLeftClick() {
                MyDialog.dimiss();
                finish();
            }
        }, new OnBtnRightClickL() {
            @Override
            public void onBtnRightClick() {
                MyDialog.dimiss();
            }
        });
        EventBus.getDefault().post(new InformEvent());
    }

    @Override
    public void deleteInformFailed() {
        ToastUtil.showToast(this,"添加失败！");
    }

    @Override
    public void informDelete(int code) {
//        mPresenter.informDelete(code);
    }

    @Override
    public Context getPresenterContext() {
        return this;
    }


}
