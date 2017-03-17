package com.bk120.cinematicket.constants;

/**
 * Created by bk120 on 2017/3/16.
 * 来源于聚合数据的相关API
 */

public class JuHeConstant {
    //获取影视信息的KEY
    public static String KEY="0196571081b01dbffe9f5073e9a8efdd";
    //查询所有城市的信息
    public static String CITY_INFO_URL="http://v.juhe.cn/movie/citys?key="+KEY;
    //正在热映的电影
    public static String CURRENT_PLAYING_FILM_URL="http://v.juhe.cn/movie/movies.today?key="+KEY+"&cityid=";


}
