package com.example.traver.shuo_shuo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.traver.FashuoshuoActivity;
import com.example.traver.R;
import com.example.traver.shujubiao.yonghu;
import com.example.traver.util.DatabaseUtil;

import org.litepal.crud.DataSupport;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by wangning on 2017/5/4.
 */

public class FenxiangFragment extends Fragment {
    List<Shuo> shuoList=new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    Button fashuo;
    Handler handler;
    ShuoAdapter shuoAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saved){
        View view=inflater.inflate(R.layout.friends_fragment,null);
        inithandler();
        initShuo(view);
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        shuoAdapter=new ShuoAdapter(shuoList,this.getContext());
        recyclerView.setAdapter(shuoAdapter);
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swrefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                List<Shuo> shuoList= DataSupport.order("xulie desc").find(Shuo.class);
                if(shuoList.size()>0){
                    DatabaseUtil.getShuo(handler,"getshuoshuo|"+shuoList.get(0).getTime()+"|c",getkongback(),"分享");
                }else {
                    DatabaseUtil.getShuo(handler,"getshuoshuo|wu|a",getkongback(),"分享");
                }

            }
        });
        return view;
    }
    public void initShuo(View view){
        shuoList=DataSupport.where("fenlei=?","分享").order("xulie desc").find(Shuo.class);
        if(shuoList.size()>0){
            DatabaseUtil.getShuo(handler,"getshuoshuo|"+shuoList.get(0).getTime()+"|c",getkongback(),"分享");
        }else {
            DatabaseUtil.getShuo(handler,"getshuoshuo|wu|a",getkongback(),"分享");
        }
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    swipeRefreshLayout.setRefreshing(true);
                    List<Shuo> shuoList=DataSupport.where("fenlei = ?","分享").order("xulie desc").find(Shuo.class);
                    if(shuoList.size()>0){
                        DatabaseUtil.getShuo(handler,"getshuoshuo|"+shuoList.get(0).getTime()+"|c",getkongback(),"分享");
                    }else {
                        DatabaseUtil.getShuo(handler,"getshuoshuo|wu|a",getkongback(),"分享");
                    }
                }
        }
    }
    public void inithandler() {
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                switch (msg.what){
                    case 1:
                        shuoList.clear();
                        List<Shuo> m=DataSupport.where("fenlei = ?","分享").order("xulie desc").find(Shuo.class);
                        for(Shuo s:m){
                            shuoList.add(s);
                        }
                        Log.d("shuo","加载完毕");
                        shuoAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                        break;
                    default:

                }
            }
        };
    }
    public byte[] getkongback(){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.jiazaiqian);
        bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
        return baos.toByteArray();
    }
}
