package com.example.traver.zhiyuangongyi.zhiyuan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traver.R;
import com.example.traver.util.DatabaseUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class PinglunActivity extends AppCompatActivity {
    TextView textView;
    Button fapinglun;
    EditText bianji;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinglun);
        Intent intent=getIntent();
        progressBar=(ProgressBar)findViewById(R.id.fapinglunprbar);
        textView=(TextView)findViewById(R.id.name_text);
        fapinglun=(Button)findViewById(R.id.fabtn);
        bianji=(EditText)findViewById(R.id.pinglun_edit);
        textView.setText(intent.getStringExtra("name"));
        fapinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bianji.getText().equals("")){
                    Toast.makeText(getApplicationContext(),"请输入内容",Toast.LENGTH_SHORT).show();
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    fa_pinglun(bianji.getText().toString());
                }
            }
        });
    }
    public void fa_pinglun(final String pinglun){
        DatabaseUtil.setJifen(3);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket s = new Socket("119.23.34.226", 30000);
                    DataOutputStream doc=new DataOutputStream(s.getOutputStream());
                    doc.writeUTF("fapinglun|"+ DatabaseUtil.getTouxiang()+"|"+DatabaseUtil.getNecen()+
                            "|"+DatabaseUtil.getTime()+"|"+pinglun+"|"+textView.getText().toString());
                    finish();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
