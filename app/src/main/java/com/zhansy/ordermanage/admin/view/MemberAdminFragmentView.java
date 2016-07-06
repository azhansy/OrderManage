package com.zhansy.ordermanage.admin.view;

import android.content.Intent;
import android.view.View;

import com.zhansy.ordermanage.base.mvp.view.IBaseUi;
import com.zhansy.ordermanage.base.widget.XListView;
import com.zhansy.ordermanage.admin.adapter.MemberAdapter;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface MemberAdminFragmentView extends IBaseUi {

    XListView getXListView();

    void setMemberAdapter(MemberAdapter adapter);
    View getNoDataView();
}
