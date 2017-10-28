package com.woosii.biz.ui.me.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.woosii.biz.R;
import com.woosii.biz.base.BaseActivity;
import com.woosii.biz.base.BaseToolbar;
import com.woosii.biz.base.MyOrderTabEntity;
import com.woosii.biz.ui.me.fragment.MyQuestionAnswerFragment;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/10/25.
 */

public class MyQuestionAnswerActivity extends BaseActivity {
    @Bind(R.id.commontablayout)
    CommonTabLayout commonTabLayout;
    @Bind(R.id.vp)
    ViewPager viewPager;
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    private String[] mTitles = {"未回答", "已回答"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private FragmentPagerAdapter mAdapter;
    private int[] mType = {0, 1};

    @Override
    protected int getLayoutId() {
        return R.layout.activtity_my_question_answer;
    }

    @Override
    protected void initToolBar() {
        toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(MyQuestionAnswerActivity.this);
            }
        });
    }

    @Override
    protected void initView() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new MyOrderTabEntity(mTitles[i]));
        }
        for (int type : mType) {
            mFragments.add(MyQuestionAnswerFragment.getInstance(type));
        }

//        mFragments.add(meFragment);
        commonTabLayout.setTabData(mTabEntities);

        mAdapter = new FragmentPagerAdapter(MyQuestionAnswerActivity.this.getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int position) {
             /*   if(detailsYarnFragment!=null&&position==1){
                    detailsYarnFragment.isVisible();
                }*/
                return mFragments.get(position);
            }
        };
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(5);
//        commonTabLayout.setTabData(mTabEntities, getActivity(),R.id.fl_change, mFragments);
        // 对ViewPager设置滑动监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            int lastPosition;

            @Override
            public void onPageSelected(int position) {
                // 页面被选中

                // 修改position
                position = position % mFragments.size();

                // 替换位置
                lastPosition = position;
                commonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //导航栏设置监听
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                commonTabLayout.setCurrentTab(position);
                viewPager.setCurrentItem(position);

            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }


}
