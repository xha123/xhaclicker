package com.yaoyao.clicker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yaoyao.clicker.R;
import com.yaoyao.clicker.activity.upcra.AddnewsActivity;
import com.yaoyao.clicker.app.AppManager;
import com.yaoyao.clicker.base.BaseActivity;
import com.yaoyao.clicker.fragment.HomeFragment;
import com.yaoyao.clicker.fragment.LikeFragment;
import com.yaoyao.clicker.fragment.MineFragment;
import com.yaoyao.clicker.fragment.SeekFragment;

import java.util.ArrayList;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;


public class MainActivity extends BaseActivity implements
        View.OnClickListener{
    RelativeLayout show_rl,home_rl1,home_rl2,home_rl3,home_rl4,home_rl5;
    LinearLayout show_ll;
    ImageView home_iv,seek_iv,camera_iv,like_iv,mine_iv,xin_iv,hongxin_iv;
    boolean home,seek,like,mine;
    boolean isopen;
    int transint ;

    FragmentManager fragmentmanager;
    HomeFragment homeFragment;
    SeekFragment seekFragment;
    LikeFragment likeFragment;
    MineFragment mineFragment;
    private Fragment mCurrentFragment;
    @Override
    public void inidata() {
        home = true;
    }

    @Override
    public void setCon() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void iniview() {
        show_rl = (RelativeLayout) findViewById(R.id.main_show_rl);
        show_ll = (LinearLayout) findViewById(R.id.main_show_ll);
        home_iv = (ImageView) findViewById(R.id.main_home_iv);
        seek_iv = (ImageView) findViewById(R.id.main_seek_iv);
        camera_iv = (ImageView) findViewById(R.id.main_camera_iv);
        like_iv = (ImageView) findViewById(R.id.main_like_iv);
        mine_iv = (ImageView) findViewById(R.id.main_mine_iv);
        home_rl1 = (RelativeLayout) findViewById(R.id.main_home_rl1);
        home_rl2 = (RelativeLayout) findViewById(R.id.main_home_rl2);
        home_rl3 = (RelativeLayout) findViewById(R.id.main_home_rl3);
        home_rl4 = (RelativeLayout) findViewById(R.id.main_home_rl4);
        home_rl5 = (RelativeLayout) findViewById(R.id.main_home_rl5);
        xin_iv = (ImageView) findViewById(R.id.main_xin_iv);
        hongxin_iv = (ImageView) findViewById(R.id.main_xin_iv1);
    }

    @Override
    public void setview() {
        // 恢复fragment;
        retrieveFragment();
        setfrag();

    }

    @Override
    public void setResume() {

        transint= show_ll.getWidth();


    }

    @Override
    public void setOnclick() {
//        home_iv.setOnClickListener(this);
//        seek_iv.setOnClickListener(this);
//        like_iv.setOnClickListener(this);
//        mine_iv.setOnClickListener(this);
//        camera_iv.setOnClickListener(this);
        home_rl1.setOnClickListener(this);
        home_rl2.setOnClickListener(this);
        home_rl3.setOnClickListener(this);
        home_rl4.setOnClickListener(this);
        home_rl5.setOnClickListener(this);

        show_rl.setOnClickListener(this);
    }


    private void setfrag() {
        if (homeFragment==null){

            homeFragment = new HomeFragment();
        }
        // 切换Fragment
        switchfragment(homeFragment);
    }

    private void switchfragment(Fragment target) {
        // add show hide的方式
        if (mCurrentFragment==target) return;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (mCurrentFragment!=null){
            transaction.hide(mCurrentFragment);
        }
        if (target.isAdded()){
            // 如果已经添加到FragmentManager里面，就展示
            transaction.show(target);
        }else {
            // 为了方便找到Fragment，我们是可以设置Tag
            String tag;
            // 把类名作为tag
            tag = target.getClass().getName();

            // 添加Fragment并设置Tag
            transaction.add(R.id.main_frame,target,tag);
        }
        transaction.commit();
        mCurrentFragment=target;
    }

    // 恢复因为重启造成的Fragmentmanager里面恢复的Fragment
    private void retrieveFragment() {
        FragmentManager manager = getSupportFragmentManager();
        homeFragment = (HomeFragment) manager.findFragmentByTag(HomeFragment.class.getName());
        seekFragment = (SeekFragment) manager.findFragmentByTag(SeekFragment.class.getName());
        likeFragment = (LikeFragment) manager.findFragmentByTag(LikeFragment.class.getName());
        mineFragment = (MineFragment) manager.findFragmentByTag(MineFragment.class.getName());
    }

    private static final int REQUEST_CODE = 1024;
    private static final int COMPRESS_REQUEST_CODE = 2048;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_home_rl1:
                if (!isopen){
                    return;
                }
                home = true;
                seek = false;
                like = false;
                mine = false;
                if (homeFragment==null){
                    homeFragment = new HomeFragment();
                }
                switchfragment(homeFragment);
                changeshow();
                break;
            case R.id.main_home_rl2:
                if (!isopen){
                    return;
                }
                home = false;
                seek = true;
                like = false;
                mine = false;
                if (seekFragment==null){
                    seekFragment = new SeekFragment();
                }
                switchfragment(seekFragment);
                changeshow();
                break;
            case R.id.main_home_rl3:
                if (!isopen){
                    return;
                }
//                openMulti();
                PhotoPickerIntent intent = new PhotoPickerIntent(MainActivity.this);
                intent.setPhotoCount(4);
                intent.setShowCamera(true);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.main_home_rl4:
                if (!isopen){
                    return;
                }
                home = false;
                seek = false;
                like = true;
                mine = false;
                if (likeFragment==null){
                    likeFragment = new LikeFragment();
                }
                switchfragment(likeFragment);
                changeshow();
                break;
            case R.id.main_home_rl5:
                if (!isopen){
                    return;
                }
                home = false;
                seek = false;
                like = false;
                mine = true;
                if (mineFragment==null){
                    mineFragment = new MineFragment();
                }
                switchfragment(mineFragment);
                changeshow();
                break;
            case R.id.main_show_rl:
                transint= show_ll.getWidth();
                ScaleAnimation sato0 = new ScaleAnimation(1,0,1,1,
                        1,0.5f,1,0.5f);

                final ScaleAnimation sato1 = new ScaleAnimation(0,1,1,1,
                        1,0.5f,1,0.5f);
                sato0.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        //作A动画结束后操作
                        if (xin_iv.getVisibility() == View.VISIBLE){
                            xin_iv.setAnimation(null);
                            xin_iv.setVisibility(View.GONE);
                            hongxin_iv.setVisibility(View.VISIBLE);
                            sato1.setDuration(150);
                            hongxin_iv.startAnimation(sato1);
                        }else {
                            hongxin_iv.setAnimation(null);
                            hongxin_iv.setVisibility(View.GONE);
                            xin_iv.setVisibility(View.VISIBLE);
                            sato1.setDuration(150);
                            xin_iv.startAnimation(sato1);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                if (isopen){
                    isopen = false;
                    TranslateAnimation ta = new TranslateAnimation(0,-transint,0,0);
                    ta.setDuration(300);
                    ta.setFillAfter(true);
                    show_ll.startAnimation(ta);
                    sato0.setDuration(150);
                    hongxin_iv.startAnimation(sato0);
                }else {
                    isopen = true;
                    show_ll.setVisibility(View.VISIBLE);
                    TranslateAnimation ta = new TranslateAnimation(-transint,0,0,0);
                    ta.setDuration(300);
                    ta.setFillAfter(true);
                    show_ll.startAnimation(ta);
                    sato0.setDuration(150);
                    xin_iv.startAnimation(sato0);
                }
                break;
        }
    }


    private void changeshow(){
        if (home){
            home_iv.setImageResource(R.mipmap.home_hei);
        }else {
            home_iv.setImageResource(R.mipmap.home);
        }
        if (seek){
            seek_iv.setImageResource(R.mipmap.seek_hei);
        }else {
            seek_iv.setImageResource(R.mipmap.seek);
        }
        if (like){
            like_iv.setImageResource(R.mipmap.like_hei);
        }else {
            like_iv.setImageResource(R.mipmap.xin);
        }
        if (mine){
            mine_iv.setImageResource(R.mipmap.mine_hei);
        }else {
            mine_iv.setImageResource(R.mipmap.person);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                Log.e(TAG, "onActivityResult: 111" );
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("image",photos);
                AppManager.getAppManager().ToOtherActivity(AddnewsActivity.class,bundle);
            }
        }
    }



    //退出
    private long clickTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis()- clickTime) < 1000){
            AppManager.getAppManager().AppExit();
        }else {
            BaseActivity.showtoast(MainActivity.this,"再点一次退出");
            clickTime = System.currentTimeMillis();
        }
    }


}
