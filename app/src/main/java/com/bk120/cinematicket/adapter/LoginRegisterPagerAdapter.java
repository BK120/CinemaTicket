package com.bk120.cinematicket.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by bk120 on 2017/3/7.
 * 登录注册界面的PagerAdapter
 */

public class LoginRegisterPagerAdapter extends FragmentPagerAdapter{
    private List<Fragment> list;
    public LoginRegisterPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public LoginRegisterPagerAdapter(FragmentManager fm,List<android.support.v4.app.Fragment> l) {
        super(fm);
        this.list=l;
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return list.get(position);
    }
}
