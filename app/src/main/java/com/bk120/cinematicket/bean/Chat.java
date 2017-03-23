package com.bk120.cinematicket.bean;

/**
 * Created by bk120 on 2017/3/22.
 * 聊天信息
 */

public class Chat {
    //标识 0标识机器人，1标识用户
    private int sign;
    //用户名
    private String name;
    //信息
    private String message;
    //时间
    private String time;
    //构造器
    public Chat(int s,String n,String m,String t){
        this.sign=s;
        this.name=n;
        this.message=m;
        this.time=t;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }
}
