package com.bk120.cinematicket.utils;

import java.util.Calendar;

/**
 * Created by bk120 on 2017/3/12.
 * 时间处理工具类
 */

public class DateUtils {
    //获取当前的日期
    public static String getCurrentDate(){
        Calendar c=Calendar.getInstance();
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH)+1;
        int day=c.get(Calendar.DAY_OF_MONTH);
        return year+"年"+month+"月"+day+"日";
    }
}
