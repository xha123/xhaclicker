package com.yaoyao.clicker.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yaoyao.clicker.R;
import com.yaoyao.clicker.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

/**  homeatt 适配器
 * Created by Administrator on 2017/8/3.
 */

public class HomePhotoPagerAdapter extends PagerAdapter{
    List<String> list;
    Context context;

    public HomePhotoPagerAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String imageurl = list.get(position);
        View view =  LayoutInflater.from(context).inflate(R.layout.photoview_item, null);
        SimpleDraweeView img = (SimpleDraweeView) view.findViewById(R.id.photo_photoview);

        img.setTag(imageurl);
        Uri uri = Uri.parse(imageurl);
        DataUtils.getInstans().setImageSrc(img,uri,800,800);
        container.addView(view);//把布局加到Viewpager的控件组里面

        return view;//显示要显示的布局
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
