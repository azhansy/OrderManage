package com.zhansy.ordermanage.inform.view;

import android.widget.ListView;
import android.widget.RelativeLayout;

import com.zhansy.ordermanage.base.mvp.view.IBaseUi;
import com.zhansy.ordermanage.inform.adapter.InformAdapter;

/**
 * Created by Administrator on 2016/4/11.
 */
public interface InformView extends IBaseUi {
    void setInformAdapter(InformAdapter adapter);
    ListView getListView();
    RelativeLayout getRelNoData();
    void deleteInformSuccess();
    void deleteInformFailed();

    void informDelete(int code);//删除公告
}
