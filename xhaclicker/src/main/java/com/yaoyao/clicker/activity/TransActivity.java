package com.yaoyao.clicker.activity;

import android.os.Handler;
import android.os.Message;

import com.yaoyao.clicker.R;
import com.yaoyao.clicker.activity.mine.LoginActivity;
import com.yaoyao.clicker.app.AppManager;
import com.yaoyao.clicker.base.BaseActivity;

/**
 * Created by Administrator on 2017/7/5.
 */

public class TransActivity extends BaseActivity{
    @Override
    public void inidata() {

    }

    @Override
    public void setCon() {
        setContentView(R.layout.activity_trans);
    }

    @Override
    public void iniview() {
        handler.sendEmptyMessageDelayed(1, 1000);
    }

    @Override
    public void setview() {

    }

    @Override
    public void setResume() {

    }

    @Override
    public void setOnclick() {

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                AppManager.getAppManager().ToOtherActivity(LoginActivity.class);
                AppManager.getAppManager().finishActivity();
            }
        }
    };
}
