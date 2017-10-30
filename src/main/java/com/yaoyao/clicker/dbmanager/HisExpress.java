package com.yaoyao.clicker.dbmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/22.
 */

public class HisExpress {
    Context context;//上下文
    SQLiteDatabase db;//数据库操作类
    public static HisExpress hisExpress;

    public static HisExpress getInstance(Context context){
        if (hisExpress==null){
            hisExpress = new HisExpress(context);
        }
        return hisExpress;
    }

    private HisExpress(Context context){
        this.context = context;
        HisdbHelper hisdbHelper = new HisdbHelper(context);
        db = hisdbHelper.getWritableDatabase();//获得数据库操作对象
    }

    public void addHisSeek(Map<String,Object> map){
        //开启一个数据暂存器
        ContentValues contentValues = new ContentValues();
        //key 列名  value  储存的数据
        contentValues.put("uid", (String) map.get("uid"));
        contentValues.put("udes", (String) map.get("udes"));
        contentValues.put("friship", (String) map.get("udes"));
        contentValues.put("name", (String) map.get("name"));
        contentValues.put("focuship", (String) map.get("focuship"));
        contentValues.put("picurl", (String) map.get("picurl"));
        //第一个参数你要操作的表，第二个参数 是否有主键，第三个参数你存储的暂存器
        db.insert("historyseek",null,contentValues);
    }

    public void delall(){
        db.delete("historyseek",null,null);
    }

    public List<Map<String,Object>> getHisSeek(){
        List<Map<String,Object>> list= new ArrayList<>();
        Cursor cursor = null;
        //从对应的数据库表中取出
        cursor = db.rawQuery("select * from historyseek",null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                Map<String,Object> maphis = new HashMap<>();
                maphis.put("uid",cursor.getString(cursor.getColumnIndex("uid")));
                maphis.put("udes",cursor.getString(cursor.getColumnIndex("udes")));
                maphis.put("friship",cursor.getString(cursor.getColumnIndex("friship")));
                maphis.put("name",cursor.getString(cursor.getColumnIndex("name")));
                maphis.put("focuship",cursor.getString(cursor.getColumnIndex("focuship")));
                maphis.put("picurl",cursor.getString(cursor.getColumnIndex("picurl")));
                list.add(maphis);
            }
        }
        Collections.reverse(list);
        return list;
    }

    public void delOneSeek(Map<String,Object> map){
        String uid = (String) map.get("uid");
        db.delete("historyseek","uid=?",new String[]{uid});
    }
}
