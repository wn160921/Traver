package com.example.traver.shuo_shuo;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/13/013.
 */

public class Shuo extends DataSupport implements Serializable {
    private int xulie;
    private String name;
    private String necen;
    private String touxiangdizhi;
    private String time;
    private String neirong;
    private String fenlei;
    private String zanren;
    private String cairen;
    private int shuliag;
    private int zanshu;
    private int caishu;
    private String image1path;
    private String image2path;
    private String image3path;
    public Shuo(){}
    public Shuo(String i){
        this.name=i;
    }
    public Shuo(String name,String time,String neirong,String fenlei,int shuliang){
        this.name=name;
        this.time=time;
        this.neirong=neirong;
        this.fenlei=fenlei;
        this.shuliag=shuliang;
    }
    public Shuo(String name,String time,String neirong,String fenlei,int shuliang,String image1pathpath){
        this.name=name;
        this.time=time;
        this.neirong=neirong;
        this.fenlei=fenlei;
        this.shuliag=shuliang;
        this.image1path=image1pathpath;
    }
    public Shuo(String name,String time,String neirong,String fenlei,int shuliang,String image1path,String image2){
        this.name=name;
        this.time=time;
        this.neirong=neirong;
        this.fenlei=fenlei;
        this.shuliag=shuliang;
        this.image1path=image1path;
        this.image2path=image2;
    }
    public Shuo(String name,String time,String neirong,String fenlei,int shuliang,String image1path,String image2,String image3){
        this.name=name;
        this.time=time;
        this.neirong=neirong;
        this.fenlei=fenlei;
        this.shuliag=shuliang;
        this.image1path=image1path;
        this.image2path=image2;
        this.image3path=image3;
    }
    public String getNecen(){
        return necen;
    }
    public void setNecen(String necen){
        this.necen=necen;
    }
    public int getXulie(){return xulie;}
    public void seeXulie(int id){xulie=id;}
    public String getName(){
        return name;
    }
    public String getTime(){
        return time;
    }
    public String getNeirong(){
        return  neirong;
    }
    public String getFenlei(){
        return fenlei;
    }
    public int getShuliag(){
        return shuliag;
    }
    public String getimage1path(){
        return image1path;
    }
    public String getImage2(){
        return image2path;
    }
    public String getImage3(){
        return image3path;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setTime(String time){
        this.time=time;
    }
    public void setNeirong(String neirong){
        this.neirong=neirong;
    }
    public void setFenlei(String fenlei){
        this.fenlei=fenlei;
    }
    public void setShuliag(int shuliag){
        this.shuliag=shuliag;
    }
    public void setImage2(String image2){
        this.image2path=image2;
    }
    public void setimage1path(String image1path){
        this.image1path=image1path;
    }
    public void setImage3(String image3){
        this.image3path =image3;
    }
    public int getZanshu(){return zanshu;}
    public int getCaishu(){return caishu;}
    public void setZanshu(int zanshu){this.zanshu=zanshu;}
    public void setCaishu(int caishu){this.caishu=caishu;}
    public String getZanren(){return zanren;}
    public String getCairen(){return cairen;}
    public void setZanren(String zanren){this.zanren=zanren;}
    public void setCairen(String cairen){this.cairen=cairen;}
    public String getTouxiangdizhi(){return touxiangdizhi;}
    public void setTouxiangdizhi(String s){touxiangdizhi=s;}
}
