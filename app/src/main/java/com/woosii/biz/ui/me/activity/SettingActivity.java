package com.woosii.biz.ui.me.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.woosii.biz.R;
import com.woosii.biz.base.BaseActivity;
import com.woosii.biz.base.BaseToolbar;
import com.woosii.biz.base.rx.RxBus;
import com.woosii.biz.event.ExitAccountEvent;
import com.woosii.biz.utils.GlideCatchUtil;
import com.woosii.biz.utils.SharedPreferencesUtil;
import com.woosii.biz.utils.ToastUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/10/23.
 */

public class SettingActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    @Bind(R.id.ll_update_password)
    LinearLayout llUpdatePassword;
    @Bind(R.id.ll_about_us)
    LinearLayout llAboutUs;
    @Bind(R.id.tv_clear)
    TextView tvClear;
    @Bind(R.id.ll_sex)
    LinearLayout llSex;
    @Bind(R.id.ll_clear)
    LinearLayout llClear;
    @Bind(R.id.tv_section)
    TextView tvSection;
    @Bind(R.id.ll_section)
    LinearLayout llSection;
    @Bind(R.id.ll_exit)
    LinearLayout llExit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initToolBar() {
        toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(SettingActivity.this);
            }
        });

    }

    @Override
    protected void initView() {
        tvClear.setText(GlideCatchUtil.getInstance().getCacheSize());

    }
    @OnClick({R.id.ll_update_password,R.id.ll_about_us,R.id.ll_clear,R.id.ll_section,R.id.ll_exit})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_update_password:
                startActivity(UpdatePasswordActivity.class);
                break;
            case R.id.ll_about_us:
            startActivity(AboutUsActivity.class);
                break;
            case R.id.ll_clear:
                if (GlideCatchUtil.getInstance().clearCacheDiskSelf()) {
                    ToastUtil.showShortToast("清除成功");
                    tvClear.setText("0");

                } else {
                    ToastUtil.showShortToast("清除失败");
                }
                break;
            case R.id.ll_section:

                break;
            case R.id.ll_exit:
                SharedPreferencesUtil.removeAll(SettingActivity.this);
                RxBus.$().postEvent(new ExitAccountEvent());
                finish_Activity(SettingActivity.this);
                break;

        }
    }


}
