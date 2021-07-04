package com.biyesheji.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.biyesheji.bean.SearchItem;

import java.util.ArrayList;

/**
 * Created by Administrator on 2020/12/3.
 */
public class BDmanage_search {
    MyHelper_user myHelper;
    SQLiteDatabase db;
    ContentValues values;
    public BDmanage_search(Context context)
    {
        myHelper =new MyHelper_user(context);
    }
    public ArrayList<SearchItem> queryAll(String name)
    {
        db=myHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from search where account=?",new String[]{name});
        if(cursor.getCount()==0)
        {
            return null;
        }
        ArrayList<SearchItem>list=new ArrayList<>();
        SearchItem item;
        while (cursor.moveToNext())
        {
            item=new SearchItem();
            item.setId(cursor.getInt(0));
            item.setSearchtext(cursor.getString(2));
            list.add(item);
        }
        return list;
    }
    public void insert(String name,String searchtext)
    {
        db=myHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from search where searchtext=?",new String[]{searchtext});
        if(cursor.getCount()==0)
        {
            db = myHelper.getWritableDatabase();
            values = new ContentValues();
            values.put("account",name);
            values.put("searchtext",searchtext);
            db.insert("search",null,values);
            db.close();
        }
    }
    public void update(String id,String searchtext)
    {
        db=myHelper.getWritableDatabase();
        values=new ContentValues();
        values.put("searchtext",searchtext);
        db.update("search", values, "_id=?", new String[]{(id)});
        db.close();
    }
    public void delete(String id)
    {
        db=myHelper.getWritableDatabase();
        db.delete("search","_id="+id,null);
        db.execSQL("DELETE FROM sqlite_sequence WHERE name='search'");
        db.execSQL("update search set _id=_id-1 where _id>" + id);
        db.close();
    }
    public void deleteall(String name)
    {
        db=myHelper.getWritableDatabase();
        db.delete("search","account="+name,null);
        db.execSQL("DELETE FROM sqlite_sequence WHERE name='search'");
        db.close();
    }
}
