package com.example.traver.dianpuruogan;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.traver.R;
import com.example.traver.fuping_fragment.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangning on 2017/5/4.
 */

public class Diannzong_fragment extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    List<Fragment> fragmentList;
    List<String> titleList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saved){
        View view=inflater.inflate(R.layout.dianpu_zong_fragment,container,false);
        initView(view);
        return view;
    }

    public void initView(View view){
        Log.d("INIT","initview ");
        viewPager=(ViewPager)view.findViewById(R.id.dianpuvp);
        tabLayout=(TabLayout)view.findViewById(R.id.dianputablayout);
        //添加fragment
        fragmentList=new ArrayList<>();
        fragmentList.add(new Tesechanpingfragment());
        fragmentList.add(new MeishiFragment());
        //添加标题
        titleList=new ArrayList<>();
        titleList.add("特色产品");
        titleList.add("美食推荐");
        //添加tab
        tabLayout.addTab(tabLayout.newTab().setText("特色产品"));
        tabLayout.addTab(tabLayout.newTab().setText("美食推荐"));
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getChildFragmentManager(),fragmentList
                ,titleList);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager,true);
    }

}
