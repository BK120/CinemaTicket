package com.bk120.cinematicket.constants;

/**
 * Created by bk120 on 2017/3/16.
 * 来源于聚合数据的相关API
 */

public class JuHeConstant {
    //获取影视信息的KEY
    public static String KEY="0196571081b01dbffe9f5073e9a8efdd";
    //检索影院的URLhttp://v.juhe.cn/movie/cinemas.local?
    // key=您申请的key&dtype=json&lat=31.30947&lon=120.6003&radius=2000
    //查询半径  最大3000米之内
    public static String RADIUS="3000";
    public static String GET_CINEM_URL="http://v.juhe.cn/movie/cinemas.local?key="+KEY+"&dtype=json";
}
