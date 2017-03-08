package com.bk120.cinematicket.bean;

/**
 * Created by bk120 on 2017/3/6.
 * 已经搜索到的电影地址对象
 */

public class FilmUrlBean {
    private String playUrl;
    public FilmUrlBean(String url){
        this.playUrl=url;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }
}
