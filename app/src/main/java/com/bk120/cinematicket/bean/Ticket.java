package com.bk120.cinematicket.bean;

/**
 * Created by bk120 on 2017/3/20.
 * 电影票对象
 */

public class Ticket {
    //电影票Id
    private int id;
    //电影Id
    private String moive_id;
    //电影名称
    private String movie_name;
    //用户名
    private String user_name;
    //价格
    private double price;
    //影院名称
    private String cinema_name;
    //影院地址
    private String cinema_address;
    //座位号0~79
    private int chair_number;
    //座位具体位置 如3排5座
    private String chair_loc;
    //日期
    private String time;
    public Ticket(String i,String mN,String uN,double p){
        this.moive_id=i;
        this.movie_name=mN;
        this.user_name=uN;
        this.price=p;
    }
    public String getMoive_id() {
        return moive_id;
    }

    public void setMoive_id(String moive_id) {
        this.moive_id = moive_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCinema_name() {
        return cinema_name;
    }

    public void setCinema_name(String cinema_name) {
        this.cinema_name = cinema_name;
    }

    public String getCinema_address() {
        return cinema_address;
    }

    public void setCinema_address(String cinema_address) {
        this.cinema_address = cinema_address;
    }

    public int getChair_number() {
        return chair_number;
    }

    public void setChair_number(int chair_number) {
        this.chair_number = chair_number;
    }

    public String getChair_loc() {
        return chair_loc;
    }

    public void setChair_loc(String chair_loc) {
        this.chair_loc = chair_loc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
