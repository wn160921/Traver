package com.example.traver.zhiyuangongyi.zhiyuan;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.traver.R;
import com.example.traver.zhiyuangongyi.DZYActivity;
import com.example.traver.zhiyuangongyi.Zhiyuan;

/**
 * Created by wangning on 2017/5/10.
 */

public class XiangqingFragment extends Fragment {
    Zhiyuan zhiyuan;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saved){
        View view=inflater.inflate(R.layout.xingqing_fragment,null);
        init(view);
        return view;
    }
    public void init(View view){
        DZYActivity activity= (DZYActivity) getActivity();
        zhiyuan=activity.getZhiyuan();
        imageView1=(ImageView)view.findViewById(R.id.image1);
        imageView2=(ImageView)view.findViewById(R.id.image2);
        imageView3=(ImageView)view.findViewById(R.id.image3);
        imageView4=(ImageView)view.findViewById(R.id.image4);
        textView=(TextView)view.findViewById(R.id.neirong);
        textView.setText(zhiyuan.neirong);
        Glide.with(getActivity()).load(zhiyuan.jieshaotu).into(imageView1);
        Glide.with(getActivity()).load(zhiyuan.image1).into(imageView2);
        Glide.with(getActivity()).load(zhiyuan.image2).into(imageView3);
        Glide.with(getActivity()).load(zhiyuan.image3).into(imageView4);
    }
}
