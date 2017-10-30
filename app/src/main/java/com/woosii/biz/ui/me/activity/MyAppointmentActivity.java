package com.woosii.biz.ui.me.activity;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.woosii.biz.R;
import com.woosii.biz.base.BaseActivity;
import com.woosii.biz.base.BaseToolbar;
import com.woosii.biz.ui.course.fragment.CourseListFragment;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/10/30.
 */

public class MyAppointmentActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    @Bind(R.id.fl_body)
    FrameLayout flBody;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_appointment;
    }

    @Override
    protected void initToolBar() {
        toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(MyAppointmentActivity.this);
            }
        });
    }

    @Override
    protected void initView() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.add(R.id.fl_body, CourseListFragment.getInstance(1), "courselist");
        transaction.commit();
    }


}
