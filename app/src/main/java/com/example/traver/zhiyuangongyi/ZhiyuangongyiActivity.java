package com.example.traver.zhiyuangongyi;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


import com.example.traver.R;
import com.example.traver.fuping_fragment.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ZhiyuangongyiActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    List<Fragment> fragmentList;
    List<String> titleList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhiyuangongyi);
        initFT();
    }

    public void initFT(){
        viewPager=(ViewPager)findViewById(R.id.zhigongvp);
        tabLayout=(TabLayout)findViewById(R.id.zhigongtablayout);
        fragmentList=new ArrayList<>();
        fragmentList.add(new GongyiFragment());
        fragmentList.add(new ZhiyuanFragment());
        titleList=new ArrayList<>();
        titleList.add("公益活动");
        titleList.add("志愿活动");

        tabLayout.addTab(tabLayout.newTab().setText("公益活动"));
        tabLayout.addTab(tabLayout.newTab().setText("志愿活动"));


        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),fragmentList,titleList);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
