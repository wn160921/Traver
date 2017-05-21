package com.example.traver.shujubiao;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/3/28/028.
 */

public class Lunbotu extends DataSupport{
    private int id;
    private String name;
    private byte[] image;
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
    public byte[] getImage(){
        return image;
    }
    public void setImage(byte[] bytes){
        image=bytes;
    }
}
