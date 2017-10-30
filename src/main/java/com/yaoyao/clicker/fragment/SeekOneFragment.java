package com.yaoyao.clicker.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.yaoyao.clicker.R;
import com.yaoyao.clicker.activity.seek.UsercenActivity;
import com.yaoyao.clicker.adapter.SeekRecyclerAdapter;
import com.yaoyao.clicker.app.AppManager;
import com.yaoyao.clicker.dbmanager.HisExpress;
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
 * Created by Administrator on 2017/8/4.
 */

public class SeekOneFragment extends Fragment{
    RecyclerView recyclerView;
    PtrClassicFrameLayout ptrClassicFrameLayout;
    SeekRecyclerAdapter seekRecyclerAdapter;

    List<Map<String,Object>> listSeek;
    String userid;
    String seekmess;//搜索信息
    MyReciver mr;//自己的自定义广播接收器
    String title;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listSeek = new ArrayList<>();
        seekmess = "-1";
        userid = (String) SharedPreferenceUtils.get("uid","");

        return inflater.inflate(R.layout.fragment_homeatt,container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.homeatt_recycler);
        ptrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.homeatt_ptr);


        getActivity().getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mr=new MyReciver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("showseek");
        getActivity().registerReceiver(mr,intentFilter);

        iniview();
        setBundle();
    }

    private void setBundle() {
        Bundle bundle = getArguments();
        title= (String) bundle.getSerializable("title");
        if (title.equals(getString(R.string.remen))){
            getSeekCall();
        }else if (title.equals(getString(R.string.zuijin))){
            listSeek = HisExpress.getInstance(getContext()).getHisSeek();
            seekRecyclerAdapter.setList(listSeek);
            seekRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void iniview() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        seekRecyclerAdapter = new SeekRecyclerAdapter(getContext());
        seekRecyclerAdapter.setList(listSeek);
        seekRecyclerAdapter.setOnClick(onClick);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(seekRecyclerAdapter);
        seekRecyclerAdapter.notifyDataSetChanged();

        setPtr();
    }

    private void setPtr() {
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

    }

    public void getSeekCall(){
        String url = HttpUtils.URL+"friship/searchfri";
        Map<String,String> map = new HashMap<>();
        map.put("requid",userid);
        map.put("key",seekmess);
        Call call = HttpUtils.getInstance().mapCall(map,url);
        call.enqueue(new UIcallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                Log.e("msg", "onFailureUI: "+e.toString() );
            }

            @Override
            public void onResponseUI(Call call, String body) {
                Log.e("msg", "onResponseUI: 搜索用户列表"+body );
                Map<String,Object> result = DataUtils.getInstans().gsonToMap(body);
                String code = (String) result.get("resultcode");
                if (code.equals(HttpUtils.SUCCESS)){
                    listSeek = (List<Map<String, Object>>) result.get("result");
                    seekRecyclerAdapter.setList(listSeek);
                    seekRecyclerAdapter.notifyDataSetChanged();
                }else {
                    HttpUtils.getInstance().Errorcode(getContext(),code);
                }
            }
        });
    }

    SeekRecyclerAdapter.OnClick onClick = new SeekRecyclerAdapter.OnClick() {
        @Override
        public void onclick(int position,Map<String,Object> seekmap) {
            if (title.equals(getString(R.string.zuijin))){
                Bundle bundle = new Bundle();
                bundle.putString("uid", (String) seekmap.get("uid"));
                AppManager.getAppManager().ToOtherActivity(UsercenActivity.class,bundle);
            }else {
                List<Map<String, Object>> list = HisExpress.getInstance(getContext()).getHisSeek();
                if (list.size()>15){
                    HisExpress.getInstance(getContext()).delall();
                }else if (title.equals(getString(R.string.remen))){
                    String seekuid = (String) seekmap.get("uid");
                    for (int i = 0; i < list.size(); i++) {
                        String uid = (String) list.get(i).get("uid");
                        if (uid.equals(seekuid)){
                            Log.e("seek", "onClick: 相同map："+seekmap.toString() );
                            HisExpress.getInstance(getContext()).delOneSeek(seekmap);
                            continue;
                        }
                    }
                }
                Log.e("seek", "onClick: t添加map"+seekmap.toString() );
                HisExpress.getInstance(getContext()).addHisSeek(seekmap);
                Bundle bundle = new Bundle();
                bundle.putString("uid", (String) seekmap.get("uid"));
                MyUtils.getInstans().setUsershowid((String) seekmap.get("uid"));
                AppManager.getAppManager().ToOtherActivity(UsercenActivity.class,bundle);
            }

        }
    };

    //自定义的广播接收器
    class MyReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //是你响应了这个广播 要执行操作
            String Action=intent.getAction();//获得Action
            if(Action.equals("showseek")) {//判断Action
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    String mess = bundle.getString("seek");
                    Log.e("seek", "onReceive: " + mess);
                    seekmess = mess;
                    if (mess.equals("-1")){
                        if (title.equals(getString(R.string.remen))){
                            getSeekCall();
                        }else {
                            listSeek = HisExpress.getInstance(getContext()).getHisSeek();
                            seekRecyclerAdapter.setList(listSeek);
                            seekRecyclerAdapter.notifyDataSetChanged();
                        }
                    }else {
                        getSeekCall();
                    }

                }
            }
        }
    }
}
