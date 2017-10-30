package com.yaoyao.clicker.activity.mine;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoyao.clicker.R;
import com.yaoyao.clicker.app.AppManager;
import com.yaoyao.clicker.base.BaseActivity;
import com.yaoyao.clicker.utils.DataUtils;
import com.yaoyao.clicker.utils.HttpUtils;
import com.yaoyao.clicker.utils.UIcallBack;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/** 手机注册页面
 * Created by Administrator on 2017/8/7.
 */

public class RegisterActivity extends BaseActivity{
    EditText editText1,editText2,editText3,editText4;
    TextView code_tv,register_tv,title_tv;
    ImageView back_iv;
    String phone,code,pass,repass;
    String showcode;
    @Override
    public void inidata() {

    }

    @Override
    public void setCon() {
        setContentView(R.layout.activity_register);
    }

    @Override
    public void iniview() {
        editText1 = (EditText) findViewById(R.id.register_phone_ed);
        editText2 = (EditText) findViewById(R.id.register_pass_ed);
        editText3 = (EditText) findViewById(R.id.register_repassword_ed);
        editText4 = (EditText) findViewById(R.id.register_code_ed);
        code_tv = (TextView) findViewById(R.id.register_code_tv);
        register_tv = (TextView) findViewById(R.id.register_over_tv);
        back_iv = (ImageView) findViewById(R.id.title_back_iv);
        title_tv = (TextView) findViewById(R.id.title_tv);
    }

    @Override
    public void setview() {
        title_tv.setText(R.string.register);

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
        code_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = editText1.getText().toString();
                if (phone.length()!=11){
                    BaseActivity.showtoast(RegisterActivity.this,"请输入正确手机号码");
                }
                getCallcode(phone);
            }
        });
        register_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = editText1.getText().toString();
                code = editText4.getText().toString();
                pass = editText2.getText().toString();
                repass = editText3.getText().toString();
                if (code.length()!=6){
                    BaseActivity.showtoast(RegisterActivity.this,"验证码有误");
                    return;
                }else if (pass.length()<6){
                    BaseActivity.showtoast(RegisterActivity.this,"密码长度不足6位");
                    return;
                }else if (!pass.equals(repass)){
                    BaseActivity.showtoast(RegisterActivity.this,"密码输入不一致");
                    return;
                }
                codePan();

            }
        });
    }

    private void codePan() {
        if (showcode!=null&&showcode.equals(code)){
            Bundle bundle = new Bundle();
            bundle.putString("phone",phone);
            bundle.putString("code",code);
            bundle.putString("pass",pass);
            AppManager.getAppManager().ToOtherActivity(RegisterTwoActivity.class,bundle);
        }else {
            BaseActivity.showtoast(RegisterActivity.this,"验证码有误");
        }
    }

    private void getCallcode(String phone) {
        String url = HttpUtils.URL+"user/phone/reg/checkcode";
        Map<String,String> map = new HashMap<>();
        map.put("phone",phone);
        map.put("type","0");
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
                    showcode = (String) result.get("result");
                    BaseActivity.showtoast(RegisterActivity.this, "验证码发送成功");
                }else {
                    HttpUtils.getInstance().Errorcode(RegisterActivity.this,code);
                }
            }
        });
    }
}
