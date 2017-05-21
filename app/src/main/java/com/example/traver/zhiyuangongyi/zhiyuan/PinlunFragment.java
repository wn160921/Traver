package com.example.traver.zhiyuangongyi.zhiyuan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.traver.R;
import com.example.traver.shuo_shuo.Dianpu;
import com.example.traver.util.DatabaseUtil;
import com.example.traver.zhiyuangongyi.DZYActivity;
import com.example.traver.zong_package.Pinglun;
import com.example.traver.zong_package.PinglunAdapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangning on 2017/5/10.
 */

public class PinlunFragment extends Fragment {
    List<Pinglun> pinglunList;
    RecyclerView recyclerView;
    PinglunAdapter adapter;
    FloatingActionButton floatingActionButton;
    String pinlunduixiang;
    boolean flag=true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saved){
        View view=inflater.inflate(R.layout.pinglun,null);
        init(view);
        //getPinglun(pinlunduixiang);
        return view;
    }
    public void init(View view){
        final DZYActivity dzyActivity= (DZYActivity) getActivity();
        pinlunduixiang=dzyActivity.getPinglun();
        pinglunList=new ArrayList<>();
        recyclerView=(RecyclerView)view.findViewById(R.id.pinlunrecycler);
        adapter=new PinglunAdapter(getContext(),pinglunList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        floatingActionButton=(FloatingActionButton)view.findViewById(R.id.fapinglun);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),PinglunActivity.class);
                intent.putExtra("name",dzyActivity.getZhiyuan().name);
                startActivity(intent);
            }
        });
    }
    public void getPinglun(final String guanjianci){
        new  Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket s = new Socket("119.23.34.226", 30000);
                    DataOutputStream doc=new DataOutputStream(s.getOutputStream());
                    doc.writeUTF("getpinglun|"+guanjianci);
                    DataInputStream dis=new DataInputStream(s.getInputStream());
                    pinglunList.clear();
                    while (true){
                        String touxiang=dis.readUTF();
                        if(touxiang.equals("jieshu")){
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });
                            Log.d("DAODOJIGE","退出");
                            break;
                        }
                        Pinglun pinglun=new Pinglun();
                        pinglun.touxiang=touxiang;
                        pinglun.nicen=dis.readUTF();
                        pinglun.shijian=dis.readUTF();
                        pinglun.neirong=dis.readUTF();
                        pinglun.zanren=dis.readUTF();
                        pinglun.zanshu=dis.readInt();
                        pinglun.id=dis.readInt();
                        pinglunList.add(pinglun);
                        Log.d("DAODOJIGE","OOOOOOO");
                        Log.d("DAODOJIGE",String.valueOf(pinglunList.size()));
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    @Override
    public void  onResume(){
        super.onResume();
        pinglunList.clear();
        getPinglun(pinlunduixiang);
    }
}
