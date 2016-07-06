//package com.zhansy.ordermanage.index;
//
//import android.os.Bundle;
//import android.os.PersistableBundle;
//import android.webkit.WebView;
//
//import com.zhansy.ordermanage.R;
//import com.zhansy.ordermanage.base.MVPBaseActivity;
//import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
//
//import butterknife.InjectView;
//import butterknife.OnClick;
//
///**
// * Created by ZHANSY
// * 邮箱：azhansy@hotmail.com
// */
//public class WebActivity extends MVPBaseActivity{
//    @InjectView(R.id.webview)
//    WebView webView;
//
//
//    @OnClick(R.id.btn_back)
//    void OnClick(){
//        finish();
//    }
//
//    @Override
//    public int getLayoutResource() {
//        return R.layout.activity_web;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
//        super.onCreate(savedInstanceState, persistentState);
//        String web = getIntent().getStringExtra("KEY_GID");
//        webView.loadUrl(web);
//        webView.setInitialScale(35);
//    }
//    @Override
//    protected boolean usePresenter() {
//        return false;
//    }
//
//    @Override
//    protected MVPBasePresenter createPresenter() {
//        return null;
//    }
//
//}
