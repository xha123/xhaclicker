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
import com.yaoyao.clicker.adapter.LikeShowRecyclerAdapter;
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
 * Created by Administrator on 2017/8/4.
 */

public class LikeshowFragment extends Fragment{

    RecyclerView recyclerView;
    LikeShowRecyclerAdapter likeShowRecyclerAdapter;
    List<Map<String,Object>> list ;
    PtrClassicFrameLayout ptrClassicFrameLayout;
    int sn,pn;
    String title,url;
    boolean isloadmore,isshua;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        list = new ArrayList<>();
        pn = 21;
        return inflater.inflate(R.layout.fragment_likeshow,container,false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.likeshow_recy);
        ptrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.likeshow_ptr);

        initview();
        setview();
        Bundle bundle = getArguments();
        title= (String) bundle.getSerializable("title");
        if (title.equals(getResources().getString(R.string.zan))){
            url = HttpUtils.URL+"pic/lovesort";
        }else if (title.equals(getResources().getString(R.string.huozan))){
            url = HttpUtils.URL+"pic/lovedsort";
        }else if (title.equals(getResources().getString(R.string.zuixin))){
            url = HttpUtils.URL+"pic/newestlovedsort";
        }
        setCall();
    }

    private void setCall() {
        String userid = (String) SharedPreferenceUtils.get("uid","");
        Map<String,String> map = new HashMap<>();
        map.put("requid",userid);
        map.put("sn",sn+"");
        map.put("pn",pn+"");
        Call call = HttpUtils.getInstance().mapCall(map,url);
        call.enqueue(new UIcallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                Log.e("msg", "onFailureUI: "+e.toString() );
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
                Log.e("msg", "onResponseUI: "+body );
                Map<String,Object> result = DataUtils.getInstans().gsonToMap(body);
                String code = (String) result.get("resultcode");
                if (code.equals(HttpUtils.SUCCESS)){
                    List<Map<String,Object>> listnow  = (List<Map<String, Object>>) result.get("result");
                    list.addAll(listnow);
                    likeShowRecyclerAdapter.setList(list);
                    likeShowRecyclerAdapter.notifyDataSetChanged();
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

    private void setview() {

    }

    private void initview() {
        likeShowRecyclerAdapter = new LikeShowRecyclerAdapter(getContext());
        likeShowRecyclerAdapter.setList(list);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
            @Override
            public int getSpanSize(int position) {
                if (position<1){
                    return 3;
                }else {
                    return 1;
                }
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(likeShowRecyclerAdapter);
        likeShowRecyclerAdapter.notifyDataSetChanged();

        ptrClassicFrameLayout.setLastUpdateTimeHeaderRelateObject(this);
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                isloadmore = true;
                sn+=pn;
                setCall();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                isshua = true;
                sn = 0;
                list.clear();
                setCall();
            }
        });

    }


}
