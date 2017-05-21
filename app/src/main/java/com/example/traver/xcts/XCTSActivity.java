package com.example.traver.xcts;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.traver.R;
import com.example.traver.fuping_fragment.JindianNFragment;
import com.example.traver.fuping_fragment.TiyanFragment;
import com.example.traver.fuping_fragment.TongnianFragment;
import com.example.traver.fuping_fragment.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class XCTSActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    List<Fragment> fragmentList;
    List<String> titleList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xcts);
        initBuJu();

    }
    public void initBuJu(){
        viewPager=(ViewPager)findViewById(R.id.xctsviewpager);
        tabLayout=(TabLayout)findViewById(R.id.xctstab);
        //添加fragment
        fragmentList=new ArrayList<>();
        fragmentList.add(new TesejdFragment());
        fragmentList.add(new NjtyFragment());
        //添加标题
        titleList=new ArrayList<>();
        titleList.add("特色景点");
        titleList.add("农家体验");
        //添加tab
        tabLayout.addTab(tabLayout.newTab().setText("特色景点"));
        tabLayout.addTab(tabLayout.newTab().setText("农家体验"));

        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),fragmentList
                ,titleList);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager,true);
    }
}
