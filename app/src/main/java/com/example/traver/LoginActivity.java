package com.example.traver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import com.example.traver.fuping_fragment.ViewPagerAdapter;
import com.example.traver.huanying.HuanyingFragment;
import com.example.traver.huanying.Image3Fragment;
import com.example.traver.huanying.ImageFragment;
import com.example.traver.huanying.image2Fragment;
import com.example.traver.shujubiao.Banben;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    ViewPager jieshaopager;
    List<Fragment> mList;
    List<String> titleList;
    LinearLayout pointGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Connector.getDatabase();
        List<Banben> Banbens =DataSupport.findAll(Banben.class);
        if(Banbens.size()==0){
            Banben banben=new Banben();
            banben.setName("lunboban");
            banben.setBanbenhao(0);
            banben.save();
        }else {
            Intent intent=new Intent();
            intent.setClass(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        initList();
        pointGroup=(LinearLayout)findViewById(R.id.pointGroup);
        jieshaopager=(ViewPager)findViewById(R.id.huan_yin_viewpager);
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),mList,titleList);
        jieshaopager.setAdapter(viewPagerAdapter);
        pointGroup.getChildAt(0).setSelected(true);
        jieshaopager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            int lastpoaition;
            @Override
            public void onPageSelected(int position) {
                pointGroup.getChildAt(lastpoaition).setSelected(false);
                pointGroup.getChildAt(position).setSelected(true);
                lastpoaition=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        jieshaopager.setPageTransformer(true, new ViewPager.PageTransformer() {
            private static final float MIN_SCALE = 0.85f;
            private static final float MIN_ALPHA = 0.5f;
            @Override
            public void transformPage(View page, float position) {
                if(position<-1){
                    page.setAlpha(0);
                }else if(position<1){
                    float scaleFactor=Math.max(MIN_SCALE,1-Math.abs(position));
                    page.setScaleX(scaleFactor);
                    page.setScaleY(scaleFactor);
                    page.setAlpha(MIN_ALPHA+(scaleFactor-MIN_SCALE)/(1-MIN_SCALE)*(1-MIN_ALPHA));
                }else {
                    page.setAlpha(0);
                }
            }

        });

    }

    public void initList(){
        mList=new ArrayList<>();
        ImageFragment imageFragment1=new ImageFragment();
        //Glide.with(this).load(R.drawable.one).into(imageFragment1.imageView);
        mList.add(imageFragment1);
        image2Fragment imageFragment2=new image2Fragment();
        //Glide.with(this).load(R.drawable.two).into(imageFragment1.imageView);
        mList.add(imageFragment2);
        Image3Fragment imageFragment3=new Image3Fragment();
        //Glide.with(this).load(R.drawable.three).into(imageFragment1.imageView);
        mList.add(imageFragment3);
        HuanyingFragment huanyingFragment=new HuanyingFragment();
        mList.add(huanyingFragment);
        titleList=new ArrayList<>();
        titleList.add("one");
        titleList.add("two");
        titleList.add("three");
        titleList.add("four");
    }
}
