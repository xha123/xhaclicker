package com.yaoyao.clicker.utils;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/** 封装OKhttp异步回调。
 * Created by Administrator on 2017/4/20.
 */

public abstract class UIcallBack implements Callback {
    //拿到主线程的handler
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onFailure(final Call call, final IOException e) {
        //通过主线程的handler.post方法，发送一个可以在主线程中运行的run方法
        handler.post(new Runnable() {
            @Override
            public void run() {
                onFailureUI(call, e);
            }
        });
    }

    @Override
    public void onResponse(final Call call, final Response response) throws IOException {
        //判断是否响应成功
        if (!response.isSuccessful()){
            throw new IOException("error code:" + response.code());
        }

        //拿到json字符串
        final String body = response.body().string();

        handler.post(new Runnable() {
            @Override
            public void run() {
                onResponseUI(call, body);
            }
        });
    }

    //抽象两个方法（运行主线程）
    public abstract void onFailureUI(Call call, IOException e);
    public abstract void onResponseUI(Call call, String body);
}
