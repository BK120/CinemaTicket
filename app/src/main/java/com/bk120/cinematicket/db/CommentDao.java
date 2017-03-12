package com.bk120.cinematicket.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bk120.cinematicket.bean.Card;
import com.bk120.cinematicket.bean.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bk120 on 2017/3/12.
 * //对影片的评论表
 */

public class CommentDao {
    private String table="comment_info";
    private MyOpenHelper helper;
    public CommentDao(Context c){
        helper=new MyOpenHelper(c);
    }
    //插入一条数据
    public void insert(Comment comment){
            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues cv=new ContentValues();
            cv.put("name",comment.getName());
            cv.put("icon",comment.getIcon());
            cv.put("describe",comment.getDescribe());
            cv.put("time",comment.getTime());
            db.insert(table,null,cv);
            db.close();
    }
    //删除一条数据
    public void delete(Comment comment){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(table,"_id=?",new String[]{comment.getId()+""});
        db.close();
    }
    //查询所有影评信息
    public List<Comment> selelctAll(){
        List<Comment> list=new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(table, null, null, null, null, null, null, null);
        while(cursor.moveToNext()){
            int id=cursor.getInt(0);
            String name=cursor.getString(1);
            int icon=cursor.getInt(2);
            String describe=cursor.getString(3);
            String time=cursor.getString(4);
            Comment u=new Comment(name,icon,describe,time);
            u.setId(id);
            list.add(u);
        }
        db.close();
        return list;
    }
}
