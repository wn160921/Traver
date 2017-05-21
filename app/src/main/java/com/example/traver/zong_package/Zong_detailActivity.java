package com.example.traver.zong_package;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.traver.R;
import com.example.traver.shuo_shuo.Dianpu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Zong_detailActivity extends AppCompatActivity {
    List<Pinglun> pingluns;
    Button fanhui;
    TextView biaoti;
    Button tiaozhuan;
    ViewPager viewPager;
    LinearLayout zhishiqi;
    TextView neirong;
    TextView zanshu;
    Button zanbtn;
    RecyclerView recyclerView;
    PinglunAdapter pinglunAdapter;
    Neirong nr;
    List<ImageView> imageViewList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zong_detail);
        initKongjian();
    }
    public void initKongjian(){
        fanhui=(Button)findViewById(R.id.fanhui_dz);
        biaoti=(TextView) findViewById(R.id.biaoti_dz);
        tiaozhuan=(Button)findViewById(R.id.ditu_dz);
        viewPager=(ViewPager)findViewById(R.id.vpzong);
        zhishiqi=(LinearLayout)findViewById(R.id.zhishiqi);
        neirong=(TextView)findViewById(R.id.jutineirong);
        zanshu=(TextView)findViewById(R.id.zanshu_zong);
        zanbtn=(Button)findViewById(R.id.zan_dt);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_dz);
        pingluns=new ArrayList<>();
        pinglunAdapter=new PinglunAdapter(this,pingluns);
        recyclerView.setAdapter(pinglunAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically(){
                return  false;
            }
        });
        nr=new Neirong();
        imageViewList=new ArrayList<>();
    }
    public void huoqushuju(){
        Intent intent=getIntent();
        String s=intent.getStringExtra("data");


    }
    public void getpinlun(String cha){
        try {
            Socket s = new Socket("119.23.34.226", 30000);
            DataOutputStream doc=new DataOutputStream(s.getOutputStream());
            doc.writeUTF(cha);
            DataInputStream dis=new DataInputStream(s.getInputStream());
            while (true){
                String touxiang=dis.readUTF();
                if(touxiang.equals("jieshu")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //pinglunAdapter.notifyDataSetChanged();
                        }
                    });
                    break;
                }
                Pinglun pinglun=new Pinglun();
                pinglun.touxiang=touxiang;
                pinglun.nicen=dis.readUTF();
                pinglun.neirong=dis.readUTF();
                pinglun.shijian=dis.readUTF();
                pinglun.zanren=dis.readUTF();
                pinglun.zanshu=dis.readInt();
                pingluns.add(pinglun);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public void initshuju(String shuju){
        try {
            Socket s = new Socket("119.23.34.226", 30000);
            DataOutputStream doc=new DataOutputStream(s.getOutputStream());
            doc.writeUTF(shuju);
            DataInputStream dis=new DataInputStream(s.getInputStream());
            while (true){
                final String biaotiwen=dis.readUTF();
                if(biaotiwen.equals("jieshu")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        biaoti.setText(nr.biaoti);
                            ImageView ima1=new ImageView(getBaseContext());
                            ima1.setScaleType(ImageView.ScaleType.FIT_XY);
                            Glide.with(getBaseContext()).load(nr.image1).into(ima1);
                            imageViewList.add(ima1);
                            ImageView ima2=new ImageView(getBaseContext());
                            ima2.setScaleType(ImageView.ScaleType.FIT_XY);
                            Glide.with(getBaseContext()).load(nr.image2).into(ima2);
                            imageViewList.add(ima2);
                            ImageView ima3=new  ImageView(getBaseContext());
                            ima3.setScaleType(ImageView.ScaleType.FIT_XY);
                            Glide.with(getBaseContext()).load(nr.image3).into(ima3);
                            imageViewList.add(ima3);
                            viewPager.setAdapter(new PagerAdapter() {
                                @Override
                                public int getCount() {
                                    return imageViewList.size();
                                }
                                @Override
                                public Object instantiateItem(ViewGroup container, int position){
                                    final View child=imageViewList.get(position);
                                    if(child.getParent()!=null){
                                        container.removeView(child);
                                    }
                                    container.addView(imageViewList.get(position));
                                    return imageViewList.get(position);
                                }
                                @Override
                                public boolean isViewFromObject(View view, Object object) {
                                    return view==object;
                                }
                            });
                            zanshu.setText(String.valueOf(nr.zanshu));


                        }
                    });
                    break;
                }
                nr.biaoti=biaotiwen;
                nr.image1=dis.readUTF();
                nr.image2=dis.readUTF();
                nr.image3=dis.readUTF();
                nr.zanren=dis.readUTF();
                nr.zanshu=dis.readInt();
                nr.uri=dis.readUTF();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
