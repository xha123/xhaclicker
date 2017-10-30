package com.yaoyao.clicker.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yaoyao.clicker.R;
import com.yaoyao.clicker.activity.like.NewsDetailActivity;
import com.yaoyao.clicker.app.AppManager;
import com.yaoyao.clicker.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/10.
 */

public class HomerecyclerAdapter extends RecyclerView.Adapter{
    public  final int TYPE_ITEM=2;//item布局
    public  final int TYPE_BOTTOM=3;//尾部局
    public int loadstate;//0为加载中，1为加载完成，2为加载失败，3为没有更多。

    Context context;
    List<Map<String,Object>> list;
    int widthpic;//图片宽高
    public HomerecyclerAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public List<Map<String, Object>> getList() {
        return list;
    }

    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }

    public int getLoadstate() {
        return loadstate;
    }

    public void setLoadstate(int loadstate) {
        this.loadstate = loadstate;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_ITEM:
                View view = LayoutInflater.from(context).inflate(R.layout.home_recy_item,parent,false);
                widthpic = parent.getWidth()/3;
                view.getLayoutParams().width = widthpic;
                view.getLayoutParams().height = widthpic;
                MyViewHolder myViewHolder = new MyViewHolder(view);
                return myViewHolder;
            case TYPE_BOTTOM:
                View view1 = LayoutInflater.from(context).inflate(R.layout.home_recy_item,parent,false);
                MyBottomHolder myBottomHolder = new MyBottomHolder(view1);
                return myBottomHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder){
            final Map<String, Object> seemap = list.get(position);
            String picurl = (String) seemap.get("purl");
            String[] picString = picurl.split(",");
            if (picString.length<=1){
                ((MyViewHolder) holder).max_iv.setVisibility(View.GONE);
            }else {
                ((MyViewHolder) holder).max_iv.setVisibility(View.VISIBLE);
            }
            String showimg = picString[0];
            showimg = showimg.replace("oss","img");
            showimg = showimg+"@250h_250w_1e_1c";
            ((MyViewHolder) holder).image_iv.setImageURI(showimg);
//            DataUtils.getInstans().setImageSrc(((MyViewHolder) holder).image_iv,uri,widthpic/2,widthpic/2);

            ((MyViewHolder) holder).image_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    String showpid = MyUtils.getInstans().doubleTwo((Double) seemap.get("pid"));
                    bundle.putString("pid", showpid);
                    AppManager.getAppManager().ToOtherActivity(NewsDetailActivity.class,bundle);
                }
            });
        }else if (holder instanceof MyBottomHolder){
//            if (loadstate==0){
//                ((MyBottomHolder) holder).tv.setText(R.string.isloadding);
//            }else if (loadstate==1){
//                ((MyBottomHolder) holder).tv.setText(R.string.loaddingok);
//            }else if (loadstate==2){
//                ((MyBottomHolder) holder).tv.setText(R.string.loadfair);
//            }else if (loadstate==3){
//                ((MyBottomHolder) holder).tv.setText(R.string.nomore);
//            }
        }

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
//        if (list.size()==0){
//            return TYPE_BOTTOM;
//        }
//        if (position==list.size()){
//            return TYPE_BOTTOM;
//        }
        return TYPE_ITEM;
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


    class MyBottomHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public MyBottomHolder(View itemView) {
            super(itemView);

        }
    }

}
