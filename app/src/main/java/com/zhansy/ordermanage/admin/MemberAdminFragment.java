package com.zhansy.ordermanage.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseFragment;
import com.zhansy.ordermanage.base.widget.XListView;
import com.zhansy.ordermanage.admin.adapter.MemberAdapter;
import com.zhansy.ordermanage.admin.view.MemberAdminFragmentView;
import com.zhansy.ordermanage.user.presenter.UserPresenterImpl;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class MemberAdminFragment extends MVPBaseFragment<UserPresenterImpl> implements MemberAdminFragmentView ,XListView.IXListViewListener{

    @InjectView(R.id.xlv_member_manage)
    XListView xlv_member_manage;
    @InjectView(R.id.rel_no_data)
    View rel_no_data;

    @OnClick(R.id.btn_back)
    void OnClick(){
        getActivity().finish();
    }
    public static MemberAdminFragment instance;
    public static MemberAdminFragment getInstance(){
        if (instance == null){
            instance = new MemberAdminFragment();
        }
        return instance;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mPresenter.getData();
        xlv_member_manage.setPullLoadEnable(true);
        xlv_member_manage.setXListViewListener(this);
        xlv_member_manage.setPullRefreshEnable(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getData();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_member_manage;
    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected UserPresenterImpl createPresenter() {
        return new UserPresenterImpl();
    }

    @Override
    public XListView getXListView() {
        return xlv_member_manage;
    }

    @Override
    public void setMemberAdapter(MemberAdapter adapter) {
        xlv_member_manage.setAdapter(adapter);
    }

    @Override
    public View getNoDataView() {
        return rel_no_data;
    }


    @Override
    public Context getPresenterContext() {
        return getActivity();
    }

    @Override
    public void onRefresh() {
        mPresenter.Refresh("");
    }

    @Override
    public void onLoadMore() {
        mPresenter.Load("");
    }
}
