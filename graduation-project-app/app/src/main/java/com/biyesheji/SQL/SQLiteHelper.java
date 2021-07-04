package com.biyesheji.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2020/12/12.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION=1;
    public static final String DB_NAME="khz.db";
    public static final String U_USERINFO="userInfo";
    public static final String U_VIDEO_PLAY_LIST = "videoPlayList";

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    /*
    *
    * 创建数据库
    * */
    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * 创建个人信息表
         */
        db.execSQL("CREATE TABLE IF NOT EXISTS "+U_USERINFO+"( "
                +"_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +"username VARCHAR, " //用户名
                +"nickname VARCHAR, "  //昵称
                +"sex VARCHAR, "  //性别
                +"signature VARCHAR"   //签名
                +")");
        /**
         * 创建视频播放记录
         */
        db.execSQL("CREATE TABLE IF NOT EXISTS "+U_VIDEO_PLAY_LIST+"( "
                +"_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +"username VARCHAR, " //用户名
                +"chapterId VARCHAR, "  //章id
                +"videoId VARCHAR, "  //节id
                +"videoPath VARCHAR,"   //视频流
                +"title VARCHAR,"   //章节名字
                +"secondTitle VARCHAR"    //视频名字
                +")");
    }
    /*
    *
    * 更新数据库
    * */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+U_USERINFO);
        db.execSQL("DROP TABLE IF EXISTS "+U_VIDEO_PLAY_LIST);
        onCreate(db);
    }
}
