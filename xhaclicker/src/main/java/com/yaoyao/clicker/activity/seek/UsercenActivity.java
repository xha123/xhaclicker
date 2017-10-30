package com.yaoyao.clicker.activity.seek;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yaoyao.clicker.R;
import com.yaoyao.clicker.activity.like.NewsDetailActivity;
import com.yaoyao.clicker.adapter.UsercenRecyclerAdapter;
import com.yaoyao.clicker.app.AppManager;
import com.yaoyao.clicker.base.BaseActivity;
import com.yaoyao.clicker.utils.DataUtils;
import com.yaoyao.clicker.utils.HttpUtils;
import com.yaoyao.clicker.utils.MyUtils;
import com.yaoyao.clicker.utils.SharedPreferenceUtils;
import com.yaoyao.clicker.utils.UIcallBack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.Call;

/**  用户中心页面
 * Created by Administrator on 2017/8/14.
 */

public class UsercenActivity extends BaseActivity{
    TextView name_tv,zan_tv,huozan_tv,mess_tv,guanzhu_tv,beiguanzhu_tv;
    ImageView back_iv,right_iv,guanzhu_iv,add_iv,icon_iv1,icon_iv2,icon_iv3;
    SimpleDraweeView show_iv1,show_iv2,show_iv3,name_iv;
    TextView zan_rl,zuixin_rl;
    LinearLayout showiv_ll;
    View left_view,right_view;
    RecyclerView recyclerView;
    UsercenRecyclerAdapter usercenRecyclerAdapter;
    List<Map<String,Object>> list;

    ScrollView scrollView;
    String uid;
    Map<String,Object> user;
    String userid;
    int sn;

    String guanurl;
    String isguan;
    float leftx,lefty,rightx,righty;
    PtrClassicFrameLayout ptrClassicFrameLayout;
    @Override
    public void inidata() {
        userid = (String) SharedPreferenceUtils.get("uid","");
        user = new HashMap<>();
        list = new ArrayList<>();
        guanurl = "";
        isguan = "";
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            uid = bundle.getString("uid");
        }
    }

    @Override
    public void setCon() {
        setContentView(R.layout.activity_usercen);
    }

    @Override
    public void iniview() {
        recyclerView = (RecyclerView) findViewById(R.id.usercen_recy);
        back_iv = (ImageView) findViewById(R.id.usercen_back_iv);
        showiv_ll = (LinearLayout) findViewById(R.id.usercen_tou_ll);
        show_iv1 = (SimpleDraweeView) findViewById(R.id.usercen_show_iv1);
        show_iv2 = (SimpleDraweeView) findViewById(R.id.usercen_show_iv2);
        show_iv3 = (SimpleDraweeView) findViewById(R.id.usercen_show_iv3);
        icon_iv1 = (ImageView) findViewById(R.id.usercen_icon_iv1);
        icon_iv2 = (ImageView) findViewById(R.id.usercen_icon_iv2);
        icon_iv3 = (ImageView) findViewById(R.id.usercen_icon_iv3);
        name_iv = (SimpleDraweeView) findViewById(R.id.usercen_name_iv);
        name_tv = (TextView) findViewById(R.id.usercen_name_tv);
        guanzhu_iv = (ImageView) findViewById(R.id.usercen_guanzhu_iv);

        add_iv = (ImageView) findViewById(R.id.usercen_add_iv);
        zan_tv = (TextView) findViewById(R.id.usercen_zan_tv);
        huozan_tv = (TextView) findViewById(R.id.usercen_huozan_tv);
        mess_tv = (TextView) findViewById(R.id.usercen_mess_tv);
        scrollView = (ScrollView) findViewById(R.id.usercen_scrollview);
        zan_rl = (TextView) findViewById(R.id.usercen_zantu_tv);
        zuixin_rl = (TextView) findViewById(R.id.usercen_zuixin_tv);

        guanzhu_tv = (TextView) findViewById(R.id.usercen_guanzu_tv);
        beiguanzhu_tv = (TextView) findViewById(R.id.usercen_bei_tv);
        left_view = findViewById(R.id.usercen_left_view);
        right_view = findViewById(R.id.usercen_right_view);
        ptrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.usercen_ptr);

    }

    @Override
    public void setview() {
        scrollView.smoothScrollTo(0,0);
        zuixin_rl.setClickable(false);
        setRecy();
        setCall();
        zuixinCall();

    }
    //最新网络请求
    private void zuixinCall() {
        String url = HttpUtils.URL+"pic/mynewspicsort";
        Map<String,String> map = new HashMap<>();
        map.put("requid",uid);
        map.put("sn",sn+"");
        map.put("pn",21+"");
        Call call = HttpUtils.getInstance().mapCall(map,url);
        call.enqueue(new UIcallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                Log.e(TAG, "onFailureUI: "+e.toString() );
            }

            @Override
            public void onResponseUI(Call call, String body) {
                Log.e(TAG, "onResponseUI: 最新图"+body );
                Map<String,Object> result = DataUtils.getInstans().gsonToMap(body);
                String code = (String) result.get("resultcode");
                if (code.equals(HttpUtils.SUCCESS)){
                    List<Map<String,Object>> listimage = (List<Map<String, Object>>) result.get("result");
                    list.addAll(listimage);
                    usercenRecyclerAdapter.setList(list);
                    usercenRecyclerAdapter.notifyDataSetChanged();
                    inishowimg();
                }else {
                    HttpUtils.getInstance().Errorcode(UsercenActivity.this,code);
                }
            }
        });

    }

    @Override
    public void setResume() {

    }

    @Override
    public void setOnclick() {
        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().finishActivity();
            }
        });
        zan_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                sn = 0;
                handler.sendEmptyMessageDelayed(101, 300);
                leftx = v.getLeft();
                rightx = right_view.getLeft();
                Log.e(TAG, "onClick: "+leftx +"   "+rightx );
                TranslateAnimation ta = new TranslateAnimation(0,leftx-rightx,0,0);
                ta.setDuration(200);
                ta.setFillAfter(true);
                right_view.startAnimation(ta);
                zan_rl.setClickable(false);
                zuixin_rl.setClickable(true);
            }
        });
        zuixin_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                sn = 0;
                handler.sendEmptyMessageDelayed(202, 300);
                rightx = v.getLeft();
                TranslateAnimation ta = new TranslateAnimation(leftx-rightx,0,0,0);
                ta.setDuration(200);
                ta.setFillAfter(true);
                right_view.startAnimation(ta);
                zan_rl.setClickable(true);
                zuixin_rl.setClickable(false);
            }
        });
        //关注
        guanzhu_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isguan.equals("0")){
                    guanurl = HttpUtils.URL+"friship/cancelfocus";
                }else if (isguan.equals("1")){
                    guanurl = HttpUtils.URL+"friship/focus";
                }
                guanCall();
            }
        });
        //添加好友
        add_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==101){
                zuixinCall();
            }else if (msg.what ==202){
                zanCall();
            }
        }
    };

    private void guanCall() {
        Map<String,String> map = new HashMap<>();
        map.put("requid",userid);
        map.put("focusid",uid);
        Call call = HttpUtils.getInstance().mapCall(map,guanurl);
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
                    if (isguan.equals("0")){
                        isguan = "1";
                        guanzhu_iv.setImageResource(R.mipmap.yiguanzhu);
                    }else if (isguan.equals("1")){
                        isguan = "0";
                        guanzhu_iv.setImageResource(R.mipmap.guanzhu);
                    }
                }else {
                    HttpUtils.getInstance().Errorcode(UsercenActivity.this,code);
                }
            }
        });
    }

    //赞网络请求
    private void zanCall() {
        String url = HttpUtils.URL+"pic/mylovesort";
        Map<String,String> map = new HashMap<>();
        map.put("requid",uid);
        map.put("sn",sn+"");
        map.put("pn",21+"");
        Call call = HttpUtils.getInstance().mapCall(map,url);
        call.enqueue(new UIcallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                Log.e(TAG, "onFailureUI: "+e.toString() );
            }

            @Override
            public void onResponseUI(Call call, String body) {
                Log.e(TAG, "onResponseUI: 用户赞"+body );
                Map<String,Object> result = DataUtils.getInstans().gsonToMap(body);
                String code = (String) result.get("resultcode");
                if (code.equals(HttpUtils.SUCCESS)){
                    List<Map<String,Object>> listzan = (List<Map<String, Object>>) result.get("result");
                    list.addAll(listzan);
                    usercenRecyclerAdapter.setList(list);
                    usercenRecyclerAdapter.notifyDataSetChanged();
                    inishowimg();
                }else {
                    HttpUtils.getInstance().Errorcode(UsercenActivity.this,code);
                }

            }
        });
    }

    private void setCall() {

        String url = HttpUtils.URL+"user/info";
        Map<String,String> map = new HashMap<>();
        map.put("requid",userid);
        map.put("reqeduid",uid);
        Call call = HttpUtils.getInstance().mapCall(map,url);
        call.enqueue(new UIcallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                Log.e(TAG, "onFailureUI: "+e.toString() );
            }

            @Override
            public void onResponseUI(Call call, String body) {
                Log.e(TAG, "onResponseUI: 用户信息"+body );
                Map<String,Object> result = DataUtils.getInstans().gsonToMap(body);
                String code = (String) result.get("resultcode");
                if (code.equals(HttpUtils.SUCCESS)){
                    user = (Map<String, Object>) result.get("result");
                    inishow();
                }else {
                    HttpUtils.getInstance().Errorcode(UsercenActivity.this,code);
                }
            }
        });
    }
    //初始化视图
    private void inishow() {
        WindowManager windowManager = this.getWindowManager();
        int width = windowManager.getDefaultDisplay().getWidth();
        showiv_ll.getLayoutParams().height = width/3*2;

        String name = (String) user.get("name");
        name_tv.setText(name);
        String nameshow = (String) user.get("picurl");
        nameshow = nameshow.replace("oss","img");
        nameshow = nameshow+"@250h_250w_1e_1c";
        name_iv.setImageURI(nameshow);
        double zan = (double) user.get("loveCount");
        double lanzan = (double) user.get("lovedCount");
        zan_tv.setText((int) zan+"");
        huozan_tv.setText((int) lanzan+"");
        double guanzhu = (double) user.get("focusCount");
        double beiguanzhu = (double) user.get("focusedCount");
        guanzhu_tv.setText((int) guanzhu+getString(R.string.guanzhu));
        beiguanzhu_tv.setText((int)beiguanzhu+getString(R.string.beiguanzhu));
        String mess = (String) user.get("udes");
        mess_tv.setText(mess);
        isguan = (String) user.get("focuship");
        if (isguan.equals("0")){
            guanzhu_iv.setImageResource(R.mipmap.guanzhu);
            guanurl = HttpUtils.URL+"friship/cancelfocus";
        }else if (isguan.equals("1")){
            guanurl = HttpUtils.URL+"friship/focus";
            guanzhu_iv.setImageResource(R.mipmap.yiguanzhu);
        }
        String isfri = (String) user.get("friship");
        if (isfri.equals("1")){
            add_iv.getLayoutParams().width = 1;
        }


    }

    private void inishowimg(){
        if (list.size()==0){
            return;
        } else if (list.size()==1){
            String urlone = (String) list.get(0).get("purl");
            String[] urlstr1 = urlone.split(",");
            if (urlstr1.length>1){
                icon_iv1.setVisibility(View.VISIBLE);
            }else {
                icon_iv1.setVisibility(View.GONE);
            }

            String url = urlstr1[0];
            url = url.replace("oss","img");
            url = url+"@720h_720w_1e_1c";
            show_iv1.setImageURI(url);
            show_iv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    String showpid = MyUtils.getInstans().doubleTwo((Double) list.get(0).get("pid"));
                    bundle.putString("pid",showpid);
                    AppManager.getAppManager().ToOtherActivity(NewsDetailActivity.class,bundle);
                }
            });
        }else if (list.size()==2){
            String urlone = (String) list.get(0).get("purl");
            String[] urlstr1 = urlone.split(",");
            if (urlstr1.length>1){
                icon_iv1.setVisibility(View.VISIBLE);
            }else {
                icon_iv1.setVisibility(View.GONE);
            }

            String url = urlstr1[0];
            url = url.replace("oss","img");
            url = url+"@720h_720w_1e_1c";
            show_iv1.setImageURI(url);
            show_iv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    String showpid = MyUtils.getInstans().doubleTwo((Double) list.get(0).get("pid"));
                    bundle.putString("pid",showpid);
                    AppManager.getAppManager().ToOtherActivity(NewsDetailActivity.class,bundle);
                }
            });

            String urltwo = (String) list.get(1).get("purl");
            String[] urlstr2 = urltwo.split(",");
            if (urlstr2.length<=1){
                icon_iv2.setVisibility(View.GONE);
            }else {
                icon_iv2.setVisibility(View.VISIBLE);
            }
            String url2 = urlstr2[0];
            url2 = url2.replace("oss","img");
            url2 = url2+"@250h_250w_1e_1c";
            show_iv2.setImageURI(url2);
            show_iv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    String showpid = MyUtils.getInstans().doubleTwo((Double) list.get(1).get("pid"));
                    bundle.putString("pid",showpid);
                    AppManager.getAppManager().ToOtherActivity(NewsDetailActivity.class,bundle);
                }
            });
        }else if (list.size()>=3){
            String urlone = (String) list.get(0).get("purl");
            String[] urlstr1 = urlone.split(",");
            if (urlstr1.length>1){
                icon_iv1.setVisibility(View.VISIBLE);
            }else {
                icon_iv1.setVisibility(View.GONE);
            }

            String url = urlstr1[0];
            url = url.replace("oss","img");
            url = url+"@720h_720w_1e_1c";
            show_iv1.setImageURI(url);
            show_iv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    String showpid = MyUtils.getInstans().doubleTwo((Double) list.get(0).get("pid"));
                    bundle.putString("pid",showpid);
                    AppManager.getAppManager().ToOtherActivity(NewsDetailActivity.class,bundle);
                }
            });

            String urltwo = (String) list.get(1).get("purl");
            String[] urlstr2 = urltwo.split(",");
            if (urlstr2.length<=1){
                icon_iv2.setVisibility(View.GONE);
            }else {
                icon_iv2.setVisibility(View.VISIBLE);
            }
            String url2 = urlstr2[0];
            url2 = url2.replace("oss","img");
            url2 = url2+"@250h_250w_1e_1c";
            show_iv2.setImageURI(url2);
            show_iv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    String showpid = MyUtils.getInstans().doubleTwo((Double) list.get(1).get("pid"));
                    bundle.putString("pid",showpid);
                    AppManager.getAppManager().ToOtherActivity(NewsDetailActivity.class,bundle);
                }
            });

            String urlthr = (String) list.get(2).get("purl");
            String[] urlstr3 = urlthr.split(",");
            if (urlstr3.length<=1){
                icon_iv3.setVisibility(View.GONE);
            }else {
                icon_iv3.setVisibility(View.VISIBLE);
            }
            String url3 = urlstr3[0];
            url3 = url3.replace("oss","img");
            url3 = url3+"@250h_250w_1e_1c";
            show_iv3.setImageURI(url3);
            show_iv3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    String showpid = MyUtils.getInstans().doubleTwo((Double) list.get(2).get("pid"));
                    bundle.putString("pid",showpid);
                    AppManager.getAppManager().ToOtherActivity(NewsDetailActivity.class,bundle);
                }
            });
        }
    }

    private void setRecy() {
        usercenRecyclerAdapter = new UsercenRecyclerAdapter(this);
        usercenRecyclerAdapter.setList(list);
//        FullyGridLayoutManager layoutManager = new FullyGridLayoutManager(this,3);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
//        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(usercenRecyclerAdapter);
//        recyclerView.setNestedScrollingEnabled(false);
        usercenRecyclerAdapter.notifyDataSetChanged();

        setPtr();
    }

    private void setPtr() {
        final PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(this);
        header.setLastUpdateTimeRelateObject(this);
        ptrClassicFrameLayout.setHeaderView(header);
        ptrClassicFrameLayout.addPtrUIHandler(header);

        ptrClassicFrameLayout.setLastUpdateTimeHeaderRelateObject(this);
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(final PtrFrameLayout frame) {
                ptrClassicFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        frame.refreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                ptrClassicFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        frame.refreshComplete();
                    }
                }, 1000);
            }
        });

//        ptrClassicFrameLayout.setResistance(1.7f);
//        ptrClassicFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
//        ptrClassicFrameLayout.setDurationToClose(200);
//        ptrClassicFrameLayout.setDurationToCloseHeader(1000);
//        // default is false
//        ptrClassicFrameLayout.setPullToRefresh(false);
//        // default is true
//        ptrClassicFrameLayout.setKeepHeaderWhenRefresh(true);
        ptrClassicFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {

                ptrClassicFrameLayout.autoRefresh();
            }
        }, 100);
    }
}
