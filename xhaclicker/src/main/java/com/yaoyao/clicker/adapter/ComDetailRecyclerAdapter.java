package com.yaoyao.clicker.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yaoyao.clicker.R;
import com.yaoyao.clicker.activity.home.MoreReplayActivity;
import com.yaoyao.clicker.app.AppManager;
import com.yaoyao.clicker.base.BaseActivity;
import com.yaoyao.clicker.bean.ComdetailBean;
import com.yaoyao.clicker.bean.HomeAttBean;
import com.yaoyao.clicker.utils.DataUtils;
import com.yaoyao.clicker.utils.HttpUtils;
import com.yaoyao.clicker.utils.MyUtils;
import com.yaoyao.clicker.utils.SharedPreferenceUtils;
import com.yaoyao.clicker.utils.UIcallBack;
import com.yaoyao.clicker.view.BackEditText;

import org.liushui.textstyleplus.ClickListener;
import org.liushui.textstyleplus.StyleBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/9/4.
 */

public class ComDetailRecyclerAdapter extends RecyclerView.Adapter implements BackEditText.BackListener{
    public final int TYPE_TOP=1;//头布局
    public  final int TYPE_ITEM=2;//item布局
    public  final int TYPE_BOTTOM=3;//尾部局

    Context context;
    List<ComdetailBean.CommentsBean.ChildsBeanX> list;
    ComdetailBean.PicinfoBean picinfoBean;
    String userid,username;
    ComdetailBean.CommentsBean.ChildsBeanX.ChildsBean childsone;
    String pid;
    int total;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public ComDetailRecyclerAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
        userid = (String) SharedPreferenceUtils.get("uid","");
        username = (String) SharedPreferenceUtils.get("name","");
    }

    public ComdetailBean.PicinfoBean getPicinfoBean() {
        return picinfoBean;
    }

    public void setPicinfoBean(ComdetailBean.PicinfoBean picinfoBean) {
        this.picinfoBean = picinfoBean;
    }

    public List<ComdetailBean.CommentsBean.ChildsBeanX> getList() {
        return list;
    }

    public void setList(List<ComdetailBean.CommentsBean.ChildsBeanX> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==TYPE_TOP){
            View view = LayoutInflater.from(context).inflate(R.layout.comde_top,parent,false);
            ComDetailRecyclerAdapter.MyHeadHolder mytopViewHolder = new MyHeadHolder(view);
            return mytopViewHolder;
        }else if (viewType==TYPE_ITEM){
            View view = LayoutInflater.from(context).inflate(R.layout.comdetail_item,parent,false);
            ComDetailRecyclerAdapter.MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyHeadHolder){
            if (picinfoBean==null){
                return;
            }
            String uri = picinfoBean.getPicurl();
            uri = uri.replace("oss","img");
            uri = uri+"@100h_100w_1e_1c";
            ((MyHeadHolder) holder).name_iv.setImageURI(uri);
            ((MyHeadHolder) holder).name_tv.setText(picinfoBean.getName());
            ((MyHeadHolder) holder).ping_tv.setText(picinfoBean.getPdes());
            ((MyHeadHolder) holder).ping_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newHuifu(picinfoBean.getName(),((MyHeadHolder) holder).ping_tv,"-1","-1",position);
                }
            });


        }else if (holder instanceof MyViewHolder){
            final ComdetailBean.CommentsBean.ChildsBeanX childsBeanX = list.get(position-1);
            String showuri = childsBeanX.getPicurl();
            showuri = showuri.replace("oss","img");
            showuri = showuri+"@100h_100w_1e_1c";
            ((MyViewHolder) holder).name_iv.setImageURI(showuri);
            ((MyViewHolder) holder).name_tv.setText(childsBeanX.getName());
            ((MyViewHolder) holder).time_tv.setText(childsBeanX.getTime());
            ((MyViewHolder) holder).ping_tv.setText(childsBeanX.getPComment());

            ((MyViewHolder) holder).ping_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (childsBeanX.getUid().equals(userid)){
                        newDel(childsBeanX.getGroupid(),"",((MyViewHolder) holder).ping_tv,position,-1);
                        return;
                    }
                    newHuifu(childsBeanX.getPComment(),((MyViewHolder) holder).ping_tv,
                            childsBeanX.getUid(),childsBeanX.getGroupid(),position);
                }
            });

            total = childsBeanX.getTotal();
            if (total==0){
                ((MyViewHolder) holder).hui_rl.setVisibility(View.GONE);
            }else if (total==1){
                ((MyViewHolder) holder).hui_rl.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).hui_tv1.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).hui_tv2.setVisibility(View.GONE);
                ((MyViewHolder) holder).more_tv.setVisibility(View.GONE);

                    childsone = childsBeanX.getChilds().get(0);
                    StyleBuilder styleBuilder = new StyleBuilder();
                    styleBuilder.addTextStyle(childsone.getName()).textColor(R.color.black).click(new ClickListener() {

                        @Override
                        public void click(String s) {
                            if (childsone.getUid().equals(userid)){
                                newDel("",childsone.getId(),((MyViewHolder) holder).hui_tv1,position,0);
                            }else {
                                newHuifu(s,((MyViewHolder) holder).hui_tv1,childsone.getUid(),
                                        childsone.getGroupid(),position);
                            }
                        }
                    }).commit()
                            .addTextStyle(" 回复 ").textColor(R.color.blackhei).commit()
                            .addTextStyle(childsone.getCname()+":").textColor(R.color.green).click(new ClickListener() {

                        @Override
                        public void click(String s) {
                            if (childsone.getCommentid().equals(userid)){
                                return;
                            }else {
                                newHuifu(s,((MyViewHolder) holder).hui_tv1,childsone.getCommentid(),
                                        childsone.getGroupid(),position);
                            }
                        }
                    }).commit()
                            .addTextStyle(childsone.getComment()).click(new ClickListener() {

                        @Override
                        public void click(String s) {
                            if (childsone.getUid().equals(userid)){
                                newDel("",childsone.getId(),((MyViewHolder) holder).hui_tv1,position,0);
                            }else {
                                newHuifu(childsone.getName(),((MyViewHolder) holder).hui_tv1,
                                        childsone.getUid(),childsone.getGroupid(),position);
                            }
                        }
                    }).commit()
                            .show(((MyViewHolder) holder).hui_tv1);
            }else if (total>=2){
                ((MyViewHolder) holder).hui_rl.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).hui_tv1.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).hui_tv2.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).more_tv.setVisibility(View.GONE);
                if (total>2){
                    ((MyViewHolder) holder).more_tv.setVisibility(View.VISIBLE);
                    ((MyViewHolder) holder).more_tv.setText("共"+total+"条回复 >");
                }

                childsone = childsBeanX.getChilds().get(0);
                final ComdetailBean.CommentsBean.ChildsBeanX.ChildsBean childstwo = childsBeanX.getChilds().get(1);
                StyleBuilder styleBuilder = new StyleBuilder();
                styleBuilder.addTextStyle(childsone.getName()).textColor(R.color.black).click(new ClickListener() {

                    @Override
                    public void click(String s) {
                        if (childsone.getUid().equals(userid)){
                            newDel("",childsone.getId(),((MyViewHolder) holder).hui_tv1,position,0);
                        }else {
                            newHuifu(s,((MyViewHolder) holder).hui_tv1,childsone.getUid(),childsone.getGroupid(),position);
                        }
                    }
                }).commit()
                        .addTextStyle(" 回复 ").textColor(R.color.blackhei).commit()
                        .addTextStyle(childsone.getCname()+":").textColor(R.color.green).click(new ClickListener() {

                    @Override
                    public void click(String s) {
                        if (childsone.getCommentid().equals(userid)){
                            return;
                        }else {
                            newHuifu(s,((MyViewHolder) holder).hui_tv1,childsone.getCommentid(),childsone.getGroupid(),position);
                        }
                    }
                }).commit()
                        .addTextStyle(childsone.getComment()).click(new ClickListener() {

                    @Override
                    public void click(String s) {
                        if (childsone.getUid().equals(userid)){
                            newDel("",childsone.getId(),((MyViewHolder) holder).hui_tv1,position,0);
                        }else {
                            newHuifu(childsone.getName(),((MyViewHolder) holder).hui_tv1,childsone.getUid(),childsone.getGroupid(),position);
                        }
                    }
                }).commit()
                        .show(((MyViewHolder) holder).hui_tv1);

                StyleBuilder styleBuilder1 = new StyleBuilder();
                styleBuilder1.addTextStyle(childstwo.getName()).textColor(R.color.black).click(new ClickListener() {
                    @Override
                    public void click(String s) {
                        if (childstwo.getUid().equals(userid)){
                            newDel("",childstwo.getId(),((MyViewHolder) holder).hui_tv2,position,1);
                        }else {
                            newHuifu(s,((MyViewHolder) holder).hui_tv2,childstwo.getUid(),childstwo.getGroupid(),position);
                        }
                    }
                }).commit()
                        .addTextStyle(" 回复 ").textColor(R.color.blackhei).commit()
                        .addTextStyle(childstwo.getCname()+":").textColor(R.color.green).click(new ClickListener() {

                    @Override
                    public void click(String s) {
                        if (childstwo.getCommentid().equals(userid)){
                            return;
                        }else {
                            newHuifu(s,((MyViewHolder) holder).hui_tv2,childstwo.getCommentid(),childstwo.getGroupid(),position);
                        }
                    }
                }).commit()
                        .addTextStyle(childstwo.getComment()).click(new ClickListener() {
                    @Override
                    public void click(String s) {
                        if (childstwo.getUid().equals(userid)){
                            newDel("",childstwo.getId(),((MyViewHolder) holder).hui_tv2,position,1);
                        }else {
                            newHuifu(childstwo.getName(),((MyViewHolder) holder).hui_tv2,childstwo.getUid(),childstwo.getGroupid(),position);
                        }
                    }
                }).commit().show(((MyViewHolder) holder).hui_tv2);
            }


            ((MyViewHolder) holder).more_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("groupid",childsBeanX.getGroupid());
                    HomeAttBean.ResultBean.CommentsBean.ChildsBeanX childsBeanX1 = new
                            HomeAttBean.ResultBean.CommentsBean.ChildsBeanX();
                    childsBeanX1.setName(childsBeanX.getName());
                    childsBeanX1.setPicurl(childsBeanX.getPicurl());
                    childsBeanX1.setPComment(childsBeanX.getPComment());
                    childsBeanX1.setUid(childsBeanX.getUid());
                    bundle.putSerializable("ping", childsBeanX1);
                    AppManager.getAppManager().ToOtherActivity(MoreReplayActivity.class,bundle);
                }
            });
        }
    }

    View shanView;
    PopupWindow shanPopup;
    //删除
    private void newDel(final String groupid, final String replyid, View view, final int position,final int delpos) {
        if (shanView == null) {
//            commentView = context.getLayoutInflater().inflate(R.layout.attitem_pinglun, null);
            shanView = LayoutInflater.from(context).inflate(R.layout.shanview,null);
        }
        if (shanPopup == null) {
            // 创建一个PopuWidow对象
            shanPopup = new PopupWindow(shanView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        // 设置动画 commentPopup.setAnimationStyle(R.style.popWindow_animation_in2out);
        // 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
        shanPopup.setFocusable(true);
        // 设置允许在外点击消失
        shanPopup.setOutsideTouchable(true);
        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        shanPopup.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bantouming_back));

        //必须加这两行，不然不会显示在键盘上方
//        shanPopup.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
//        shanPopup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // PopupWindow的显示及位置设置
        shanPopup.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        TextView del_tv = (TextView) shanView.findViewById(R.id.shan_del_tv);
        TextView cancel_tv = (TextView) shanView.findViewById(R.id.shan_can_tv);
        View shan_view = shanView.findViewById(R.id.shan_view);
        del_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shanPopup.dismiss();
                String url;
                Map<String,String> map = new HashMap<>();
                if (groupid.equals("")){
                    url = HttpUtils.URL+"pic/delreply";
                    map.put("requid",userid);
                    map.put("replyid",replyid);
                }else {
                    url = HttpUtils.URL+"pic/delcomment";
                    map.put("requid",userid);
                    map.put("groupid",groupid);
                }

                Call call = HttpUtils.getInstance().mapCall(map,url);
                call.enqueue(new UIcallBack() {
                    @Override
                    public void onFailureUI(Call call, IOException e) {
                        Log.e("msg", "onFailureUI: "+e.toString() );
                    }

                    @Override
                    public void onResponseUI(Call call, String body) {
                        Log.e("msg", "onResponseUI: 删除评论"+body );
                        Map<String,Object> result = DataUtils.getInstans().gsonToMap(body);
                        String code = (String) result.get("resultcode");
                        if (code.equals(HttpUtils.SUCCESS)){
                            BaseActivity.showtoast(context,"删除成功");
                            if (groupid.equals("")){
                                list.get(position-1).getChilds().remove(delpos);
                                int huitotal = list.get(position-1).getTotal();
                                list.get(position-1).setTotal(huitotal-1);
                                notifyItemChanged(position);
                            }else {
                                if (pingSuss!=null){
                                    pingSuss.getCall();
                                }
                            }


                        }else {
                            HttpUtils.getInstance().Errorcode(context,code);
                        }
                    }
                });
            }
        });
        cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shanPopup.dismiss();
            }
        });
        shan_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shanPopup.dismiss();
            }
        });

    }


    public  View commentView = null;
    public PopupWindow commentPopup = null;
    public  String result = "";
    public BackEditText popup_live_comment_edit;
    public TextView popup_live_comment_send;

    private void newHuifu(final String name, final View view, final String commentedid,
                          final String groupid, final int position) {

        if (commentView == null) {
//            commentView = context.getLayoutInflater().inflate(R.layout.attitem_pinglun, null);
            commentView = LayoutInflater.from(context).inflate(R.layout.attitem_pinglun,null);
        }
        if (commentPopup == null) {
            // 创建一个PopuWidow对象
            commentPopup = new PopupWindow(commentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        // 设置动画 commentPopup.setAnimationStyle(R.style.popWindow_animation_in2out);
        // 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
        commentPopup.setFocusable(true);
        // 设置允许在外点击消失
        commentPopup.setOutsideTouchable(true);
        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        commentPopup.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.touming_back));
        //必须加这两行，不然不会显示在键盘上方
        commentPopup.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        commentPopup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // PopupWindow的显示及位置设置
        commentPopup.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        popup_live_comment_edit = (BackEditText) commentView.findViewById(R.id.popup_live_comment_edit);
        popup_live_comment_send = (TextView) commentView.findViewById(R.id.popup_live_comment_send);
        popup_live_comment_edit.setBackListener(this);
        popup_live_comment_edit.setHint(context.getString(R.string.huifuone)+name);
        //这是布局中发送按钮的监听
        popup_live_comment_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = popup_live_comment_edit.getText().toString().trim();
                if ( result.length() != 0) {
                    //把数据传出去
                    huifuCall(result,commentedid,groupid,name,position);
                    DataUtils.getInstans().closeKeyboard(context,popup_live_comment_edit);
                    //关闭popup
                    commentPopup.dismiss();
                }
            }
        });
        //设置popup关闭时要做的操作
        commentPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                DataUtils.getInstans().closeKeyboard(context,view);
                popup_live_comment_edit.setText("");
            }
        });
//        //关闭软键盘
//        popup_live_comment_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                Log.e("home", "onEditorAction: "+actionId );
//                if (actionId== EditorInfo.IME_ACTION_DONE){
//                    commentPopup.dismiss();
//                }
//                return false;
//            }
//        });

        //显示软键盘
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //此方法就不提供了，网上一大推
                DataUtils.getInstans().openKeyboard(context,popup_live_comment_edit);
            }
        }, 150);
    }



    //评论回复的网络请求
    private void huifuCall(final String comment, final String commentedid, final String groupid, final String name, final int position) {
        String url = HttpUtils.URL+"pic/comment";
        Map<String,String> map = new HashMap<>();
        map.put("pid",pid);
        map.put("commentid",userid);
        map.put("commentedid",commentedid);
        map.put("content",MyUtils.getInstans().getIsoShow(comment));
        if (!groupid.equals("-1")){
            map.put("groupid",groupid);
        }
        Call call = HttpUtils.getInstance().mapCall(map,url);
        call.enqueue(new UIcallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                Log.e("msg", "onFailureUI: "+e.toString() );
            }

            @Override
            public void onResponseUI(Call call, String body) {
                Log.e("msg", "onResponseUI: 回复成功"+body );
                Map<String,Object> result = DataUtils.getInstans().gsonToMap(body);
                String code = (String) result.get("resultcode");
                if (code.equals(HttpUtils.SUCCESS)){

                    if (commentedid.equals("-1")){//更新评论
                        BaseActivity.showtoast(context,"评论成功");
                        if (pingSuss!=null){
                            pingSuss.getCall();
                        }

                    }else {//更新回复
                        BaseActivity.showtoast(context,"回复成功");
                        total++;
                        ComdetailBean.CommentsBean.ChildsBeanX.ChildsBean childsBeannew = new
                                ComdetailBean.CommentsBean.ChildsBeanX.ChildsBean();
                        String newid = (String) result.get("result");
                        childsBeannew.setId(newid);
                        childsBeannew.setPid(pid);
                        childsBeannew.setUid(userid);
                        childsBeannew.setName(username);
                        childsBeannew.setComment(comment);
                        childsBeannew.setTime(MyUtils.getInstans().getnowTime());
                        childsBeannew.setCommentid(commentedid);
                        childsBeannew.setGroupid(groupid);
                        childsBeannew.setCname(name);
                        childsBeannew.setPicurl((String) SharedPreferenceUtils.get("picurl",""));

                        List<ComdetailBean.CommentsBean.ChildsBeanX.ChildsBean> listnow = new
                                ArrayList<ComdetailBean.CommentsBean.ChildsBeanX.ChildsBean>();
                        listnow.add(childsBeannew);
                        listnow.addAll(list.get(position-1).getChilds());
                        list.get(position-1).setChilds(listnow);
                        list.get(position-1).setTotal(total);
                        notifyItemChanged(position);

                    }
                }else {
                    HttpUtils.getInstance().Errorcode(context,code);
                }

            }
        });
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

    @Override
    public void back(TextView textView) {
        commentPopup.dismiss();
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

    PingSuss pingSuss;

    public PingSuss getPingSuss() {
        return pingSuss;
    }

    public void setPingSuss(PingSuss pingSuss) {
        this.pingSuss = pingSuss;
    }

    public  interface  PingSuss{
        void getCall();
    }
}
