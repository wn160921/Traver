package com.example.traver.zhumingjingdian;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.traver.R;
import com.example.traver.mmp.OnTouchUpListener;
import com.example.traver.mmp.PPRecyclerView;
import com.example.traver.zhiyuangongyi.Zhiyuan;
import com.example.traver.zhiyuangongyi.ZhiyuanAdapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ZhumingjingdianActivity extends Activity {
    private PPRecyclerView recyclerLayout;
    ZhiyuanAdapter adapter;
    private View header;
    private View footer;
    public List<Zhiyuan> dianpuList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dianpu_fragment);
        header= LayoutInflater.from(this).inflate(R.layout.header,null);
        footer=LayoutInflater.from(this).inflate(R.layout.footer,null);
        Glide.with(this).load(R.drawable.shuaxing).into((ImageView) footer.findViewById(R.id.footimage));
        initalV();
    }
    public void initalV(){
        recyclerLayout=(PPRecyclerView)findViewById(R.id.dianpureceyer);
        dianpuList=new ArrayList<>();
        initDianpu();
        recyclerLayout.addHeaderView(header,200);
        recyclerLayout.addFooterView(footer,200);
        adapter=new ZhiyuanAdapter(dianpuList,this);
        recyclerLayout.setMyRecyclerView(new LinearLayoutManager(this),adapter);
        recyclerLayout.addOnTouchUpListener(new OnTouchUpListener() {
            @Override
            public void onRefresh() {
                Log.i("angel", "OnRefreshing: 正在刷新");
                recyclerLayout.setIsScrollRefresh(false);
                ObjectAnimator animator=ObjectAnimator.ofFloat(header,"rotation",0f,360f);
                animator.setDuration(5000);
                animator.setRepeatMode(ValueAnimator.RESTART);
                animator.setRepeatCount(2);
                animator.start();
                //      recycler.setScrollTo(recycler.getTotal(), 0);
            }

            @Override
            public void onLoading() {
                Log.i("angel", "OnRefreshing: 正在加载");
                recyclerLayout.setIsScrollLoad(false);
            }
        });
    }
    public void initDianpu(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket s = new Socket("119.23.34.226", 30000);
                    DataOutputStream doc=new DataOutputStream(s.getOutputStream());
                    doc.writeUTF("getping|zhumingjingdian");
                    DataInputStream dis=new DataInputStream(s.getInputStream());
                    while (true){
                        String dianming=dis.readUTF();
                        if(dianming.equals("jieshu")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });
                            break;
                        }
                        Zhiyuan zhiyuan=new Zhiyuan();
                        zhiyuan.name=dianming;
                        zhiyuan.jieshaotu=dis.readUTF();
                        zhiyuan.image1=dis.readUTF();
                        zhiyuan.image2=dis.readUTF();
                        zhiyuan.image3=dis.readUTF();
                        zhiyuan.neirong=dis.readUTF();
                        zhiyuan.zanren=dis.readUTF();
                        zhiyuan.zanshu=dis.readInt();
                        zhiyuan.uri=dis.readUTF();
                        dianpuList.add(zhiyuan);
                    }
                }catch (IOException e){

                }
            }
        }).start();
    }

}
