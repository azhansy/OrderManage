package com.zhansy.ordermanage.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;

import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.base.mvp.view.IBaseUi;

import java.io.File;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class UserActivity extends MVPBaseActivity {
    public String curFragmentTag = "";

    @Override
    public int getLayoutResource() {
        return R.layout.activity_base;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String key = getIntent().getStringExtra(IBaseUi.KEY_GID);  //传过来的值
        if (key.equals("update")){
            curFragmentTag = "UserUpdateFragment";
            getSupportFragmentManager().beginTransaction().add(R.id.container, UserUpdateFragment.getInstance(),"UserUpdateFragment").commit();
        }else {
            curFragmentTag = "UserFragment";
            getSupportFragmentManager().beginTransaction().add(R.id.container, UserFragment.getInstance(),"UserFragment").commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*在这里，我们通过碎片管理器中的Tag，就是每个碎片的名称，来获取对应的fragment*/
        Fragment f = getSupportFragmentManager().findFragmentByTag(curFragmentTag);
        /*然后在碎片中调用重写的onActivityResult方法*/
        f.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode){
//            case 1:
//                if (resultCode == RESULT_OK) {
//                    presenter.cropPhoto(data.getData());
//                }else{
//                    presenter.cancel();
//                }
//                break;
//            case 2:
//                if (resultCode == RESULT_OK) {
//                    File temp = new File(Environment.getExternalStorageDirectory()
//                            + "/head.jpg");
//                    presenter.cropPhoto(Uri.fromFile(temp));
//                    fileName=temp.toString();
//                }else{
//                    presenter.cancel();
//                }
//                break;
//            case 3:
//                if (data != null) {
//                    Bundle extras = data.getExtras();
//                    head = extras.getParcelable("data");
//                    if(head!=null){
//                        presenter.saveSd(head);
//                        presenter.submitHead();
//                        user_head_img.setImageBitmap(head);
//                        presenter.cancel();
//                    }
//                }
//                break;
//            case 4:
//                if(data!=null){
//                    String region=data.getStringExtra("region");
//                    address_txt.setText(region);
//                }
//        }
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
