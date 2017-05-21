package com.example.traver.fuping_fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by wangning on 2017/4/19.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private List<String> titlelist;
    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titlelist) {
        super(fm);
        this.fragmentList=fragmentList;
        this.titlelist=titlelist;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
    @Override
    public CharSequence getPageTitle(int position){
        return titlelist.get(position);
    }
}
