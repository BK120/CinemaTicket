package com.bk120.cinematicket.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bk120 on 2017/3/7.
 * //创建表
 */

public class MyOpenHelper extends SQLiteOpenHelper{
    //数据库名称
    public static String DBNAME="buyTicket.db";
    //版本
    public  static int VERSION=1;
    public MyOpenHelper(Context context){
        super(context,DBNAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建用户表
        /**
         * _id主键自动增长   ints
         * username 用户名  String  同时用户名也是单一存在的，没有相同的用户名存在用户表中
         * password  密码  String
         * status  登录状态  0,1  --同一时间同一设备只能有一个用户处于登录状态
         * balance  账户余额  double
         * motto   座右铭  String
         * isRememberPWD  是否记住密码  0,1
         * isAutoLogin   是否自动登录  0,1
         */
        String user_sqlTable="create table user_info(_id Integer " +
                "primary key autoincrement,username,password,status,balance,motto,isRememberPWD,isAutoLogin)";
        db.execSQL(user_sqlTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
