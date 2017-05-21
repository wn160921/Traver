package com.example.traver.huanying;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;

import com.example.traver.LoginActivity;
import com.example.traver.MainActivity;
import com.example.traver.R;
import com.example.traver.ZhuceActivity;
import com.example.traver.util.DatabaseUtil;

/**
 * Created by wangning on 2017/5/6.
 */

public class HuanyingFragment extends Fragment {
    Button denluBtn;
    Button zhuceBtn;
    EditText zhanghuEdit;
    EditText mimaEdit;
    Handler handler;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saved){
        View view=inflater.inflate(R.layout.huangying_fragment,container,false);
        initHangler();
        denluBtn=(Button)view.findViewById(R.id.denlu_huan);
        zhanghuEdit=(EditText)view.findViewById(R.id.zhanghaoiedt);
        mimaEdit=(EditText)view.findViewById(R.id.mimaiedt);
        denluBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                String s="denglu|"+zhanghuEdit.getText().toString()+"|"+mimaEdit.getText().toString();
                Log.d("zhuce",s);
                DatabaseUtil.denglu(handler,s);
            }
        });
        zhuceBtn=(Button)view.findViewById(R.id.zhuce_huan);
        zhuceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),ZhuceActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }
    public void initHangler(){
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                switch (msg.what){
                    case 0:
                        Intent intent=new Intent();
                        intent.setClass(getActivity(),MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        break;
                    case 1:
                }
            }
        };
    }
}
