package com.yaoyao.clicker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yaoyao.clicker.R;
import com.yaoyao.clicker.bean.HomeAttBean;
import com.yaoyao.clicker.bean.MorerepBean;
import com.yaoyao.clicker.utils.SharedPreferenceUtils;

import org.liushui.textstyleplus.ClickListener;
import org.liushui.textstyleplus.StyleBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/5.
 */

public class MoreReplyRecyclerAdapter extends RecyclerView.Adapter{
    public final int TYPE_TOP=1;//头布局
    public  final int TYPE_ITEM=2;//item布局
    public  final int TYPE_BOTTOM=3;//尾部局

    Context context;
    List<MorerepBean.ResultBean> list;
    HomeAttBean.ResultBean.CommentsBean.ChildsBeanX childs;
    String userid;

    public MoreReplyRecyclerAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
        userid = (String) SharedPreferenceUtils.get("uid","");
    }

    public HomeAttBean.ResultBean.CommentsBean.ChildsBeanX getChilds() {
        return childs;
    }

    public void setChilds(HomeAttBean.ResultBean.CommentsBean.ChildsBeanX childs) {
        this.childs = childs;
    }

    public List<MorerepBean.ResultBean> getList() {
        return list;
    }

    public void setList(List<MorerepBean.ResultBean> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_TOP:
                View view = LayoutInflater.from(context).inflate(R.layout.comde_top,parent,false);
                MyHeadHolder myHeadHolder = new MyHeadHolder(view);
                return myHeadHolder;
            case TYPE_ITEM:
                View view1 = LayoutInflater.from(context).inflate(R.layout.comdetail_item,parent,false);
                MyViewHolder myViewHolder = new MyViewHolder(view1);
                return myViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyHeadHolder){
            if (childs==null){
                return;
            }
            String uri = childs.getPicurl();
            uri = uri.replace("oss","img");
            uri = uri+"@100h_100w_1e_1c";
            ((MyHeadHolder) holder).name_iv.setImageURI(uri);
            ((MyHeadHolder) holder).name_tv.setText(childs.getName());
            ((MyHeadHolder) holder).ping_tv.setText(childs.getPComment());
        }else if (holder instanceof MyViewHolder){

            final MorerepBean.ResultBean childsone = list.get(position-1);
            ((MyViewHolder) holder).hui_rl.setVisibility(View.VISIBLE);
            ((MyViewHolder) holder).hui_tv1.setVisibility(View.VISIBLE);
            ((MyViewHolder) holder).hui_tv2.setVisibility(View.GONE);
            ((MyViewHolder) holder).more_tv.setVisibility(View.GONE);
            String showuri = childsone.getPicurl();
            showuri = showuri.replace("oss","img");
            showuri = showuri+"@100h_100w_1e_1c";
            ((MyViewHolder) holder).name_iv.setImageURI(showuri);
            ((MyViewHolder) holder).name_tv.setText(childsone.getName());
            ((MyViewHolder) holder).time_tv.setText(childsone.getTime());
            ((MyViewHolder) holder).ping_tv.getLayoutParams().height = 1;

            StyleBuilder styleBuilder = new StyleBuilder();
            styleBuilder
                    .addTextStyle(" 回复 ").textColor(R.color.blackhei).commit()
                    .addTextStyle(childsone.getCname()+":").textColor(R.color.green).click(new ClickListener() {

                @Override
                public void click(String s) {
                    if (childsone.getCommentid().equals(userid)){
                        return;
                    }else {
//                        newHuifu(s,((MyViewHolder) holder).hui_tv1,childsone);
                    }
                }
            }).commit()
                    .addTextStyle(childsone.getComment()).click(new ClickListener() {

                @Override
                public void click(String s) {
                    if (childsone.getUid().equals(userid)){
                        newDel();
                    }else {
//                        newHuifu(childsone.getName(),((MyViewHolder) holder).hui_tv1,childsone);
                    }
                }
            }).commit()
                    .show(((MyViewHolder) holder).hui_tv1);

        }
    }
    //删除
    private void newDel() {

    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return TYPE_TOP;
        }else {
            return TYPE_ITEM;
        }
    }

    class MyHeadHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView name_iv;
        TextView name_tv,ping_tv;

        public MyHeadHolder(View itemView) {
            super(itemView);
            name_iv = (SimpleDraweeView) itemView.findViewById(R.id.comdetop_name_iv);
            name_tv = (TextView) itemView.findViewById(R.id.comdetop_name_tv);
            ping_tv = (TextView) itemView.findViewById(R.id.comdetop_ping_tv);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView name_iv;
        TextView name_tv,ping_tv,time_tv;
        RelativeLayout hui_rl;
        TextView hui_tv1,hui_tv2,more_tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            name_iv = (SimpleDraweeView) itemView.findViewById(R.id.comdeitem_name_iv);
            name_tv = (TextView) itemView.findViewById(R.id.comdeitem_name_tv);
            ping_tv = (TextView) itemView.findViewById(R.id.comdeitem_ping_tv);
            time_tv = (TextView) itemView.findViewById(R.id.comdeitem_time_tv);
            hui_rl = (RelativeLayout) itemView.findViewById(R.id.comdeitem_onepinglun_rl);
            hui_tv1 = (TextView) itemView.findViewById(R.id.comdeitem_comhuifu_tv1);
            hui_tv2 = (TextView) itemView.findViewById(R.id.comdeitem_comhuifu_tv2);
            more_tv = (TextView) itemView.findViewById(R.id.comdeitem_huifu_more_tv);
        }
    }
}
