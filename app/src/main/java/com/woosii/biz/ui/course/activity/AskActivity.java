package com.woosii.biz.ui.course.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.woosii.biz.AppConstant;
import com.woosii.biz.R;
import com.woosii.biz.base.BaseActivity;
import com.woosii.biz.base.bean.json.AskBean;
import com.woosii.biz.base.bean.json.PayInfoBean;
import com.woosii.biz.base.bean.json.PayReqBean;
import com.woosii.biz.base.rx.RxBus;
import com.woosii.biz.common.dialog.DialogInterface;
import com.woosii.biz.common.dialog.LoadingDialog;
import com.woosii.biz.common.dialog.NormalAlertDialog;
import com.woosii.biz.common.dialog.NormalSelectionDialog;
import com.woosii.biz.event.WeChatPayEvent;
import com.woosii.biz.ui.course.contract.AskContract;
import com.woosii.biz.ui.course.presenter.AskPresenter;
import com.woosii.biz.utils.SharedPreferencesUtil;
import com.woosii.biz.utils.ToastUtil;
import com.woosii.biz.utils.payutil.AuthResult;
import com.woosii.biz.utils.payutil.PayResult;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/10/12.
 */

public class AskActivity extends BaseActivity<AskPresenter> implements AskContract.View {
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    @Bind(R.id.toolbar_leftbutton)
    ImageView toolbarLeftbutton;
    @Bind(R.id.toolbar_rightbutton)
    TextView toolbarRightbutton;
    @Bind(R.id.et_ask)
    EditText etAsk;
    @Bind(R.id.tv_num)
    TextView tvNum;

    private LoadingDialog mLoadingDialog;
    private int pay_type=1;//1是微信，2是支付宝

    private String teacher_id;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        ToastUtil.showShortToast("支付成功，您的问题已发布");
                        etAsk.setText("");
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(AskActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(AskActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(AskActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_ask;
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void initView() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/record/20171017170607.mp3");

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("audio", file.getName(), requestFile);
//        Map<String, RequestBody> map = new HashMap<>();
        RequestBody uidBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf("3"));
        RequestBody pidBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf("73"));
//            RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//            map.put("Filedata", fileBody);
        mPresenter.changeHeadSuccess(uidBody,pidBody, body);
        Bundle bundle = getIntent().getExtras();

        teacher_id= bundle.getString("teacher_id");
        etAsk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int a=etAsk.getText().toString().length();
                tvNum.setText(120-a+"");

            }
        });
        event();
    }
    private Subscription subscription;
    private void event() {
        subscription = RxBus.$().toObservable(WeChatPayEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WeChatPayEvent>() {
                    @Override
                    public void call(WeChatPayEvent event) {
                       ToastUtil.showShortToast("支付成功，您的问题已发布");
                        etAsk.setText("");
                    }
                });
    }
    @OnClick({R.id.toolbar_rightbutton,R.id.toolbar_leftbutton})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.toolbar_leftbutton:
                finish_Activity(AskActivity.this);
                break;
            case R.id.toolbar_rightbutton:
                if (etAsk.getText().toString().trim().length() < 1) {
                    ToastUtil.showShortToast("请输入问题");
                    return;
                }


                List<String> headData = new ArrayList<>();
                headData.add("微信支付");
                headData.add("支付宝支付");
                NormalSelectionDialog chaHeaddialog = new NormalSelectionDialog.Builder(AskActivity.this)
                        .setItemHeight(45)
                        .setItemTextColor(R.color.blue)
                        .setItemTextSize(14)
                        .setItemWidth(0.7f)
                        .setCancleButtonText("取消")
                        .setOnItemListener(new DialogInterface.OnItemClickListener<NormalSelectionDialog>() {
                            @Override
                            public void onItemClick(NormalSelectionDialog dialog, View button, int position) {
                                switch (position) {
                                    case 0:
//                                        ToastUtil.showShortToast("微信");
                                        pay_type=1;
                                        askByUser();
                                        dialog.dismiss();
                                        break;
                                    case 1:
//                                        ToastUtil.showShortToast("支付宝");
                                        pay_type=2;
                                        askByUser();
                                        dialog.dismiss();
                                        break;
                                }

                            }
                        }).setTouchOutside(true)
                        .build();

                chaHeaddialog.setData(headData);
                chaHeaddialog.show();

                break;
        }

    }
   public void askByUser(){
       Map<String, String> map = new HashMap<>();
       map.put("user_id",SharedPreferencesUtil.getValue(AskActivity.this,SharedPreferencesUtil.USER_ID,"")+"");
       map.put("amount", "0.01");
       map.put("teacher_id", teacher_id);
       map.put("problem",etAsk.getText().toString().toString());
       mPresenter.askByUser(map);
   }
    //调起微信支付
    private void toWeiXinPay(PayReq req){
        IWXAPI api = WXAPIFactory.createWXAPI(this, AppConstant.WEIXIN_APP_ID);
        api.registerApp(AppConstant.WEIXIN_APP_ID);
        if(!api.isWXAppInstalled())
        {
            NormalAlertDialog dialog = new NormalAlertDialog.Builder(this)
                    .setBoolTitle(false)
                    .setContentText("检测到手机没有安转微信")
                    .setSingleModel(true)
                    .setSingleText("确认")
                    .setHeight(0.23f)
                    .setWidth(0.65f)
                    .setSingleListener(new DialogInterface.OnSingleClickListener<NormalAlertDialog>() {
                        @Override
                        public void clickSingleButton(NormalAlertDialog dialog, View view) {
                            dialog.dismiss();
                        }
                    }).setTouchOutside(false)
                    .build();
            dialog.show();
            return;
        }
        if(!api.isWXAppSupportAPI())
        {
            new NormalAlertDialog.Builder(this)
                    .setBoolTitle(false)
                    .setContentText("当前版本不支持支付功能")
                    .setSingleModel(true)
                    .setSingleText("确认")
                    .setHeight(0.23f)
                    .setWidth(0.65f)
                    .setSingleListener(new DialogInterface.OnSingleClickListener<NormalAlertDialog>() {
                        @Override
                        public void clickSingleButton(NormalAlertDialog dialog, View view) {
                            dialog.dismiss();
                        }
                    }).setTouchOutside(false)
                    .build().show();
            return;
        }

        api.sendReq(req);
    }

    @Override
    public void getPayOrderInfoSuccess(final PayInfoBean model) {

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(AskActivity.this);
                Map<String, String> result = alipay.payV2(model.getContent(), true);


                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();


    }

    @Override
    public void getWeChatOrderInfoSuccess(PayReqBean bean) {
        PayReq req = new PayReq();
        req.appId			= bean.getAppid();
        req.partnerId		= bean.getPartnerid();
        req.prepayId		= bean.getPrepayid();
        req.nonceStr		= bean.getNoncestr();
        req.timeStamp		= bean.getTimestamp();
        req.packageValue	= "Sign=WXPay";
        req.sign			= bean.getSign();
        req.extData			= "app data"; // optional

        toWeiXinPay(req);
    }

    @Override
    public void askByUserSuccess(AskBean model) {
        if(model.getCode()==0){
            ToastUtil.showShortToast("服务器繁忙");
        }else{
            if(pay_type==1){
                Map<String, String> map1 = new HashMap<>();
                map1.put("user_id", SharedPreferencesUtil.getValue(AskActivity.this,SharedPreferencesUtil.USER_ID,"")+"");
                map1.put("money_order", "0.01");
                map1.put("subject", "沃噻提问支付");
                map1.put("goods_code", model.getGoods_code());
                mPresenter.getWeChatOrderInfo(map1);
            }else{

                    Map<String, String> map1 = new HashMap<>();
                    map1.put("user_id", SharedPreferencesUtil.getValue(AskActivity.this,SharedPreferencesUtil.USER_ID,"")+"");
                    map1.put("money_order", "0.01");
                    map1.put("subject", "沃噻提问支付");
                    map1.put("goods_code", model.getGoods_code());
                    mPresenter.getPayOrderInfo(map1);
            }

        }

    }

    @Override
    public void loadFail(String msg) {

    }

    @Override
    public void showLoading() {
        mLoadingDialog = new LoadingDialog(AskActivity.this, "加载中...", false);
        mLoadingDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.cancelDialog();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null&&!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

}
