package com.example.traver.fuping_fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.traver.R;
import com.example.traver.shuo_shuo.Shuo;
import com.example.traver.util.DatabaseUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    String name;
    Handler handler;
    TextView biaoti;
    Button fanhui;
    Button ditu;
    ViewPager nfj_viewPager;
    TextView jieshao;
    TextView pinglun;
    ProgressBar progressBar;
    static List<ImageView> mList;
    String uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initK();
        getMyIntent();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                switch (msg.what){
                    case 1:
                        List<NFJingdian> nfJingdianList= DataSupport.where("name = ?",name).find(NFJingdian.class);
                        NFJingdian nfJingdian=nfJingdianList.get(0);
                        pinglun.setText(nfJingdian.getPinglun());
                        jieshao.setText(nfJingdian.getJieshao());
                        uri=nfJingdianList.get(0).getUri();
                        mList=new ArrayList<>();
                        ImageView ima1=new ImageView(getBaseContext());
                        ima1.setScaleType(ImageView.ScaleType.FIT_XY);
                        Glide.with(getBaseContext()).load(nfJingdian.getPic1()).into(ima1);
                        mList.add(ima1);
                        ImageView ima2=new ImageView(getBaseContext());
                        ima2.setScaleType(ImageView.ScaleType.FIT_XY);
                        Glide.with(getBaseContext()).load(nfJingdian.getPic2()).into(ima2);
                        mList.add(ima2);
                        ImageView ima3=new  ImageView(getBaseContext());
                        ima3.setScaleType(ImageView.ScaleType.FIT_XY);
                        Glide.with(getBaseContext()).load(nfJingdian.getPic3()).into(ima3);
                        mList.add(ima3);
                        nfj_viewPager.setAdapter(new PagerAdapter() {
                            @Override
                            public int getCount() {
                                return mList.size();
                            }
                            public Object instantiateItem(ViewGroup container,int position){
                                final View child=mList.get(position);
                                if(child.getParent()!=null){
                                    container.removeView(child);
                                }
                                container.addView(mList.get(position));
                                return mList.get(position);
                            }
                            @Override
                            public boolean isViewFromObject(View view, Object object) {
                                return view==object;
                            }
                            @Override
                            public void destroyItem(ViewGroup container, int position, Object o){}
                        });
                        nfj_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

                        break;
                    case 2:
                        int progress=progressBar.getProgress();
                        progress+=10;
                        progressBar.setProgress(progress);
                        if(progress==30){
                            progressBar.setVisibility(View.GONE);
                        }
                    default:
                }
            }
        };
        getMyIntent();
        DatabaseUtil.getNFJDDetil(handler,name);
    }

    public void initK(){
        progressBar=(ProgressBar)findViewById(R.id.detail_pb);
        biaoti=(TextView)findViewById(R.id.biaoti_dt);
        fanhui=(Button)findViewById(R.id.fanhui);
        ditu=(Button)findViewById(R.id.ditu_nj);
        jieshao=(TextView)findViewById(R.id.jieshao_nf);
        pinglun=(TextView)findViewById(R.id.nf_pinglun);
        nfj_viewPager=(ViewPager)findViewById(R.id.nf_vp);
        ditu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAvilible(getBaseContext(),"com.baidu.BaiduMap")){
                    Intent intent=new Intent();
                    intent.setData(Uri.parse(uri));
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"未安装百度地图",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //获取Intent
    public void getMyIntent(){
        Intent intent=getIntent();
        name=intent.getStringExtra("name");
        biaoti.setText(name);
    }
    //确定是否装了baiduap的方法
    private boolean isAvilible(Context context,String packageName){
        final PackageManager packageManager=context.getPackageManager();
        List<String> names=new ArrayList<>();
        //获取所有已安装程序的报信息
        List<PackageInfo> packageInfos=packageManager.getInstalledPackages(0);
        if(packageInfos!=null){
            for (int i=0;i<packageInfos.size();i++){
                String pn=packageInfos.get(i).packageName;
                names.add(pn);
            }
        }
        return names.contains(packageName);
    }
}
