package com.yaoyao.clicker.dbmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/5/22.
 */

public class HisdbHelper extends SQLiteOpenHelper{
    private final String DB_NAME="clicker.db";//数据库的名字 一般后缀为db
    private  int DB_VERSION=1;//数据库版本

    public HisdbHelper(Context context) {
        super(context, "clicker.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table historyseek(uid text,udes text,friship text,name text,focuship text,picurl text)");
        //创建了一张名为historyseek的表 里面有6列  数据类型为文本.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
