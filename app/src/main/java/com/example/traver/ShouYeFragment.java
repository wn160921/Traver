package com.example.traver;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.transcode.BitmapToGlideDrawableTranscoder;
import com.example.traver.jiudianpiaowu.JiudianpiaowuActivity;
import com.example.traver.shougongzhizhuo.ShougongzhizhuoActivity;
import com.example.traver.shujubiao.Banben;
import com.example.traver.shujubiao.Lunbotu;
import com.example.traver.util.DatabaseUtil;
import com.example.traver.xcts.XCTSActivity;
import com.example.traver.youxijiyi.YouxijiyiActivity;
import com.example.traver.zhiyuangongyi.ZhiyuangongyiActivity;
import com.example.traver.zhumingjingdian.ZhumingjingdianActivity;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/28/028.
 */

public class ShouYeFragment extends Fragment {
    ViewPager viewPager;
    static List<ImageView> mList;
    Handler handler;
    Button sousuo;
    Button xiangcunteseBtn;
    Button zhiyuangongyiBtn;
    Button shougongzhizhuBtn;
    Button jiudianpiaowuBtn;
    Button zhumingjindianBtn;
    Button youxijiyiBtn;
    Runnable runnable;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.zhuye,null);
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                ss();
            }
        };
        getLunboimage();
        initview(view);
        initBtn(view);
        return view;
    }
    public void initBtn(View view){
        sousuo=(Button)view.findViewById(R.id.sousuo);
        xiangcunteseBtn=(Button)view.findViewById(R.id.xiangcuntese);
        zhiyuangongyiBtn=(Button) view.findViewById(R.id.zhiyuangongyi);
        shougongzhizhuBtn=(Button)view.findViewById(R.id.shougongzhizhuo);
        jiudianpiaowuBtn=(Button)view.findViewById(R.id.jiudianpiaowu);
        zhumingjindianBtn=(Button)view.findViewById(R.id.zhumingjinnqu);
        youxijiyiBtn=(Button)view.findViewById(R.id.youxijiyi);
        sousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),SearchActivity.class);
                startActivity(intent);
            }
        });
        xiangcunteseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), XCTSActivity.class);
                startActivity(intent);
            }
        });
        jiudianpiaowuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), JiudianpiaowuActivity.class);
                startActivity(intent);
            }
        });
        zhiyuangongyiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), ZhiyuangongyiActivity.class);
                startActivity(intent);
            }
        });
        youxijiyiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), YouxijiyiActivity.class);
                startActivity(intent);
            }
        });
        shougongzhizhuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),ShougongzhizhuoActivity.class);
                startActivity(intent);
            }
        });
        zhumingjindianBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), ZhumingjingdianActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initview(View view){

        viewPager=(ViewPager)view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new CycleAdapter());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        runnable=new Runnable() {
            @Override
            public void run() {
                int currentitem=viewPager.getCurrentItem();
                viewPager.setCurrentItem(currentitem+1);
                handler.postDelayed(this,3000);
            }
        };
        handler.postDelayed(runnable,3000);
    }

    @Override
    public void onPause(){
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    public void getLunboimage(){
        List<Banben> banbens =DataSupport.findAll(Banben.class);
        Log.d("ben di",String.valueOf(banbens.get(0).getBanbenhao()));
        if(banbens.get(0).getBanbenhao()>0){
            mList=new ArrayList<ImageView>();
            for(int i=0;i<3;i++) {
                ImageView img = new ImageView(getContext());
                img.setScaleType(ImageView.ScaleType.FIT_XY);
                mList.add(img);
            }
            ss();
        }else {
            mList=new ArrayList<ImageView>();
            for(int i=0;i<3;i++){
                ImageView img=new ImageView(getContext());
                img.setScaleType(ImageView.ScaleType.FIT_XY);
                mList.add(img);
            }
        }
            DatabaseUtil.genxinlunbo(handler);
    }
    class CycleAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }
        @Override
       public Object instantiateItem(ViewGroup container,int position){
            position=position%mList.size();
            final View child=mList.get(position);
            if(child.getParent()!=null){
                container.removeView(child);
            }
            container.addView(mList.get(position));
            return mList.get(position);
        }
        @Override
        public void destroyItem(ViewGroup container,int position,Object o){}
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }
    public void ss(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<Lunbotu> lunbotus=DataSupport.findAll(Lunbotu.class);
                int i=0;
                for(;i<3;i++){
                    mList.get(i).setImageBitmap(BitmapFactory.decodeByteArray(lunbotus.get(i).getImage(),0,lunbotus.get(i).getImage().length));
                }
            }
        });
    }

}


