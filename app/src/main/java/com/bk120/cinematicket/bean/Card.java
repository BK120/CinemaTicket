package com.bk120.cinematicket.bean;

/**
 * Created by bk120 on 2017/3/8.
 * 信用卡
 * 钱包界面，显示当前信用卡种类如，建行，农行，工行，支付宝，微信，QQ等
 */

public class Card {
    private int id;
    //拥有者，单一
    private String hostName;
    //类型,建行，农行，啥的?
    private int type;
    //账号名
    private String cardName;
    //余额
    private double balance;
    //背景颜色,0~5
    private int bgColor;
    public Card(String hn,int type,double b){
        this.hostName=hn;
        this.type=type;
        this.balance=b;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    @Override
    public String toString() {
        return "[id="+id+";hostName="+hostName+";type="+type+";cardName="+cardName+";balance="+"" +
                balance+";bgColor="+bgColor+"+]";
    }
}
