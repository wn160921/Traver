package com.example.traver.huanying;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.traver.R;

/**
 * Created by wangning on 2017/5/6.
 */

public class Image3Fragment extends Fragment {
    public ImageView imageView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saved){
        View view=inflater.inflate(R.layout.emptyimage,container,false);
        imageView=(ImageView)view.findViewById(R.id.yitu);
        Glide.with(this).load(R.drawable.three).into(imageView);
        return view;
    }
}
