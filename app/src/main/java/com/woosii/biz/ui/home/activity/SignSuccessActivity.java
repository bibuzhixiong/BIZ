package com.woosii.biz.ui.home.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.woosii.biz.R;
import com.woosii.biz.base.BaseActivity;
import com.woosii.biz.base.BaseToolbar;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/10/21.
 */

public class SignSuccessActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    @Bind(R.id.tv_sign_num)
    TextView tvSignNum;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_success;
    }

    @Override
    protected void initToolBar() {
        toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(SignSuccessActivity.this);
            }
        });
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        String num = bundle.getString("NUM");
        tvSignNum.setText(num+"次");
    }



}
