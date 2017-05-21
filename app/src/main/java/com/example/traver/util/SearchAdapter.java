package com.example.traver.util;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.example.traver.R;
import com.example.traver.zhiyuangongyi.Zhiyuan;

import java.util.List;

/**
 * Created by Administrator on 2017/4/12/012.
 */

public class SearchAdapter extends CommonAdapter<Zhiyuan> {
    Context context;
    public SearchAdapter(Context context, List<Zhiyuan> data, int layoutId) {
        super(context, data, layoutId);
        this.context=context;
    }

    @Override
    public void convert(ViewHolder holder, int position) {
        //holder.textView.setText(mData.get(position).name);
        //Glide.with(context).load(mData.get(position).jieshaotu).into(holder.imageView);
    }
}
