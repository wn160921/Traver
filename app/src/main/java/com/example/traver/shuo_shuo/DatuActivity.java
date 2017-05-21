package com.example.traver.shuo_shuo;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.traver.R;;
import java.util.ArrayList;
import java.util.List;

public class DatuActivity extends AppCompatActivity {
    ViewPager viewPager;
    List<ImageView> imageViewList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datu);
        //viewPager=(ViewPager)findViewById(R.id.datuvp);
        Intent intent=getIntent();
        Shuo shuo= (Shuo) intent.getSerializableExtra("shuo");
        imageViewList=new ArrayList<>();
        viewPager=(ViewPager)findViewById(R.id.datuvp);
        if(shuo.getimage1path()!=null){
            ImageView imageView1=new ImageView(this);
            imageView1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            Glide.with(this).load("http://119.23.34.226/"+shuo.getimage1path()).into(imageView1);
            imageViewList.add(imageView1);
        }
        if(shuo.getImage2()!=null){
            ImageView imageView2=new ImageView(this);
            imageView2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            Glide.with(this).load("http://119.23.34.226/"+shuo.getImage2()).into(imageView2);
            imageViewList.add(imageView2);
        }
        if(shuo.getImage3()!=null){
            ImageView imageView3=new ImageView(this);
            imageView3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            Glide.with(this).load("http://119.23.34.226/"+shuo.getImage3()).into(imageView3);
            imageViewList.add(imageView3);
        }
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imageViewList.size();
            }
            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }
            @Override
            public void destroyItem(ViewGroup container,int position,Object o){}
            @Override
            public Object instantiateItem(ViewGroup container, int position){
                final View child=imageViewList.get(position);
                if(child.getParent()!=null){
                    container.removeView(child);
                }
                container.addView(imageViewList.get(position));
                return imageViewList.get(position);
            }
        });
    }
}
