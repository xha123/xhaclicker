package com.yaoyao.clicker.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yaoyao.clicker.R;
import com.yaoyao.clicker.base.BaseActivity;
import com.yaoyao.clicker.view.MyPhotoView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/3.
 */

public class ShowPhotoActivity extends BaseActivity{

    ViewPager viewPager;
    List<String> listimg;
    int pos;//显示的下标

    @Override
    public void inidata() {
        listimg = new ArrayList<>();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            listimg = bundle.getStringArrayList("image");
            pos = bundle.getInt("pos");
        }
    }

    @Override
    public void setCon() {
        setContentView(R.layout.activity_photoshow);
    }

    @Override
    public void iniview() {
        viewPager = (ViewPager) findViewById(R.id.photoshow_viewpager);
    }

    @Override
    public void setview() {
        setviewpager();
    }

        //设置可缩放的图片显示
    private void setviewpager() {
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return listimg.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
//                return super.instantiateItem(container, position);
                String imageurl = listimg.get(position);
                View view =  LayoutInflater.from(ShowPhotoActivity.this).inflate(R.layout.photoshowview_item, null);
//                SimpleDraweeView img = (SimpleDraweeView) view.findViewById(R.id.photo_photoview);
                MyPhotoView img = (MyPhotoView) view.findViewById(R.id.photoshow_myphoto);
                img.setTag(imageurl);
                img.setImageUri(imageurl,900,900);
//                Uri uri = Uri.parse(imageurl);
//                DataUtils.getInstans().setImageSrc(img,uri,img.getWidth(),img.getHeight());
                container.addView(view);//把布局加到Viewpager的控件组里面
                return view;//显示要显示的布局
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
//                super.destroyItem(container, position, object);
                container.removeView((View) object);
            }
        });
        viewPager.setCurrentItem(pos);
    }

    @Override
    public void setResume() {

    }

    @Override
    public void setOnclick() {

    }
}
