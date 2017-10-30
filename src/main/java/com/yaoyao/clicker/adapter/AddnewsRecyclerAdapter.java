package com.yaoyao.clicker.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yaoyao.clicker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/12.
 */

public class AddnewsRecyclerAdapter extends RecyclerView.Adapter{
    Context context;
    List<String> listimg;

    public AddnewsRecyclerAdapter(Context context) {
        this.context = context;
        listimg = new ArrayList<>();
    }

    public List<String> getListimg() {
        return listimg;
    }

    public void setListimg(List<String> listimg) {
        this.listimg = listimg;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.addnews_item,parent,false);
        WindowManager windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        int widthview = windowManager.getDefaultDisplay().getWidth();
        view.getLayoutParams().width = widthview/2;
        view.getLayoutParams().height = widthview/2;
        MyHeadHolder myHeadHolder = new MyHeadHolder(view);
        return myHeadHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyHeadHolder){
            if (listimg.size()<4){
                if (position<listimg.size()){
                    ((MyHeadHolder) holder).del_iv.setVisibility(View.VISIBLE);
//                    Bitmap bitmap = BitmapFactory.decodeFile(listimg.get(position),getBitmapOption(2));
                    Glide.with(context).load(listimg.get(position))
                            .centerCrop()
                            .into(((MyHeadHolder) holder).add_iv);
                }else if (position == listimg.size()){
                    ((MyHeadHolder) holder).del_iv.setVisibility(View.GONE);
                    ((MyHeadHolder) holder).add_iv.setImageResource(R.mipmap.addnews_add);
                }

                if (onClick!=null){
                    ((MyHeadHolder) holder).add_iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listimg.size()<4&&position==listimg.size()){
                                int allint = 4- listimg.size();
                                onClick.onclick(position,allint);
                            }
                        }
                    });
                }

            }else {
                ((MyHeadHolder) holder).del_iv.setVisibility(View.VISIBLE);
//                Bitmap bitmap = BitmapFactory.decodeFile(listimg.get(position),getBitmapOption(2));
                Glide.with(context).load(listimg.get(position))
                        .centerCrop()
                        .into(((MyHeadHolder) holder).add_iv);
            }

            ((MyHeadHolder) holder).del_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listimg.remove(position);
//                    notifyItemRemoved(position);
                    notifyDataSetChanged();
                }
            });

        }
    }

    private BitmapFactory.Options getBitmapOption(int inSampleSize){
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }

    @Override
    public int getItemCount() {
        if (listimg.size()<4){
            return listimg.size()+1;
        }
        return 4;
    }

    class MyHeadHolder extends RecyclerView.ViewHolder{
            ImageView add_iv,del_iv;

        public MyHeadHolder(View itemView) {
            super(itemView);
            add_iv = (ImageView) itemView.findViewById(R.id.addnews_add_iv);
            del_iv = (ImageView) itemView.findViewById(R.id.addnews_del_iv);
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
        void onclick(int position,int allint);
    }
}
