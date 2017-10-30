package com.yaoyao.clicker.activity.like;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.yaoyao.clicker.R;
import com.yaoyao.clicker.activity.home.ComDetailActivity;
import com.yaoyao.clicker.activity.home.MoreReplayActivity;
import com.yaoyao.clicker.activity.home.ShowPhotoActivity;
import com.yaoyao.clicker.adapter.HomePhotoPagerAdapter;
import com.yaoyao.clicker.app.AppManager;
import com.yaoyao.clicker.base.BaseActivity;
import com.yaoyao.clicker.bean.HomeAttBean;
import com.yaoyao.clicker.bean.NewsDetailBean;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;

import static com.yaoyao.clicker.app.Myapp.context;

/** 图片详情
 * Created by Administrator on 2017/9/1.
 */

public class NewsDetailActivity extends BaseActivity implements BackEditText.BackListener{
    SimpleDraweeView image_iv,name_iv,show_iv,onename_iv;
    ImageView back_iv;
    ImageView max_iv,guanzhu_iv,addpar_iv,xing_iv,pinglun_iv,share_iv,send_iv;
    TextView name_tv,nameshow_tv,hongzan_tv,lanzan_tv,onename_tv,onetime_tv;
    TextView onepinglun_tv,huifumore_tv,allpinglun_tv;
    TextView comhuifu_tv1,comhuifu_tv2;
    RelativeLayout onepinglun_rl,ping_rl;
    CustomViewpager viewPager;

    Xcircleindicator xcircleindicator;
    String pid,userid;
    boolean isguanzhu;//是否关注
    int pingluntiao;//评论条数
    int huifutiao;//回复条数
    int hongzan,lanzan;//红赞，蓝赞个数
    int pagerChoose;//viewpager选中页数
    int Ymove;
    int Xmove;
    float xdown,ydown;

    NewsDetailBean.PicinfoBean picbean;
    NewsDetailBean.CommentsBean commentsbean;
    String username;
    NewsDetailBean.CommentsBean.ChildsBeanX.ChildsBean childsone;
    @Override
    public void inidata() {
        Intent intent = getIntent();
        Bundle bundle  = intent.getExtras();
        if (bundle!=null){
            pid = bundle.getString("pid");
            Log.e(TAG, "inidata: "+pid );
        }
        userid = (String) SharedPreferenceUtils.get("uid","");
        username = (String) SharedPreferenceUtils.get("name","");
    }

    @Override
    public void setCon() {
        setContentView(R.layout.activity_newsdetail);
    }

    @Override
    public void iniview() {
        name_iv = (SimpleDraweeView) findViewById(R.id.attitem_name_iv);
        show_iv = (SimpleDraweeView) findViewById(R.id.attitem_show_iv);
        onename_iv = (SimpleDraweeView) findViewById(R.id.attitem_onename_iv);
        max_iv = (ImageView) findViewById(R.id.home_item_max_iv);
        xing_iv = (ImageView) findViewById(R.id.attitem_xing_iv);
        pinglun_iv = (ImageView) findViewById(R.id.attitem_pinglun_iv);
        send_iv = (ImageView) findViewById(R.id.attitem_xiaoxi_iv);
        share_iv = (ImageView) findViewById(R.id.attitem_share_iv);
        name_tv = (TextView) findViewById(R.id.attitem_name_tv);
        onename_tv = (TextView) findViewById(R.id.attitem_onename_tv);
        onetime_tv = (TextView) findViewById(R.id.attitem_onetime_tv);
        nameshow_tv = (TextView) findViewById(R.id.attitem_message_tv);
        hongzan_tv = (TextView) findViewById(R.id.attitem_hongzan_tv);
        lanzan_tv = (TextView) findViewById(R.id.attitem_lanzan_tv);
        onepinglun_tv = (TextView) findViewById(R.id.attitem_onepinglun_tv);
        guanzhu_iv = (ImageView) findViewById(R.id.attitem_guanmzhu_tv);
        addpar_iv = (ImageView) findViewById(R.id.attitem_add_iv);

        onepinglun_rl = (RelativeLayout) findViewById(R.id.attitem_onepinglun_rl);
        ping_rl = (RelativeLayout) findViewById(R.id.attitem_ping_rl);
        comhuifu_tv1 = (TextView) findViewById(R.id.attitem_comhuifu_tv1);
        comhuifu_tv2 = (TextView) findViewById(R.id.attitem_comhuifu_tv2);
        huifumore_tv = (TextView) findViewById(R.id.attitem_huifu_more_tv);
        allpinglun_tv = (TextView) findViewById(R.id.attitem_allpinglun_tv);
        viewPager = (CustomViewpager) findViewById(R.id.attitem_viewpager);


        xcircleindicator = (Xcircleindicator) findViewById(R.id.attitem_yuan_view);
        back_iv = (ImageView) findViewById(R.id.title_back_iv);
    }


    @Override
    public void setResume() {


    }

    @Override
    public void setOnclick() {
        //回退
        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().finishActivity();
            }
        });
        //评论
        pinglun_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    newHuifu(picbean.getName(),pinglun_iv,"-1","-1");
            }
        });
        //红赞
        xing_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    zanCall();
            }
        });
        //更多回复
        huifumore_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                HomeAttBean.ResultBean.CommentsBean.ChildsBeanX  childone = new
                        HomeAttBean.ResultBean.CommentsBean.ChildsBeanX();
                childone.setGroupid(commentsbean.getChilds().get(0).getGroupid());
                childone.setTotal(commentsbean.getChilds().get(0).getTotal());
                childone.setPComment(commentsbean.getChilds().get(0).getPComment());
                childone.setSn(commentsbean.getChilds().get(0).getSn());
                childone.setPn(commentsbean.getChilds().get(0).getPn());
                childone.setTime(commentsbean.getChilds().get(0).getTime());
                childone.setUid(commentsbean.getChilds().get(0).getUid());
                childone.setName(commentsbean.getChilds().get(0).getName());
                childone.setPicurl(commentsbean.getChilds().get(0).getPicurl());
                childone.setChilds(null);
                bundle.putSerializable("ping",childone);
                bundle.putString("groupid",childone.getGroupid());
                AppManager.getAppManager().ToOtherActivity(MoreReplayActivity.class,bundle);
            }
        });
        //所有评论
        allpinglun_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("pid",picbean.getPid()+"");
                bundle.putString("uid",picbean.getUid());
                AppManager.getAppManager().ToOtherActivity(ComDetailActivity.class,bundle);
            }
        });
        //关注
        guanzhu_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGuanCall();
            }
        });

    }


    //关注网络请求
    private void getGuanCall() {
        String url;

        if (isguanzhu){
            url = HttpUtils.URL+"friship/cancelfocus";
        }else {
            url = HttpUtils.URL+"friship/focus";
        }
        Map<String,String> map = new HashMap<>();
        map.put("requid",userid);
        map.put("focusid",picbean.getUid());
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
                        guanzhu_iv.setImageResource(R.mipmap.guanzhu);
                    }else {
                        isguanzhu = true;
                        guanzhu_iv.setImageResource(R.mipmap.yiguanzhu);
                    }
                }else {
                    HttpUtils.getInstance().Errorcode(context,code);
                }
            }
        });
    }

    //赞网络请求
    private void zanCall() {
        String url = HttpUtils.URL+"pic/love";
        Map<String,String> map = new HashMap<String, String>();
        map.put("uid",userid);
        map.put("pid",picbean.getPid()+"");
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
                    xing_iv.setImageResource(R.mipmap.home_hongxing);
                    hongzan++;
                    hongzan_tv.setText(hongzan+"");
                }else {
                    HttpUtils.getInstance().Errorcode(context,code);
                }
            }
        });
    }

    @Override
    public void setview() {
        setCall();
    }

    private void setCall() {
        String url = HttpUtils.URL+"pic/iteminfo";
        Map<String,String> map = new HashMap<>();
        map.put("requid",userid);
        map.put("pid",pid);
        Call call = HttpUtils.getInstance().mapCall(map,url);
        call.enqueue(new UIcallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                Log.e(TAG, "onFailureUI: "+e.toString() );
            }

            @Override
            public void onResponseUI(Call call, String body) {
                Log.e(TAG, "onResponseUI: 图片动态详情"+body );
                NewsDetailBean newsDetailBean = new Gson().fromJson(body,NewsDetailBean.class);
                String code = newsDetailBean.getResultcode();
                if (code.equals(HttpUtils.SUCCESS)){
                    picbean = newsDetailBean.getPicinfo();//用户信息
                    commentsbean = newsDetailBean.getComments();//评论信息
                    iniviewshow();
                }else {
                    HttpUtils.getInstance().Errorcode(NewsDetailActivity.this,code);
                }
            }
        });
    }

    //初始化视图显示
    private void iniviewshow() {

        String name_show = picbean.getPicurl();
        name_show = name_show.replace("oss","img");
        name_show = name_show+"@100h_100w_1e_1c";
        Uri nameuri = Uri.parse(name_show);
        DataUtils.getInstans().setImageSrc(name_iv,nameuri,60,60);
        name_tv.setText(picbean.getName());
        nameshow_tv.setText(picbean.getPdes());
        if (picbean.getFocuship().equals("0")){//判断是否关注
            guanzhu_iv.setImageResource(R.mipmap.guanzhu);
            isguanzhu = false;
        }else if (picbean.getFocuship().equals("1")){
            guanzhu_iv.setImageResource(R.mipmap.yiguanzhu);
            isguanzhu = true;
        }
        if (picbean.getCtime()==null||picbean.getCtime().equals("")){
            xing_iv.setImageResource(R.mipmap.home_kongxin);
        }else {
            xing_iv.setImageResource(R.mipmap.home_hongxing);
        }

        if (picbean.getFriship().equals("0")){ //判断是否是好友
//                addpar_iv.setVisibility(View.VISIBLE);
        }else if (picbean.getFocuship().equals("1")){
            addpar_iv.getLayoutParams().width = 0;
        }
        hongzan = picbean.getLoveCount();
        lanzan = picbean.getLovedCount();
        hongzan_tv.setText(hongzan+"");
        lanzan_tv.setText(lanzan+"");
        //图片处理
        String picshow = picbean.getPurl();
        final String[] picString = picshow.split(",");
        if (picString.length>1){ //判断图片数量大于1
            viewPager.setVisibility(View.VISIBLE);
            show_iv.setVisibility(View.GONE);
            xcircleindicator.setVisibility(View.VISIBLE);
            setViewpager(picString);
        }else if (picString.length==1) {   //图片数量等于1
            viewPager.setVisibility(View.GONE);
            show_iv.setVisibility(View.VISIBLE);
            xcircleindicator.setVisibility(View.GONE);
            WindowManager windowManager = (WindowManager) getSystemService(context.WINDOW_SERVICE);
            int widthview = windowManager.getDefaultDisplay().getWidth();
            String[] wihi = picbean.getSize().split("-");
            double width = 0;
            double hight = 0;
            try {
                width = Double.parseDouble(wihi[0]);
                hight = Double.parseDouble(wihi[1]);
            } catch (Exception e) {
                Log.e("err", "onBindViewHolder: " + e.toString());
            }
            double bi = width / hight;
            if (bi > 1) {//横图
                show_iv.getLayoutParams().width = widthview;
                show_iv.getLayoutParams().height = (int) (widthview / bi);
                Uri uri = Uri.parse(picString[0]);
                DataUtils.getInstans().setImageSrc(show_iv, uri, widthview / 2, (int) (widthview / bi) / 2);
            } else if (bi < 1) {//竖图
                show_iv.getLayoutParams().height = widthview;
                show_iv.getLayoutParams().width = (int) (widthview * bi);
                Uri uri = Uri.parse(picString[0]);
                DataUtils.getInstans().setImageSrc(show_iv, uri, (int) (widthview * bi) / 2, widthview / 2);
            } else if (bi == 1) {//方图
                show_iv.getLayoutParams().height = widthview;
                show_iv.getLayoutParams().width = widthview;
                Uri uri = Uri.parse(picString[0]);
                DataUtils.getInstans().setImageSrc(show_iv, uri, widthview, widthview);
            }
            show_iv.setOnClickListener(new View.OnClickListener() { //图片点击事件
                @Override
                public void onClick(View v) {
                    ArrayList<String> listimg = new ArrayList<String>();
                    listimg.add(picString[0]);
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("image", (ArrayList<String>) listimg);
                    bundle.putInt("pos", pagerChoose);
                    AppManager.getAppManager().ToOtherActivity(ShowPhotoActivity.class, bundle);
                }
            });
        }
        //评论回复处理
        pingluntiao = commentsbean.getTotal();
        if (pingluntiao==0){//判断评论条数
            ping_rl.setVisibility(View.GONE);
        }else {
            ping_rl.setVisibility(View.VISIBLE);
            if (pingluntiao>1){
                allpinglun_tv.setVisibility(View.VISIBLE);
                allpinglun_tv.setText("查看所有"+commentsbean.getTotal()+"条评论 >");
            }else {
                allpinglun_tv.setVisibility(View.GONE);
            }
            ping_rl.setVisibility(View.VISIBLE);
            final NewsDetailBean.CommentsBean.ChildsBeanX huifu = commentsbean.getChilds().get(0);

            onename_tv.setText(huifu.getName());
            onetime_tv.setText(huifu.getTime());
            onepinglun_tv.setText(huifu.getPComment());
            Uri onenameuri = Uri.parse(huifu.getPicurl());
            DataUtils.getInstans().setImageSrc(onename_iv,onenameuri,60,60);
            //回复第一条
            onepinglun_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (huifu.getUid().equals(userid)){
                        newDel(onepinglun_tv,huifu.getGroupid(),"");
                    }else {
                        newHuifu(huifu.getName(),onepinglun_tv,huifu.getUid(),huifu.getGroupid());
                    }
                }
            });

            huifumore_tv.setVisibility(View.GONE);
            //判断回复条数
            huifutiao = huifu.getTotal();
            if (huifutiao==0){
                onepinglun_rl.setVisibility(View.GONE);
            }else if (huifutiao==1){
                onepinglun_rl.setVisibility(View.VISIBLE);
                comhuifu_tv1.setVisibility(View.VISIBLE);
                comhuifu_tv2.setVisibility(View.GONE);
                childsone = huifu.getChilds().get(0);

                StyleBuilder styleBuilder = new StyleBuilder();
                styleBuilder.addTextStyle(childsone.getName()).textColor(R.color.black).click(new ClickListener() {

                    @Override
                    public void click(String s) {
                        if (childsone.getUid().equals(userid)){
                            newDel(comhuifu_tv1,"",childsone.getId());
                        }else {
                            newHuifu(s,comhuifu_tv1,childsone.getUid(),childsone.getGroupid());
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
                            newHuifu(s,comhuifu_tv1,childsone.getCommentid(),childsone.getGroupid());
                        }
                    }
                }).commit()
                        .addTextStyle(childsone.getComment()).click(new ClickListener() {

                    @Override
                    public void click(String s) {
                        if (childsone.getUid().equals(userid)){
                            newDel(comhuifu_tv1,"",childsone.getId());
                        }else {
                            newHuifu(s,comhuifu_tv1,childsone.getUid(),childsone.getGroupid());
                        }
                    }
                }).commit()
                        .show(comhuifu_tv1);

            }else if (huifutiao>=2){
                onepinglun_rl.setVisibility(View.VISIBLE);
                comhuifu_tv1.setVisibility(View.VISIBLE);
                comhuifu_tv2.setVisibility(View.VISIBLE);
                if (huifu.getTotal()>2){
                    huifumore_tv.setVisibility(View.VISIBLE);
                    huifumore_tv.setText("共"+huifu.getTotal()+"条回复 >");
                }

                childsone = huifu.getChilds().get(0);
                final NewsDetailBean.CommentsBean.ChildsBeanX.ChildsBean childstwo = huifu.getChilds().get(1);
                StyleBuilder styleBuilder = new StyleBuilder();
                styleBuilder.addTextStyle(childsone.getName()).textColor(R.color.black).click(new ClickListener() {

                    @Override
                    public void click(String s) {
                        if (childsone.getUid().equals(userid)){
                            newDel(comhuifu_tv1,"",childsone.getId());
                        }else {
                            newHuifu(s,comhuifu_tv1,childsone.getUid(),childsone.getGroupid());
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
                            newHuifu(s,comhuifu_tv1,childsone.getCommentid(),childsone.getGroupid());
                        }
                    }
                }).commit()
                        .addTextStyle(childsone.getComment()).click(new ClickListener() {

                    @Override
                    public void click(String s) {
                        if (childsone.getUid().equals(userid)){
                            newDel(comhuifu_tv1,"",childsone.getId());
                        }else {
                            newHuifu(s,comhuifu_tv1,childsone.getUid(),childsone.getGroupid());
                        }
                    }
                }).commit()
                        .show(comhuifu_tv1);

                StyleBuilder styleBuilder1 = new StyleBuilder();
                styleBuilder1.addTextStyle(childstwo.getName()).textColor(R.color.black).click(new ClickListener() {
                    @Override
                    public void click(String s) {
                        if (childstwo.getUid().equals(userid)){
                            newDel(comhuifu_tv2,"",childstwo.getId());
                        }else {
                            newHuifu(s,comhuifu_tv2,childstwo.getCommentid(),childstwo.getGroupid());
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
                            newHuifu(s,comhuifu_tv2,childstwo.getCommentid(),childstwo.getGroupid());
                        }
                    }
                }).commit()
                        .addTextStyle(childstwo.getComment()).click(new ClickListener() {
                    @Override
                    public void click(String s) {
                        if (childstwo.getUid().equals(userid)){
                            newDel(comhuifu_tv2,"",childstwo.getId());
                        }else {
                            newHuifu(s,comhuifu_tv2,childstwo.getCommentid(),childstwo.getGroupid());
                        }
                    }
                }).commit().show(comhuifu_tv2);
            }
        }
    }


    public  View commentView = null;
    public PopupWindow commentPopup = null;
    public  String result = "";
    public BackEditText popup_live_comment_edit;
    public TextView popup_live_comment_send;
    /**
     * 评论 回复的popwindow
     * @param name 显示名字
     * @param view 显示View
     * @param commentedid 回复者id
     * @param groupid 组id
     */
    private void newHuifu(final String name, final View view, final String commentedid, final String groupid) {

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
            popup_live_comment_edit.setHint(getString(R.string.pinglun));
        }else {
            popup_live_comment_edit.setHint(context.getString(R.string.huifuone)+name);
        }

        //这是布局中发送按钮的监听
        popup_live_comment_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = popup_live_comment_edit.getText().toString().trim();
                if ( result.length() != 0) {
                    //把数据传出去
                    huifuCall(result,commentedid,groupid,name);
                    DataUtils.getInstans().closeKeyboard(NewsDetailActivity.this,popup_live_comment_edit);
                    //关闭popup
                    commentPopup.dismiss();
                }
            }
        });
        //设置popup关闭时要做的操作
        commentPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                DataUtils.getInstans().closeKeyboard(NewsDetailActivity.this,popup_live_comment_edit);
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
                DataUtils.getInstans().openKeyboard(NewsDetailActivity.this,popup_live_comment_edit);
            }
        }, 150);
    }
    //评论回复的网络请求
    private void huifuCall(final String comment, final String commentedid, final String groupid, final String name) {
        String url = HttpUtils.URL+"pic/comment";
        Map<String,String> map = new HashMap<>();
        map.put("pid",pid);
        map.put("commentid",userid);
        map.put("commentedid",commentedid);
        String isocomment = MyUtils.getInstans().getIsoShow(comment);
        map.put("content",isocomment);
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
                Log.e("msg", "onResponseUI: 回复成功" + body);
                Map<String, Object> result = DataUtils.getInstans().gsonToMap(body);
                String code = (String) result.get("resultcode");
                if (code.equals(HttpUtils.SUCCESS)) {

                    if (commentedid.equals("-1")) {  //更新评论
                        showtoast(NewsDetailActivity.this, "评论成功");

                        setCall();

                    } else {  //更新回复
                        showtoast(NewsDetailActivity.this, "回复成功");
                        huifutiao++;
                        NewsDetailBean.CommentsBean.ChildsBeanX.ChildsBean childadd = new
                                NewsDetailBean.CommentsBean.ChildsBeanX.ChildsBean();

                        childadd.setGroupid(groupid);
                        childadd.setPid(pid);
                        childadd.setUid(userid);
                        childadd.setComment(comment);
                        childadd.setTime(MyUtils.getInstans().getnowTime());
                        childadd.setCommentid(commentedid);
                        childadd.setName(username);
                        childadd.setCname(name);
                        final String replyid = (String) result.get("result");
                        childadd.setId(replyid);
                        childadd.setPicurl((String) SharedPreferenceUtils.get("picurl",""));
                        List<NewsDetailBean.CommentsBean.ChildsBeanX.ChildsBean> listchi = new
                                ArrayList<NewsDetailBean.CommentsBean.ChildsBeanX.ChildsBean>();
                        listchi.add(childadd);
                        listchi.addAll(commentsbean.getChilds().get(0).getChilds());
                        commentsbean.getChilds().get(0).setChilds(listchi);
                        commentsbean.getChilds().get(0).setTotal(huifutiao);
                        iniviewshow();
                    }
                }
            }
        });
    }

    View shanView;
    PopupWindow shanPopup;

    //删除评论
    private void newDel(View view, final String groupid, final String replyid) {
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
                        Log.e(TAG, "onFailureUI: "+e.toString() );
                    }

                    @Override
                    public void onResponseUI(Call call, String body) {
                        Log.e(TAG, "onResponseUI: 删除评论"+body );
                        Map<String,Object> result = DataUtils.getInstans().gsonToMap(body);
                        String code = (String) result.get("resultcode");
                        if (code.equals(HttpUtils.SUCCESS)){
                            BaseActivity.showtoast(context,"删除成功");
                            setCall();
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

    @Override
    public void back(TextView textView) {
        commentPopup.dismiss();
    }

    //设置viewpager
    private void setViewpager(String[] picStr) {

        final List<String> listimg = new ArrayList<>();
        for (int i = 0; i < picStr.length; i++) {
            listimg.add(picStr[i]);
        }
        //设置圆点指示器
        xcircleindicator.initData(listimg.size(),4);
//        xcircleindicator.setPageTotalCount(listimg.size());
        xcircleindicator.setCurrentPage(0);

        HomePhotoPagerAdapter homePhotoPagerAdapter = new HomePhotoPagerAdapter(context);
        homePhotoPagerAdapter.setList(listimg);
        viewPager.setAdapter(homePhotoPagerAdapter);
        viewPager.setCurrentItem(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }


            @Override
            public void onPageSelected(int position) {
                pagerChoose = position;
                xcircleindicator.setCurrentPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setOnTouchListener(new View.OnTouchListener() {
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


}
