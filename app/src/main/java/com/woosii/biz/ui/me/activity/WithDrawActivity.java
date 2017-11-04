package com.woosii.biz.ui.me.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.woosii.biz.AppConstant;
import com.woosii.biz.R;
import com.woosii.biz.base.BaseActivity;
import com.woosii.biz.base.BaseToolbar;
import com.woosii.biz.base.bean.json.BaseInfoBean;
import com.woosii.biz.base.bean.json.WechatBean;
import com.woosii.biz.base.rx.RxBus;
import com.woosii.biz.common.dialog.LoadingDialog;
import com.woosii.biz.event.BalanceEvent;
import com.woosii.biz.event.WeChatEvent;
import com.woosii.biz.ui.me.contract.WithDrawContract;
import com.woosii.biz.ui.me.presenter.WithDrawPresenter;
import com.woosii.biz.utils.SharedPreferencesUtil;
import com.woosii.biz.utils.ToastUtil;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/11/3.
 */

public class WithDrawActivity extends BaseActivity<WithDrawPresenter> implements WithDrawContract.View {
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    @Bind(R.id.tv_balance_money)
    TextView tvBalanceMoney;
    @Bind(R.id.et_input_number)
    EditText etInputNumber;
    @Bind(R.id.tv_roll_out_now)
    TextView tvRollOutNow;

    private String cashout_amt;
    private IWXAPI api;
    private LoadingDialog mLoadingDialog;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdraw;
    }

    @Override
    protected void initToolBar() {
        toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(WithDrawActivity.this);
            }
        });
    }

    @Override
    protected void initView() {
        Bundle bundle=getIntent().getExtras();
        cashout_amt= bundle.getString("money");
        tvBalanceMoney.setText(cashout_amt);
        etInputNumber.addTextChangedListener(new TextWatcher() {
            /**
             *  这里的charSequence表示改变之前的内容，通常start和count组合，
             *  可以在charSequence中读取本次改变字段中被改变的内容。而after表示改变后新的内容的数量。
             */
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            /**
             *这里的charSequence表示改变之后的内容，通常start和count组合，
             * 可以在charSequence中读取本次改变字段中新的内容。而before表示被改变的内容的数量。
             */
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (!TextUtils.isEmpty(charSequence)){
                    tvRollOutNow.setEnabled(true);
                    tvRollOutNow.setBackgroundResource(R.drawable.shape_bluebtn_selector);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)){
                    tvRollOutNow.setEnabled(false);
                    tvRollOutNow.setBackgroundResource(R.color.gray);
                }
            }
        });

        tvRollOutNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Double money=Double.parseDouble(cashout_amt);
                    Double jianmoney=  Double.parseDouble(etInputNumber.getText().toString().trim());
                    if(jianmoney<1){
                        ToastUtil.showShortToast("至少提现1元");
                        return;
                    }
                    if(jianmoney<=money){
                        loginWX();
                    }else{
                        ToastUtil.showShortToast("超出可提现金额！");
                    }

                }catch(Exception e){

                }
            }
        });
        event();
    }

    public void loginWX() {
//        ToastUtil.showShortToast("jinlail ");
        //注册到微信
        api = WXAPIFactory.createWXAPI(this, AppConstant.WEIXIN_APP_ID, true);
        api.registerApp(AppConstant.WEIXIN_APP_ID);

//        登录过程
//        isWXLogin = true;

        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo";
        api.sendReq(req);
    }
    private Subscription subscription;
    private void event() {
        subscription = RxBus.$().toObservable(WeChatEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WeChatEvent>() {
                    @Override
                    public void call(WeChatEvent event) {
                        if (event.getCode().equals("-1")) {
                            finish_Activity(WithDrawActivity.this);
                        } else {
                            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                                    + AppConstant.WEIXIN_APP_ID
                                    + "&secret="
                                    + AppConstant.APP_SECRET
                                    + "&code="
                                    + event.getCode()
                                    + "&grant_type=authorization_code";
                            mPresenter.loadWeChatData(url);
                        }

                    }
                });
    }

    @Override
    public void loadWeChatData(WechatBean wechatBean) {
        Map<String,String> map=new HashMap<>();
        map.put("user_id", SharedPreferencesUtil.getValue(WithDrawActivity.this,SharedPreferencesUtil.USER_ID,"")+"");
        map.put("money",etInputNumber.getText().toString().trim());
        map.put("openid",wechatBean.getOpenid());
        mPresenter.withDraw(map);
    }

    @Override
    public void withDrawSuccess(BaseInfoBean model) {
        ToastUtil.showShortToast(model.getMessage());

        if(model.getCode()==0){
            double nowBalance=Double.parseDouble(cashout_amt)-Double.parseDouble(etInputNumber.getText().toString().trim());
            DecimalFormat    df   = new DecimalFormat("0.00");
            cashout_amt=df.format(nowBalance)+"";
            RxBus.$().postEvent(new BalanceEvent(cashout_amt+""));
            finish_Activity(WithDrawActivity.this);
        }

    }

    @Override
    public void loadFail(String msg) {
ToastUtil.showShortToast(msg);
    }

    @Override
    public void showLoading() {
        mLoadingDialog = new LoadingDialog(WithDrawActivity.this, "加载中...", false);
        mLoadingDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.cancelDialog();
        }
    }
}
