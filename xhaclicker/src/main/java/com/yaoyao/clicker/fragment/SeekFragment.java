package com.yaoyao.clicker.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yaoyao.clicker.R;
import com.yaoyao.clicker.adapter.HomePagerAdapter;
import com.yaoyao.clicker.utils.DataUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/5.
 */

public class SeekFragment extends Fragment{
    EditText seek_ed;
    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView cancel_iv;
    ArrayList<String> title;
    ArrayList<Fragment> fragments;
    HomePagerAdapter homePagerAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        title = new ArrayList<>();
        fragments = new ArrayList<>();
        title.add(getResources().getString(R.string.remen));
        title.add(getResources().getString(R.string.zuijin));
        for (int i = 0; i < title.size(); i++) {
                SeekOneFragment seekOneFragment = new SeekOneFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("title",title.get(i));
                seekOneFragment.setArguments(bundle);
                fragments.add(seekOneFragment);
        }

        return inflater.inflate(R.layout.fragment_seek,container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        seek_ed = (EditText) view.findViewById(R.id.seek_seek_ed);
        tabLayout = (TabLayout) view.findViewById(R.id.seek_tab);
        viewPager = (ViewPager) view.findViewById(R.id.seek_viewpager);
        cancel_iv = (ImageView) view.findViewById(R.id.seek_cancel_iv);

        initview();
        setRecy();
    }

    private void setRecy() {
        homePagerAdapter = new HomePagerAdapter(getFragmentManager());
        homePagerAdapter.setList_title(title);
        homePagerAdapter.setList_fragment(fragments);
        viewPager.setAdapter(homePagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabLayout, 60, 60);
            }
        });
    }

    private void initview() {
       seek_ed.addTextChangedListener(textWatcher);
        cancel_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel_iv.setVisibility(View.GONE);
                seek_ed.setText("");
                DataUtils.getInstans().closeKeyboard(getContext(),seek_ed);
            }
        });
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.e("seek", "afterTextChanged: "+s.toString() );
            String seektext = seek_ed.getText().toString();
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            if (seektext==null || seektext.equals("")){
                cancel_iv.setVisibility(View.GONE);
                bundle.putString("seek","-1");
            }else {
                cancel_iv.setVisibility(View.VISIBLE);
                bundle.putString("seek",seektext);
            }
            intent.putExtras(bundle);
            intent.setAction("showseek");
            getActivity().sendBroadcast(intent);
        }
    };


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
}
