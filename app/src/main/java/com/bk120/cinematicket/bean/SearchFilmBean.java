package com.bk120.cinematicket.bean;

/**
 * Created by bk120 on 2017/3/5.
 * 已经搜索到的电影电视剧对象
 */

public class SearchFilmBean {
    //影视名
    private String title;
    //影视类型
    private String tag;
    //演员
    private String acts;
    //出厂年份
    private String year;
    //评分
    private String rating;
    //地区
    private String area;
    //导演
    private String dir;
    //影视描述
    private String desc;
    //当前影视状态 play:表示能播放
    private String vdo_status;
    //视频图片
    private String cover;
    //优酷的播放页面
    private String youKuFilmUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getActs() {
        return acts;
    }

    public void setActs(String acts) {
        this.acts = acts;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getVdo_status() {
        return vdo_status;
    }

    public void setVdo_status(String vdo_status) {
        this.vdo_status = vdo_status;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getYouKuFilmUrl() {
        return youKuFilmUrl;
    }

    public void setYouKuFilmUrl(String youKuFilmUrl) {
        this.youKuFilmUrl = youKuFilmUrl;
    }
}
