package com.yaoyao.clicker.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yaoyao.clicker.R;
import com.yaoyao.clicker.activity.GuanzhuActivity;
import com.yaoyao.clicker.activity.HuanCenterActivity;
import com.yaoyao.clicker.activity.like.NewsDetailActivity;
import com.yaoyao.clicker.activity.mine.InstallActivity;
import com.yaoyao.clicker.activity.mine.MineshowActivity;
import com.yaoyao.clicker.adapter.UsercenRecyclerAdapter;
import com.yaoyao.clicker.app.AppManager;
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

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/5.
 */

public class MineFragment extends Fragment{
    ImageView install_iv,mine_iv,huan_iv,qing_iv;
    SimpleDraweeView name_iv;
    TextView name_tv,zan_tv,huozan_tv,guanzhu_tv,beiguanzhu_tv;
    boolean shua,iszan;//是否刷新界面
    float leftx,rightx;
    TextView zanshow_tv,zuixin_tv;
    View right_view;
    String userid;
    int sn;
    List<Map<String,Object>> list ;
    RecyclerView recyclerView;
    UsercenRecyclerAdapter usercenRecyclerAdapter;

    PtrClassicFrameLayout ptrClassicFrameLayout;
    ImageView icon_iv1,icon_iv2,icon_iv3;
    SimpleDraweeView show_iv1,show_iv2,show_iv3;
    LinearLayout showiv_ll;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MyUtils.getInstans().setMineShua(true);
        list = new ArrayList<>();
        userid = (String) SharedPreferenceUtils.get("uid","");

        return inflater.inflate(R.layout.fragment_mine,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        install_iv = (ImageView) view.findViewById(R.id.mine_install_iv);
        mine_iv = (ImageView) view.findViewById(R.id.mine_mineshow_iv);
        huan_iv = (ImageView) view.findViewById(R.id.mine_huan_iv);
        qing_iv = (ImageView) view.findViewById(R.id.mine_qing_iv);
        name_iv = (SimpleDraweeView) view.findViewById(R.id.mine_show_iv);
        name_tv = (TextView) view.findViewById(R.id.mine_name_tv);
        zan_tv = (TextView) view.findViewById(R.id.mine_zan_tv);
        huozan_tv = (TextView) view.findViewById(R.id.mine_huozan_tv);
        guanzhu_tv = (TextView) view.findViewById(R.id.mine_guanzu_tv);
        beiguanzhu_tv = (TextView) view.findViewById(R.id.mine_bei_tv);

        zanshow_tv = (TextView) view.findViewById(R.id.mine_zanshow_tv);
        zuixin_tv = (TextView) view.findViewById(R.id.mine_zuixin_tv);
        right_view = view.findViewById(R.id.mine_right_view);
        recyclerView = (RecyclerView) view.findViewById(R.id.mine_recy);

        showiv_ll = (LinearLayout) view.findViewById(R.id.mine_tou_ll);
        show_iv1 = (SimpleDraweeView) view.findViewById(R.id.mine_show_iv1);
        show_iv2 = (SimpleDraweeView) view.findViewById(R.id.mine_show_iv2);
        show_iv3 = (SimpleDraweeView) view.findViewById(R.id.mine_show_iv3);
        icon_iv1 = (ImageView) view.findViewById(R.id.mine_icon_iv1);
        icon_iv2 = (ImageView) view.findViewById(R.id.mine_icon_iv2);
        icon_iv3 = (ImageView) view.findViewById(R.id.mine_icon_iv3);
        ptrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.mine_ptr);

        WindowManager windowManager = getActivity().getWindowManager();
        int width = windowManager.getDefaultDisplay().getWidth();
        showiv_ll.getLayoutParams().height = width/3*2;

        iniview();
        iniRecy();
        setview();
        setOnclick();
    }



    private void setPtr() {
        ptrClassicFrameLayout.setLastUpdateTimeHeaderRelateObject(this);
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                sn = sn+21;
                if (iszan){
                    zanCall();
                }else {
                    zuixinCall();
                }
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
    }

    private void iniRecy() {
        usercenRecyclerAdapter = new UsercenRecyclerAdapter(getContext());
        usercenRecyclerAdapter.setList(list);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(usercenRecyclerAdapter);
        usercenRecyclerAdapter.notifyDataSetChanged();
        zuixinCall();
    }


    @Override
    public void onResume() {
        super.onResume();
        shua = MyUtils.getInstans().isMineShua();
        if (shua){
            MyUtils.getInstans().setMineShua(false);
        }else {
            return;
        }
        setCall();
        setPtr();
    }

    private void setCall() {

        String url = HttpUtils.URL+"user/info";
        Map<String,String> map = new HashMap<>();
        map.put("requid",userid);
        map.put("reqeduid",userid);
        Call call = HttpUtils.getInstance().mapCall(map,url);
        call.enqueue(new UIcallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                Log.e("msg", "onFailureUI: "+e.toString() );
            }

            @Override
            public void onResponseUI(Call call, String body) {
                Log.e("msg", "onResponseUI: 个人信息"+body );
                Map<String,Object> result = DataUtils.getInstans().gsonToMap(body);
                    String code = (String) result.get("resultcode");
                    if (code.equals(HttpUtils.SUCCESS)){
                    Log.e("mine", "onResponseUI: success" );
                    Map<String,Object> user = (Map<String, Object>) result.get("result");
                    for (String key:user.keySet()) {
                        SharedPreferenceUtils.put(key,user.get(key));
                    }

                    iniview();
                }else {
                    HttpUtils.getInstance().Errorcode(getContext(),code);
                }
            }
        });
    }

    private void setOnclick() {
        install_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().ToOtherActivity(InstallActivity.class);
            }
        });
        mine_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().ToOtherActivity(MineshowActivity.class);
            }
        });
        huan_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().ToOtherActivity(HuanCenterActivity.class);
            }
        });
        qing_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().ToOtherActivity(GuanzhuActivity.class);
            }
        });
        zanshow_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iszan = true;
                leftx = v.getLeft();
                rightx = zuixin_tv.getLeft();
                TranslateAnimation ta = new TranslateAnimation(0,leftx-rightx,0,0);
                ta.setDuration(200);
                ta.setFillAfter(true);
                right_view.startAnimation(ta);
                zanshow_tv.setClickable(false);
                zuixin_tv.setClickable(true);
                list.clear();
                sn = 0;
                handler.sendEmptyMessageDelayed(202, 300);
            }
        });
        zuixin_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iszan = false;
                rightx = v.getLeft();
                TranslateAnimation ta = new TranslateAnimation(leftx-rightx,0,0,0);
                ta.setDuration(200);
                ta.setFillAfter(true);
                right_view.startAnimation(ta);
                zanshow_tv.setClickable(true);
                zuixin_tv.setClickable(false);
                list.clear();
                sn = 0;
                handler.sendEmptyMessageDelayed(101, 300);
            }
        });
        zuixin_tv.setClickable(false);
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


    //赞网络请求
    private void zanCall() {
        String url = HttpUtils.URL+"pic/mylovesort";
        Map<String,String> map = new HashMap<>();
        map.put("requid",userid);
        map.put("sn",sn+"");
        map.put("pn",21+"");
        Call call = HttpUtils.getInstance().mapCall(map,url);
        call.enqueue(new UIcallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                Log.e("msg", "onFailureUI: "+e.toString() );
                ptrClassicFrameLayout.refreshComplete();
            }

            @Override
            public void onResponseUI(Call call, String body) {
                Log.e("msg", "onResponseUI: 自己赞"+body );
                ptrClassicFrameLayout.refreshComplete();
                Map<String,Object> result = DataUtils.getInstans().gsonToMap(body);
                String code = (String) result.get("resultcode");
                if (code.equals(HttpUtils.SUCCESS)){
                    List<Map<String,Object>> listzan = (List<Map<String, Object>>) result.get("result");
                    if (listzan.size()==0){
                        if (sn!=0){
                            sn=sn-21;
                            return;
                        }
                        return;
                    }
                    list.addAll(listzan);
                    usercenRecyclerAdapter.setList(list);
                    usercenRecyclerAdapter.notifyDataSetChanged();
                    inishowimg();
                }else {
                    HttpUtils.getInstance().Errorcode(getContext(),code);
                }

            }
        });
    }

    //最新网络请求
    private void zuixinCall() {
//        userid = MyUtils.getInstans().getUsershowid();
        Log.e("mine", "zuixinCall: "+userid );
        String url = HttpUtils.URL+"pic/mynewspicsort";
        Map<String,String> map = new HashMap<>();
        map.put("requid",userid);
        map.put("sn",sn+"");
        map.put("pn",21+"");
        Call call = HttpUtils.getInstance().mapCall(map,url);
        call.enqueue(new UIcallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                Log.e("msg", "onFailureUI: "+e.toString() );
                ptrClassicFrameLayout.refreshComplete();
            }

            @Override
            public void onResponseUI(Call call, String body) {
                Log.e("msg", "onResponseUI: 最新图"+body );
                ptrClassicFrameLayout.refreshComplete();
                Map<String,Object> result = DataUtils.getInstans().gsonToMap(body);
                String code = (String) result.get("resultcode");
                if (code.equals(HttpUtils.SUCCESS)){
                    List<Map<String,Object>> listimage = (List<Map<String, Object>>) result.get("result");
                    if (listimage.size()==0){
                        if (sn!=0){
                            sn=sn-21;
                            return;
                        }
                        return;
                    }
                    list.addAll(listimage);
                    usercenRecyclerAdapter.setList(list);
                    usercenRecyclerAdapter.notifyDataSetChanged();
                    inishowimg();
                }else {
                    HttpUtils.getInstance().Errorcode(getContext(),code);
                }
            }
        });

    }

    private void iniview() {
        String name = (String) SharedPreferenceUtils.get("name","");
        name_tv.setText(name);
        String picurl = (String) SharedPreferenceUtils.get("picurl","");
        picurl = picurl.replace("oss","img");
        picurl = picurl+"@250h_250w_1e_1c";
        name_iv.setImageURI(picurl);
        int zan = (int) SharedPreferenceUtils.get("loveCount",0);
        zan_tv.setText(zan+"");
        int huozan = (int) SharedPreferenceUtils.get("lovedCount",0);
        huozan_tv.setText(huozan+"");
        int guanz = (int) SharedPreferenceUtils.get("focusCount",0);
        guanzhu_tv.setText(guanz+"关注");
        int beiguanz = (int) SharedPreferenceUtils.get("focusedCount",0);
        beiguanzhu_tv.setText(beiguanz+"被关注");

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

    private void setview() {

    }
}
