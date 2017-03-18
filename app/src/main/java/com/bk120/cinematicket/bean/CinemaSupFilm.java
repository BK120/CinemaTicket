package com.bk120.cinematicket.bean;

/**
 * Created by bk120 on 2017/3/18.
 * 支持当前电影的影院信息实体类
 */

public class CinemaSupFilm {
    //影院ID
    private String cinemaId;
    //影院名称
    private String cinemaName;
    //影院地址
    private String address;
    //经度
    private String latitde;
    //纬度
    private String longtitude;
    public CinemaSupFilm(String i,String n,String a,String lat,String lon){
        this.cinemaId=i;
        this.cinemaName=n;
        this.address=a;
        this.latitde=lat;
        this.longtitude=lon;
    }

    public String getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(String cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitde() {
        return latitde;
    }

    public void setLatitde(String latitde) {
        this.latitde = latitde;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }
}
