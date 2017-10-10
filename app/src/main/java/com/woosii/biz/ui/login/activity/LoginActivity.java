package com.woosii.biz.ui.login.activity;

import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.woosii.biz.AppConstant;
import com.woosii.biz.R;
import com.woosii.biz.base.BaseActivity;
import com.woosii.biz.base.bean.LoginBean;
import com.woosii.biz.base.bean.json.BaseInfoBean;
import com.woosii.biz.common.dialog.LoadingDialog;
import com.woosii.biz.ui.login.contract.LoginContract;
import com.woosii.biz.ui.login.presenter.LoginPresenter;
import com.woosii.biz.utils.CheckUtils;
import com.woosii.biz.utils.CountDownTimerUtil;
import com.woosii.biz.utils.SharedPreferencesUtil;
import com.woosii.biz.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/9/22.
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View, View.OnClickListener {
    @Bind(R.id.ll_login_layout)
    LinearLayout llLoginLayout;
    @Bind(R.id.img_weixin)
    ImageView imgWeixin;
    @Bind(R.id.ll_weixin)
    LinearLayout llWeixin;
    private Animation mTranslate; // 位移动画
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
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.ll_code)
    LinearLayout llCode;
    @Bind(R.id.ll_register)
    LinearLayout llRegister;
    @Bind(R.id.tv_register)
    TextView tvRegister;
    @Bind(R.id.ll_login)
    LinearLayout llLogin;

    private LoadingDialog mLoadingDialog;

    private boolean isLogin = true;
    private IWXAPI api;

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
//        mTranslate = AnimationUtils.loadAnimation(this, R.anim.my_translate);
//        llLoginLayout.setAnimation(mTranslate);
//        llLoginLayout.setVisibility(View.VISIBLE);

        //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
        api = WXAPIFactory.createWXAPI(LoginActivity.this, AppConstant.WEIXIN_APP_ID, false);
        // 将该app注册到微信
        api.registerApp(AppConstant.WEIXIN_APP_ID);
    }

    @OnClick({R.id.bt_sure, R.id.img_close, R.id.tv_code, R.id.tv_register})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_close:
                if (isLogin == false) {
                    //显示登录界面
                    isLogin = true;
                    tvTitle.setText("欢迎登录");
                    btSure.setText("登录");
                    llCode.setVisibility(View.GONE);
                    llLogin.setVisibility(View.VISIBLE);
                    llRegister.setVisibility(View.GONE);
                } else {
                    finish_Activity(LoginActivity.this);
                }
                break;
            case R.id.tv_register:
                if (isLogin == false) {
                    //显示登录界面
                    isLogin = true;
                    tvTitle.setText("欢迎登录");
                    btSure.setText("登录");
                    llCode.setVisibility(View.GONE);
                    llLogin.setVisibility(View.VISIBLE);
                    llRegister.setVisibility(View.GONE);
                } else {
                    //显示注册界面
                    isLogin = false;
                    tvTitle.setText("欢迎注册");
                    btSure.setText("注册");
                    llCode.setVisibility(View.VISIBLE);
                    llLogin.setVisibility(View.GONE);
                    llRegister.setVisibility(View.VISIBLE);
                }
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
                mPresenter.getRegisterCode(map);
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
                if (isLogin == true) {
                    //登录请求
                    if (etPassword.getText().toString().trim().length() < 6) {
                        ToastUtil.showCenterShortToast("密码必须大于6位数");
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
                    Map<String, String> map2 = new HashMap<>();
                    map2.put("phone", phone);
                    map2.put("password", CheckUtils.md5(etPassword.getText().toString().trim()));
                    mPresenter.loginByPassword(map2);
                } else {
                    //注册请求

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
                    map1.put("phone", phone);
                    map1.put("vcode", CheckUtils.md5(etCode.getText().toString().trim()));
                    map1.put("addtime", System.currentTimeMillis() / 1000 + "");
                    map1.put("password", CheckUtils.md5(etPassword.getText().toString().trim()));
                    mPresenter.register(map1);
                }

                break;
            case R.id.img_weixin:

                break;
        }
    }

    @Override
    public void getRegisterCodeSuccess(BaseInfoBean info) {
        CountDownTimerUtil mCountDownTimerUtils = new CountDownTimerUtil(tvCode, 60000, 1000);
        mCountDownTimerUtils.start();
        ToastUtil.showShortToast(info.getMessage());
    }

    @Override
    public void registerSuccess(BaseInfoBean model) {
        ToastUtil.showCenterShortToast(model.getMessage());
    }

    @Override

    public void loginByPassword(LoginBean model) {
        SharedPreferencesUtil.putValue(LoginActivity.this,SharedPreferencesUtil.USER_ID,model.getUser_id());
        ToastUtil.showShortToast(model.getMessage() + "token:" + model.getToken() + "    user_id:" + model.getUser_id());
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

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(isLogin==false){
            //显示登录界面
            isLogin=true;
            tvTitle.setText("欢迎登录");
            btSure.setText("登录");
            llCode.setVisibility(View.GONE);
            llLogin.setVisibility(View.VISIBLE);
            llRegister.setVisibility(View.GONE);
        }else{
            finish_Activity(LoginActivity.this);
        }
    }*/

    /**
     * 监听Back键按下事件,方法2:
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (isLogin == false) {
                //显示登录界面
                isLogin = true;
                tvTitle.setText("欢迎登录");
                btSure.setText("登录");
                llCode.setVisibility(View.GONE);
                llLogin.setVisibility(View.VISIBLE);
                llRegister.setVisibility(View.GONE);
            } else {
                finish_Activity(LoginActivity.this);
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }


}
