package com.bk120.cinematicket.bean;

/**
 * Created by bk120 on 2017/3/7.
 * 注册成功信号
 */

public class RegisterSuccessSign {
    private String userName;
    private String passWord;
    public RegisterSuccessSign(String name,String pwd){
        this.userName=name;
        this.passWord=pwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
