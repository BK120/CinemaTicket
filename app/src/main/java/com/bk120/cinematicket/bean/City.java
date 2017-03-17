package com.bk120.cinematicket.bean;

/**
 * Created by bk120 on 2017/3/17.
 * 支持查询的所有城市信息
 */

public class City {
    //支持恶城市Id
    private String id;
    //城市名
    private String name;
    //支持的城市影院数量
    private String count;
    public City(String i,String n,String c){
        this.id=i;
        this.name=n;
        this.count=c;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
