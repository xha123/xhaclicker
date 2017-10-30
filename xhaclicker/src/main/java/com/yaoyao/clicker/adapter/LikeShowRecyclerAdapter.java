package com.yaoyao.clicker.adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
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
import com.yaoyao.clicker.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/4.
 */

public class LikeShowRecyclerAdapter extends RecyclerView.Adapter{
    List<Map<String,Object>> list;
    Context context;
    int widthview;
    public LikeShowRecyclerAdapter(Context context) {
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
        switch (viewType){
            case 0:
                View view = LayoutInflater.from(context).inflate(R.layout.likeshow_head_item,parent,false);
                WindowManager windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
                widthview = windowManager.getDefaultDisplay().getWidth();
                view.getLayoutParams().width = widthview;
                 view.getLayoutParams().height = widthview/3*2;
                MyHeadHolder myHeadHolder = new MyHeadHolder(view);
                return myHeadHolder;
            case 1:
                View view1 = LayoutInflater.from(context).inflate(R.layout.home_recy_item,parent,false);
                 view1.getLayoutParams().width = parent.getWidth()/3;
                view1.getLayoutParams().height = parent.getWidth()/3;
                MyViewHolder myViewHolder = new MyViewHolder(view1);
                return myViewHolder;
        }
            return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyHeadHolder){
            if (list.size()==0){
                return;
            }else if (list.size()==1){
                final Map<String,Object> mapone = list.get(0);
                String purl = (String) mapone.get("purl");
                String[] purlS = purl.split(",");
                if (purlS.length>1){
                    ((MyHeadHolder) holder).max_iv1.setVisibility(View.VISIBLE);
                }else {
                    ((MyHeadHolder) holder).max_iv1.setVisibility(View.GONE);
                }
                Uri uri1 = Uri.parse(purlS[0]);
                DataUtils.getInstans().setImageSrc(((MyHeadHolder) holder).image_iv1,uri1,widthview,widthview);
                ((MyHeadHolder) holder).image_iv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("pid", (String) mapone.get("pid"));
                        AppManager.getAppManager().ToOtherActivity(NewsDetailActivity.class,bundle);
                    }
                });
            }else if (list.size()==2){
                final Map<String,Object> mapone = list.get(0);
                String purl = (String) mapone.get("purl");
                String[] purlS = purl.split(",");
                if (purlS.length>1){
                    ((MyHeadHolder) holder).max_iv1.setVisibility(View.VISIBLE);
                }else {
                    ((MyHeadHolder) holder).max_iv1.setVisibility(View.GONE);
                }
                Uri uri = Uri.parse(purlS[0]);
                DataUtils.getInstans().setImageSrc(((MyHeadHolder) holder).image_iv1,uri,widthview,widthview);
                ((MyHeadHolder) holder).image_iv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("pid", (String) mapone.get("pid"));
                        AppManager.getAppManager().ToOtherActivity(NewsDetailActivity.class,bundle);
                    }
                });
                final Map<String,Object> maptwo = list.get(1);
                String purl1 = (String) maptwo.get("purl");
                String[] purlS1 = purl1.split(",");
                if (purlS1.length>1){
                    ((MyHeadHolder) holder).max_iv2.setVisibility(View.VISIBLE);
                }else {
                    ((MyHeadHolder) holder).max_iv2.setVisibility(View.GONE);
                }
                String oneshow = purlS1[0];
                oneshow = oneshow.replace("oss","img");
                oneshow = oneshow+"@250h_250w_1e_1c";
                ((MyHeadHolder) holder).image_iv2.setImageURI(oneshow);
                ((MyHeadHolder) holder).image_iv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("pid", (String) maptwo.get("pid"));
                        AppManager.getAppManager().ToOtherActivity(NewsDetailActivity.class,bundle);
                    }
                });
            }else if (list.size()>=3){
                final Map<String,Object> mapone = list.get(0);
                String purl = (String) mapone.get("purl");
                String[] purlS = purl.split(",");
                if (purlS.length>1){
                    ((MyHeadHolder) holder).max_iv1.setVisibility(View.VISIBLE);
                }else {
                    ((MyHeadHolder) holder).max_iv1.setVisibility(View.GONE);
                }
                Uri uri = Uri.parse(purlS[0]);
                DataUtils.getInstans().setImageSrc(((MyHeadHolder) holder).image_iv1,uri,widthview,widthview);
                ((MyHeadHolder) holder).image_iv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("pid", (String) mapone.get("pid"));
                        AppManager.getAppManager().ToOtherActivity(NewsDetailActivity.class,bundle);
                    }
                });
                final Map<String,Object> maptwo = list.get(1);
                String purl1 = (String) maptwo.get("purl");
                String[] purlS1 = purl1.split(",");
                if (purlS1.length>1){
                    ((MyHeadHolder) holder).max_iv2.setVisibility(View.VISIBLE);
                }else {
                    ((MyHeadHolder) holder).max_iv2.setVisibility(View.GONE);
                }
                String oneshow = purlS1[0];
                oneshow = oneshow.replace("oss","img");
                oneshow = oneshow+"@250h_250w_1e_1c";
                ((MyHeadHolder) holder).image_iv2.setImageURI(oneshow);
                ((MyHeadHolder) holder).image_iv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("pid", (String) maptwo.get("pid"));
                        AppManager.getAppManager().ToOtherActivity(NewsDetailActivity.class,bundle);
                    }
                });

                final Map<String,Object> mapthr = list.get(2);
                String purl2 = (String) mapthr.get("purl");
                String[] purlS2 = purl2.split(",");
                if (purlS2.length>1){
                    ((MyHeadHolder) holder).max_iv3.setVisibility(View.VISIBLE);
                }else {
                    ((MyHeadHolder) holder).max_iv3.setVisibility(View.GONE);
                }
                String twoshow = purlS2[0];
                twoshow = twoshow.replace("oss","img");
                twoshow = twoshow+"@250h_250w_1e_1c";
                ((MyHeadHolder) holder).image_iv3.setImageURI(twoshow);
                ((MyHeadHolder) holder).image_iv3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("pid", (String) mapthr.get("pid"));
                        AppManager.getAppManager().ToOtherActivity(NewsDetailActivity.class,bundle);
                    }
                });
            }
        }else if (holder instanceof MyViewHolder){
            if (position+2>list.size()-1){
                return;
            }
            final Map<String,Object> map = list.get(position+2);
            String purl = (String) map.get("purl");
            String[] purlStr = purl.split(",");
            if (purlStr.length>1){
                ((MyViewHolder) holder).max_iv.setVisibility(View.VISIBLE);
            }else {
                ((MyViewHolder) holder).max_iv.setVisibility(View.GONE);
            }
            String mapshow = purlStr[0];
            mapshow = mapshow.replace("oss","img");
            mapshow = mapshow+"@250h_250w_1e_1c";
            ((MyViewHolder) holder).image_iv.setImageURI(mapshow);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("pid", (String) map.get("pid"));
                    AppManager.getAppManager().ToOtherActivity(NewsDetailActivity.class,bundle);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return list.size()-2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return 0;
        }else {
            return 1;
        }
    }



    //
    class MyHeadHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView image_iv1,image_iv2,image_iv3;
        ImageView max_iv1,max_iv2,max_iv3;

        public MyHeadHolder(View itemView) {
            super(itemView);
            image_iv1 = (SimpleDraweeView) itemView.findViewById(R.id.like_head_iv1);
            image_iv2 = (SimpleDraweeView) itemView.findViewById(R.id.like_head_iv2);
            image_iv3 = (SimpleDraweeView) itemView.findViewById(R.id.like_head_iv3);
            max_iv1 = (ImageView) itemView.findViewById(R.id.like_max_iv1);
            max_iv2 = (ImageView) itemView.findViewById(R.id.like_max_iv2);
            max_iv3 = (ImageView) itemView.findViewById(R.id.like_max_iv3);
        }
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

}
