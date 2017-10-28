package com.woosii.biz.ui.me.activity;

import android.view.View;

import com.woosii.biz.R;
import com.woosii.biz.base.BaseActivity;
import com.woosii.biz.base.BaseToolbar;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/10/23.
 */

public class AboutUsActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initToolBar() {
        toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(AboutUsActivity.this);
            }
        });
    }

    @Override
    protected void initView() {

    }

}
