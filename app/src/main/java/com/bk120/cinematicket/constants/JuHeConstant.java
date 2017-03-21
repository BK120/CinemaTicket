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
    //当前电影当前城市的所有支持该电影的影院信息
    //http://v.juhe.cn/movie/movies.cinemas?key=您申请的KEY&cityid=1&movieid=190465
    public static String CINEMA_SUP_FILM_URL="http://v.juhe.cn/movie/movies.cinemas?key="+KEY;
    //要购买的电影详情
    //http://v.juhe.cn/movie/query?key=您申请的key&movieid=137742
    public static String BUY_FILM_URL="http://v.juhe.cn/movie/query?key="+KEY+"&movieid=";


}
