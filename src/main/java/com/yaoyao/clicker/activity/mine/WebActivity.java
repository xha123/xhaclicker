package com.yaoyao.clicker.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoyao.clicker.R;
import com.yaoyao.clicker.app.AppManager;
import com.yaoyao.clicker.base.BaseActivity;

/**
 * Created by Administrator on 2017/8/8.
 */

public class WebActivity extends BaseActivity{
    TextView title_tv;
    ImageView back_iv;
    WebView webView;
    String mUrl;
    @Override
    public void inidata() {
        Intent intent =getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            mUrl = bundle.getString("url");
        }
    }

    @Override
    public void setCon() {
        setContentView(R.layout.activity_webview);
    }

    @Override
    public void iniview() {
        title_tv = (TextView) findViewById(R.id.title_tv);
        back_iv = (ImageView) findViewById(R.id.title_back_iv);
        webView = (WebView) findViewById(R.id.webview);
    }

    @Override
    public void setview() {
        WebSettings mWebSettings = webView.getSettings();
        mWebSettings.setSupportZoom(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setSupportZoom(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setDefaultTextEncodingName("utf-8");
        mWebSettings.setLoadsImagesAutomatically(true);
        webView.setWebViewClient(webViewClient);
        webView.loadUrl(mUrl);
    }

    @Override
    public void setResume() {
        title_tv.setText(R.string.xieyi);
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

    WebViewClient webViewClient = new WebViewClient(){

        /**
         * 多页面在同一个WebView中打开，就是不新建activity或者调用系统浏览器打开
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.e(TAG, "shouldOverrideUrlLoading: "+url );
            view.loadUrl(url);
            return true;
        }

    };
}
