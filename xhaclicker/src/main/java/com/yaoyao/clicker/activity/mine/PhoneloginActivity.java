package com.yaoyao.clicker.activity.mine;

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
import com.yaoyao.clicker.utils.SharedPreferenceUtils;
import com.yaoyao.clicker.utils.UIcallBack;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/8/7.
 */

public class PhoneloginActivity extends BaseActivity implements View.OnClickListener{
    ImageView back_iv;
    TextView title_tv;
    EditText phone_ed,pass_ed;
    TextView register_tv,forgetpass_tv,login_tv;

    @Override
    public void inidata() {

    }

    @Override
    public void setCon() {
        setContentView(R.layout.activity_phonelogin);
    }

    @Override
    public void iniview() {
        back_iv = (ImageView) findViewById(R.id.title_back_iv);
        title_tv = (TextView) findViewById(R.id.title_tv);
        phone_ed = (EditText) findViewById(R.id.phonelogin_phone_ed);
        pass_ed = (EditText) findViewById(R.id.phonelogin_password_ed);
        register_tv = (TextView) findViewById(R.id.phonelogin_register_tv);
        forgetpass_tv = (TextView) findViewById(R.id.phonelogin_forgetpass_tv);
        login_tv = (TextView) findViewById(R.id.phonelogin_login_tv);
    }

    @Override
    public void setview() {

    }

    @Override
    public void setResume() {

    }

    @Override
    public void setOnclick() {
        back_iv.setOnClickListener(this);
        register_tv.setOnClickListener(this);
        forgetpass_tv.setOnClickListener(this);
        login_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back_iv:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.phonelogin_register_tv:
                AppManager.getAppManager().ToOtherActivity(RegisterActivity.class);
                break;
            case R.id.phonelogin_forgetpass_tv:
                AppManager.getAppManager().ToOtherActivity(ForgetpassActivity.class);
                break;
            case R.id.phonelogin_login_tv:
                String phone = phone_ed.getText().toString();
                String pass = pass_ed.getText().toString();
                if (phone.length()!=11){
                    BaseActivity.showtoast(PhoneloginActivity.this,"手机号码不正确");
                    return;
                }
                if (pass.length()==0){
                    BaseActivity.showtoast(PhoneloginActivity.this,"请输入密码");
                    return;
                }
                getCalllogin(phone,pass);
                break;
        }
    }

    private void getCalllogin(String phone,String pass) {
        String url = HttpUtils.URL+"user/phone/login";
        Map<String,String> map = new HashMap<>();
        map.put("phone",phone);
        map.put("psd",pass);
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
                    Log.e(TAG, "onResponseUI: success" );
                    Map<String,Object> user = (Map<String, Object>) result.get("result");
                    for (String key:user.keySet()) {
                        SharedPreferenceUtils.put(key,user.get(key));
                    }
                    BaseActivity.showtoast(PhoneloginActivity.this,"登录成功");
                    AppManager.getAppManager().ToOtherActivity(MainActivity.class);
                }else {
                    HttpUtils.getInstance().Errorcode(PhoneloginActivity.this,code);
                }
            }
        });
    }
}
