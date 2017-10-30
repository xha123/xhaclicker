package com.yaoyao.clicker.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.WindowManager;

import static com.yaoyao.clicker.app.Myapp.context;

/**
 * Created by Administrator on 2017/8/3.
 */

public class CustomViewpager extends ViewPager {
    public CustomViewpager(Context context) {
        super(context);
    }

    public CustomViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

//        int height = 0;
//        for (int i = 0; i < getChildCount(); i++) {
//            View child = getChildAt(i);
//            child.measure(widthMeasureSpec,
//                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//            int h = child.getMeasuredHeight();
//            if (h > height)
//                height = h;
//        }
//
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
//                MeasureSpec.EXACTLY);

        WindowManager windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        int widthview = windowManager.getDefaultDisplay().getWidth();
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(widthview,MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
