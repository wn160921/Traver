package com.example.traver;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.traver.shujubiao.yonghu;
import com.example.traver.util.DatabaseUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/3/28/028.
 */

public class WodeFragment extends Fragment {
    Handler handler;
    Button denglu;
    Button zhuce;
    Button shezhi;
    EditText yonghuming;
    EditText mima;
    TextView yhm;
    TextView jifen;
    RelativeLayout R1;
    LinearLayout R2;
    CircleImageView touxiang;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle s){
        final View view=inflater.inflate(R.layout.wode_fragment,null);
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                switch (msg.what){
                    case 0:
                        initL(view);
                        break;
                    case 1:

                }
            }
        };
        initbutton(view);
        initL(view);
        return view;
    }
    public void initbutton(View view){
        touxiang=(CircleImageView)view.findViewById(R.id.touxiang);
        yonghuming=(EditText)view.findViewById(R.id.yonghuming);
        mima=(EditText)view.findViewById(R.id.mima);
        yhm=(TextView)view.findViewById(R.id.yhming);
        jifen=(TextView)view.findViewById(R.id.jifen);
        denglu=(Button)view.findViewById(R.id.denglu);
        denglu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                String s="denglu|"+yonghuming.getText().toString()+"|"+mima.getText().toString();
                Log.d("zhuce",s);
                DatabaseUtil.denglu(handler,s);
            }
        });
        zhuce=(Button)view.findViewById(R.id.zhuce);
        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),ZhuceActivity.class);
                startActivityForResult(intent,1);
            }
        });
        shezhi=(Button)view.findViewById(R.id.shezhi);
        shezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),ShezhiActivity.class);
                startActivityForResult(intent,2);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case 1:
                if(resultCode==1){
                    initl(getview());
                }

                break;
            case 2:
                if(resultCode==1){
                    initL(getView());
                }
            default:
        }
    }
    public void initL(View view){
        R1=(RelativeLayout)view.findViewById(R.id.relativeone);
        R2=(LinearLayout) view.findViewById(R.id.relativetwo);
        R1.setVisibility(View.VISIBLE);
        R2.setVisibility(View.VISIBLE);
        List<yonghu> yonghuList= DataSupport.findAll(yonghu.class);
        if(yonghuList.size()!=0){
            R1.setVisibility(View.GONE);
            R2.setVisibility(View.VISIBLE);
            yhm.setText(yonghuList.get(0).getNecen());
            jifen.setText(String.valueOf(yonghuList.get(0).getJifen()));
            Log.d("denlutu","http://119.23.34.226/"+yonghuList.get(0).getTouxiang());
            Glide.with(this).load("http://119.23.34.226/"+yonghuList.get(0).getTouxiang()).into(touxiang);
        }else {
            R1.setVisibility(View.VISIBLE);
            R2.setVisibility(View.GONE);
        }
    }
}
