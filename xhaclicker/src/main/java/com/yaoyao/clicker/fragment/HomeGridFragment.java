package com.yaoyao.clicker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yaoyao.clicker.R;
import com.yaoyao.clicker.adapter.HomerecyclerAdapter;
import com.yaoyao.clicker.utils.DataUtils;
import com.yaoyao.clicker.utils.HttpUtils;
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
 * Created by Administrator on 2017/7/6.
 */

public class HomeGridFragment extends Fragment{
    PtrClassicFrameLayout ptrClassicFrameLayout;
    RecyclerView recyclerView;
    ArrayList<Map<String,Object>> imagelist;
    HomerecyclerAdapter homerecyclerAdapter;
    String userid;
    String pid;
    boolean isloadmore;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        imagelist = new ArrayList<>();
        userid = (String) SharedPreferenceUtils.get("uid","");
        pid = "0";

        return inflater.inflate(R.layout.fragment_homegrid,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ptrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.homegrid_ptr);
        recyclerView = (RecyclerView) view.findViewById(R.id.home_recycler);

        setinit();
        setview();
        getCall();
    }

    private void getCall() {
        String url = HttpUtils.URL+"pic/classifypic";
        Map<String,String> map = new HashMap<>();
        map.put("requid",userid);
        map.put("pid",pid);
        Call call = HttpUtils.getInstance().mapCall(map,url);
        call.enqueue(new UIcallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                Log.e("msg", "onFailureUI: "+e.toString() );
                ptrClassicFrameLayout.refreshComplete();
                if (isloadmore){
                    isloadmore = false;
                }
            }

            @Override
            public void onResponseUI(Call call, String body) {
                Log.e("msg", "onResponseUI: 热推"+body );
                Map<String,Object> result = DataUtils.getInstans().gsonToMap(body);
                String code = (String) result.get("resultcode");
                if (code.equals(HttpUtils.SUCCESS)){
                    List<Map<String,Object>> listre = (List<Map<String, Object>>) result.get("result");
                    if (listre.size()>0){
                        imagelist.addAll(listre);
                        homerecyclerAdapter.setList(imagelist);
                        homerecyclerAdapter.notifyDataSetChanged();
                    }
                }else {
                    HttpUtils.getInstance().Errorcode(getContext(),code);
                }
                ptrClassicFrameLayout.refreshComplete();
                if (isloadmore){
                    isloadmore=false;
                }
            }
        });
    }

    private void setinit() {
        homerecyclerAdapter = new HomerecyclerAdapter(getContext());

        homerecyclerAdapter.setList(imagelist);
//        homerecyclerAdapter.setOnClick(onclick);
        GridLayoutManager manager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(homerecyclerAdapter);
        homerecyclerAdapter.notifyDataSetChanged();
    }

    private void setview() {

        ptrClassicFrameLayout.setLastUpdateTimeHeaderRelateObject(this);
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                isloadmore = true;
                if (imagelist.size()==0){
                    if (isloadmore){
                        isloadmore = false;
                        frame.refreshComplete();
                    }
                    return;
                }
                double pic = (double) imagelist.get(imagelist.size()-1).get("pid");
                pid = pic+"";
                getCall();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
               imagelist.clear();
                pid = "0";
                getCall();
            }
        });


    }

}
