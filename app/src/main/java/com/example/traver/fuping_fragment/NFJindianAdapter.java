package com.example.traver.fuping_fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.traver.R;

import java.util.List;

/**
 * Created by wangning on 2017/4/24.
 */

public class NFJindianAdapter extends RecyclerView.Adapter<NFJindianAdapter.NFjindianVHolder> {
    private List<NFJingdian> nfJingdienlist;
    public NFJindianAdapter(List<NFJingdian> nfJingdienlist){
        this.nfJingdienlist=nfJingdienlist;
    }

    @Override
    public NFjindianVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.n_jindian_item,
                parent,false);
        final NFjindianVHolder holder=new NFjindianVHolder(view);
        holder.nfjview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("name",holder.jingdian_name.getText().toString());
                intent.setClass(view.getContext(),DetailActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(NFjindianVHolder holder, int position) {
        holder.jindianlog.setImageBitmap(BitmapFactory.decodeByteArray(nfJingdienlist.get(position).getLog(),0,nfJingdienlist.get(position).getLog().length));
        holder.jingdian_name.setText(nfJingdienlist.get(position).getName());
        holder.jingdian_didian.setText(nfJingdienlist.get(position).getWeizhi());

    }

    @Override
    public int getItemCount() {
        return nfJingdienlist.size();
    }

    static class NFjindianVHolder extends RecyclerView.ViewHolder{
        ImageView jindianlog;
        TextView jingdian_name;
        TextView jingdian_didian;
        View nfjview;
        public NFjindianVHolder(View itemView) {
            super(itemView);
            nfjview=itemView;
            jindianlog=(ImageView)itemView.findViewById(R.id.jindianlog);
            jingdian_name=(TextView)itemView.findViewById(R.id.jingdian_name);
            jingdian_didian=(TextView)itemView.findViewById(R.id.jingdian_weizhi);
        }
    }
}
