package com.example.traver;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.traver.fuping_fragment.JindianNFragment;
import com.example.traver.fuping_fragment.TiyanFragment;
import com.example.traver.fuping_fragment.TongnianFragment;
import com.example.traver.fuping_fragment.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class NongcunfupinActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    List<Fragment> fragmentList;
    List<String> titleList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nongcunfupin);
        initBuJu();

    }
    public void initBuJu(){
        viewPager=(ViewPager)findViewById(R.id.viewpager_nong);
        tabLayout=(TabLayout)findViewById(R.id.tab_top);
        //添加fragment
        fragmentList=new ArrayList<>();
        fragmentList.add(new JindianNFragment());
        fragmentList.add(new TiyanFragment());
        fragmentList.add(new TongnianFragment());
        //添加标题
        titleList=new ArrayList<>();
        titleList.add("特色景点");
        titleList.add("农家体验");
        titleList.add("童年游乐");
        //添加tab
        tabLayout.addTab(tabLayout.newTab().setText("特色景点"));
        tabLayout.addTab(tabLayout.newTab().setText("农家体验"));
        tabLayout.addTab(tabLayout.newTab().setText("童年游乐"));

        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),fragmentList
        ,titleList);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager,true);
    }
}
