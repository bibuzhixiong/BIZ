package com.woosii.biz.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.woosii.biz.R;
import com.woosii.biz.base.ActivityManager;
import com.woosii.biz.base.BaseActivity;
import com.woosii.biz.base.bean.MainTabEntity;
import com.woosii.biz.base.rx.RxBus;
import com.woosii.biz.event.UpdateJPushEvent;
import com.woosii.biz.jpush.ExampleUtil;
import com.woosii.biz.ui.course.fragment.CourseFragment;
import com.woosii.biz.ui.home.fragment.HomeFragment;
import com.woosii.biz.ui.me.fragment.MeFragment;
import com.woosii.biz.ui.question.fragmet.QuestionFragment;
import com.woosii.biz.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import butterknife.Bind;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/9/23.
 */

public class MainActivity extends BaseActivity {
    @Bind(R.id.tab_layout)
    CommonTabLayout tabLayout;


    private String[] mTitles = {"首页", "课程","问答","我的"};
    private int[] mIconUnselectIds = {
            R.drawable.tab_btn_home_default,R.drawable.tab_btn_yuyue_default,R.drawable.tab_btn_fenda_default,R.drawable.tab_btn_me_default};
    private int[] mIconSelectIds = {
            R.drawable.tab_btn_home_selected,R.drawable.tab_btn_yuyue_selected,R.drawable.tab_btn_fenda_selected,R.drawable.tab_btn_me_selected};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();


    private HomeFragment homeFragment;
    private CourseFragment courseFragment;
    private QuestionFragment questionFragment;
    private MeFragment meFragment;

    private int currentPosition;
    private Subscription subscription;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initToolBar() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化frament
        initFragment(savedInstanceState);
        tabLayout.measure(0,0);
//        Log.e("TTT",System.currentTimeMillis()+"--几位");
    }

    @Override
    protected void initView() {
        initTab();
        //设置极光推送的别名

        mHandler.sendMessage(mHandler.obtainMessage(1001, SharedPreferencesUtil.getValue(MainActivity.this,SharedPreferencesUtil.USER_ID,"")+""));
        event();
    }

    private void event() {
        subscription = RxBus.$().toObservable(Object.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object event) {
                        if (event instanceof UpdateJPushEvent) {
                            mHandler.sendMessage(mHandler.obtainMessage(1001, SharedPreferencesUtil.getValue(MainActivity.this,SharedPreferencesUtil.USER_ID,"")+""));
                        }

                    }
                });
    }
    /**
     * 初始化tab
     */
    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new MainTabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tabLayout.setTabData(mTabEntities);
        //点击监听
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                tabLayout.setCurrentTab(position);
                SwitchTo(position);
            }
            @Override
            public void onTabReselect(int position) {

            }
        });
    }
    /**
     * 初始化碎片
     */
    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (savedInstanceState != null) {
            homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("homeFragment");
            courseFragment = (CourseFragment) getSupportFragmentManager().findFragmentByTag("courseFragment");
            meFragment = (MeFragment) getSupportFragmentManager().findFragmentByTag("meFragment");
            questionFragment= (QuestionFragment) getSupportFragmentManager().findFragmentByTag("questionFragment");
            currentTabPosition = savedInstanceState.getInt("HOME_CURRENT_TAB_POSITION");
        } else {
            homeFragment = new HomeFragment();
            courseFragment = new CourseFragment();
            questionFragment=new QuestionFragment();
            meFragment = new MeFragment();
            transaction.add(R.id.fl_body, homeFragment, "homeFragment");
            transaction.add(R.id.fl_body, courseFragment, "courseFragment");
            transaction.add(R.id.fl_body, questionFragment, "questionFragment");
            transaction.add(R.id.fl_body, meFragment, "meFragment");
        }
        transaction.commit();
        SwitchTo(currentTabPosition);
        tabLayout.setCurrentTab(currentTabPosition);
    }
    /**
     * 切换
     */
    private void SwitchTo(int position) {
        currentPosition=position;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            //首页
            case 0:
                transaction.hide(courseFragment);
                transaction.hide(questionFragment);
                transaction.hide(meFragment);
                transaction.show(homeFragment);
                transaction.commitAllowingStateLoss();
                break;
            //课程
            case 1:
                transaction.hide(homeFragment);
                transaction.hide(meFragment);
                transaction.hide(questionFragment);
                transaction.show(courseFragment);
                transaction.commitAllowingStateLoss();
                break;
            //问答
            case 2:
                transaction.hide(homeFragment);
                transaction.hide(courseFragment);
                transaction.hide(meFragment);
                transaction.show(questionFragment);
                transaction.commitAllowingStateLoss();
                break;
            //我的
            case 3:
                transaction.hide(homeFragment);
                transaction.hide(courseFragment);
                transaction.hide(questionFragment);
                transaction.show(meFragment);
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
        }
    }
    //JPush设置别名
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1001:
                    Set<String> s=new HashSet<>();

                    String user_id= SharedPreferencesUtil.getValue(MainActivity.this,SharedPreferencesUtil.USER_ID,"")+"";

                    if(user_id.equals("")){
                        JPushInterface.setAliasAndTags(getApplicationContext(), "", s, mAliasCallback);
                    }else{
                        s.add(user_id);
                        JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, s, mAliasCallback);
                    }

                    break;
            }
        }
    };
    //JPush设置别名
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.e("Jpush",logs);
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.e("Jpush",logs);
                    //失败的话，隔60秒再设置别名
                    if (ExampleUtil.isConnected(getApplicationContext())) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(1001, alias), 1000 * 60);
                    } else {
                        Log.e("Jpush",logs);
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
//                    Log.e("Jpush",logs);
            }

        }

    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * 双击返回键退出应用
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                ActivityManager.getActivityMar().exitApp(MainActivity.this);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
