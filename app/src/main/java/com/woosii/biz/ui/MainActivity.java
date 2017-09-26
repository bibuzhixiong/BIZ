package com.woosii.biz.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.woosii.biz.R;
import com.woosii.biz.base.BaseActivity;
import com.woosii.biz.base.bean.MainTabEntity;
import com.woosii.biz.ui.course.fragment.CourseFragment;
import com.woosii.biz.ui.home.fragment.HomeFragment;
import com.woosii.biz.ui.me.fragment.MeFragment;
import com.woosii.biz.ui.question.fragmet.QuestionFragment;

import java.util.ArrayList;

import butterknife.Bind;

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
        
    }

    @Override
    protected void initView() {
        initTab();

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



}
