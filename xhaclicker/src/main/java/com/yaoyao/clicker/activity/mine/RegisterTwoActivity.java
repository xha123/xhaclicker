package com.yaoyao.clicker.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoyao.clicker.R;
import com.yaoyao.clicker.activity.MainActivity;
import com.yaoyao.clicker.app.AppManager;
import com.yaoyao.clicker.base.BaseActivity;
import com.yaoyao.clicker.utils.DataUtils;
import com.yaoyao.clicker.utils.HttpUtils;
import com.yaoyao.clicker.utils.UIcallBack;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**  注册信息页面
 * Created by Administrator on 2017/8/7.
 */

public class RegisterTwoActivity extends BaseActivity{
    ImageView back_iv,addiv_iv;
    TextView title_tv,over_tv;
    EditText nick_ed,miaoshu_ed;
    String phone,code,pass,imgurl;
    @Override
    public void inidata() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            phone = bundle.getString("phone");
            code = bundle.getString("code");
            pass = bundle.getString("pass");
        }
    }

    @Override
    public void setCon() {
        setContentView(R.layout.activity_registertwo);
    }

    @Override
    public void iniview() {
        back_iv = (ImageView) findViewById(R.id.title_back_iv);
        addiv_iv = (ImageView) findViewById(R.id.registertwo_addiv_iv);
        title_tv = (TextView) findViewById(R.id.title_tv);
        over_tv = (TextView) findViewById(R.id.registertwo_over_tv);
        nick_ed = (EditText) findViewById(R.id.registertwo_nick_ed);
        miaoshu_ed = (EditText) findViewById(R.id.registertwo_miaoshu_ed);

    }

    @Override
    public void setview() {
        title_tv.setText(R.string.registermessage);
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
        over_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = nick_ed.getText().toString();
                if (nickname==null||nickname.equals("")){
                    BaseActivity.showtoast(RegisterTwoActivity.this,"昵称不能为空");
                    return;
                }
                getCallMessage(nickname);
            }
        });
    }

    private void getCallMessage(String nickname) {
        String url = HttpUtils.URL+"user/phone/reg";
        Map<String,String> map = new HashMap<>();
        map.put("phone",phone);
        map.put("name",nickname);
        map.put("psd",pass);
        map.put("checkcode",code);
        if (imgurl!=null){
            map.put("picurl",imgurl);
        }
        String miaoshu = miaoshu_ed.getText().toString();
        if (miaoshu!=null || !miaoshu.equals("")){
            map.put("udes",miaoshu);
        }
        Call call = HttpUtils.getInstance().mapCall(map,url);
        call.enqueue(new UIcallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                Log.e(TAG, "onFailureUI: "+e.toString() );
            }

            @Override
            public void onResponseUI(Call call, String body) {
                Log.e(TAG, "onResponseUI: "+body );
                Map<String,Object> result = DataUtils.getInstans().gsonToMap(body);
                String code = (String) result.get("resultcode");
                if (code.equals(HttpUtils.SUCCESS)){
                    AppManager.getAppManager().ToOtherActivity(MainActivity.class);
                }else {
                    HttpUtils.getInstance().Errorcode(RegisterTwoActivity.this,code);
                }
            }
        });
    }
}
