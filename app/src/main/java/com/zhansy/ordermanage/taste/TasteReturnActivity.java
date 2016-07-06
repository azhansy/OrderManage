package com.zhansy.ordermanage.taste;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.flyco.dialog.listener.OnBtnLeftClickL;
import com.flyco.dialog.listener.OnBtnRightClickL;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.base.dialog.MyDialog;
import com.zhansy.ordermanage.base.mvp.model.TasteReturnBean;
import com.zhansy.ordermanage.taste.presenter.TasteReturnPresenterImpl;
import com.zhansy.ordermanage.taste.view.TasteReturnView;
import com.zhansy.ordermanage.utils.PattenUtil;
import com.zhansy.ordermanage.utils.ToastUtil;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 意见反馈，用于反馈给厂家
 */
public class TasteReturnActivity extends MVPBaseActivity<TasteReturnPresenterImpl> implements TasteReturnView{

    @InjectView(R.id.return_back_editetext)EditText returnContent;
    @InjectView(R.id.edit_phone_number)EditText edit_phone_number;
//    @InjectView(R.id.btn_send)Button btn_send;
    @OnClick({R.id.btn_back,R.id.btn_send})
    void OnClick(View v){
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_send:
                if (PattenUtil.validateMobileNO(edit_phone_number.getText().toString().trim())){
                    Toast.makeText(this,"请输入正确的11位手机号",Toast.LENGTH_SHORT).show();
                }
                if (getTasteMassage()) {
                    MyDialog.showCustomDialog(this, "确定提出建议？", new OnBtnRightClickL() {
                        @Override
                        public void onBtnRightClick() {
                            mPresenter.commitTasteMassege(bean);
                            MyDialog.dimiss();
                        }
                    });

                }else {
                    Toast.makeText(this,"反馈内容或手机号码不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private TasteReturnBean bean;
    private boolean getTasteMassage(){
        if (!returnContent.getText().toString().equals("") && !edit_phone_number.getText().toString().equals("")){
            bean = new TasteReturnBean();
            bean.setContent(returnContent.getText().toString().trim());
            bean.setPhone(edit_phone_number.getText().toString().trim());
            return true;
        }
        return false;
    }
    @Override
    public int getLayoutResource() {
        return R.layout.activity_return_back;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected TasteReturnPresenterImpl createPresenter() {
        return new TasteReturnPresenterImpl();
    }

    @Override
    public void commitSuccess() {
        MyDialog.showDoubleDialog(this, "提交成功", "返回主页面", "确定", new OnBtnLeftClickL() {
            @Override
            public void onBtnLeftClick() {
                finish();
            }
        }, new OnBtnRightClickL() {
            @Override
            public void onBtnRightClick() {
                MyDialog.dimiss();
            }
        });
    }

    @Override
    public void commitFailed() {
        ToastUtil.showToast(this,"提交失败，请稍后再试");
    }

    @Override
    public Context getPresenterContext() {
        return this;
    }
}
