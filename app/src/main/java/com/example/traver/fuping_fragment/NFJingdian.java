package com.example.traver.fuping_fragment;

import org.litepal.crud.DataSupport;

/**
 * Created by wangning on 2017/4/24.
 */

public class NFJingdian extends DataSupport{
    private byte[] log;
    private String name;
    private String weizhi;
    private String pinglun;
    private String jieshao;
    private String uri;
    private String pic1;
    private String pic2;
    private String pic3;
    public String getUri(){
        return uri;
    }
    public void setUri(String u){
        uri=u;
    }
    public byte[] getLog(){
        return log;
    }
    public void setLog(byte[] log){
        this.log=log;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getWeizhi(){
        return weizhi;
    }
    public void setWeizhi(String weizhi){
        this.weizhi=weizhi;
    }
    public String getPinglun(){
        return pinglun;
    }
    public void setPinglun(String p){
        pinglun=p;
    }
    public String getJieshao(){
        return jieshao;
    }
    public void setJieshao(String s){
        jieshao=s;
    }
    public String getPic1(){
        return pic1;
    }
    public String getPic2(){
        return pic2;
    }
    public String getPic3(){
        return pic3;
    }
    public void setPic1(String b){
        pic1=b;
    }
    public void setPic2(String b){
        pic2=b;
    }
    public void setPic3(String b){
        pic3=b;
    }
}
