package com.bk120.cinematicket.bean;

/**
 * Created by bk120 on 2017/3/17.
 * 要购买的电影信息信号量
 */

public class BuyFilmSign {
    private String sign;
    public BuyFilmSign(String s){
        this.sign=s;
    }
    public String getSign() {
        return sign;
    }
}
