package com.yaoyao.clicker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yaoyao.clicker.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/4.
 */

public class SeekRecyclerAdapter extends RecyclerView.Adapter<SeekRecyclerAdapter.MyViewHolder>{
    Context context;
    List<Map<String,Object>> list;

    public SeekRecyclerAdapter(Context context) {
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
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.seek_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Map<String,Object> seekmap = list.get(position);
        String name = (String) seekmap.get("name");
        String udes = (String) seekmap.get("udes");
        holder.name_tv.setText(name);
        holder.mess_tv.setText(udes);
        String nameshow = (String) seekmap.get("picurl");
        nameshow = nameshow.replace("oss","img");
        nameshow = nameshow+"@250h_250w_1e_1c";
        holder.image_iv.setImageURI(nameshow);

        if (onClick!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  onClick.onclick(position,seekmap);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //自定义缓冲类继承自系统缓冲类
    class MyViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView image_iv;
        TextView name_tv,mess_tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            image_iv = (SimpleDraweeView) itemView.findViewById(R.id.seekitem_name_iv);
            name_tv = (TextView) itemView.findViewById(R.id.seekitem_name_tv);
            mess_tv = (TextView) itemView.findViewById(R.id.seekitem_name_mess_tv);
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
        void onclick(int position,Map<String,Object> map);
    }
}
