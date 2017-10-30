package com.yaoyao.clicker.utils;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.yaoyao.clicker.app.Myapp;

/**
 * Created by Administrator on 2017/8/7.
 */

public class SharedPreferenceUtils {
    private static final String SHARED_NAME = "user";

//    个人信息{"resultcode":"200","result":{"aid":3,"uid":"F9C7F5D3F6B944658682D324789CDDD6","name":"成都车展1",
//            "phone":"18382414601","picurl":"http://clicker1.oss-cn-shenzhen.aliyuncs.com/6053E7AB-DF8F-44D3-84A1-B00A19C2C04F.jpeg",
//            "focusCount":5,"focusedCount":3,"loveCount":1,"lovedCount":0,"sex":0,"hxid":"18382414601","udes":" 我们都会有些不是去问问",
//            "friship":"0","focuship":"0","signSendLove":0}}

    //用户信息存入取出
    public static void put(String key, Object value) {
        SharedPreferences preference = Myapp.getAppContext().getSharedPreferences(SHARED_NAME, 0);
        Editor eidtor = preference.edit();
        if (value instanceof String) {
            eidtor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            eidtor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            eidtor.putFloat(key, (Float) value);
        } else if (value instanceof Boolean) {
            eidtor.putBoolean(key, (Boolean) value);
        }else if (value instanceof Double){
            int showvalue = (int) Math.pow((double) value,1);
            eidtor.putInt(key, showvalue);
        }else {
            Log.e("share", "put: 错误类型" );
        }
        eidtor.commit();
    }

    public static Object get(String key, Object defaultValue) {
        SharedPreferences preference = Myapp.getAppContext().getSharedPreferences(SHARED_NAME, 0);
        if (defaultValue instanceof String) {
            return preference.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return preference.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Float) {
            return preference.getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            return preference.getBoolean(key, (Boolean) defaultValue);
        } else {
            Log.e("share", "put: 错误类型" );
            throw new RuntimeException("错误类型");
        }
    }

    public static void clear() {
        SharedPreferences preference = Myapp.getAppContext().getSharedPreferences(SHARED_NAME, 0);
        Editor eidtor = preference.edit();
        eidtor.clear();
        eidtor.apply();
        eidtor.commit();
    }


}
