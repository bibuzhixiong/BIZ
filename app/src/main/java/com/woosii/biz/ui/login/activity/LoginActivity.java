package com.woosii.biz.ui.login.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.woosii.biz.R;
import com.woosii.biz.base.BaseActivity;
import com.woosii.biz.base.bean.json.RegisterInfoBean;
import com.woosii.biz.ui.login.contract.LoginContract;
import com.woosii.biz.ui.login.presenter.LoginPresenter;
import com.woosii.biz.utils.CheckUtils;
import com.woosii.biz.utils.CountDownTimerUtil;
import com.woosii.biz.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import common.dialog.LoadingDialog;

/**
 * Created by Administrator on 2017/9/22.
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View, View.OnClickListener {

    @Bind(R.id.img_close)
    ImageView imgClose;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.tv_code)
    TextView tvCode;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.bt_sure)
    Button btSure;

    private LoadingDialog mLoadingDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initToolBar() {
//        StatusBarCompat.setStatusBarColor(LoginActivity.this, Color.WHITE,125);
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.bt_sure, R.id.img_close,R.id.tv_code})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_close:

                break;
            case R.id.tv_code:
                if(etPhone.getText().toString().trim().equals("")){
                    ToastUtil.showCenterShortToast("请输入手机号码");
                    return;
                }
                if (!CheckUtils.isMobileNO(etPhone.getText().toString().trim())) {
                    ToastUtil.showCenterShortToast("请输入正确的手机号码");
                    return;
                }
                tvCode.setText("获取验证码中");

                Map<String, String> map = new HashMap<>();
                map.put("phone", etPhone.getText().toString().trim());
                mPresenter.getRegisterCode(map);
                break;
            case R.id.bt_sure:
                String phone=etPhone.getText().toString().trim();
                if(phone.equals("")){
                    ToastUtil.showCenterShortToast("请输入手机号码");
                    return;
                }
                if (!CheckUtils.isMobileNO(phone)) {
                    ToastUtil.showCenterShortToast("请输入正确的手机号码");
                    return;
                }
                if(etCode.getText().toString().trim().equals("")){
                    ToastUtil.showCenterShortToast("请输入验证码");
                    return;
                }
                if(etPassword.getText().toString().trim().length()<6){
                    ToastUtil.showCenterShortToast("密码必须大于6位数");
                    return;
                }
                Map<String, String> map1 = new HashMap<>();
                map1.put("phone", phone);
                map1.put("vcode", CheckUtils.md5(etCode.getText().toString().trim()));
                map1.put("addtime", System.currentTimeMillis()/1000+"");
                map1.put("password", CheckUtils.md5(etPassword.getText().toString().trim()));
                mPresenter.register(map1);

                break;
        }
    }

    @Override
    public void getRegisterCodeSuccess(RegisterInfoBean info) {
        CountDownTimerUtil mCountDownTimerUtils = new CountDownTimerUtil(tvCode, 60000, 1000);
        mCountDownTimerUtils.start();
        ToastUtil.showShortToast(info.getMessage());
    }

    @Override
    public void registerSuccess(RegisterInfoBean model) {
        ToastUtil.showCenterShortToast(model.getMessage());
    }

    @Override
    public void loadFail(String msg) {
        ToastUtil.showCenterShortToast(msg);
    }

    @Override
    public void showLoading() {
        mLoadingDialog = new LoadingDialog(LoginActivity.this, "加载中...", false);
        mLoadingDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.cancelDialog();
        }
    }



}
