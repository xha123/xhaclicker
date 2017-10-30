package com.yaoyao.clicker.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.yaoyao.clicker.base.BaseActivity;

import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


/** 网络请求基类
 * Created by Administrator on 2017/4/20.
 */

public class HttpUtils {
    private static HttpUtils httpUtils;

    private OkHttpClient okHttpClient;
    private Gson gson;

    public static String URL = "http://192.168.1.90:8080/Takeout/user/";

    public static String SUCCESS = "200";


    private HttpUtils(){
        okHttpClient = new OkHttpClient();
        gson = new Gson();

    }

    public static HttpUtils getInstance(){
        if (httpUtils==null){
            httpUtils = new HttpUtils();
        }
        return httpUtils;
    }

    /**
     *  网络请求基本模式
     * @param map 请求数据
     * @return Call
     */
    public Call mapCall(Map<String,String> map,String url){
        FormBody.Builder builder = new FormBody.Builder();
        for (String key: map.keySet()) {
            builder.add(key,map.get(key));
        }
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return okHttpClient.newCall(request);
    }


//    public Call maptwoCall(Map<String,Object> map,String url){
//        FormBody.Builder builder = new FormBody.Builder();
//        for (String key: map.keySet()) {
//            builder.add(key,map.get(key));
//        }
//        RequestBody requestBody = builder.build();
//        Request request = new Request.Builder()
//                .url(url)
//                .post(requestBody)
//                .build();
//        return okHttpClient.newCall(request);
//    }

    /**
     * 错误码反馈信息
     * @param context
     * @param code
     */
    public void Errorcode(Context context,String code){
        if (code.equals("201")){
            BaseActivity.showtoast(context,"缺少参数，请稍后再试");
        }else if (code.equals("202")){
            BaseActivity.showtoast(context,"用户不存在");
        }else if (code.equals("203")){
            BaseActivity.showtoast(context,"存入失败");
        }else if (code.equals("204")){
            BaseActivity.showtoast(context,"图片不存在");
        }else if (code.equals("205")){
            BaseActivity.showtoast(context,"连接失败，请稍后重试");
        }else if (code.equals("206")){
            BaseActivity.showtoast(context,"你们还不是好友");
        }else if (code.equals("207")){
            BaseActivity.showtoast(context,"用户名或密码错误");
        }else if (code.equals("208")){
            BaseActivity.showtoast(context,"连接失败，请稍后重试");
        }else if (code.equals("209")){
            BaseActivity.showtoast(context,"连接失败，请稍后重试");
        }else if (code.equals("210")){
            BaseActivity.showtoast(context,"用户已存在");
        }else if (code.equals("211")){
            BaseActivity.showtoast(context,"赞不足");
        }else if (code.equals("212")){
            BaseActivity.showtoast(context,"已拉黑");
        }else if (code.equals("213")){
            BaseActivity.showtoast(context,"已关注");
        }else if (code.equals("214")){
            BaseActivity.showtoast(context,"已加为好友");
        }else if (code.equals("215")){
            BaseActivity.showtoast(context,"已举报");
        }else if (code.equals("216")){
            BaseActivity.showtoast(context,"验证码错误");
        }else if (code.equals("217")){
            BaseActivity.showtoast(context,"赞数不能为0");
        }else if (code.equals("218")){
            BaseActivity.showtoast(context,"删除失败，请稍后再试");
        }else if (code.equals("219")){
            BaseActivity.showtoast(context,"不是黑名单");
        }else if (code.equals("224")){
            BaseActivity.showtoast(context,"该手机号已注册");
        }

        else if (code.equals("228")){
            BaseActivity.showtoast(context,"请勿重复点赞");
        }



        else {
            BaseActivity.showtoast(context,"请稍后再试");
        }
    }

}
