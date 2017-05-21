package com.example.traver.jiudianpiaowu;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.traver.R;
import com.example.traver.mmp.OnTouchUpListener;
import com.example.traver.mmp.PPRecyclerView;
import com.example.traver.shuo_shuo.Dianpu;
import com.example.traver.shuo_shuo.NumAdapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangning on 2017/5/4.
 */

public class HuocheFragment extends Fragment {
    private PPRecyclerView recyclerLayout;
    NumAdapter adapter;
    private View header;
    private View footer;
    public List<Dianpu> dianpuList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saved){
        View view=inflater.inflate(R.layout.dianpu_fragment,null);
        header=inflater.inflate(R.layout.header,null);
        footer=inflater.inflate(R.layout.footer,null);
        Glide.with(getContext()).load(R.drawable.shuaxing).into((ImageView) footer.findViewById(R.id.footimage));
        inital(view);
        return view;
    }
    private void inital(View view){
        recyclerLayout=(PPRecyclerView) view.findViewById(R.id.dianpureceyer);
        dianpuList=new ArrayList<>();
        initDianpu();
        recyclerLayout.addHeaderView(header,200);
        recyclerLayout.addFooterView(footer,200);
        adapter=new NumAdapter(dianpuList,getContext());
        recyclerLayout.setMyRecyclerView(new LinearLayoutManager(getContext()),adapter);
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
                    doc.writeUTF("getdianpumeishi");
                    DataInputStream dis=new DataInputStream(s.getInputStream());
                    while (true){
                        String dianming=dis.readUTF();
                        if(dianming.equals("jieshu")){
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });
                            break;
                        }
                        String dizhi=dis.readUTF();
                        Dianpu dianpu=new Dianpu();
                        dianpu.setDianming(dianming);
                        dianpu.setImgDiZhi(dizhi);
                        dianpuList.add(dianpu);
                    }
                }catch (IOException e){

                }
            }
        }).start();
    }
}
