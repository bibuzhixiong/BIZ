package com.woosii.biz.ui.course.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.woosii.biz.AppConstant;
import com.woosii.biz.R;
import com.woosii.biz.base.BaseActivity;
import com.woosii.biz.base.BaseToolbar;
import com.woosii.biz.base.bean.LoginBean;
import com.woosii.biz.base.bean.json.PayInfoBean;
import com.woosii.biz.base.bean.json.PayReqBean;
import com.woosii.biz.base.rx.RxBus;
import com.woosii.biz.common.dialog.DialogInterface;
import com.woosii.biz.common.dialog.LoadingDialog;
import com.woosii.biz.common.dialog.NormalAlertDialog;
import com.woosii.biz.common.dialog.NormalSelectionDialog;
import com.woosii.biz.event.UserInfoEvent;
import com.woosii.biz.event.WeChatPayEvent;
import com.woosii.biz.ui.course.contract.OpenMenbershipContract;
import com.woosii.biz.ui.course.presenter.OpenMenbershipPresenter;
import com.woosii.biz.utils.GlideUtil;
import com.woosii.biz.utils.SharedPreferencesUtil;
import com.woosii.biz.utils.ToastUtil;
import com.woosii.biz.utils.payutil.AuthResult;
import com.woosii.biz.utils.payutil.PayResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/10/17.
 */

public class OpenMembershipActivity extends BaseActivity<OpenMenbershipPresenter> implements OpenMenbershipContract.View {
    @Bind(R.id.ll_select_college)
    LinearLayout ll_select_college;
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    @Bind(R.id.tv_college)
    TextView tvCollege;
    @Bind(R.id.img_head)
    ImageView imgHead;
    @Bind(R.id.tv_nick_name)
    TextView tvNickName;
    @Bind(R.id.tv_vip)
    TextView tvVip;
    @Bind(R.id.bt_kaitong)
    Button btKaitong;
    private LoadingDialog mLoadingDialog;

    private String class_id="";
    private int pay_type=1;//1是微信，2是支付宝
    private PopupWindow mPopupWindow;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
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
                        Toast.makeText(OpenMembershipActivity.this, "支付成功,您已成为VIP会员", Toast.LENGTH_SHORT).show();
                        showPopuwindow();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(OpenMembershipActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(OpenMembershipActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(OpenMembershipActivity.this,
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
        return R.layout.activity_open_menbreship;
    }

    @Override
    protected void initToolBar() {
        toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(OpenMembershipActivity.this);
            }
        });
    }

    @Override
    protected void initView() {
        String nickname = SharedPreferencesUtil.getValue(OpenMembershipActivity.this, SharedPreferencesUtil.NICK_NAME, "") + "";
        String headpath = SharedPreferencesUtil.getValue(OpenMembershipActivity.this, SharedPreferencesUtil.HEAD_PATH, "") + "";
        String vip = SharedPreferencesUtil.getValue(OpenMembershipActivity.this, SharedPreferencesUtil.HEAD_PATH, "") + "";
        if (!nickname.equals("")) {
            tvNickName.setText(nickname);
        }

        if (!headpath.equals("")) {
            if(!headpath.contains("http")){
                headpath= AppConstant.BASE_URL+headpath;
            }
            GlideUtil.getInstance().LoadContextCircleBitmap(OpenMembershipActivity.this, headpath, imgHead, R.drawable.def_touxiang, R.drawable.def_touxiang);
        }
        if (!vip.equals("1")) {
            tvVip.setText("您当前未开通VIP会员");

        } else {
            tvVip.setText("您当前已经开通VIP会员");
        }
        btKaitong.setBackgroundResource(R.color.gray);
        btKaitong.setEnabled(false);
//        String SharedPreferencesUtil.getValue(OpenMembershipActivity.this,SharedPreferencesUtil.NICK_NAME,"")+"";
        event();
    }
    private Subscription subscription;
    private void event() {
        subscription = RxBus.$().toObservable(WeChatPayEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WeChatPayEvent>() {
                    @Override
                    public void call(WeChatPayEvent event) {
                        Toast.makeText(OpenMembershipActivity.this, "支付成功,您已成为VIP会员", Toast.LENGTH_SHORT).show();
                        showPopuwindow();
                    }
                });
    }

    private void showPopuwindow(){
        SharedPreferencesUtil.putValue(OpenMembershipActivity.this,SharedPreferencesUtil.VIP,"1");
        //修改个人中心信息
        RxBus.$().postEvent(new UserInfoEvent(new LoginBean(0,"",SharedPreferencesUtil.getValue(OpenMembershipActivity.this,SharedPreferencesUtil.TOKEN,"")+"",SharedPreferencesUtil.getValue(OpenMembershipActivity.this,SharedPreferencesUtil.USER_ID,"")+"")));
        LayoutInflater mLayoutInflater = (LayoutInflater) this
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View music_popunwindwow = mLayoutInflater.inflate(
                R.layout.popuwindow_payment, null);
        mPopupWindow = new PopupWindow(music_popunwindwow, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
         music_popunwindwow.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish_Activity(OpenMembershipActivity.this);
             }
         });
        mPopupWindow.showAtLocation(findViewById(R.id.ll_select_college), Gravity.CENTER, 0, 0);
    }
    @OnClick({R.id.ll_select_college,R.id.bt_kaitong})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_select_college:
                startActivityForResult(SelectCollegeActivity.class, 101);

                break;

            case R.id.bt_kaitong:
             if((SharedPreferencesUtil.getValue(OpenMembershipActivity.this,SharedPreferencesUtil.VIP,"")+"").equals("1")){
                 ToastUtil.showShortToast("您已经是VIP会员");
                 return;
             }
                List<String> headData = new ArrayList<>();
                headData.add("微信支付");
                headData.add("支付宝支付");
                NormalSelectionDialog chaHeaddialog = new NormalSelectionDialog.Builder(OpenMembershipActivity.this)
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

                                            Map<String, String> map = new HashMap<>();
                                            map.put("user_id", SharedPreferencesUtil.getValue(OpenMembershipActivity.this,SharedPreferencesUtil.USER_ID,"")+"");
                                            map.put("subject", "沃噻开通会员");
                                            map.put("c_id",class_id);
                                            map.put("goods_type", "1");
                                            map.put("goods_code", SharedPreferencesUtil.getValue(OpenMembershipActivity.this,SharedPreferencesUtil.USER_ID,"")+"");
                                            mPresenter.getWeChatOrderInfo(map);

                                        dialog.dismiss();
                                        break;
                                    case 1:
//                                        ToastUtil.showShortToast("支付宝");
                                        pay_type=2;
                                        Map<String, String> map1 = new HashMap<>();
                                        map1.put("user_id", SharedPreferencesUtil.getValue(OpenMembershipActivity.this,SharedPreferencesUtil.USER_ID,"")+"");
                                        map1.put("subject", "沃噻开通会员");
                                        map1.put("c_id",class_id);
                                        map1.put("goods_type", "1");
                                        map1.put("goods_code", SharedPreferencesUtil.getValue(OpenMembershipActivity.this,SharedPreferencesUtil.USER_ID,"")+"");
                                        mPresenter.getPayOrderInfo(map1);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 & resultCode == RESULT_OK) {

            String college = data.getStringExtra("college");
            class_id = data.getStringExtra("class_id");
            tvCollege.setText(college);
            btKaitong.setBackgroundResource(R.drawable.shape_bluebtn_selector1);
            btKaitong.setEnabled(true);

        }
    }


    @Override
    public void getPayOrderInfoSuccess(final PayInfoBean model) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(OpenMembershipActivity.this);
                Map<String, String> result = alipay.payV2(model.getContent(), true);
                Log.e("UUU", model.getContent());
                Log.i("msp", result.toString());

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
        Log.e("EEE","来了");
        api.sendReq(req);
    }
    @Override
    public void loadFail(String msg) {

    }
    @Override
    public void showLoading() {
        mLoadingDialog = new LoadingDialog(OpenMembershipActivity.this, "加载中...", false);
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
