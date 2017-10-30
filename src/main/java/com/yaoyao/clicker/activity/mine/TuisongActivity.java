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

public class TuisongActivity extends BaseActivity{
    TextView title_tv,right_tv;
    ImageView back_iv,set_iv;
    int tuisongchoose;
    @Override
    public void inidata() {

    }

    @Override
    public void setCon() {
        setContentView(R.layout.activity_tuisong);
    }

    @Override
    public void iniview() {
        title_tv = (TextView) findViewById(R.id.title_tv);
        right_tv = (TextView) findViewById(R.id.title_right_tv);
        back_iv = (ImageView) findViewById(R.id.title_back_iv);
        set_iv = (ImageView) findViewById(R.id.tuisong_set_iv);
    }

    @Override
    public void setview() {
        title_tv.setText(R.string.tuisongins);
        right_tv.setText(R.string.over);

        setBack();
    }

    private void setBack() {
        if (tuisongchoose==1){
            set_iv.setImageResource(R.mipmap.tuisong_blue);
        }else if (tuisongchoose==0){
            set_iv.setImageResource(R.mipmap.tuisong_gray);
        }
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
        set_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tuisongchoose==0){
                    tuisongchoose = 1;
                }else {
                    tuisongchoose = 0;
                }
                setBack();
            }
        });
        right_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().finishActivity();
            }
        });
    }
}
