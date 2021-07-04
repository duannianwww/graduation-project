package com.biyesheji.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2020/12/6.
 */
public class MyHelper_user extends SQLiteOpenHelper {
    public MyHelper_user(Context context) {
        super(context, "incast_user.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE search(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "account text," +
                "searchtext text" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
