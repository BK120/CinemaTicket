package com.bk120.cinematicket.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bk120.cinematicket.bean.Card;
import com.bk120.cinematicket.bean.Ticket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bk120 on 2017/3/20.
 * 电影票查询Dao层
 */

public class TicketInfoDao {
    private String table="ticket_info";
    private MyOpenHelper helper;
    public TicketInfoDao(Context context){
        helper=new MyOpenHelper(context);
    }
    //插入一条数据
    public void insert(Ticket ticket){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("movie_id",ticket.getMoive_id());
        cv.put("movie_name",ticket.getMovie_name());
        cv.put("user_name",ticket.getUser_name());
        cv.put("price",ticket.getPrice());
        cv.put("cinema_name",ticket.getCinema_name());
        cv.put("cinema_address",ticket.getCinema_address());
        cv.put("chair_number",ticket.getChair_number());
        cv.put("chair_loc",ticket.getChair_loc());
        cv.put("time",ticket.getTime());
        db.insert(table,null,cv);
        db.close();
    }
    //删除一条数据
    public void delete(Ticket ticket){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(table,"_id=?",new String[]{ticket.getId()+""});
        db.close();
    }

    //查询一个用户的Card信息
    public List<Ticket> selectAll(String user_name){
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Ticket> list=null;
        Cursor cursor = db.query(table, new String[]{"_id","movie_id","movie_name",
                "price","cinema_name","cinema_address","chair_number","chair_loc","time"},"user_name=?",
                new String[]{user_name}, null, null, null);
        if (cursor.getCount()==0){
            return list;
        }
        list=new ArrayList<>();
        while (cursor.moveToNext()){
            int id=cursor.getInt(0);
            String movie_id=cursor.getString(1);
            String movie_name=cursor.getString(2);
            double price = cursor.getDouble(3);
            String cinema_name = cursor.getString(4);
            String cinema_address = cursor.getString(5);
            int chair_number = cursor.getInt(6);
            String chair_loc = cursor.getString(7);
            String time = cursor.getString(8);
            Ticket ticket=new Ticket(movie_id,movie_name,user_name,price);
            ticket.setId(id);
            ticket.setChair_loc(chair_loc);
            ticket.setChair_number(chair_number);
            ticket.setCinema_address(cinema_address);
            ticket.setCinema_name(cinema_name);
            ticket.setTime(time);
            list.add(ticket);
        }
        return list;
    }
    //查询所有用户的购票信息
    public List<Ticket> selectAllUsers(){
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Ticket> list=null;
        Cursor cursor = db.query(table, new String[]{"_id","movie_id","movie_name",
                        "price","cinema_name","cinema_address","chair_number","chair_loc","time","user_name"},null,
                null, null, null, null);
        if (cursor.getCount()==0){
            return list;
        }
        list=new ArrayList<>();
        while (cursor.moveToNext()){
            int id=cursor.getInt(0);
            String movie_id=cursor.getString(1);
            String movie_name=cursor.getString(2);
            double price = cursor.getDouble(3);
            String cinema_name = cursor.getString(4);
            String cinema_address = cursor.getString(5);
            int chair_number = cursor.getInt(6);
            String chair_loc = cursor.getString(7);
            String time = cursor.getString(8);
            String user_name = cursor.getString(9);
            Ticket ticket=new Ticket(movie_id,movie_name,user_name,price);
            ticket.setId(id);
            ticket.setChair_loc(chair_loc);
            ticket.setChair_number(chair_number);
            ticket.setCinema_address(cinema_address);
            ticket.setCinema_name(cinema_name);
            ticket.setTime(time);
            list.add(ticket);
        }
        return list;
    }


}
