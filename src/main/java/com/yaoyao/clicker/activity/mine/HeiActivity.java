package com.yaoyao.clicker.activity.mine;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoyao.clicker.R;
import com.yaoyao.clicker.app.AppManager;
import com.yaoyao.clicker.base.BaseActivity;

/**
 * Created by Administrator on 2017/8/8.
 */

public class HeiActivity extends BaseActivity{
    ImageView back_iv;
    TextView title_tv,right_tv;
    @Override
    public void inidata() {

    }

    @Override
    public void setCon() {
        setContentView(R.layout.activity_hei);
    }

    @Override
    public void iniview() {
        back_iv = (ImageView) findViewById(R.id.title_back_iv);
        title_tv = (TextView) findViewById(R.id.title_tv);
        right_tv = (TextView) findViewById(R.id.title_right_tv);
    }

    @Override
    public void setview() {
        title_tv.setText(R.string.heimingd);
        right_tv.setText(R.string.bianji);
    }

    @Override
    public void setResume() {

    }

    @Override
    public void setOnclick() {
        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().finishActivity();
            }
        });
    }
}
