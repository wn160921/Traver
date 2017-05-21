package com.example.traver.jiudianpiaowu;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.traver.R;
import com.example.traver.fuping_fragment.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class JiudianpiaowuActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    List<Fragment> fragmentList;
    List<String> titleList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiudianpiaowu);
        initBuJu();
    }
    public void initBuJu(){
        viewPager=(ViewPager)findViewById(R.id.jpvp);
        tabLayout=(TabLayout)findViewById(R.id.jptablayout);
        //添加fragment
        fragmentList=new ArrayList<>();
        fragmentList.add(new JiudianFragment());
        fragmentList.add(new FeijiFragment());
        fragmentList.add(new HuocheFragment());
        //添加标题
        titleList=new ArrayList<>();
        titleList.add("酒店");
        titleList.add("飞机");
        titleList.add("火车");
        //添加tab
        tabLayout.addTab(tabLayout.newTab().setText("酒店"));
        tabLayout.addTab(tabLayout.newTab().setText("飞机"));
        tabLayout.addTab(tabLayout.newTab().setText("火车"));

        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),fragmentList
                ,titleList);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager,true);
    }
}
