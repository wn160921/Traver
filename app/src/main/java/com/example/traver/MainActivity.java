package com.example.traver;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import com.example.traver.dianpuruogan.Diannzong_fragment;
import com.example.traver.shuo_shuo.ShuojiZongFragment;

public class MainActivity extends FragmentActivity {
    private FragmentTabHost mTabhost;
    private LayoutInflater layoutInflater;
    private Class fragmentArray[] ={ShouYeFragment.class, Diannzong_fragment.class, ShuojiZongFragment.class,WodeFragment.class};
    private int mImageviewArray[]={R.drawable.tab_home_btn,R.drawable.tab_dianpu_btn,R.drawable.tab_friends_btn,R.drawable.tab_wode_btn};
    private String textArray[]={"首页","店铺","朋友","我的"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_layout);
        initview();
    }
    private void initview(){
        layoutInflater=LayoutInflater.from(this);
        mTabhost=(FragmentTabHost)findViewById(R.id.tabhost);
        mTabhost.setup(this,getSupportFragmentManager(),R.id.realtabcontent);
        int count=fragmentArray.length;
        for(int i=0;i<count;i++){
            TabHost.TabSpec tabSpec=mTabhost.newTabSpec(textArray[i]).setIndicator(getTabItemView(i));
            mTabhost.addTab(tabSpec,fragmentArray[i],null);

        }
    }
    private View getTabItemView(int index){
        View view=layoutInflater.inflate(R.layout.tab_item_viiew,null);
        ImageView imageView=(ImageView)view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageviewArray[index]);
        return view;
    }
}
