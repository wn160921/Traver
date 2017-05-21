package com.example.traver.fuping_fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.traver.R;
import com.example.traver.shuo_shuo.Shuo;
import com.example.traver.util.DatabaseUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangning on 2017/4/19.
 */

public class JindianNFragment extends Fragment {
    RecyclerView recyclerView;
    NFJindianAdapter nfJindianAdapter;
    List<NFJingdian> nfJingdianList;
    Handler handler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jindian_n_fragment, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.n_jindian_recycle);
        LinearLayoutManager layoutManager=new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        initList();
        nfJindianAdapter=new NFJindianAdapter(nfJingdianList);
        recyclerView.setAdapter(nfJindianAdapter);
        initHandler();
        DatabaseUtil.getNFjindianList(handler);
        return view;
    }
    public void initHandler(){
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                switch (msg.what){
                    case 0:
                        nfJingdianList.clear();
                        List<NFJingdian> m= DataSupport.findAll(NFJingdian.class);
                        for(NFJingdian s:m){
                            nfJingdianList.add(s);
                        }
                        Log.d("jingdian","加载完毕"+String.valueOf(m.size()));
                        nfJindianAdapter.notifyDataSetChanged();
                        break;
                    default:
                }
            }
        };
    }
    public void initList(){
        nfJingdianList=new ArrayList<>();
    }
}
