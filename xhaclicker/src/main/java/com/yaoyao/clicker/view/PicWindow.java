package com.yaoyao.clicker.view;

/**
 * Created by Administrator on 2017/8/23.
 */

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yaoyao.clicker.R;


public class PicWindow extends PopupWindow implements View.OnClickListener{

    public interface Listener {
        /**
         * 来自相册
         */
        void toGallery();

        /**
         * 来自相机
         */
        void toCamera();
    }

    private final Activity activity;

    private final Listener listener;

    TextView album_bu,camre_bu;

    @SuppressWarnings("all")
    public PicWindow(Activity activity, Listener listener) {

        super(activity.getLayoutInflater().inflate(R.layout.layout_popu, null),
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);


        album_bu = (TextView) getContentView().findViewById(R.id.btn_popu_album);
        camre_bu = (TextView) getContentView().findViewById(R.id.btn_popu_camera);
        this.activity = activity;
        this.listener = listener;

        album_bu.setOnClickListener(this);
        camre_bu.setOnClickListener(this);

        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());
    }

    public void show() {
        showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_popu_album:
                Log.e("msg", "onClick: 调起相册" );
                listener.toGallery();
                break;
            case R.id.btn_popu_camera:
                Log.e("msg", "onClick: 调起相机" );
                listener.toCamera();
                break;
        }
        dismiss();
    }

}
