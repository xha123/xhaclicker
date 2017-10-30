package com.yaoyao.clicker.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yaoyao.clicker.R;
import com.yaoyao.clicker.adapter.MoreReplyRecyclerAdapter;
import com.yaoyao.clicker.base.BaseActivity;
import com.yaoyao.clicker.bean.HomeAttBean;
import com.yaoyao.clicker.bean.MorerepBean;
import com.yaoyao.clicker.utils.HttpUtils;
import com.yaoyao.clicker.utils.UIcallBack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/9/5.
 */

public class MoreReplayActivity extends BaseActivity{
    ImageView back_iv;
    TextView title_tv;
    RecyclerView recyclerView;
    String groupid,time;
    int sn;

    List<MorerepBean.ResultBean> list;
    HomeAttBean.ResultBean.CommentsBean.ChildsBeanX childs;
    MoreReplyRecyclerAdapter moreReplyRecyclerAdapter;
    @Override
    public void inidata() {
        time = "-1";
        list = new ArrayList<>();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            groupid = bundle.getString("groupid");
            childs = (HomeAttBean.ResultBean.CommentsBean.ChildsBeanX) bundle.getSerializable("ping");
        }

    }

    @Override
    public void setCon() {
        setContentView(R.layout.activity_morereply);
    }

    @Override
    public void iniview() {
        back_iv = (ImageView) findViewById(R.id.title_back_iv);
        title_tv = (TextView) findViewById(R.id.title_tv);
        recyclerView = (RecyclerView) findViewById(R.id.morerep_recy);
    }

    @Override
    public void setResume() {

    }

    @Override
    public void setOnclick() {

    }

    @Override
    public void setview() {


        setRecy();
    }

    private void setRecy() {
        moreReplyRecyclerAdapter = new MoreReplyRecyclerAdapter(this);
        moreReplyRecyclerAdapter.setList(list);
        moreReplyRecyclerAdapter.setChilds(childs);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(moreReplyRecyclerAdapter);
        moreReplyRecyclerAdapter.notifyDataSetChanged();
        setCall();
    }

    private void setCall() {
        String url = HttpUtils.URL+"pic/item_child_more";
        Map<String,String> map = new HashMap<>();
        map.put("groupid",groupid);
        map.put("sn",sn+"");
        map.put("pn","21");
        map.put("time",time);
        Call call = HttpUtils.getInstance().mapCall(map,url);
        call.enqueue(new UIcallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                Log.e(TAG, "onFailureUI: "+e.toString() );
            }

            @Override
            public void onResponseUI(Call call, String body) {
                Log.e(TAG, "onResponseUI: 更多回复"+body );
                MorerepBean morerep = new Gson().fromJson(body,MorerepBean.class);
                String code = morerep.getResultcode();
                if (code.equals(HttpUtils.SUCCESS)){
                    List<MorerepBean.ResultBean> listone = morerep.getResult();
                    list.addAll(listone);
                    moreReplyRecyclerAdapter.setList(list);
                    moreReplyRecyclerAdapter.notifyDataSetChanged();
                }else {
                    HttpUtils.getInstance().Errorcode(MoreReplayActivity.this,code);
                }
            }
        });
    }
}
