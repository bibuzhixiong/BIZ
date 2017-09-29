package com.woosii.biz.ui.me.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.woosii.biz.R;
import com.woosii.biz.base.BaseFragment;
import com.woosii.biz.ui.login.activity.LoginActivity;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/9/25.
 */

public class MeFragment extends BaseFragment {
    @Bind(R.id.ll_my_login)
    LinearLayout llMyLogin;

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
                intent.setClass(getActivity(),LoginActivity.class);
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


}
