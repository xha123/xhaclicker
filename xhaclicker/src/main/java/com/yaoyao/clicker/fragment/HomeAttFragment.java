package com.yaoyao.clicker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.yaoyao.clicker.R;
import com.yaoyao.clicker.adapter.HomeAttRecyclerAdapter;
import com.yaoyao.clicker.bean.HomeAttBean;
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
 * Created by Administrator on 2017/8/2.
 */

public class HomeAttFragment extends Fragment {

    RecyclerView recyclerView;
    List<HomeAttBean.ResultBean> list;
    List<String> listshowimg;
    boolean isshua;

    String userid,pid;
    boolean isloadmore;
    HomeAttRecyclerAdapter homeAttRecyclerAdapter;
    PtrClassicFrameLayout ptrClassicFrameLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        list = new ArrayList<>();
        listshowimg = new ArrayList<>();
        pid = "0";
        userid = (String) SharedPreferenceUtils.get("uid","");
        MyUtils.getInstans().setHomeShua(true);

        return inflater.inflate(R.layout.fragment_homeatt,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ptrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.homeatt_ptr);
        recyclerView = (RecyclerView) view.findViewById(R.id.homeatt_recycler);

        setinit();

    }


    @Override
    public void onResume() {
        super.onResume();
        if (MyUtils.getInstans().isHomeShua()){
            MyUtils.getInstans().setHomeShua(false);
            list.clear();
            homeAttRecyclerAdapter.setList(list);
            getCall();
        }
    }

    private void getCall() {
        String url = HttpUtils.URL+"pic/top";
        Map<String,String> map = new HashMap<>();
        map.put("requid",userid);
        map.put("pid",pid);
        Call call = HttpUtils.getInstance().mapCall(map,url);
        call.enqueue(new UIcallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ptrClassicFrameLayout.refreshComplete();
                if (isloadmore){
                    isloadmore = false;
                }
                if (isshua){
                    isshua = false;
                }
            }

            @Override
            public void onResponseUI(Call call, String body) {
                Log.e("msg", "onResponseUI: 关注"+body );
                HomeAttBean guanzhu = new Gson().fromJson(body,HomeAttBean.class);
//                Map<String,Object> result = DataUtils.getInstans().gsonToMap(body);
                String code = guanzhu.getResultcode();
                if (code.equals(HttpUtils.SUCCESS)){
                    List<HomeAttBean.ResultBean> listtop = guanzhu.getResult();
                    if (listtop.size()>=1){
                        list = homeAttRecyclerAdapter.getList();
                        list.addAll(listtop);
                        homeAttRecyclerAdapter.setList(list);
                        homeAttRecyclerAdapter.notifyDataSetChanged();
                    }else {

                    }
                }else {
                    HttpUtils.getInstance().Errorcode(getContext(),code);
                }
                ptrClassicFrameLayout.refreshComplete();
                if (isloadmore){
                    isloadmore = false;
                }
                if (isshua){
                    isshua = false;
                }
            }
        });
    }


    private void setinit() {
        homeAttRecyclerAdapter = new HomeAttRecyclerAdapter(getContext());
        homeAttRecyclerAdapter.setList(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(homeAttRecyclerAdapter);
        homeAttRecyclerAdapter.notifyDataSetChanged();
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isshua){
                    return true;
                }else {
                    return false;
                }
            }
        });

        setPtr();
    }

    private void setPtr() {
        ptrClassicFrameLayout.setLastUpdateTimeHeaderRelateObject(this);
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                isloadmore = true;
                int pic = list.get(list.size()-1).getPicinfo().getPid();
                pid = pic+"";
                getCall();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                isshua = true;
                pid = "0";
                list.clear();
                getCall();
            }
        });
    }


        //Fragment点击back键在Activity中处理
//    private boolean mHandledPress = false;
//
//    protected BackHandlerInterface backHandlerInterface;
//    public interface BackHandlerInterface {
//        public void setSelectedFragment(HomeAttFragment backHandledFragment);
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //回调函数赋值
//        if(!(getActivity()  instanceof BackHandlerInterface)) {
//            throw new ClassCastException("Hosting activity must implement BackHandlerInterface");
//        } else {
//            backHandlerInterface = (BackHandlerInterface) getActivity();
//        }
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        //将自己的实例传出去
//        backHandlerInterface.setSelectedFragment(this);
//    }
//
//    public boolean onBackPressed(){
////        if (isshowiv){
////           homeatt_viewpager.setVisibility(View.GONE);
////            isshowiv = false;
////            return true;
////        }
//
//        return false;
//    }



}
