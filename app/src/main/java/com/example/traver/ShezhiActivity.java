package com.example.traver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.traver.shujubiao.yonghu;

import org.litepal.crud.DataSupport;

public class ShezhiActivity extends Activity {
    Button qzh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shezhi);
        qzh=(Button)findViewById(R.id.qie_z_h);
        qzh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.deleteAll(yonghu.class);
                Intent intent=new Intent();
                setResult(1);
                finish();
            }
        });
    }
}
