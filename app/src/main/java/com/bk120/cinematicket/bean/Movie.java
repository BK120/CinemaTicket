package com.bk120.cinematicket.bean;

/**
 * Created by bk120 on 2017/3/17.
 * 当前正在热映的电影信息
 */

public class Movie {
    //影片ID
    private String movie_id;
    //影片名称
    private String movie_name;
    //影片海报路径
    private String pic_url;
    //构造器
    public Movie(String i,String n,String u){
        this.movie_id=i;
        this.movie_name=n;
        this.pic_url=u;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }
}
