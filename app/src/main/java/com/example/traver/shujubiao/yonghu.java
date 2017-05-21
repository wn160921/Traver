package com.example.traver.shujubiao;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/4/12/012.
 */

public class yonghu extends DataSupport {
    private String yonghuming;
    private String necen;
    private int jifen;
    private String touxiang;
    public String getYonghuming(){
        return yonghuming;
    }
    public void setYonghuming(String s){
        yonghuming=s;
    }

    public int getJifen(){
        return jifen;
    }
    public void setJifen(int j){
        jifen=j;
    }

    public String getTouxiang(){
        return touxiang;
    }
    public void setTouxiang(String s){
        touxiang=s;
    }
    public String getNecen(){
        return necen;
    }
    public void setNecen(String necen){
        this.necen=necen;
    }
}
