package com.yaoyao.clicker.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static com.yaoyao.clicker.base.BaseActivity.TAG;

/** 工具类
 * Created by Administrator on 2017/7/31.
 */

public class DataUtils {

    private static volatile DataUtils dataUtils;

    public DataUtils() {
    }

    public static DataUtils getInstans(){
        // 对象实例化时与否判断（不使用同步代码块，instance不等于null时，直接返回对象，提高运行效率）
        if (dataUtils==null){
            //同步代码块（对象未初始化时，使用同步代码块，保证多线程访问时对象在第一次创建后，不再重复被创建）
            synchronized (DataUtils.class) {
                //未初始化，则初始instance变量
                if (dataUtils == null) {
                    dataUtils = new DataUtils();
                }
            }
        }
        return dataUtils;
    }



    /**
     * 通过时间戳获取年月日
     * @param time
     * @return
     */
    public String getTime(long time){
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
        String d = format.format(time);
        try {
            Date date=format.parse(d);
//            Log.e("msg", "onBindViewHolder: "+d );
//            Log.e("msg", "onBindViewHolder: "+date );
            return d;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获得当地年月日时分秒
     * @param time
     * @return
     */
    public String getNowTime(long time){
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = format.format(time);
        try {
            Date date=format.parse(d);
            return d;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /** 改变键盘输入法的状态，如果已经弹出就关闭，如果关闭了就强制弹出 */
    public void chageInputState(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /** 强制关闭软键盘 */
    public void closeKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /** 打开软键盘*/
    public void openKeyboard(Context context,View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
    }

    /**
     * 判断手机号是否合法
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public boolean isChinaPhone(String str)
            throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }
    //手机号正则表达式
    private static final String REGEX_PHONE_NUMBER = "^(0(10|2\\d|[3-9]\\d\\d)[- ]{0,3}\\d{7,8}|0?1[3584]\\d{9})$";
    //邮箱正则表达式
    public final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }



    /**
     *  判断网络是否可用
     * @param context
     * @return
     */
    public boolean wangLuo(Context context){
        // 1.获取系统服务
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // 2.获取net信息
        NetworkInfo info = cm.getActiveNetworkInfo();
        // 3.判断网络是否可用
        if (info != null && info.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将json对象转换成map集合
     * @param gsonStr
     * @return
     */
    public Map<String, Object> gsonToMap(String gsonStr) {
        Log.e("tag", gsonStr);
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> map = gson.fromJson(gsonStr, type);
        return map;
    }

    /**
     * 将图片压缩显示
     * @param draweeView
     * @param uri
     * @param width
     * @param height
     */
    public void setImageSrc(final SimpleDraweeView draweeView, Uri uri, int width, int height) {
        ImageRequestBuilder builder = ImageRequestBuilder.newBuilderWithSource(uri);
        if(width > 0 && height > 0){
            builder.setResizeOptions(new ResizeOptions(width, height));
        }
        ImageRequest request = builder.build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setTapToRetryEnabled(true)
                .setOldController(draweeView.getController())
                .build();
        draweeView.setController(controller);
    }

    /**
     * 獲取本地
     * @param context
     * @return
     */
    public static Location getLocation(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 返回所有已知的位置提供者的名称列表，包括未获准访问或调用活动目前已停用的。
        //List<String> lp = lm.getAllProviders();
        Criteria criteria = new Criteria();
        criteria.setCostAllowed(true);
        //设置位置服务免费
        criteria.setAccuracy(Criteria.ACCURACY_COARSE); //设置水平位置精度
        //getBestProvider 只有允许访问调用活动的位置供应商将被返回
//        criteria.setAccuracy(Criteria.ACCURACY_FINE);//高精度
        criteria.setAltitudeRequired(false);//无海拔要求
        criteria.setBearingRequired(false);//无方位要求
        String providerName = lm.getBestProvider(criteria, true);

        if (providerName != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            Location location = lm.getLastKnownLocation(providerName);
            if(location!=null){
                return location;
            }else {
                Log.e(TAG, "getLocation: 定位失败" );
                return getLocationByNetwork(context);
            }
        }
        return null;
    }

    public static Location getLocationByNetwork(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {

            // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
            @Override
            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {

            }

            // Provider被enable时触发此函数，比如GPS被打开
            @Override
            public void onProviderEnabled(String provider) {

            }

            // Provider被disable时触发此函数，比如GPS被关闭
            @Override
            public void onProviderDisabled(String provider) {

            }

            // 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    Log.e("location",
                            "Location changed : Lat: " + location.getLatitude()
                                    + " Lng: " + location.getLongitude());
                }
            }
        };
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 3000, 0, locationListener);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        Location location = locationManager
                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        double latitude = 0;
        double longitude = 0;
        if (location != null) {
            latitude = location.getLatitude(); // 经度
            longitude = location.getLongitude(); // 纬度
            return location;
        }else {
            Log.e(TAG, "getLocationByNetwork: wifi");
            return null;
        }
    }




}
