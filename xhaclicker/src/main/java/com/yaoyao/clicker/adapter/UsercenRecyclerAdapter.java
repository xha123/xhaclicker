package com.yaoyao.clicker.adapter;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yaoyao.clicker.R;
import com.yaoyao.clicker.activity.like.NewsDetailActivity;
import com.yaoyao.clicker.app.AppManager;
import com.yaoyao.clicker.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/14.
 */

public class UsercenRecyclerAdapter extends RecyclerView.Adapter{
    Context context;
    List<Map<String,Object>> list;
    int ood = 0;
    public UsercenRecyclerAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public List<Map<String, Object>> getList() {
        return list;
    }

    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(context).inflate(R.layout.home_recy_item,parent,false);
        WindowManager window = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                int width = window.getDefaultDisplay().getWidth()/3;
                view1.getLayoutParams().width = width;
                view1.getLayoutParams().height = width;
                MyViewHolder myViewHolder = new MyViewHolder(view1);
                return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder){
            final Map<String,Object> map = list.get(position+3);
            String urlimg = (String) map.get("purl");
            String[] urlstr = urlimg.split(",");
            if (urlstr.length<=1){
                ((MyViewHolder) holder).max_iv.setVisibility(View.GONE);
            }else {
                ((MyViewHolder) holder).max_iv.setVisibility(View.VISIBLE);
            }
            String urlshow = urlstr[0];
            urlshow = urlshow.replace("oss","img");
            urlshow = urlshow+"@250h_250w_1e_1c";
            ((MyViewHolder) holder).image_iv.setImageURI(urlshow);

            ((MyViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    String showpid = MyUtils.getInstans().doubleTwo((Double) map.get("pid"));
                    bundle.putString("pid", showpid);
                    AppManager.getAppManager().ToOtherActivity(NewsDetailActivity.class,bundle);
                }
            });
        }else {
            Log.e("seek", "onBindViewHolder: 错误" );
        }

    }

    @Override
    public int getItemCount() {
        return list.size()-3;
    }


    //自定义缓冲类继承自系统缓冲类
    class MyViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView image_iv;
        ImageView max_iv;
        RelativeLayout shid_rl;
        public MyViewHolder(View itemView) {
            super(itemView);
            image_iv = (SimpleDraweeView) itemView.findViewById(R.id.home_item_show_iv);
            max_iv = (ImageView) itemView.findViewById(R.id.home_item_max_iv);
        }
    }

    //自定义点击监听
    OnClick onClick;

    public OnClick getOnClick() {
        return onClick;
    }

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }
    //定义接口，回调监听
    public interface OnClick{
        void onclick(int position,String shid);
    }

}
