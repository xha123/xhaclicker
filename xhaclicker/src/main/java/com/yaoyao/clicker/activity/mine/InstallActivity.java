package com.yaoyao.clicker.activity.mine;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yaoyao.clicker.R;
import com.yaoyao.clicker.app.AppManager;
import com.yaoyao.clicker.base.BaseActivity;
import com.yaoyao.clicker.utils.SharedPreferenceUtils;

/**
 * Created by Administrator on 2017/8/7.
 */

public class InstallActivity extends BaseActivity implements View.OnClickListener{
   RelativeLayout changepass_rl,hei_rl,language_rl,tuisong_rl,xieyi_rl,question_rl,cleanseek_rl;
    RelativeLayout cleanliao_rl,exit_rl;
    ImageView back_iv;
    TextView title_tv;
    @Override
    public void inidata() {

    }

    @Override
    public void setCon() {
        setContentView(R.layout.activity_install);
    }

    @Override
    public void iniview() {
        changepass_rl = (RelativeLayout) findViewById(R.id.install_changepass_rl);
        hei_rl = (RelativeLayout) findViewById(R.id.install_hei_rl);
        language_rl = (RelativeLayout) findViewById(R.id.install_langueage_rl);
        tuisong_rl = (RelativeLayout) findViewById(R.id.install_tuisong_rl);
        xieyi_rl = (RelativeLayout) findViewById(R.id.install_help_rl);
        question_rl = (RelativeLayout) findViewById(R.id.install_qustion_rl);
        cleanseek_rl = (RelativeLayout) findViewById(R.id.install_cleansou_rl);
        cleanliao_rl = (RelativeLayout) findViewById(R.id.install_cleanliao_rl);
        exit_rl = (RelativeLayout) findViewById(R.id.install_exit_rl);
        back_iv = (ImageView) findViewById(R.id.title_back_iv);
        title_tv = (TextView) findViewById(R.id.title_tv);
    }

    @Override
    public void setview() {
        title_tv.setText("设置");
    }

    @Override
    public void setResume() {

    }

    @Override
    public void setOnclick() {
        back_iv.setOnClickListener(this);
        changepass_rl.setOnClickListener(this);
        hei_rl.setOnClickListener(this);
        language_rl.setOnClickListener(this);
        tuisong_rl.setOnClickListener(this);
        xieyi_rl.setOnClickListener(this);
        question_rl.setOnClickListener(this);
        cleanseek_rl.setOnClickListener(this);
        cleanliao_rl.setOnClickListener(this);
        exit_rl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back_iv:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.install_changepass_rl:
                AppManager.getAppManager().ToOtherActivity(ForgetpassActivity.class);
                break;
            case R.id.install_hei_rl:
                AppManager.getAppManager().ToOtherActivity(HeiActivity.class);
                break;
            case R.id.install_langueage_rl:
                AppManager.getAppManager().ToOtherActivity(LanguageActivity.class);
                break;
            case R.id.install_tuisong_rl:
                AppManager.getAppManager().ToOtherActivity(TuisongActivity.class);
                break;
            case R.id.install_help_rl:
                Bundle bundle = new Bundle();
                bundle.putString("url","");
                AppManager.getAppManager().ToOtherActivity(WebActivity.class,bundle);
                break;
            case R.id.install_qustion_rl:
                BaseActivity.showtoast(InstallActivity.this,"暂未开放");
                break;
            case R.id.install_cleansou_rl:
                DialogInterface.OnClickListener dialogOnclicListener=new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case Dialog.BUTTON_POSITIVE:
                                Toast.makeText(InstallActivity.this, "清除成功" + which, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                break;
                            case Dialog.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器
                builder.setTitle("消息提示"); //设置标题
                builder.setMessage("是否清除搜索历史?"); //设置内容
                builder.setPositiveButton("确认",dialogOnclicListener);
                builder.setNegativeButton("取消", dialogOnclicListener);
                builder.create().show();
                break;
            case R.id.install_cleanliao_rl:
                DialogInterface.OnClickListener dialogOnclicListener1=new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case Dialog.BUTTON_POSITIVE:
                                Toast.makeText(InstallActivity.this, "清除成功" + which, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                break;
                            case Dialog.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder1=new AlertDialog.Builder(this);  //先得到构造器
                builder1.setTitle("消息提示"); //设置标题
                builder1.setMessage("是否清除聊天记录?"); //设置内容
                builder1.setPositiveButton("确认",dialogOnclicListener1);
                builder1.setNegativeButton("取消", dialogOnclicListener1);
                builder1.create().show();
                break;
            case R.id.install_exit_rl:
                SharedPreferenceUtils.clear();
                AppManager.getAppManager().ToOtherActivity(LoginActivity.class);
                AppManager.getAppManager().finishOtherActivity();
                break;
        }

    }
}
