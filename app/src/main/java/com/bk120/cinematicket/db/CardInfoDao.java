package com.bk120.cinematicket.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bk120.cinematicket.bean.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bk120 on 2017/3/8.
 * Card表的dao层操作
 */

public class CardInfoDao {
    private String table="card_info";
    private MyOpenHelper helper;
    public CardInfoDao(Context context){
        helper=new MyOpenHelper(context);
    }
    //插入一条数据
    public void insert(Card card){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("hostName",card.getHostName());
        cv.put("cardName",card.getCardName());
        cv.put("type",card.getType());
        cv.put("bgColor",card.getBgColor());
        cv.put("balance",card.getBalance());
        db.insert(table,null,cv);
        db.close();
    }
    //删除一条数据
    public void delete(Card card){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(table,"_id=?",new String[]{card.getId()+""});
        db.close();
    }
    //更新一条数据
    public void update(Card card){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("hostName",card.getHostName());
        cv.put("cardName",card.getCardName());
        cv.put("type",card.getType());
        cv.put("bgColor",card.getBgColor());
        cv.put("balance",card.getBalance());
        db.update(table,cv,"_id=?",new String[]{card.getId()+""});
        db.close();
    }
    //查询所有Card信息
    public List<Card> selectAll(String hostName){
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Card> list=null;
        Cursor cursor = db.query(table, new String[]{"_id","cardName","type","bgColor",
        "balance"},"hostName=?", new String[]{hostName}, null, null, null);
        if (cursor.getCount()==0){
            return list;
        }
         list=new ArrayList<>();
        while (cursor.moveToNext()){
            int id=cursor.getInt(0);
            String cardName=cursor.getString(1);
            int type=cursor.getInt(2);
            int bgColor=cursor.getInt(3);
            double balance=cursor.getDouble(4);
            Card card=new Card(hostName,type,balance);
            card.setId(id);
            card.setBgColor(bgColor);
            card.setCardName(cardName);
            list.add(card);
        }
        return list;
    }

}
