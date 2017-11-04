package com.woosii.biz.ui.course.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.woosii.biz.R;
import com.woosii.biz.base.BaseActivity;
import com.woosii.biz.base.BaseToolbar;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/11/2.
 */

public class PreviewQuestionsCompleteActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    @Bind(R.id.tv_correct)
    TextView tvCorrect;
    @Bind(R.id.tv_error)
    TextView tvError;
    @Bind(R.id.bt_back)
    Button btBack;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_preview_questions_complete;
    }

    @Override
    protected void initToolBar() {
        toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(PreviewQuestionsCompleteActivity.this);
            }
        });
    }

    @Override
    protected void initView() {
        Bundle bundle=getIntent().getExtras();
        String correct=bundle.getString("correct");
        String error=bundle.getString("error");
        tvCorrect.setText(correct);
        tvError.setText(error);

            btBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish_Activity(PreviewQuestionsCompleteActivity.this);
                }
            });
    }


}
