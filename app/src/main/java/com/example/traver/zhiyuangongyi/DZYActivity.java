package com.example.traver.zhiyuangongyi;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.traver.R;
import com.example.traver.fuping_fragment.ViewPagerAdapter;
import com.example.traver.zhiyuangongyi.zhiyuan.PinlunFragment;
import com.example.traver.zhiyuangongyi.zhiyuan.XiangqingFragment;
import com.example.traver.zong_package.Pinglun;

import java.util.ArrayList;
import java.util.List;

public class DZYActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    List<Fragment> fragmentList;
    List<String> titleList;
    List<Pinglun> pinglunList;
    Zhiyuan zhiyuan;
    TextView biaoti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dzy);
        init();
    }
    public void init(){
        Intent intent=getIntent();
        zhiyuan= (Zhiyuan) intent.getSerializableExtra("zhiyuan");
        biaoti=(TextView)findViewById(R.id.biaoti_zy);
        biaoti.setText(zhiyuan.name);
        viewPager=(ViewPager)findViewById(R.id.zhiyuanvp);
        tabLayout=(TabLayout)findViewById(R.id.zhiyuantab);
        fragmentList=new ArrayList<>();
        fragmentList.add(new XiangqingFragment());
        fragmentList.add(new PinlunFragment());
        titleList=new ArrayList<>();
        titleList.add("详情");
        titleList.add("评论");

        tabLayout.addTab(tabLayout.newTab().setText("详情"));
        tabLayout.addTab(tabLayout.newTab().setText("评论"));

        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),fragmentList,titleList);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    public Zhiyuan getZhiyuan(){
        return zhiyuan;
    }
    public String getPinglun(){
        return zhiyuan.name;
    }
}
