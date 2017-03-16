package com.bk120.cinematicket.bean;

/**
 * Created by bk120 on 2017/3/16.
 * 影院信息
 */

public class Cinema {
    //影院名称
    private String name;
    //影院地址
    private String address;
    //联系电话
    private String telephone;
    //经度
    private String latitude;
    //纬度
    private String longtitudel;
    //交通路线
    private String trafficRoutes;
    //距离
    private String distance;
    //构造器
    public Cinema(String n,String a){
        this.name=n;
        this.address=a;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitudel() {
        return longtitudel;
    }

    public void setLongtitudel(String longtitudel) {
        this.longtitudel = longtitudel;
    }

    public String getTrafficRoutes() {
        return trafficRoutes;
    }

    public void setTrafficRoutes(String trafficRoutes) {
        this.trafficRoutes = trafficRoutes;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
