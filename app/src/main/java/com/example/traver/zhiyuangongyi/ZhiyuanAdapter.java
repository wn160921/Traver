package com.example.traver.zhiyuangongyi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.traver.R;
import java.util.List;

/**
 * Created by wangning on 2017/5/9.
 */

public class ZhiyuanAdapter extends RecyclerView.Adapter<ZhiyuanAdapter.ZYViewHolde> {
    List<Zhiyuan> zhiyuanList;
    Context context;
    public ZhiyuanAdapter(List<Zhiyuan> zhiyuanList,Context context){
        this.zhiyuanList=zhiyuanList;
        this.context=context;
    }
    @Override
    public ZYViewHolde onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dian_pu_item,parent,false);
        final ZYViewHolde zyViewHolde=new ZYViewHolde(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("zhiyuan",zhiyuanList.get(zyViewHolde.getAdapterPosition()));
                intent.setClass(parent.getContext(),DZYActivity.class);
                parent.getContext().startActivity(intent);
            }
        });
        return zyViewHolde;
    }

    @Override
    public void onBindViewHolder(ZYViewHolde holder, int position) {
        Zhiyuan zhiyuan=zhiyuanList.get(position);
        holder.textView.setText(zhiyuan.name);
        Glide.with(context).load(zhiyuan.jieshaotu).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return zhiyuanList.size();
    }


    static class ZYViewHolde extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
        public ZYViewHolde(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.dian_ming);
            imageView=(ImageView)itemView.findViewById(R.id.dian_img);
        }
    }
}
