package com.bk120.cinematicket;

import android.app.Application;

import com.bk120.cinematicket.bean.Comment;
import com.bk120.cinematicket.constants.MainConstant;
import com.bk120.cinematicket.db.CommentDao;
import com.bk120.cinematicket.utils.SharePreferencesUtils;

/**
 * Created by bk120 on 2017/3/12.
 */

public class MyApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //程序开始前做一些初始化操作,注意资源容易初始化多次
        initCommet();
    }
    //初始化化一些评论数据
    private void initCommet() {
        //判断是否需要
        boolean aBoolean = SharePreferencesUtils.getBoolean(this, MainConstant.IS_INIT_RES, false);
        if(!aBoolean){
            CommentDao dao=new CommentDao(this);
            for(int i=0;i< MainConstant.COMMENT_NAME.length;i++){
                Comment c=new Comment(MainConstant.COMMENT_NAME[i],i,MainConstant.COMMENT_DESCRIBE[i],
                        MainConstant.COMMENT_TIME[i]);
                dao.insert(c);
            }
            SharePreferencesUtils.putBoolean(this,MainConstant.IS_INIT_RES,true);
        }else {
            return;
        }

    }
}
