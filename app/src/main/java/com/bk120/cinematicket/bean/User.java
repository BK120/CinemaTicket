package com.bk120.cinematicket.bean;

/**
 * Created by bk120 on 2017/3/7.
 * 用户表
 */

public class User {
    //唯一标识，用户Id
    private int id;
    private String username;
    private String password;
    private String  status;
    private double balance;
    private String motto;
    private int isRememberPWD;
    private  int isAutoLogin;
    public User(String username ,String password){
        this.username=username;
        this.password=password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public int getIsRememberPWD() {
        return isRememberPWD;
    }

    public void setIsRememberPWD(int isRememberPWD) {
        this.isRememberPWD = isRememberPWD;
    }

    public int getIsAutoLogin() {
        return isAutoLogin;
    }

    public void setIsAutoLogin(int isAutoLogin) {
        this.isAutoLogin = isAutoLogin;
    }

    @Override
    public String toString() {
        return "[id="+id+";username="+this.username+";password="+password+";status="+status+";balance="+balance
                +";motto="+motto+";isRemeberPWD="+isRememberPWD+";isAutoLogin="+isAutoLogin+"]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
