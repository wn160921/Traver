package com.example.traver.shuo_shuo;;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.traver.R;

import java.util.List;

/**
 * Created by Administrator on 2017/4/13/013.
 */

public class NumAdapter extends RecyclerView.Adapter<NumAdapter.NUMViewHolde> {
    List<Dianpu> dianpuList;
    Context context;
    public NumAdapter(List<Dianpu> dianpuList,Context context){
        this.dianpuList=dianpuList;
        this.context=context;
    }
    @Override
    public NUMViewHolde onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dian_pu_item,parent,false);
        NUMViewHolde numViewHolde=new NUMViewHolde(view);
        return numViewHolde;
    }

    @Override
    public void onBindViewHolder(NUMViewHolde holder, int position) {
        Dianpu dianpu=dianpuList.get(position);
        holder.textView.setText(dianpu.getDianming());
        Glide.with(context).load(dianpu.getImgDiZhi()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return dianpuList.size();
    }

    static class NUMViewHolde extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
        public NUMViewHolde(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.dian_ming);
            imageView=(ImageView)itemView.findViewById(R.id.dian_img);
        }
    }
}
