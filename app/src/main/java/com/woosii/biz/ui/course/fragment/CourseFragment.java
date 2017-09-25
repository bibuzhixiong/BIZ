package com.woosii.biz.ui.course.fragment;

import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.woosii.biz.R;
import com.woosii.biz.base.BaseFragment;
import com.woosii.biz.base.MyOrderTabEntity;
import com.woosii.biz.ui.me.fragment.MeFragment;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/9/25.
 */

public class CourseFragment  extends BaseFragment{
    @Bind(R.id.commontablayout)
    CommonTabLayout commonTabLayout;
    @Bind(R.id.fl_change)
    FrameLayout fl_change;

    private String[] mTitles = {"预约课程", "已预约", "课程回放"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_course;
    }

    @Override
    protected void initView() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new MyOrderTabEntity(mTitles[i]));
        }
        MeFragment meFragment=new MeFragment();

        mFragments.add(meFragment);

//        mFragments.add(meFragment);
        commonTabLayout.setTabData(mTabEntities, getActivity(),R.id.fl_change, mFragments);
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }
}
