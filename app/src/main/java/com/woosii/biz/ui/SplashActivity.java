package com.woosii.biz.ui;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.woosii.biz.R;
import com.woosii.biz.base.BaseActivity;

/**
 * Created by Administrator on 2017/11/9.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void initView() {
        View rootview = View.inflate(this, R.layout.activity_splash, null);
//        Log.i(ConfigInfo.TAG,"System.currentTimeMillis():"+System.currentTimeMillis());
        setContentView(rootview);

        //初始化渐变动画
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.activity_alpha);
        //设置动画监听器
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //当监听到动画结束时，开始跳转到MainActivity中去
             startActivity(MainActivity.class);
            }
        });

        //开始播放动画
        rootview.startAnimation(animation);
    }

}
