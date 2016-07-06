package com.zhansy.ordermanage.feedback.view;

import android.view.View;

import com.zhansy.ordermanage.base.mvp.view.IBaseUi;
import com.zhansy.ordermanage.base.widget.XListView;
import com.zhansy.ordermanage.order.adapter.FeedBackAdapter;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface FeedBackView extends IBaseUi {

    void setFeedBackAdapter(FeedBackAdapter adapter);

    XListView getXListView();

    View getNoDataView();

    void deleteSuccess();
    void deleteFailed();
}
