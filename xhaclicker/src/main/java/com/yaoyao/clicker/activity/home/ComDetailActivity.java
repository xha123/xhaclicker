package com.yaoyao.clicker.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yaoyao.clicker.R;
import com.yaoyao.clicker.adapter.ComDetailRecyclerAdapter;
import com.yaoyao.clicker.app.AppManager;
import com.yaoyao.clicker.base.BaseActivity;
import com.yaoyao.clicker.bean.ComdetailBean;
import com.yaoyao.clicker.utils.HttpUtils;
import com.yaoyao.clicker.utils.SharedPreferenceUtils;
import com.yaoyao.clicker.utils.UIcallBack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrClassicDefaultFooter;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.Call;

/**  评论详情页
 * Created by Administrator on 2017/9/4.
 */

public class ComDetailActivity extends BaseActivity{
    ImageView back_iv;
    TextView title_tv;
    RecyclerView recyclerView;

    String userid,pid,uid,time;
    ComDetailRecyclerAdapter comDetailRecyclerAdapter;
    PtrFrameLayout ptrFrameLayout;
    int sn;
    List<ComdetailBean.CommentsBean.ChildsBeanX> list;
    @Override
    public void inidata() {
        userid = (String) SharedPreferenceUtils.get("uid","");
        list = new ArrayList<>();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            pid = bundle.getString("pid");
            uid = bundle.getString("uid");
        }
        time = "";
    }

    @Override
    public void setCon() {
        setContentView(R.layout.activity_comdetail);
    }

    @Override
    public void iniview() {
        back_iv = (ImageView) findViewById(R.id.title_back_iv);
        title_tv = (TextView) findViewById(R.id.title_tv);
        recyclerView = (RecyclerView) findViewById(R.id.comdetail_recy);
        ptrFrameLayout = (PtrFrameLayout) findViewById(R.id.comde_ptr);
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
    }

    @Override
    public void setview() {
        title_tv.setText(R.string.pinglun);

        setRecy();
        setPtr();
    }

    private void setPtr() {
        PtrClassicDefaultFooter ptrClassicDefaultFooter = new PtrClassicDefaultFooter(this);
        ptrFrameLayout.setFooterView(ptrClassicDefaultFooter);
        ptrFrameLayout.addPtrUIHandler(ptrClassicDefaultFooter);

        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                time = list.get(list.size()-1).getTime();
                MoreCall();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

            }
        });
    }

    private void setRecy() {
        comDetailRecyclerAdapter = new ComDetailRecyclerAdapter(this);
        comDetailRecyclerAdapter.setList(list);
        comDetailRecyclerAdapter.setPid(pid);
        comDetailRecyclerAdapter.setPingSuss(pingSuss);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(comDetailRecyclerAdapter);
        comDetailRecyclerAdapter.notifyDataSetChanged();

        setCall();
    }

    ComDetailRecyclerAdapter.PingSuss pingSuss = new ComDetailRecyclerAdapter.PingSuss() {
        @Override
        public void getCall() {
            list.clear();
            setCall();
        }
    };

    private void setCall() {
        String url = HttpUtils.URL+"pic/iteminfo";
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
                Log.e(TAG, "onResponseUI: 用户评论"+body );
                ComdetailBean comdetailBean = new Gson().fromJson(body,ComdetailBean.class);
                String code = comdetailBean.getResultcode();
                if (code.equals(HttpUtils.SUCCESS)){
                    List<ComdetailBean.CommentsBean.ChildsBeanX> listnow = comdetailBean.getComments().getChilds();
                    if (listnow==null){ return;}
                    list.addAll(listnow);
                    comDetailRecyclerAdapter.setList(list);
                    comDetailRecyclerAdapter.setPicinfoBean(comdetailBean.getPicinfo());
                    comDetailRecyclerAdapter.notifyDataSetChanged();
                }else {
                    HttpUtils.getInstance().Errorcode(ComDetailActivity.this,code);
                }
            }
        });
    }

    private void MoreCall() {
        String url = HttpUtils.URL+"pic/item_more";
        Map<String,String> map = new HashMap<>();
        map.put("pid",pid);
        map.put("sn",sn+"");
        map.put("pn","21");
        map.put("time",time);
        Call call = HttpUtils.getInstance().mapCall(map,url);
        call.enqueue(new UIcallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                Log.e(TAG, "onFailureUI: "+e.toString() );
                ptrFrameLayout.refreshComplete();
            }

            @Override
            public void onResponseUI(Call call, String body) {
                Log.e(TAG, "onResponseUI: 更多评论"+body );
                ptrFrameLayout.refreshComplete();

            }
        });
    }
}
