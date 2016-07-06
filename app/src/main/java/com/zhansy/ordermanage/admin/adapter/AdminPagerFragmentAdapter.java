package com.zhansy.ordermanage.admin.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zhansy.ordermanage.admin.MemberAdminFragment;
//import com.zhansy.ordermanage.admin.OrderAdminFragment;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class AdminPagerFragmentAdapter extends FragmentPagerAdapter {

    String[] title = {"会员信息","订单审核"};
    MemberAdminFragment memberAdminFragment;
//    OrderAdminFragment orderAdminFragment;

    public AdminPagerFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                memberAdminFragment = new MemberAdminFragment();
                return memberAdminFragment;
            case 1:
//                orderAdminFragment = new OrderAdminFragment();
//                return orderAdminFragment;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
