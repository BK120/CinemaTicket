package com.bk120.cinematicket.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bk120.cinematicket.bean.User;

/**
 * Created by bk120 on 2017/3/7.
 * dao层处理user_info表中的数据正删改查
 */

public class UserInfoDao {
    private Context mContext;
    private MyOpenHelper mMyOpenHelper;
    private String table="user_info";
    public UserInfoDao(Context c){
        this.mContext=c;
        mMyOpenHelper=new MyOpenHelper(c);
    }
    //增加插入一条数据一条数据
    public void insert(User user){
        SQLiteDatabase db = mMyOpenHelper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("username",user.getUsername());
        cv.put("password",user.getPassword());
        cv.put("status",user.getStatus());
        cv.put("balance",user.getBalance());
        cv.put("motto",user.getMotto());
        cv.put("isRememberPWD",user.getIsRememberPWD());
        cv.put("isAutoLogin",user.getIsAutoLogin());
        db.insert(table,null,cv);
        db.close();
    }
    //根据用户名查找一个用户,查询不到返回null
    public User select(String username){
        User user=null;
        SQLiteDatabase db = mMyOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(table, new String[]{"_id", "password", "status", "balance", "motto", "isRememberPWD", "isAutoLogin"},
                "username=?", new String[]{username}, null, null, null);
        if(cursor.moveToNext()){
            int id=cursor.getInt(0);
            String pwd=cursor.getString(1);
            String status=cursor.getString(2);
            double balance=cursor.getDouble(3);
            String motto=cursor.getString(4);
            int isRememberPWD=cursor.getInt(5);
            int isAutoLogin=cursor.getInt(6);
            user=new User(username,pwd);
            user.setId(id);
            user.setStatus(status);
            user.setBalance(balance);
            user.setIsAutoLogin(isAutoLogin);
            user.setIsRememberPWD(isRememberPWD);
            user.setMotto(motto);
        }
        db.close();
        return user;
    }
    //获得一个当前在线登录的用户,否则返回为空
    public User selectOnLine(){
        User user=null;
        //查询值传入不能为必须跟开始传入值相同，最好不要用Int来查询  很坑
        String x="1";
        SQLiteDatabase db = mMyOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(table, new String[]{"_id", "username","password", "balance", "motto", "isRememberPWD", "isAutoLogin"},
                "status=?", new String[]{x}, null, null, null);
        if(cursor.moveToNext()){
            int id=cursor.getInt(0);
            String username=cursor.getString(1);
            String pwd=cursor.getString(2);
            double balance=cursor.getDouble(3);
            String motto=cursor.getString(4);
            int isRememberPWD=cursor.getInt(5);
            int isAutoLogin=cursor.getInt(6);
            user=new User(username,pwd);
            user.setId(id);
            user.setStatus("1");
            user.setBalance(balance);
            user.setIsAutoLogin(isAutoLogin);
            user.setIsRememberPWD(isRememberPWD);
            user.setMotto(motto);
        }
        db.close();
        return user;
    }
    //修改一个用户的信息，比如，用户名，密码，座右铭，各种登录状态等,以Id为唯一标识
    //前提是当前当前用户处在登录状态
    public void update(User user){
        SQLiteDatabase db = mMyOpenHelper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("username",user.getUsername());
        cv.put("password",user.getPassword());
        cv.put("status",user.getStatus());
        cv.put("balance",user.getBalance());
        cv.put("motto",user.getMotto());
        cv.put("isRememberPWD",user.getIsRememberPWD());
        cv.put("isAutoLogin",user.getIsAutoLogin());
        db.update(table,cv,"_id=?",new String[]{user.getId()+""});
        db.close();
    }
}
