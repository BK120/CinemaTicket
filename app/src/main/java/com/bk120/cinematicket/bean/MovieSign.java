package com.bk120.cinematicket.bean;

/**
 * Created by bk120 on 2017/3/17.
 * 所有当前正在热映的电影信息信号量
 */

public class MovieSign {
    private String sign;
    public MovieSign(String s){
        this.sign=s;
    }
    public String getSign() {
        return sign;
    }
}
