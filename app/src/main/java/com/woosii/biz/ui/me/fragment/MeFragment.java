package com.woosii.biz.ui.me.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.woosii.biz.R;
import com.woosii.biz.base.BaseFragment;
import com.woosii.biz.ui.login.activity.LoginActivity;
import com.woosii.biz.ui.me.activity.CourseCodeActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/9/25.
 */

public class MeFragment extends BaseFragment implements View.OnClickListener{
    @Bind(R.id.ll_my_login)
    LinearLayout llMyLogin;
    @Bind(R.id.ll_code)
    LinearLayout llCode;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView() {
        llMyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(LoginActivity.class);
                Intent intent = new Intent();
                intent.setClass(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
//                activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                activity.overridePendingTransition(R.anim.my_push_up_in, R.anim.my_push_down_in);
                llMyLogin.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        activity.overridePendingTransition(R.anim.my_push_up_in, R.anim.my_push_down_in);
                    }
                }, 1000);
//                startActivity(LoginActivity.class);
            }
        });
    }
    @OnClick({R.id.ll_code})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_code:
              startActivity(CourseCodeActivity.class);

                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
