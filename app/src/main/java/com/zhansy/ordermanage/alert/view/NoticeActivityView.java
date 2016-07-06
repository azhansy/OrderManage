package com.zhansy.ordermanage.alert.view;

import android.view.View;
import android.widget.ListView;

import com.zhansy.ordermanage.base.mvp.view.IBaseUi;
import com.zhansy.ordermanage.alert.adapter.NoticeActivityAdapter;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface NoticeActivityView extends IBaseUi{

    void setNoticeActivityAdapter(NoticeActivityAdapter adapter);

    void setdelectTip(boolean b);

    View getRelNoData();

    ListView getListView();

    void setNotify(String tip);


}
