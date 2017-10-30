package com.yaoyao.clicker.utils;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/8/8.
 */

public class MyUtils {

    private static volatile MyUtils myUtils;
    public MyUtils() {
    }
    public static MyUtils getInstans(){
        // 对象实例化时与否判断（不使用同步代码块，instance不等于null时，直接返回对象，提高运行效率）
        if (myUtils==null){
            //同步代码块（对象未初始化时，使用同步代码块，保证多线程访问时对象在第一次创建后，不再重复被创建）
            synchronized (MyUtils.class) {
                //未初始化，则初始instance变量
                if (myUtils == null) {
                    myUtils = new MyUtils();
                }
            }
        }
        return myUtils;
    }


    boolean mineShua;//个人中心是否刷新
    boolean likeShua;
    boolean homeShua;
    boolean seekShua;
    boolean retuiShua;
    String usershowid;
//    AccessKey、SecretKeyId、SecurityToken、Expiration
    String accessKey,secretKeyId,securityToken,expiration;

    public boolean isRetuiShua() {
        return retuiShua;
    }

    public void setRetuiShua(boolean retuiShua) {
        this.retuiShua = retuiShua;
    }

    public boolean isHomeShua() {
        return homeShua;
    }

    public void setHomeShua(boolean homeShua) {
        this.homeShua = homeShua;
    }

    public boolean isLikeShua() {
        return likeShua;
    }

    public void setLikeShua(boolean likeShua) {
        this.likeShua = likeShua;
    }

    public boolean isSeekShua() {
        return seekShua;
    }

    public void setSeekShua(boolean seekShua) {
        this.seekShua = seekShua;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getSecretKeyId() {
        return secretKeyId;
    }

    public void setSecretKeyId(String secretKeyId) {
        this.secretKeyId = secretKeyId;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public String getUsershowid() {
        return usershowid;
    }

    public void setUsershowid(String usershowid) {
        this.usershowid = usershowid;
    }

    public boolean isMineShua() {
        return mineShua;
    }

    public void setMineShua(boolean mineShua) {
        this.mineShua = mineShua;
    }

    //设置double精确到小数点后两位
    public String doubleTwo(double price){
//        DecimalFormat df = new DecimalFormat("#.00");
        String shows = String.format("%.2f",price);
        if (shows.indexOf(".")>0){
            shows = shows.replaceAll("0+?$", "");//去掉多余的0
            shows = shows.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return shows;
    }

    public String getnowTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowtime = sdf.format(new java.util.Date());
        return nowtime;
    }

    /**
     * 获取uuid
     * @return
     */
    public String getMyUUID(){
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString();
        Log.e("debug","----->UUID"+uuid);
        return uniqueId;
    }

    /**
     * 中文转码
     * @param comment
     * @return
     */
    public String getIsoShow(String comment){
        String iso_comment;
        try {
            iso_comment = new String(comment.getBytes("UTF-8"),"ISO8859-1");
            return  iso_comment;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * map转json字符串
     * @param map
     * @return
     */
    public String mapToJson(Map<String, Object> map)
    {
        String tab_str = "";
        String json = tab_str + "{";
        int i = 0;
        for (String key : map.keySet())
        {
            if (i >= map.size())
                break;
            String content = "";
            try
            {
                List<Map<String, Object>> list = (List<Map<String, Object>>) map.get(key);
                content += "[";
                int j = 0;
                for (Map<String, Object> map2 : list) {
                    if (j == list.size())
                        break;
                    content += mapToJson(map2) + (j++ == list.size() - 1 ? "" : ",");
                }
                content += tab_str + "]"/* + (i == (map.size() - 1) ? "" : ",")*/;
            }
            catch (Exception e)
            {
                content = "\"" + map.get(key).toString() + "\"";
            }
            json += tab_str + "\"" + key + "\":" + content + (i++ == map.size() - 1 ? "" : ",");
        }
        json += tab_str + "}";
        return json;
    }


}
