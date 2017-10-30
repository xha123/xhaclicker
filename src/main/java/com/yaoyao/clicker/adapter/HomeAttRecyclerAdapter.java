package com.yaoyao.clicker.adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yaoyao.clicker.R;
import com.yaoyao.clicker.activity.home.ComDetailActivity;
import com.yaoyao.clicker.activity.home.MoreReplayActivity;
import com.yaoyao.clicker.activity.home.ShowPhotoActivity;
import com.yaoyao.clicker.app.AppManager;
import com.yaoyao.clicker.base.BaseActivity;
import com.yaoyao.clicker.bean.HomeAttBean;
import com.yaoyao.clicker.utils.DataUtils;
import com.yaoyao.clicker.utils.HttpUtils;
import com.yaoyao.clicker.utils.MyUtils;
import com.yaoyao.clicker.utils.SharedPreferenceUtils;
import com.yaoyao.clicker.utils.UIcallBack;
import com.yaoyao.clicker.view.BackEditText;
import com.yaoyao.clicker.view.CustomViewpager;
import com.yaoyao.clicker.view.Xcircleindicator;

import org.liushui.textstyleplus.ClickListener;
import org.liushui.textstyleplus.StyleBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.sharesdk.onekeyshare.OnekeyShare;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/8/2.
 */

public class HomeAttRecyclerAdapter extends RecyclerView.Adapter<HomeAttRecyclerAdapter.MyViewHolder>
                implements BackEditText.BackListener{
    Context context;
    List<HomeAttBean.ResultBean> list;

    int Ymove;
    int Xmove;
    float xdown,ydown;
    int pagerChoose;
    String nameone;
    boolean isguanzhu;//是否关注
    String uid ,username;
//    int pingluntiao;//评论条数
//    int huifutiao;//回复条数
//    int hongzan,lanzan;//红赞，蓝赞个数

    String TAG ="msg";

    public HomeAttRecyclerAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
        uid = (String) SharedPreferenceUtils.get("uid","");
        username = (String) SharedPreferenceUtils.get("name","");
    }

    public List<HomeAttBean.ResultBean> getList() {
        return list;
    }

    public void setList(List<HomeAttBean.ResultBean> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.homeatt_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (position%2==1){ //判断bootom显示左右
            holder.view1.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.view2.setBackgroundColor(context.getResources().getColor(R.color.black));
        }else {
            holder.view1.setBackgroundColor(context.getResources().getColor(R.color.black));
            holder.view2.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

        HomeAttBean.ResultBean mapone = list.get(position);
        final HomeAttBean.ResultBean.PicinfoBean picbean= mapone.getPicinfo();//用户信息
        final HomeAttBean.ResultBean.CommentsBean commentsbean = mapone.getComments();//评论信息
        String picshow = picbean.getPurl();
        final String[] picString = picshow.split(",");
        if (picString.length>1){ //判断图片数量大于1
            holder.viewPager.setVisibility(View.VISIBLE);
            holder.show_iv.setVisibility(View.GONE);
            holder.xcircleindicator.setVisibility(View.VISIBLE);
            setViewpager(holder,position,picString);

        }else if (picString.length==1){  //图片数量等于1
            holder.viewPager.setVisibility(View.GONE);
            holder.show_iv.setVisibility(View.VISIBLE);
            holder.xcircleindicator.setVisibility(View.GONE);
            WindowManager windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
            int widthview = windowManager.getDefaultDisplay().getWidth();
            String[] wihi = mapone.getPicinfo().getSize().split("-");
            double width = 0;
            double hight = 0;
            try {
                width = Double.parseDouble(wihi[0]);
                hight = Double.parseDouble(wihi[1]);
            }catch (Exception e){
                Log.e("err", "onBindViewHolder: "+e.toString() );
            }
           double bi = width/hight;
            if (bi>1){ //横图
                holder.show_iv.getLayoutParams().width = widthview;
                holder.show_iv.getLayoutParams().height = (int) (widthview/bi);
                Uri uri = Uri.parse(picString[0]);
                DataUtils.getInstans().setImageSrc(holder.show_iv,uri,widthview/2, (int) (widthview/bi)/2);
            }else if (bi<1){ //竖图
                holder.show_iv.getLayoutParams().height = widthview;
                holder.show_iv.getLayoutParams().width = (int) (widthview*bi);
                Uri uri = Uri.parse(picString[0]);
                DataUtils.getInstans().setImageSrc(holder.show_iv,uri, (int) (widthview*bi)/2,widthview/2);
            }else if (bi==1||bi==0){ //方图
                holder.show_iv.getLayoutParams().height = widthview;
                holder.show_iv.getLayoutParams().width = widthview;
                Uri uri = Uri.parse(picString[0]);
                DataUtils.getInstans().setImageSrc(holder.show_iv,uri,widthview,widthview);
            }
            holder.show_iv.setOnClickListener(new View.OnClickListener() {  //图片点击事件
                @Override
                public void onClick(View v) {
                    ArrayList<String> listimg = new ArrayList<String>();
                    listimg.add(picString[0]);
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("image", (ArrayList<String>) listimg);
                    bundle.putInt("pos",pagerChoose);
                    AppManager.getAppManager().ToOtherActivity(ShowPhotoActivity.class,bundle);
                }
            });
        }

        setbootom(position);
        String name_show = picbean.getPicurl();
        name_show = name_show.replace("oss","img");
        name_show = name_show+"@100h_100w_1e_1c";
        holder.name_iv.setImageURI(name_show);
        holder.name_tv.setText(picbean.getName());
        holder.nameshow_tv.setText(picbean.getPdes());

        if (picbean.getFocuship().equals("0")){//判断是否关注
            holder.guanzhu_iv.setImageResource(R.mipmap.guanzhu);
            isguanzhu = false;
        }else if (picbean.getFocuship().equals("1")){
            holder.guanzhu_iv.setImageResource(R.mipmap.yiguanzhu);
            isguanzhu = true;
        }
        if (picbean.getFriship().equals("0")){//判断是否是好友
            holder.addpar_iv.setVisibility(View.VISIBLE);
        }else if (picbean.getFocuship().equals("1")){
            holder.addpar_iv.setVisibility(View.INVISIBLE);
        }
        if (picbean.getCtime()==null||picbean.getCtime().equals("")){//判断是否点赞
            holder.xing_iv.setImageResource(R.mipmap.home_kongxin);
        }else {
            holder.xing_iv.setImageResource(R.mipmap.home_hongxing);
        }
        if (picbean.getUid().equals(uid)){//判断是否是自己的图片
            holder.deltu_iv.setVisibility(View.VISIBLE);
            holder.guanzhu_iv.setVisibility(View.GONE);
            holder.addpar_iv.setVisibility(View.GONE);
        }else {
            holder.deltu_iv.setVisibility(View.GONE);
            holder.guanzhu_iv.setVisibility(View.VISIBLE);
            holder.addpar_iv.setVisibility(View.VISIBLE);
        }

       int hongzan = picbean.getLoveCount();
       int lanzan = picbean.getLovedCount();
        holder.hongzan_tv.setText(hongzan+"");
        holder.lanzan_tv.setText(lanzan+"");
       int pingluntiao = commentsbean.getTotal();
        if (pingluntiao<=0){ //判断评论条数
            holder.ping_rl.setVisibility(View.GONE);
            holder.allpinglun_tv.setVisibility(View.GONE);
        }else {
            if (pingluntiao>1){
                holder.allpinglun_tv.setVisibility(View.VISIBLE);
                holder.allpinglun_tv.setText("查看所有"+commentsbean.getTotal()+"条评论 >");
            }else {
                holder.allpinglun_tv.setVisibility(View.GONE);
            }
            holder.ping_rl.setVisibility(View.VISIBLE);
            if (commentsbean.getChilds()==null){
                holder.onepinglun_rl.setVisibility(View.GONE);
                holder.huifumore_tv.setVisibility(View.GONE);
                return;
            }

            Log.e(TAG, "onBindViewHolder: 回复详情"+commentsbean.toString());
            final HomeAttBean.ResultBean.CommentsBean.ChildsBeanX huifu = commentsbean.getChilds().get(0);
            //显示评论
            holder.onename_tv.setText(huifu.getName());
            holder.onetime_tv.setText(huifu.getTime());
            holder.onepinglun_tv.setText(huifu.getPComment());
            String oneuri = huifu.getPicurl();
            oneuri = oneuri.replace("oss","img");
            oneuri = oneuri+"@100h_100w_1e_1c";
            holder.onename_iv.setImageURI(oneuri);
            //回复第一条
            holder.onepinglun_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (uid.equals(huifu.getUid())){//删除
                        newDelping(huifu.getGroupid(),"",position,holder.onepinglun_tv,-1);
                    }else {//回复
                        liveCommentEdit(context,holder.onepinglun_tv,liveComment,huifu.getName(),picbean.getPid()+"",
                                uid,huifu.getUid(),huifu.getGroupid(),holder,position);
                    }
                }
            });

            holder.huifumore_tv.setVisibility(View.GONE);
            //判断回复条数并显示回复
            int huifutiao = huifu.getTotal();
            if (huifutiao==0){
                holder.onepinglun_rl.setVisibility(View.GONE);
            }else if (huifutiao==1){
                holder.onepinglun_rl.setVisibility(View.VISIBLE);
                holder.comhuifu_tv1.setVisibility(View.VISIBLE);
                holder.comhuifu_tv2.setVisibility(View.GONE);
                final HomeAttBean.ResultBean.CommentsBean.ChildsBeanX.ChildsBean childsone = huifu.getChilds().get(0);
                StyleBuilder styleBuilder = new StyleBuilder();
                styleBuilder.addTextStyle(childsone.getName()).textColor(R.color.black).click(new ClickListener() {

                    @Override
                    public void click(String s) {
                        if (childsone.getUid().equals(uid)){
                            newDelping("",childsone.getId(),position,holder.comhuifu_tv1,0);
                        }else {
                            liveCommentEdit(context,holder.comhuifu_tv1,liveComment,childsone.getName(),
                                    picbean.getPid()+"",uid,childsone.getUid(),childsone.getGroupid(),holder,position);
                        }

                    }
                }).commit()
                        .addTextStyle(" 回复 ").textColor(R.color.black).commit()
                        .addTextStyle(childsone.getCname()+": ").textColor(R.color.black).click(new ClickListener() {

                    @Override
                    public void click(String s) {
                        if (childsone.getCommentid().equals(uid)){
                            return;
                        }else {
                            liveCommentEdit(context,holder.comhuifu_tv1,liveComment,childsone.getCname(),
                                    childsone.getPid()+"",uid,childsone.getCommentid(),childsone.getGroupid(),holder,position );
                        }

                    }
                }).commit()
                        .addTextStyle(childsone.getComment()).click(new ClickListener() {

                    @Override
                    public void click(String s) {
                        if (childsone.getUid().equals(uid)){
                            newDelping("",childsone.getId(),position,holder.comhuifu_tv1,0);
                        }else {
                            liveCommentEdit(context,holder.comhuifu_tv1,liveComment,childsone.getName(),
                                    picbean.getPid()+"",uid,childsone.getUid(),childsone.getGroupid(),holder,position);
                        }
                    }
                }).commit()
                        .show(holder.comhuifu_tv1);

            }else if (huifutiao>=2){
                holder.onepinglun_rl.setVisibility(View.VISIBLE);
                holder.comhuifu_tv1.setVisibility(View.VISIBLE);
                holder.comhuifu_tv2.setVisibility(View.VISIBLE);
                if (huifu.getTotal()>2){
                    holder.huifumore_tv.setVisibility(View.VISIBLE);
                    holder.huifumore_tv.setText("共"+huifu.getTotal()+"条回复 >");
                }

                final HomeAttBean.ResultBean.CommentsBean.ChildsBeanX.ChildsBean childsone = huifu.getChilds().get(0);
                final HomeAttBean.ResultBean.CommentsBean.ChildsBeanX.ChildsBean childstwo = huifu.getChilds().get(1);
                StyleBuilder styleBuilder = new StyleBuilder();
                styleBuilder.addTextStyle(childsone.getName()).textColor(R.color.black).click(new ClickListener() {

                    @Override
                    public void click(String s) {
                        if (childsone.getUid().equals(uid)){
                            newDelping("",childsone.getId(),position,holder.comhuifu_tv1,0);
                        }else {
                            liveCommentEdit(context,holder.comhuifu_tv1,liveComment,childsone.getName(),
                                    picbean.getPid()+"",uid,childsone.getUid(),childsone.getGroupid(),holder,position);
                        }
                    }
                }).commit()
                        .addTextStyle(" 回复 ").textColor(R.color.black).commit()
                        .addTextStyle(childsone.getCname()+": ").textColor(R.color.green).click(new ClickListener() {

                    @Override
                    public void click(String s) {
                        if (childsone.getCommentid().equals(uid)){
                            return;
                        }else {
                            liveCommentEdit(context,holder.comhuifu_tv1,liveComment,childsone.getCname(),
                                    childsone.getPid()+"",uid,childsone.getCommentid(),childsone.getGroupid(),holder,position );
                        }

                    }
                }).commit()
                        .addTextStyle(childsone.getComment()).click(new ClickListener() {

                    @Override
                    public void click(String s) {
                        if (childsone.getUid().equals(uid)){
                            newDelping("",childsone.getId(),position,holder.comhuifu_tv1,0);
                        }else {
                            liveCommentEdit(context,holder.comhuifu_tv1,liveComment,childsone.getName(),
                                    picbean.getPid()+"",uid,childsone.getUid(),childsone.getGroupid(),holder,position);
                        }
                    }
                }).commit()
                        .show(holder.comhuifu_tv1);

                StyleBuilder styleBuilder1 = new StyleBuilder();
                styleBuilder1.addTextStyle(childstwo.getName()).textColor(R.color.black).click(new ClickListener() {
                    @Override
                    public void click(String s) {
                        if (childstwo.getUid().equals(uid)){
                            newDelping("",childstwo.getId(),position,holder.comhuifu_tv2,1);
                        }else {
                            liveCommentEdit(context,holder.comhuifu_tv2,liveComment,childstwo.getName(),
                                    childstwo.getPid()+"",uid,childstwo.getUid(),childstwo.getGroupid(),holder,position);
                        }
                    }
                }).commit()
                        .addTextStyle(" 回复 ").textColor(R.color.black).commit()
                        .addTextStyle(childstwo.getCname()+": ").textColor(R.color.green).click(new ClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void click(String s) {
                        if (childstwo.getCommentid().equals(uid)){
                            return;
                        }else {
                            liveCommentEdit(context,holder.comhuifu_tv2,liveComment,childstwo.getCname(),
                                    childstwo.getPid()+"",uid,childstwo.getCommentid(),childstwo.getGroupid(),holder,position);
                        }
                    }
                }).commit()
                        .addTextStyle(childstwo.getComment()).click(new ClickListener() {
                    @Override
                    public void click(String s) {
                        if (childstwo.getUid().equals(uid)){
                            newDelping("",childstwo.getId(),position,holder.comhuifu_tv2,1);
                        }else {
                            liveCommentEdit(context,holder.comhuifu_tv2,liveComment,childstwo.getName(),
                                    childstwo.getPid()+"",uid,childstwo.getUid(),childstwo.getGroupid(),holder,position);
                        }
                    }
                }).commit().show(holder.comhuifu_tv2);
            }
        }




        setOnclickall(holder,picbean,position);
        //更多回复
        holder.huifumore_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("groupid",commentsbean.getChilds().get(0).getGroupid());
                bundle.putSerializable("ping", (Serializable) commentsbean.getChilds().get(0));
                AppManager.getAppManager().ToOtherActivity(MoreReplayActivity.class,bundle);
            }
        });
    }


    //设置点击事件
    private void setOnclickall(final MyViewHolder holder, final HomeAttBean.ResultBean.PicinfoBean picinfoBean, final int position) {
        //所有评论
        holder.allpinglun_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("pid", String.valueOf(picinfoBean.getPid()));
                bundle.putString("uid",picinfoBean.getUid());
                AppManager.getAppManager().ToOtherActivity(ComDetailActivity.class,bundle);
            }
        });
        //分享
        holder.share_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setShare();
            }
        });

        //评论
        holder.pinglun_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liveCommentEdit(context,holder.pinglun_iv,liveComment,picinfoBean.getName(),
                        picinfoBean.getPid()+"",uid,"-1","-1",holder,position);
            }
        });
        //赞红星
        holder.xing_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = HttpUtils.URL+"pic/love";
                Map<String,String> map = new HashMap<String, String>();
                map.put("uid",uid);
                map.put("pid",picinfoBean.getPid()+"");
                Call call = HttpUtils.getInstance().mapCall(map,url);
                call.enqueue(new UIcallBack() {
                    @Override
                    public void onFailureUI(Call call, IOException e) {
                        Log.e(TAG, "onFailureUI: "+e.toString() );
                    }

                    @Override
                    public void onResponseUI(Call call, String body) {
                        Log.e(TAG, "onResponseUI: "+body );
                        Map<String,Object> result = DataUtils.getInstans().gsonToMap(body);
                        String code = (String) result.get("resultcode");
                        if (code.equals(HttpUtils.SUCCESS)){
                            holder.xing_iv.setImageResource(R.mipmap.home_hongxing);
                            MyUtils.getInstans().setMineShua(true);
                            MyUtils.getInstans().setLikeShua(true);
                            int hongzan = picinfoBean.getLoveCount();
                            hongzan++;
                            holder.hongzan_tv.setText(hongzan+"");
                        }else {
                            HttpUtils.getInstance().Errorcode(context,code);
                        }
                    }
                });
            }
        });
        //发消息
        holder.send_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //删除图片
        holder.deltu_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               newDelTu(holder.deltu_iv,picinfoBean.getPid()+"",position);
            }
        });

        //关注
        holder.guanzhu_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url;

                if (isguanzhu){
                    url = HttpUtils.URL+"friship/cancelfocus";
                }else {
                    url = HttpUtils.URL+"friship/focus";
                }
                Map<String,String> map = new HashMap<>();
                map.put("requid",uid);
                map.put("focusid",picinfoBean.getUid());
                Call call = HttpUtils.getInstance().mapCall(map,url);
                call.enqueue(new UIcallBack() {
                    @Override
                    public void onFailureUI(Call call, IOException e) {
                        Log.e("msg", "onFailureUI: "+e.toString() );
                    }

                    @Override
                    public void onResponseUI(Call call, String body) {
                        Log.e("msg", "onResponseUI: "+body );
                        Map<String,Object> result = DataUtils.getInstans().gsonToMap(body);
                        String code = (String) result.get("resultcode");
                        if (code.equals(HttpUtils.SUCCESS)){
                            if (isguanzhu){
                                isguanzhu = false;
                                holder.guanzhu_iv.setImageResource(R.mipmap.guanzhu);
                            }else {
                                isguanzhu = true;
                                holder.guanzhu_iv.setImageResource(R.mipmap.yiguanzhu);
                            }
                        }else {
                            HttpUtils.getInstance().Errorcode(context,code);
                        }
                    }
                });
            }
        });
        //加好友
        holder.addpar_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    //评论的网络请求（回复)
    liveCommentResult liveComment = new liveCommentResult() {
        @Override
        public void onResult(final boolean confirmed, final String comment, final String pid,
                             final String commentid, final String commentedid, final String groupid,
                             final MyViewHolder holder, final int position, final View view,
                             final String Cname) {
            String url = HttpUtils.URL+"pic/comment";
            Map<String,String> map = new HashMap<>();
            map.put("pid",pid);
            map.put("commentid",commentid);
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
                    Log.e("msg", "onResponseUI: "+body );
                    Map<String,Object> result = DataUtils.getInstans().gsonToMap(body);
                    String code = (String) result.get("resultcode");
                    if (code.equals(HttpUtils.SUCCESS)){
                        if (commentedid.equals("-1")){  //更新评论
                            BaseActivity.showtoast(context,context.getString(R.string.pingsucc));
                            int pingluntiao = list.get(position).getComments().getTotal();
                            if (pingluntiao>=1){
                                holder.allpinglun_tv.setVisibility(View.VISIBLE);
                                holder.allpinglun_tv.setText("查看所有"+(pingluntiao+1)+"条评论 >");
                            }

                            final String groupid = (String) result.get("result");
                            HomeAttBean.ResultBean.CommentsBean.ChildsBeanX childnew = new HomeAttBean.ResultBean.CommentsBean.ChildsBeanX();
                            childnew.setUid(commentid);
                            childnew.setName(username);
                            childnew.setPicurl((String) SharedPreferenceUtils.get("picurl",""));
                            childnew.setPComment(comment);
                            childnew.setGroupid(groupid);
                            childnew.setTime(MyUtils.getInstans().getnowTime());
                            childnew.setChilds(null);
                            List<HomeAttBean.ResultBean.CommentsBean.ChildsBeanX> listnew = new
                                    ArrayList<HomeAttBean.ResultBean.CommentsBean.ChildsBeanX>();
                            listnew.add(childnew);
                            if (pingluntiao>0){
                                listnew.addAll(list.get(position).getComments().getChilds());
                            }
                            list.get(position).getComments().setTotal(pingluntiao+1);
                            list.get(position).getComments().setChilds(listnew);
                            notifyItemChanged(position);

                        }else { //更新回复

                            HomeAttBean.ResultBean.CommentsBean.ChildsBeanX.ChildsBean childnew = new
                                        HomeAttBean.ResultBean.CommentsBean.ChildsBeanX.ChildsBean();
                            childnew.setGroupid(groupid);
                            childnew.setPid(pid);
                            childnew.setUid(commentid);
                            childnew.setComment(comment);
                            childnew.setTime(MyUtils.getInstans().getnowTime());
                            childnew.setCommentid(commentedid);
                            childnew.setName(username);
                            childnew.setCname(Cname);
                            final String replyid = (String) result.get("result");
                            childnew.setId(replyid);
                            childnew.setPicurl((String) SharedPreferenceUtils.get("picurl",""));
                            List<HomeAttBean.ResultBean.CommentsBean.ChildsBeanX.ChildsBean> listnewchild = new
                                    ArrayList<HomeAttBean.ResultBean.CommentsBean.ChildsBeanX.ChildsBean>();
                            listnewchild.add(childnew);
                            int huifutiao = list.get(position).getComments().getChilds().get(0).getTotal();
                            if (huifutiao>0){
                                listnewchild.addAll(list.get(position).getComments().getChilds().get(0).getChilds());
                            }
                            huifutiao++;
                            list.get(position).getComments().getChilds().get(0).setChilds(listnewchild);
                            list.get(position).getComments().getChilds().get(0).setTotal(huifutiao);
                            notifyItemChanged(position);

//                            holder.onepinglun_rl.setVisibility(View.GONE);
//                            if (huifutiao==0){
//                                huifutiao++;
//                                holder.onepinglun_rl.setVisibility(View.VISIBLE);
//                                holder.comhuifu_tv1.setVisibility(View.VISIBLE);
//                                holder.comhuifu_tv2.setVisibility(View.GONE);
//                                holder.huifumore_tv.setVisibility(View.GONE);
//
//                            }
                        }
                    }else {
                        HttpUtils.getInstance().Errorcode(context,code);
                    }

                }
            });
        }
    };

    //删除图片
    private void newDelTu(View view, final String pid, final int position) {

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
                String url = HttpUtils.URL+"pic/delpic";
                Map<String,String> map = new HashMap<>();
                map.put("pid",pid);
                map.put("requid",uid);
                Call call = HttpUtils.getInstance().mapCall(map,url);
                call.enqueue(new UIcallBack() {
                    @Override
                    public void onFailureUI(Call call, IOException e) {
                        Log.e(TAG, "onFailureUI: "+e.toString() );
                    }

                    @Override
                    public void onResponseUI(Call call, String body) {
                        Log.e(TAG, "onResponseUI: 删除发表"+body );
                        Map<String,Object> result = DataUtils.getInstans().gsonToMap(body);
                        String code = (String) result.get("resultcode");
                        if (code.equals(HttpUtils.SUCCESS)){
                            BaseActivity.showtoast(context,"删除成功");
                            list.remove(position);
                            notifyItemRemoved(position);
                        }else {

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

    //删除评论
    private void newDelping(final String groupid, final String replyid, final int position, View view, final int delpos) {

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
                if (groupid.equals("")){//删除回复
                    url = HttpUtils.URL+"pic/delreply";
                    map.put("requid",uid);
                    map.put("replyid",replyid);
                }else {//删除评论
                    url = HttpUtils.URL+"pic/delcomment";
                    map.put("requid",uid);
                    map.put("groupid",groupid);
                }

                Call call = HttpUtils.getInstance().mapCall(map,url);
                call.enqueue(new UIcallBack() {
                    @Override
                    public void onFailureUI(Call call, IOException e) {
                        Log.e(TAG, "onFailureUI: "+e.toString() );
                    }

                    @Override
                    public void onResponseUI(Call call, String body) {
                        Log.e(TAG, "onResponseUI: 删除评论"+body );
                        Map<String,Object> result = DataUtils.getInstans().gsonToMap(body);
                        String code = (String) result.get("resultcode");
                        if (code.equals(HttpUtils.SUCCESS)){
                            BaseActivity.showtoast(context,"删除成功");
                            if (groupid.equals("")){  //删除回复
                                list.get(position).getComments().getChilds().get(0).getChilds().remove(delpos);
                                int huitotal = list.get(position).getComments().getChilds().get(0).getTotal();
                                list.get(position).getComments().getChilds().get(0).setTotal(huitotal-1);
                            }else {
                                list.get(position).getComments().getChilds().remove(0);
//                                int pingtotal = list.get(position).getComments().getTotal();
                                int pingluntiao = list.get(position).getComments().getTotal();
                                list.get(position).getComments().setTotal(pingluntiao-1);
                            }

                            notifyItemChanged(position);
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

    //设置item下显示
    private void setbootom(int position) {

    }

    //设置viewpager
    private void setViewpager(final MyViewHolder holder, int position,String[] picStr) {
        final List<String> listimg = new ArrayList<>();
        for (int i = 0; i < picStr.length; i++) {
            listimg.add(picStr[i]);
        }
        //设置圆点指示器
        holder.xcircleindicator.initData(listimg.size(),0);
        holder.xcircleindicator.setCurrentPage(0);

        HomePhotoPagerAdapter homePhotoPagerAdapter = new HomePhotoPagerAdapter(context);
        homePhotoPagerAdapter.setList(listimg);
        holder.viewPager.setAdapter(homePhotoPagerAdapter);
        holder.viewPager.setCurrentItem(0);

        holder.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPageSelected(int position) {
                pagerChoose = position;
                holder.xcircleindicator.setCurrentPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
            holder.viewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            xdown = event.getRawX();
                            ydown = event.getRawY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            Xmove = (int) (event.getRawX() - xdown);
                            Ymove = (int) (event.getRawY()  - ydown);
                            break;
                        case MotionEvent.ACTION_UP:
                            if (Math.abs(Xmove)<=20 && Math.abs(Ymove)<=20){
                                Bundle bundle = new Bundle();
                                bundle.putStringArrayList("image", (ArrayList<String>) listimg);
                                bundle.putInt("pos",pagerChoose);
                                AppManager.getAppManager().ToOtherActivity(ShowPhotoActivity.class,bundle);
                            }
                            break;
                    }
                    return false;
                }
            });

        }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  View commentView = null;//评论
    public  View shanView = null;
    public PopupWindow shanPopup = null;
    public PopupWindow commentPopup = null;
    public  String result = "";
    public  liveCommentResult liveCommentResult = null;
    public BackEditText popup_live_comment_edit;
    public TextView popup_live_comment_send;

    //创建popwindow     context view,点击的view  commentReult,回调  nameone,显示回复者名称
    // pid,图片id  commentid，评论人id  commentedid，回复者id  groupid 组id（回复选填）,holder,position,回复者名字
    public void liveCommentEdit(final Context context, final View view, final liveCommentResult commentResult,
                                final String nameone, final String pid, final String commentid,
                                final String commentedid, final String groupid, final MyViewHolder holder,
                                final int position) {
        liveCommentResult = commentResult;
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
        if (commentedid.equals("-1")){
            popup_live_comment_edit.setHint(context.getString(R.string.pinglun));
        }else {
            popup_live_comment_edit.setHint(context.getString(R.string.huifuone)+nameone);
        }

        //这是布局中发送按钮的监听
        popup_live_comment_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = popup_live_comment_edit.getText().toString().trim();
                if (liveCommentResult != null && result.length() != 0) {
                    //把数据传出去
                    liveCommentResult.onResult(true, result,pid,commentid,commentedid,groupid,
                            holder,position,view,nameone);

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
        }, 200);
    }

    @Override
    public void back(TextView textView) {
        commentPopup.dismiss();
    }

    /**
     * 发送评论回调
     */
    public interface liveCommentResult {
        void onResult(boolean confirmed, String comment,String pid,
                      String commentid,String commentedid,String groupid,
                      MyViewHolder holder,int position,View view,String Cname);
    }




    //设置分享
    private void setShare() {
        OnekeyShare oks = new OnekeyShare();
//关闭sso授权
        oks.disableSSOWhenAuthorize();

// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
// titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
// text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(context.getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(context);
    }

    //自定义缓冲类继承自系统缓冲类

    class MyViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView image_iv,name_iv,show_iv,onename_iv;
        ImageView max_iv,guanzhu_iv,addpar_iv,xing_iv,pinglun_iv,share_iv,send_iv,deltu_iv;
        TextView name_tv,nameshow_tv,hongzan_tv,lanzan_tv,onename_tv,onetime_tv;
        TextView onepinglun_tv,huifumore_tv,allpinglun_tv;
        TextView comhuifu_tv1,comhuifu_tv2;
        RelativeLayout onepinglun_rl,ping_rl;
        CustomViewpager viewPager;
        View view1,view2;

        Xcircleindicator xcircleindicator;
        public MyViewHolder(View itemView) {
            super(itemView);
            name_iv = (SimpleDraweeView) itemView.findViewById(R.id.attitem_name_iv);
            show_iv = (SimpleDraweeView) itemView.findViewById(R.id.attitem_show_iv);
            onename_iv = (SimpleDraweeView) itemView.findViewById(R.id.attitem_onename_iv);
            max_iv = (ImageView) itemView.findViewById(R.id.home_item_max_iv);
            xing_iv = (ImageView) itemView.findViewById(R.id.attitem_xing_iv);
            pinglun_iv = (ImageView) itemView.findViewById(R.id.attitem_pinglun_iv);
            send_iv = (ImageView) itemView.findViewById(R.id.attitem_xiaoxi_iv);
            deltu_iv = (ImageView) itemView.findViewById(R.id.attitem_deltu_iv);
            share_iv = (ImageView) itemView.findViewById(R.id.attitem_share_iv);
            name_tv = (TextView) itemView.findViewById(R.id.attitem_name_tv);
            onename_tv = (TextView) itemView.findViewById(R.id.attitem_onename_tv);
            onetime_tv = (TextView) itemView.findViewById(R.id.attitem_onetime_tv);
            nameshow_tv = (TextView) itemView.findViewById(R.id.attitem_message_tv);
            hongzan_tv = (TextView) itemView.findViewById(R.id.attitem_hongzan_tv);
            lanzan_tv = (TextView) itemView.findViewById(R.id.attitem_lanzan_tv);
            onepinglun_tv = (TextView) itemView.findViewById(R.id.attitem_onepinglun_tv);
            guanzhu_iv = (ImageView) itemView.findViewById(R.id.attitem_guanmzhu_tv);
            addpar_iv = (ImageView) itemView.findViewById(R.id.attitem_add_iv);

            onepinglun_rl = (RelativeLayout) itemView.findViewById(R.id.attitem_onepinglun_rl);
            ping_rl = (RelativeLayout) itemView.findViewById(R.id.attitem_ping_rl);
            comhuifu_tv1 = (TextView) itemView.findViewById(R.id.attitem_comhuifu_tv1);
            comhuifu_tv2 = (TextView) itemView.findViewById(R.id.attitem_comhuifu_tv2);
            huifumore_tv = (TextView) itemView.findViewById(R.id.attitem_huifu_more_tv);
            allpinglun_tv = (TextView) itemView.findViewById(R.id.attitem_allpinglun_tv);
            viewPager = (CustomViewpager) itemView.findViewById(R.id.attitem_viewpager);
            view1 = itemView.findViewById(R.id.attitem_bottom_view1);
            view2 = itemView.findViewById(R.id.attitem_bottom_view2);

            xcircleindicator = (Xcircleindicator) itemView.findViewById(R.id.attitem_yuan_view);
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
