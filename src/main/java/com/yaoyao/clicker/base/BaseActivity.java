package com.yaoyao.clicker.base;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Toast;

import com.yaoyao.clicker.app.AppManager;


/**
 * Activity基类 解决代码复用性
 * Created on 2016/11/16.
 */

public abstract class BaseActivity extends CheckPermissionsActivity {

    public static final String TAG = "msg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //透明状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        //透明导航栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        inidata();
        setCon();
        iniview();
        setview();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setResume();
        setOnclick();
    }

    public abstract void inidata();
    public abstract void setCon();
    public abstract void iniview();
    public abstract void setResume();
    public abstract void setOnclick();

    public abstract void setview();

    @Override
    protected void onDestroy()
    {
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();
    }

    @Override//设置字体不随系统改变
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }



    /**
     *  去重吐司
     * @param context 上下文
     * @param text 显示信息
     */
    static Toast mToast;
    public static void showtoast(Context context, String text){
        if (mToast==null){
            mToast = Toast.makeText(context,text,Toast.LENGTH_SHORT);
        }else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

}
