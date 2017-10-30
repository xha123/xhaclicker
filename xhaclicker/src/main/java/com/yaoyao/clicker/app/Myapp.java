package com.yaoyao.clicker.app;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.yaoyao.clicker.fresco.ImageLoaderConfig;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/5/8.
 */

public class Myapp extends MultiDexApplication {
    private static Map<String, Object> hashMap = null;
    private static Object object = null;
    public static Context context;



    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
//        SharedUtils.init(context);
        setHashMap(new HashMap<String, Object>());
        //初始化Fresco
        Fresco.initialize(context, ImageLoaderConfig.getImagePipelineConfig(context));
        //初始化极光推送
        JPushInterface.setDebugMode(true);//正式版的时候设置false，关闭调试
        JPushInterface.init(this);
    }

    public static Map<String, Object> getHashMap()
    {
        return hashMap;
    }

    public static void setHashMap(Map<String, Object> map)
    {
        hashMap = map;
    }

    public static Context getAppContext()
    {
        return context;
    }

    public static Object getObject() {
        return object;
    }

    public static void setObject(Object object) {
        Myapp.object = object;
    }
}
