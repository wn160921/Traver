package com.example.traver.shuo_shuo;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.traver.R;
import com.example.traver.zhiyuangongyi.DZYActivity;
import com.example.traver.zhiyuangongyi.Zhiyuan;
import com.example.traver.zhiyuangongyi.zhiyuan.PinglunActivity;
import com.example.traver.zong_package.Pinglun;
import com.example.traver.zong_package.PinglunAdapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SPActivity extends AppCompatActivity {
    List<Pinglun> pinglunList;
    RecyclerView recyclerView;
    PinglunAdapter adapter;
    FloatingActionButton floatingActionButton;
    String pinlunduixiang;
    boolean flag=true;
    int xulie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pinglun);
        Intent intent=getIntent();
        pinlunduixiang=intent.getStringExtra("pldx");
        xulie = intent.getIntExtra("xulie",0);
        init();
    }
    public void init(){
        pinglunList=new ArrayList<>();
        recyclerView=(RecyclerView)findViewById(R.id.pinlunrecycler);
        adapter=new PinglunAdapter(this,pinglunList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        floatingActionButton=(FloatingActionButton)findViewById(R.id.fapinglun);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getApplicationContext(),PinglunActivity.class);
                intent.putExtra("name",String.valueOf(xulie));
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
                            runOnUiThread(new Runnable() {
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
