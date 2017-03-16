package com.bk120.cinematicket.bean;

/**
 * Created by bk120 on 2017/3/16.
 * 返回获取电影院的数据信号量
 */

public class CinemaSign {
    private String sign;
    public CinemaSign(String address){
        this.sign=address;
    }
    public String getSign(){
        return this.sign;
    }
}
