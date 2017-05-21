package com.example.traver.zong_package;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.traver.R;
import com.example.traver.util.DatabaseUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wangning on 2017/5/7.
 */

public class PinglunAdapter extends RecyclerView.Adapter<PinglunAdapter.PinlunViewHolder>{
    private Context context;
    private List<Pinglun> pinglunList;
    public PinglunAdapter(Context context,List<Pinglun> pinglunList){
        this.context=context;
        this.pinglunList=pinglunList;
    }
    @Override
    public PinlunViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.pinglun_item,parent,false);
        final PinlunViewHolder pinlunViewHolder=new PinlunViewHolder(view);
        pinlunViewHolder.zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pinglun p=pinglunList.get(pinlunViewHolder.getAdapterPosition());
                if(DatabaseUtil.isDenglu()){
                    if(p.zanren.contains(DatabaseUtil.getName())){
                        p.zanren=p.zanren.replaceAll(DatabaseUtil.getName(),"");
                        p.zanshu=p.zanshu-1;
                        gpzan(String.valueOf(p.id)+"|"+p.zanren+"|"+String.valueOf(p.zanshu));
                        notifyDataSetChanged();
                    }else {
                        p.zanren=p.zanren+DatabaseUtil.getName();
                        p.zanshu+=1;
                        gpzan(String.valueOf(p.id)+"|"+p.zanren+"|"+String.valueOf(p.zanshu));
                        pinlunViewHolder.zan.setSelected(false);
                        notifyDataSetChanged();
                    }
                }else {
                    Toast.makeText(context,"请先登录",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return pinlunViewHolder;
    }

    @Override
    public void onBindViewHolder(PinlunViewHolder holder, int position) {
        Pinglun pinglun=pinglunList.get(position);
        if(pinglun.zanren.contains(DatabaseUtil.getName())){
            holder.zan.setSelected(true);
        }else {
            holder.zan.setSelected(false);
        }
        Glide.with(context).load("http://119.23.34.226/"+pinglun.touxiang).into(holder.touxiang);
        holder.nicen.setText(pinglun.nicen);
        holder.shijian.setText(pinglun.shijian);
        holder.neirong.setText(pinglun.neirong);
        holder.zanshu.setText(String.valueOf(pinglun.zanshu));
    }

    @Override
    public int getItemCount() {
        return pinglunList.size();
    }

    static class PinlunViewHolder extends RecyclerView.ViewHolder {
        CircleImageView touxiang;
        TextView nicen;
        TextView shijian;
        TextView neirong;
        TextView zanshu;
        Button zan;
        public PinlunViewHolder(View view) {
            super(view);
            touxiang=(CircleImageView)view.findViewById(R.id.ptouxiang_zong);
            nicen=(TextView)view.findViewById(R.id.pnicen);
            shijian=(TextView)view.findViewById(R.id.pshijian);
            neirong=(TextView)view.findViewById(R.id.pnn_zong);
            zanshu=(TextView)view.findViewById(R.id.pinglunzanshu_zong);
            zan=(Button)view.findViewById(R.id.pinglunzan_dt);
        }

    }
    public void gpzan(final String xin){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket s = new Socket("119.23.34.226", 30000);
                    DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                    dos.writeUTF("gpzan|"+xin);
                    dos.flush();
                    dos.close();
                    s.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
