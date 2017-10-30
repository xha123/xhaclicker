package com.yaoyao.clicker.activity.mine;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mob.MobSDK;
import com.yaoyao.clicker.R;
import com.yaoyao.clicker.activity.MainActivity;
import com.yaoyao.clicker.app.AppManager;
import com.yaoyao.clicker.base.BaseActivity;
import com.yaoyao.clicker.utils.SharedPreferenceUtils;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.wechat.friends.Wechat;

/** 登录页面
 * Created by Administrator on 2017/7/5.
 */

public class LoginActivity extends BaseActivity implements PlatformActionListener {
    TextView phone_tv,weixin_tv,weibo_tv;
    ImageView show_iv;
    LinearLayout show_ll;
    String uid;
    @Override
    public void inidata() {
        MobSDK.init(this,"1cfa593a527e6","db256f39e6a5c5ba591d51f051c0407d");
        uid = (String) SharedPreferenceUtils.get("uid","");
        if (!uid.equals("")){
            AppManager.getAppManager().ToOtherActivity(MainActivity.class);
            AppManager.getAppManager().finishActivity();
        }
    }

    @Override
    public void setCon() {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void iniview() {
        phone_tv = (TextView) findViewById(R.id.login_phone_tv);
        weibo_tv = (TextView) findViewById(R.id.login_weibo_tv);
        weixin_tv = (TextView) findViewById(R.id.login_weixin_tv);
        show_iv = (ImageView) findViewById(R.id.login_show_iv);
        show_ll = (LinearLayout) findViewById(R.id.login_show_ll);
    }

    @Override
    public void setview() {
        handler.sendEmptyMessageDelayed(1, 500);
    }

    @Override
    public void setResume() {

    }

    @Override
    public void setOnclick() {
        phone_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().ToOtherActivity(PhoneloginActivity.class);
            }
        });
        weibo_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                authorize(ShareSDK.getPlatform(SinaWeibo.NAME));
                authorize(new SinaWeibo());
            }
        });
        weixin_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authorize(new Wechat());
            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        AppManager.getAppManager().AppExit();
    }


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                show_iv.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.login_head);
                //通过某一个控件来启动动画。谁启动，谁具有该动画效果
                show_iv.startAnimation(animation);
                handler.sendEmptyMessageDelayed(2,500);
            }else if (msg.what==2){
                show_ll.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.login_bottom);
                show_ll.startAnimation(animation);
            }else if (msg.what==5){
                BaseActivity.showtoast(LoginActivity.this,"授权出错");
            }else if (msg.what==6){
                BaseActivity.showtoast(LoginActivity.this,"取消登录");
            }else if (msg.what==7){
                BaseActivity.showtoast(LoginActivity.this,"登录中...");
            }
        }
    };
    //三方登录
    public void authorize(Platform platform){
        if (platform==null){
            BaseActivity.showtoast(LoginActivity.this,"网络错误，请稍后再试");
        }
        if (platform.isAuthValid()){ //如果已经授权，取消授权
            platform.removeAccount(true);
        }
        platform.setPlatformActionListener(this);//获取用户数据
        platform.SSOSetting(false);// true不使用SSO授权，false使用SSO授权
        platform.showUser(null);//要数据，不要功能
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        if(i==Platform.ACTION_AUTHORIZING){
            //当前操作是授权的情况
            Log.d("Kodulf","hashMap="+hashMap);
        }else if(i==Platform.ACTION_USER_INFOR){ //通过platform 获取授权的用户了
            handler.sendEmptyMessage(7);
            if (platform.getName().equals(Wechat.NAME)){
                Log.e(TAG, "onComplete: 微信登录" );
                Log.e(TAG, "onComplete: "+hashMap.toString() );
            }else if (platform.getName().equals(SinaWeibo.NAME)){
                Log.e(TAG, "onComplete: 微博登录" );
                Log.e(TAG, "onComplete: "+hashMap.toString() );
            }
        }
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        handler.sendEmptyMessage(5);
    }

    @Override
    public void onCancel(Platform platform, int i) {
        handler.sendEmptyMessage(6);
    }
}
