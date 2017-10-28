package com.woosii.biz.ui.login.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.woosii.biz.R;
import com.woosii.biz.base.BaseActivity;
import com.woosii.biz.base.bean.LoginBean;
import com.woosii.biz.base.bean.json.BaseInfoBean;
import com.woosii.biz.base.rx.RxBus;
import com.woosii.biz.common.dialog.LoadingDialog;
import com.woosii.biz.event.UserInfoEvent;
import com.woosii.biz.event.WeChatEvent;
import com.woosii.biz.ui.login.contract.ForgetPasswordContract;
import com.woosii.biz.ui.login.presenter.ForgetPasswordPresenter;
import com.woosii.biz.utils.CheckUtils;
import com.woosii.biz.utils.CountDownTimerUtil;
import com.woosii.biz.utils.SharedPreferencesUtil;
import com.woosii.biz.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/10/21.
 */

public class ForgetPasswordActivity extends BaseActivity<ForgetPasswordPresenter> implements ForgetPasswordContract.View {
    @Bind(R.id.img_close)
    ImageView imgClose;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.tv_code)
    TextView tvCode;
    @Bind(R.id.ll_code)
    LinearLayout llCode;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.bt_sure)
    Button btSure;
    @Bind(R.id.ll_login_layout)
    LinearLayout llLoginLayout;
    @Bind(R.id.imageView)
    ImageView imageView;
    private LoadingDialog mLoadingDialog;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.img_close,R.id.tv_code,R.id.bt_sure})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_close:
                finish_Activity(ForgetPasswordActivity.this);
                break;
            case R.id.tv_code:
                if (etPhone.getText().toString().trim().equals("")) {
                    ToastUtil.showCenterShortToast("请输入手机号码");
                    return;
                }
                if (!CheckUtils.isMobileNO(etPhone.getText().toString().trim())) {
                    ToastUtil.showCenterShortToast("请输入正确的手机号码");
                    return;
                }
//                tvCode.setText("获取验证码中");

                Map<String, String> map = new HashMap<>();
                map.put("phone", etPhone.getText().toString().trim());
                map.put("type", "2");
                mPresenter.getCode(map);
                break;
            case R.id.bt_sure:
                String phone = etPhone.getText().toString().trim();
                if (phone.equals("")) {
                    ToastUtil.showCenterShortToast("请输入手机号码");
                    return;
                }
                if (!CheckUtils.isMobileNO(phone)) {
                    ToastUtil.showCenterShortToast("请输入正确的手机号码");
                    return;
                }

                if (etCode.getText().toString().trim().equals("")) {
                    ToastUtil.showCenterShortToast("请输入验证码");
                    return;
                }
                if (etPassword.getText().toString().trim().equals("")) {
                    ToastUtil.showCenterShortToast("请输入密码");
                    return;
                }
                if (etPassword.getText().toString().trim().length() < 6) {
                    ToastUtil.showCenterShortToast("密码必须大于6位数");
                    return;
                }
                Map<String, String> map1 = new HashMap<>();
                map1.put("phone", etPhone.getText().toString().trim());
                map1.put("vcode",CheckUtils.md5(etCode.getText().toString().trim()));
                map1.put("password", CheckUtils.md5(etPassword.getText().toString().trim()));
                mPresenter.forgetPassword(map1);


                break;

        }
    }


    @Override
    public void getCodeSuccess(BaseInfoBean model) {
        CountDownTimerUtil mCountDownTimerUtils = new CountDownTimerUtil(tvCode, 60000, 1000);
        mCountDownTimerUtils.start();
        ToastUtil.showShortToast(model.getMessage());
    }

    @Override
    public void forgetPassworddSuccess(LoginBean model) {
    if(model.getCode()==1){
        SharedPreferencesUtil.putValue(ForgetPasswordActivity.this, SharedPreferencesUtil.USER_ID,model.getUser_id());
        SharedPreferencesUtil.putValue(ForgetPasswordActivity.this, SharedPreferencesUtil.TOKEN,model.getToken());
        //杀死登录界面
        RxBus.$().postEvent(new WeChatEvent("-1"));
        //个人中心获取信息
        RxBus.$().postEvent(new UserInfoEvent(new LoginBean(0,"",model.getToken(),model.getUser_id())));
        finish_Activity(ForgetPasswordActivity.this);
    }else{
        ToastUtil.showShortToast(model.getMessage());
    }

    }

    @Override
    public void loadFail(String msg) {
        ToastUtil.showCenterShortToast(msg);
    }


    @Override
    public void showLoading() {
        mLoadingDialog = new LoadingDialog(ForgetPasswordActivity.this, "加载中...", false);
        mLoadingDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.cancelDialog();
        }
    }
}
