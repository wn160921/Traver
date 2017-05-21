package com.example.traver.shuo_shuo;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.traver.R;
import com.example.traver.shougongzhizhuo.ShougongzhizhuoActivity;
import com.example.traver.shujubiao.yonghu;
import com.example.traver.util.DatabaseUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Administrator on 2017/4/14/014.
 */

public class ShuoAdapter extends RecyclerView.Adapter<ShuoAdapter.MyViewHolder> {
    private List<Shuo> shuoList;
    public Context context;
    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView yname;
        TextView shijian;
        TextView neirong;
        TextView pinglun;
        TextView zantext;
        TextView caitext;
        ImageView image1;
        ImageView image2;
        ImageView image3;
        ImageView touxiang;
        EditText pinedit;
        Button fashuoping;
        Button zan;
        Button cai;
        Button jiagenduo;
        Button kanpinglun;
        public MyViewHolder(View view){
            super(view);
            touxiang=(ImageView)view.findViewById(R.id.touxiang);
            yname=(TextView)view.findViewById(R.id.name);
            neirong=(TextView)view.findViewById(R.id.neir);
            pinglun=(TextView)view.findViewById(R.id.pinglun);
            shijian=(TextView)view.findViewById(R.id.shijian);
            zantext=(TextView)view.findViewById(R.id.zantext);
            caitext=(TextView)view.findViewById(R.id.caitext);
            image1=(ImageView)view.findViewById(R.id.image1);
            image2=(ImageView)view.findViewById(R.id.image2);
            image3=(ImageView)view.findViewById(R.id.image3);
            pinedit=(EditText)view.findViewById(R.id.pin_s_e);
            fashuoping=(Button)view.findViewById(R.id.fspinglun);
            zan=(Button)view.findViewById(R.id.zan);
            cai=(Button)view.findViewById(R.id.cai);
            jiagenduo=(Button)view.findViewById(R.id.jiagenduo);
            kanpinglun=(Button)view.findViewById(R.id.kanpinglun);
        }
    }
    public ShuoAdapter(List<Shuo> shuoList,Context context){
        this.shuoList=shuoList;
        this.context=context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.shuo_item,parent,false);
        final MyViewHolder holder=new MyViewHolder(view);
        holder.fashuoping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shuo shuo=new Shuo();
                shuo.updateAll("xulie = ?",String.valueOf(shuoList.get(holder.getAdapterPosition()).getXulie()));
                //String pins=holder.yname.getText()+":"+holder.pinedit.getText().toString()+"#"+shuoList.get(holder.getAdapterPosition()).getPinglun();
                //pins=pins.replaceAll("#","\n");
                //shuoList.get(holder.getAdapterPosition()).setPinglun(pins);
                int id=shuoList.get(holder.getAdapterPosition()).getXulie();
                //DatabaseUtil.fashuoshuopinglun(String.valueOf(id)+"|"+holder.yname.getText()+":"+holder.pinedit.getText().toString()+"#"+shuoList.get(holder.getAdapterPosition()).getPinglun());
                notifyDataSetChanged();
                InputMethodManager imm=(InputMethodManager)parent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(holder.pinedit.getWindowToken(),0);
                holder.pinedit.setText("");
            }
        });
        holder.zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shuo current=shuoList.get(holder.getAdapterPosition());
                if(!current.getZanren().contains(DatabaseUtil.getName())){
                    holder.zan.setSelected(true);
                    Shuo shuo=new Shuo();
                    shuo.setZanren(current.getZanren()+DatabaseUtil.getName());
                    shuo.setZanshu(current.getZanshu()+1);
                    shuo.updateAll("xulie = ?",String.valueOf(current.getXulie()));
                    current.setZanren(current.getZanren()+DatabaseUtil.getName());
                    current.setZanshu(current.getZanshu()+1);
                    notifyDataSetChanged();
                    DatabaseUtil.gexzan(current.getXulie()+"|"+String.valueOf(current.getZanshu())+"|"+current.getZanren());
                }else {
                    holder.zan.setSelected(false);
                    Shuo shuo=new Shuo();
                    shuo.setZanren(current.getZanren().replaceAll(DatabaseUtil.getName(),""));
                    shuo.setZanshu(current.getZanshu()-1);
                    shuo.updateAll("xulie = ?",String.valueOf(current.getXulie()));
                    current.setZanren(current.getZanren().replaceAll(DatabaseUtil.getName(),""));
                    current.setZanshu(current.getZanshu()-1);
                    notifyDataSetChanged();
                }
            }
        });
        holder.cai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shuo current=shuoList.get(holder.getAdapterPosition());
                if(!current.getCairen().contains(DatabaseUtil.getName())){
                    Shuo shuo=new Shuo();
                    shuo.setCairen(current.getCairen()+DatabaseUtil.getName());
                    shuo.setCaishu(current.getCaishu()+1);
                    shuo.updateAll("xulie = ?",String.valueOf(current.getXulie()));
                    current.setCairen(current.getCairen()+DatabaseUtil.getName());
                    current.setCaishu(current.getCaishu()+1);
                    notifyDataSetChanged();
                    DatabaseUtil.gexcai(current.getXulie()+"|"+String.valueOf(current.getCaishu())+"|"+current.getCairen());
                }else {
                    Shuo shuo=new Shuo();
                    shuo.setCairen(current.getCairen().replaceAll(DatabaseUtil.getName(),""));
                    shuo.setCaishu(current.getCaishu()-1);
                    shuo.updateAll("xulie = ?",String.valueOf(current.getXulie()));
                    current.setCairen(current.getCairen().replaceAll(DatabaseUtil.getName(),""));
                    current.setCaishu(current.getCaishu()-1);
                    notifyDataSetChanged();
                    DatabaseUtil.gexcai(current.getXulie()+"|"+String.valueOf(current.getCaishu())+"|"+current.getCairen());
                }
            }
        });
        holder.kanpinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shuo shuo=shuoList.get(holder.getAdapterPosition());
                Intent intent=new Intent();
                intent.setClass(context,SPActivity.class);
                intent.putExtra("pldx",String.valueOf(shuo.getXulie()));
                intent.putExtra("xulie",shuo.getXulie());
                context.startActivity(intent);
            }
        });
        imageClick(holder);
        return holder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder,int position){

        Shuo shuo=shuoList.get(position);
        Log.d("jiagenduo",String.valueOf(holder.getAdapterPosition())+String.valueOf(shuoList.size()));
        if(position==shuoList.size()-1){
            holder.jiagenduo.setVisibility(View.VISIBLE);
        }else {
            holder.jiagenduo.setVisibility(View.GONE);
        }
        if(shuo.getZanren().contains(DatabaseUtil.getName())){
            holder.zan.setSelected(true);
        }
        holder.yname.setText(shuo.getNecen());
        holder.zantext.setText(String.valueOf(shuo.getZanshu()));
        holder.caitext.setText(String.valueOf(shuo.getCaishu()));
        holder.neirong.setText(shuo.getNeirong());
        holder.shijian.setText(shuo.getTime());
        Glide.with(context).load("http://119.23.34.226/"+shuo.getTouxiangdizhi()).into(holder.touxiang);
        //String pins=shuo.getPinglun();
        //pins=pins.replaceAll("#","\n");
        //holder.pinglun.setText(pins);
        int shu=shuo.getShuliag();
        if (shu>0){
            holder.image1.setVisibility(View.VISIBLE);
            Glide.with(context).load("http://119.23.34.226/"+shuo.getimage1path()).into(holder.image1);
            Log.d("JIA","http://119.23.34.226/"+shuo.getimage1path());
        }else {
            holder.image1.setVisibility(View.GONE);
            holder.image2.setVisibility(View.GONE);
            holder.image3.setVisibility(View.GONE);
        }
        if(shu>1){
            holder.image2.setVisibility(View.VISIBLE);
            Glide.with(context).load("http://119.23.34.226/"+shuo.getImage2()).into(holder.image2);
        }else {
            holder.image2.setVisibility(View.GONE);
            holder.image3.setVisibility(View.GONE);
        }
        if(shu>2){
            holder.image3.setVisibility(View.VISIBLE);
            Glide.with(context).load("http://119.23.34.226/"+shuo.getImage3()).into(holder.image3);
        }else {
            holder.image3.setVisibility(View.GONE);
        }
    }
    @Override
    public int getItemCount(){
        return shuoList.size();
    }
    public void imageClick(final MyViewHolder holder){
        holder.image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("shuo",shuoList.get(holder.getAdapterPosition()));
                intent.setClass(context,DatuActivity.class);
                context.startActivity(intent);
            }
        });
        holder.image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("shuo",shuoList.get(holder.getAdapterPosition()));
                intent.setClass(context,DatuActivity.class);
                context.startActivity(intent);
            }
        });
        holder.image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("shuo",shuoList.get(holder.getAdapterPosition()));
                intent.setClass(context,DatuActivity.class);
                context.startActivity(intent);
            }
        });
    }
}
