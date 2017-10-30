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

public class LanguageActivity extends BaseActivity implements View.OnClickListener{
    TextView title_tv,right_tv,chjian_tv,chfan_tv,english_tv;
    ImageView back_iv;
    int languagechoose;
    @Override
    public void inidata() {

    }

    @Override
    public void setCon() {
        setContentView(R.layout.activity_language);
    }

    @Override
    public void iniview() {
        title_tv = (TextView) findViewById(R.id.title_tv);
        right_tv = (TextView) findViewById(R.id.title_right_tv);
        chjian_tv = (TextView) findViewById(R.id.language_zhjian_tv);
        chfan_tv = (TextView) findViewById(R.id.language_zhfan_tv);
        english_tv = (TextView) findViewById(R.id.language_en_tv);
        back_iv = (ImageView) findViewById(R.id.title_back_iv);
    }

    @Override
    public void setview() {
        title_tv.setText(R.string.langueage);
        right_tv.setText(R.string.over);

        setLanguage();
    }

    @Override
    public void setResume() {

    }

    @Override
    public void setOnclick() {
        back_iv.setOnClickListener(this);
        right_tv.setOnClickListener(this);
        chjian_tv.setOnClickListener(this);
        chfan_tv.setOnClickListener(this);
        english_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back_iv:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.title_right_tv:

                break;
            case R.id.language_zhjian_tv:
                languagechoose = 1;
                setLanguage();
                break;
            case R.id.language_zhfan_tv:
                languagechoose = 2;
                setLanguage();
                break;
            case R.id.language_en_tv:
                languagechoose = 3;
                setLanguage();
                break;
        }
    }

    private void setLanguage() {
        if (languagechoose==1){
            chjian_tv.setBackground(getResources().getDrawable(R.drawable.blue_roud_back));
            chfan_tv.setBackground(getResources().getDrawable(R.drawable.gray_rond_back));
            english_tv.setBackground(getResources().getDrawable(R.drawable.gray_rond_back));
        }else if (languagechoose==2){
            chjian_tv.setBackground(getResources().getDrawable(R.drawable.gray_rond_back));
            chfan_tv.setBackground(getResources().getDrawable(R.drawable.blue_roud_back));
            english_tv.setBackground(getResources().getDrawable(R.drawable.gray_rond_back));
        }else if (languagechoose==3){
            chjian_tv.setBackground(getResources().getDrawable(R.drawable.gray_rond_back));
            chfan_tv.setBackground(getResources().getDrawable(R.drawable.gray_rond_back));
            english_tv.setBackground(getResources().getDrawable(R.drawable.blue_roud_back));
        }
    }
}
