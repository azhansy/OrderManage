package com.zhansy.ordermanage.order.adapter;

//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//
//import com.zhansy.ordermanage.order.DeliveryFragment;
//import com.zhansy.ordermanage.order.RestockFragment;

/**
 * Created by ZHANSY on 2016/3/1.

public class AdminPagerFragmentAdapter extends FragmentPagerAdapter {

    String[] title = {"出货单","进货单"};
    DeliveryFragment deliveryFragment;
    RestockFragment rstockFragment;

    public AdminPagerFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                deliveryFragment = new DeliveryFragment();
                return deliveryFragment;
            case 1:
                rstockFragment = new RestockFragment();
                return rstockFragment;
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
 */