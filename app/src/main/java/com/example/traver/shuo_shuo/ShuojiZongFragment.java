package com.example.traver.shuo_shuo;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.traver.FashuoshuoActivity;
import com.example.traver.R;

import com.example.traver.fuping_fragment.ViewPagerAdapter;
import com.example.traver.util.DatabaseUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * Created by wangning on 2017/5/4.
 */

public class ShuojiZongFragment extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    List<Fragment> fragmentList;
    List<String> titleList;
    Button fashuo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saved){
        View view=inflater.inflate(R.layout.shuo_zong_fragment,container,false);
        initView(view);
        return view;
    }

    public void initView(View view){
        fashuo=(Button)view.findViewById(R.id.fashuobtn);
        fashuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DatabaseUtil.isDenglu()){
                    Intent intent=new Intent();
                    intent.setClass(getActivity(), FashuoshuoActivity.class);
                    startActivityForResult(intent,1);
                }else {
                    Toast.makeText(getContext(),"请先登录",Toast.LENGTH_SHORT).show();
                }

            }
        });
        Log.d("INIT","initview ");
        viewPager=(ViewPager)view.findViewById(R.id.shuo_vp);
        tabLayout=(TabLayout)view.findViewById(R.id.shuotablayout);
        //添加fragment
        fragmentList=new ArrayList<>();
        fragmentList.add(new FenxiangFragment());
        fragmentList.add(new TiwenFragment());
        fragmentList.add(new GongyiFragment());
        //添加标题
        titleList=new ArrayList<>();
        titleList.add("分享");
        titleList.add("提问");
        titleList.add("公益");
        //添加tab
        tabLayout.addTab(tabLayout.newTab().setText("分享"));
        tabLayout.addTab(tabLayout.newTab().setText("提问"));
        tabLayout.addTab(tabLayout.newTab().setText("公益"));
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getChildFragmentManager(),fragmentList
                ,titleList);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager,true);
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK){

                }
        }
    }
}
