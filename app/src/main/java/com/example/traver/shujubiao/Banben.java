package com.example.traver.shujubiao;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/4/8/008.
 */

public class Banben extends DataSupport {
    private String name;
    private int banbenhao;
    public int getBanbenhao(){
        return banbenhao;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setBanbenhao(int banbenhao){
        this.banbenhao=banbenhao;
    }
}
