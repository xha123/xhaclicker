package com.yaoyao.clicker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jwenfeng.library.pulltorefresh.view.HeadView;
import com.yaoyao.clicker.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/8/11.
 */

public class HeadRefView extends FrameLayout implements HeadView{
    private TextView tv,tv1;
    private ImageView arrow;
    private ProgressBar progressBar;

    public HeadRefView(Context context) {
        this(context,null);

    }

    public HeadRefView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HeadRefView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.pull_header,null);
        addView(view);
        tv = (TextView) view.findViewById(R.id.header_tv);
        tv1 = (TextView) view.findViewById(R.id.header_tv1);
        arrow = (ImageView) view.findViewById(R.id.header_arrow);
        progressBar = (ProgressBar) view.findViewById(R.id.header_progress);
    }

    @Override
    public void begin() {

    }

    @Override
    public void progress(float progress, float all) {
        float s = progress / all;
        if (s >= 0.9f){
            arrow.setRotation(180);
        }else{
            arrow.setRotation(0);
        }
        if (progress >= all-10){
            tv.setText("松开立即刷新");
        }else{
            tv.setText("下拉可以刷新");
        }
    }

    @Override
    public void finishing(float progress, float all) {

    }

    @Override
    public void loading() {
        arrow.setVisibility(GONE);
        progressBar.setVisibility(VISIBLE);
        tv.setText("刷新中...");
        SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");
        Date curDate =  new Date(System.currentTimeMillis());
        tv1.setText("最近更新： "+formatter.format(curDate));
    }

    @Override
    public void normal() {
        arrow.setVisibility(VISIBLE);
        progressBar.setVisibility(GONE);
        tv.setText("下拉可以刷新");
    }

    @Override
    public View getView() {
        return this;
    }
}
