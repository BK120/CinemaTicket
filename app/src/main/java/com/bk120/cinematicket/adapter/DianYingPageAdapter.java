package com.bk120.cinematicket.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by bk120 on 2017/3/3.
 */

public class DianYingPageAdapter extends FragmentPagerAdapter{
    private List<Fragment> list;
    public DianYingPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public DianYingPageAdapter(FragmentManager fm,List<Fragment> l) {
        super(fm);
        this.list=l;
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }
}
