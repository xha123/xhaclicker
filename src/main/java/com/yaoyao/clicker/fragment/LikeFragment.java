package com.yaoyao.clicker.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yaoyao.clicker.R;
import com.yaoyao.clicker.activity.GuanzhuActivity;
import com.yaoyao.clicker.adapter.HomePagerAdapter;
import com.yaoyao.clicker.app.AppManager;
import com.yaoyao.clicker.utils.MyUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/5.
 */

public class LikeFragment extends Fragment{

    TabLayout tabLayout;
    ViewPager viewPager;
    ArrayList<String> title;
    ArrayList<Fragment> fragments;
    HomePagerAdapter homePagerAdapter;
    ImageView xin_iv,par_iv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        title = new ArrayList<>();
        fragments = new ArrayList<>();
        title.add(getResources().getString(R.string.zan));
        title.add(getResources().getString(R.string.huozan));
        title.add(getResources().getString(R.string.zuixin));
        for (int i = 0; i < title.size(); i++) {
            LikeshowFragment likeshowFragment = new LikeshowFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("title",title.get(i));
            likeshowFragment.setArguments(bundle);
            fragments.add(likeshowFragment);
        }

        return inflater.inflate(R.layout.fragment_xinxin,container,false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = (TabLayout) view.findViewById(R.id.xin_tab);
        viewPager = (ViewPager) view.findViewById(R.id.xin_viewpager);
        xin_iv = (ImageView) view.findViewById(R.id.xin_xin_iv);
        par_iv = (ImageView) view.findViewById(R.id.xin_par_iv);

        initview();
        setview();
        setOnclick();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyUtils.getInstans().isLikeShua()){
            fragments.clear();
            MyUtils.getInstans().setLikeShua(false);
            for (int i = 0; i < title.size(); i++) {
                LikeshowFragment likeshowFragment = new LikeshowFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("title",title.get(i));
                likeshowFragment.setArguments(bundle);
                fragments.add(likeshowFragment);
            }
        }
    }

    private void setOnclick() {
        xin_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().ToOtherActivity(GuanzhuActivity.class);
            }
        });
    }

    private void setview() {
        homePagerAdapter = new HomePagerAdapter(getFragmentManager());
        homePagerAdapter.setList_title(title);
        homePagerAdapter.setList_fragment(fragments);
        viewPager.setAdapter(homePagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabLayout, 30, 30);
            }
        });
    }

    //设置tablayout下划线宽度
    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    private void initview() {

    }

}
