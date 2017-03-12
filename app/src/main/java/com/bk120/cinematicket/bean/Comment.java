package com.bk120.cinematicket.bean;

/**
 * Created by bk120 on 2017/3/12.
 * 影评对象
 */

public class Comment {
    private int id;
    private String name;
    private int icon;
    private String describe;
    private String time;
    public Comment(String n,int i,String d,String t){
        this.name=n;
        this.icon=i;
        this.describe=d;
        this.time=t;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
