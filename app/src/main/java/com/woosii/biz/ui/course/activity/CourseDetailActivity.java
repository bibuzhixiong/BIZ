package com.woosii.biz.ui.course.activity;

import android.view.View;
import android.widget.LinearLayout;

import com.woosii.biz.R;
import com.woosii.biz.base.BaseActivity;
import com.woosii.biz.base.BaseToolbar;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/10/9.
 */

public class CourseDetailActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    @Bind(R.id.ll_ask)
    LinearLayout llAsk;
    @Bind(R.id.ll_enter_now)
    LinearLayout llEnterNow;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_course_deatil;
    }

    @Override
    protected void initToolBar() {
        toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(CourseDetailActivity.this);
            }
        });
    }

    @Override
    protected void initView() {

    }


}
