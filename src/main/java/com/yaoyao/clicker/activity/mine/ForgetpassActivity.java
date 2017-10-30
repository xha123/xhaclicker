package com.yaoyao.clicker.activity.mine;

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

/**
 * Created by Administrator on 2017/8/7.
 */

public class ForgetpassActivity extends BaseActivity implements View.OnClickListener{
    ImageView back_iv;
    TextView title_tv,code_tv,over_tv;
    EditText phone_ed,code_ed,pass_ed,repass_ed;
    String phonecode;

    @Override
    public void inidata() {
        phonecode = "";
    }

    @Override
    public void setCon() {
        setContentView(R.layout.activity_forgetpass);
    }

    @Override
    public void iniview() {
        back_iv = (ImageView) findViewById(R.id.title_back_iv);
        title_tv = (TextView) findViewById(R.id.title_tv);
        code_tv = (TextView) findViewById(R.id.forgetpass_code_tv);
        over_tv = (TextView) findViewById(R.id.forgetpass_over_tv);
        phone_ed = (EditText) findViewById(R.id.forgetpass_phone_ed);
        code_ed = (EditText) findViewById(R.id.forgetpass_code_ed);
        pass_ed = (EditText) findViewById(R.id.forgetpass_pass_ed);
        repass_ed = (EditText) findViewById(R.id.forgetpass_repass_ed);
    }

    @Override
    public void setview() {
        title_tv.setText(R.string.forgetpass);
    }

    @Override
    public void setResume() {

    }

    @Override
    public void setOnclick() {
        code_tv.setOnClickListener(this);
        over_tv.setOnClickListener(this);
        back_iv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.forgetpass_code_tv:
                String phonenumber = phone_ed.getText().toString();
                if (phonenumber.length()!=11){
                    BaseActivity.showtoast(ForgetpassActivity.this,"请输入正确手机号码");
                    return;
                }
                getCallcode(phonenumber);
                break;
            case R.id.forgetpass_over_tv:
                String code = code_ed.getText().toString();
                String pass = pass_ed.getText().toString();
                String repass = repass_ed.getText().toString();
                if (code.length()!=6){
                    BaseActivity.showtoast(ForgetpassActivity.this,"验证码有误");
                    return;
                }else if (pass.length()<6){
                    BaseActivity.showtoast(ForgetpassActivity.this,"密码长度不足6位");
                    return;
                }else if (!pass.equals(repass)){
                    BaseActivity.showtoast(ForgetpassActivity.this,"密码输入不一致");
                    return;
                }
                getCallforgetjpass(code,pass);
                break;
            case R.id.title_back_iv:
                AppManager.getAppManager().finishActivity();
                break;
        }
    }

    private void getCallforgetjpass(String code,String pass) {
        String url = HttpUtils.URL+"user/phone/changepsd/checkcode";
        Map<String,String> map = new HashMap<>();
        map.put("checkcode",code);
        map.put("phone",phone_ed.getText().toString());
        map.put("newpsd",pass);
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
                if (result.get("resultcode").equals(HttpUtils.SUCCESS)){
                    BaseActivity.showtoast(ForgetpassActivity.this,"修改成功");
                    AppManager.getAppManager().ToOtherActivity(LoginActivity.class);
                    AppManager.getAppManager().finishActivity();
                }else {
                    HttpUtils.getInstance().Errorcode(ForgetpassActivity.this, (String) result.get("resultcode"));
                }
            }
        });
    }

    private void getCallcode(String phone) {
        Map<String,String> map = new HashMap<>();
        map.put("phone",phone);
        map.put("type","1");
        String url = HttpUtils.URL+"user/phone/reg/checkcode";
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
                if (result.get("resultcode").equals(HttpUtils.SUCCESS)){
                    phonecode = (String) result.get("result");
                }else {

                }
            }
        });
    }
}
