package com.woosii.biz.ui.me.activity;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.woosii.biz.AppConstant;
import com.woosii.biz.R;
import com.woosii.biz.adapter.CommentAdapter;
import com.woosii.biz.base.BaseActivity;
import com.woosii.biz.base.BaseToolbar;
import com.woosii.biz.base.bean.json.BaseInfoBean;
import com.woosii.biz.base.bean.json.BasePagingBean;
import com.woosii.biz.base.bean.json.CommentsBean;
import com.woosii.biz.base.bean.json.PayInfoBean;
import com.woosii.biz.base.bean.json.PayReqBean;
import com.woosii.biz.base.bean.json.QuestionAnswerDetailBean;
import com.woosii.biz.base.rx.RxBus;
import com.woosii.biz.common.dialog.DialogInterface;
import com.woosii.biz.common.dialog.LoadingDialog;
import com.woosii.biz.common.dialog.NormalAlertDialog;
import com.woosii.biz.common.dialog.NormalSelectionDialog;
import com.woosii.biz.event.WeChatPayEvent;
import com.woosii.biz.ui.login.activity.LoginActivity;
import com.woosii.biz.ui.me.contract.QuestionAnswerDetailContract;
import com.woosii.biz.ui.me.presenter.QuestionAnswerDetailPresenter;
import com.woosii.biz.utils.GlideUtil;
import com.woosii.biz.utils.RescourseUtil;
import com.woosii.biz.utils.SharedPreferencesUtil;
import com.woosii.biz.utils.TimeUtils;
import com.woosii.biz.utils.ToastUtil;
import com.woosii.biz.utils.payutil.AuthResult;
import com.woosii.biz.utils.payutil.PayResult;
import com.woosii.biz.widget.CustomLoadMoreView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/10/28.
 */

public class QuestionAnswerDetailActivity extends BaseActivity<QuestionAnswerDetailPresenter> implements QuestionAnswerDetailContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.tv_name_time)
    TextView tvNameTime;
    @Bind(R.id.img_head)
    ImageView imgHead;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.ll_play)
    LinearLayout llPlay;
    @Bind(R.id.tv_play_time)
    TextView tvPlayTime;

    @Bind(R.id.seekBar)
    SeekBar seekBar;

    MediaPlayer mediaPlayer = null;
    boolean isPaused = false, isChanging = false;
    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.tv_comment_content)
    EditText tvCommentContent;
    @Bind(R.id.tv_send_comment)
    TextView tvSendComment;
    private Handler handler;

    private long alltime;
    //private   QuestionListBean questionListBean=new QuestionListBean(0,0,"","","","","","");
    private QuestionAnswerDetailBean questionAnswerDetailBean = new QuestionAnswerDetailBean(0, 0, "", "", "", "", "", "",0,0.0f);

    private String p_id;
    private String goods_code;


    @Bind(R.id.recycleview)
    RecyclerView recycleview;
    @Bind(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    CommentAdapter adapter;
    List<CommentsBean> list = new ArrayList<>();
    private int page = 1;  //页数
    private int totalPages = 1;//总页数
    private int pay_type=1;//1是微信，2是支付宝

    private LoadingDialog mLoadingDialog;


    private View notDataView;
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
                        onResume();
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        Toast.makeText(OpenMembershipActivity.this, "支付成功,您已成为VIP会员", Toast.LENGTH_SHORT).show();
//                        showPopuwindow();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(QuestionAnswerDetailActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(QuestionAnswerDetailActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(QuestionAnswerDetailActivity.this,
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
        return R.layout.activity_question_answer_detail;
    }

    @Override
    protected void initToolBar() {
        toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(QuestionAnswerDetailActivity.this);
            }
        });
    }

    @Override
    protected void initView() {

        mSwipeRefreshLayout.setColorSchemeColors(RescourseUtil.getColor(R.color.blue),
                RescourseUtil.getColor(R.color.blue1));
        //设置刷新出现的位置
        mSwipeRefreshLayout.setProgressViewEndTarget(false, 120);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //设置布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(QuestionAnswerDetailActivity.this);
        recycleview.setLayoutManager(linearLayoutManager);

        adapter = new CommentAdapter(list);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        recycleview.setAdapter(adapter);

        adapter.setOnLoadMoreListener(this, recycleview);
        adapter.setLoadMoreView(new CustomLoadMoreView());

        notDataView = getLayoutInflater().inflate(R.layout.empty_comment, (ViewGroup) recycleview.getParent(), false);
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setEmptyView(R.layout.loading_comment, (ViewGroup) recycleview.getParent());
                onRefresh();
            }
        });

        p_id = getIntent().getStringExtra("p_id");
        goods_code=getIntent().getStringExtra("goods_code");
        loadData(false);

        llPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((SharedPreferencesUtil.getValue(QuestionAnswerDetailActivity.this, SharedPreferencesUtil.USER_ID, "") + "").equals("")) {
                    startActivity(LoginActivity.class);
                    return;
                }
                if (questionAnswerDetailBean.getFree() == 0 && questionAnswerDetailBean.getBuy() == 0) {
                    List<String> headData = new ArrayList<>();
                    headData.add("微信支付");
                    headData.add("支付宝支付");
                    NormalSelectionDialog chaHeaddialog = new NormalSelectionDialog.Builder(QuestionAnswerDetailActivity.this)
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
                                            map.put("user_id", SharedPreferencesUtil.getValue(QuestionAnswerDetailActivity.this,SharedPreferencesUtil.USER_ID,"")+"");
                                            map.put("subject", "购买分答语音答案");
                                            map.put("goods_type", "3");
                                            map.put("goods_code", goods_code);
                                            mPresenter.getWeChatOrderInfo(map);
                                            dialog.dismiss();
                                            break;
                                        case 1:
//                                        ToastUtil.showShortToast("支付宝");
                                            pay_type=2;
                                            Map<String, String> map1 = new HashMap<>();
                                            map1.put("user_id", SharedPreferencesUtil.getValue(QuestionAnswerDetailActivity.this,SharedPreferencesUtil.USER_ID,"")+"");
                                            map1.put("subject", "购买分答语音答案");
                                            map1.put("goods_type", "3");
                                            map1.put("goods_code", goods_code);
                                            mPresenter.getPayOrderInfo(map1);
                                            dialog.dismiss();
                                            break;
                                    }

                                }
                            }).setTouchOutside(true)
                            .build();

                    chaHeaddialog.setData(headData);
                    chaHeaddialog.show();
                    return;
                }
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        isPaused = true;
                    } else {
                        mediaPlayer.start();
                        isPaused = false;
                    }


//                    mediaPlayer.release();
//                    mediaPlayer = null;
//                    isPaused=false;
                    return;
                } else {
                    mediaPlayer = new MediaPlayer();


                }

           /* if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();
                return;
            }else if(mediaPlayer.isPlaying()){
                mediaPlayer.start();
                return;
            }*/
                try {
                    mediaPlayer.setDataSource(AppConstant.BASE_URL + questionAnswerDetailBean.getAudio());
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.prepareAsync();
//                    mediaPlayer.prepare();
//                    mediaPlayer.start();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {

                            // 装载完毕回调
                            mediaPlayer.start();
                            seekBar.setMax(mediaPlayer.getDuration());
                            alltime = mediaPlayer.getDuration();
                            Log.e("WWW", mediaPlayer.getDuration() + "--");

//                            tvPlay.setText( mediaPlayer.getDuration()+"=--");
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        handler = new Handler() {
            public void handleMessage(Message msg) {
                if (mediaPlayer != null) {
                    Log.e("EEE", mediaPlayer.getCurrentPosition() + "--");
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    tvPlayTime.setText(TimeUtils.long2String(mediaPlayer.getCurrentPosition()) + " / " + TimeUtils.long2String(alltime));
                }


            }
        };
        //实现消息传递
        DelayThread delaythread = new DelayThread(100);
        delaythread.start();


        tvSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=tvCommentContent.getText().toString().trim();
                if(content.equals("")){
                    ToastUtil.showShortToast("评论不能为空");
                    return;
                }
                if ((SharedPreferencesUtil.getValue(QuestionAnswerDetailActivity.this, SharedPreferencesUtil.USER_ID, "") + "").equals("")) {
                    startActivity(LoginActivity.class);
                    return;
                }
                if(questionAnswerDetailBean.getBuy()==0&&questionAnswerDetailBean.getFree()==0){
                    ToastUtil.showShortToast("购买后才能评论");
                    return;

                }
                Map<String,String> map2=new HashMap<String, String>();
                map2.put("user_id",SharedPreferencesUtil.getValue(QuestionAnswerDetailActivity.this, SharedPreferencesUtil.USER_ID, "") + "");
                map2.put("p_id",p_id);
                map2.put("comment",content);
                mPresenter.comment(map2);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", SharedPreferencesUtil.getValue(QuestionAnswerDetailActivity.this, SharedPreferencesUtil.USER_ID, "") + "");
        map.put("p_id", p_id);
        mPresenter.getQuestionAnswerDetial(map);
    }

    private Subscription subscription;
    private void event() {
        subscription = RxBus.$().toObservable(WeChatPayEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WeChatPayEvent>() {
                    @Override
                    public void call(WeChatPayEvent event) {
                       onResume();

                    }
                });
    }
    //swifload判断是否是下拉加载
    private void loadData(boolean isRefresh) {
        Map<String, String> map = new HashMap<>();
        map.put("psize", "10");
        map.put("p_id", p_id);

        if (isRefresh) {
            map.put("pindex", "1");
            mPresenter.freshComments(map);
        } else {
            map.put("pindex", page + "");
            mPresenter.getComments(map);

        }


    }


    /**
     * 148.148      * 停止播放
     * 149.149
     */
    protected void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            llPlay.setEnabled(true);
            Toast.makeText(this, "停止播放", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (subscription != null&&!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.onDestroy();
    }

    @Override
    public void getCommentsSuccess(BasePagingBean<CommentsBean> model) {
        if(model.getChild()==null){
            adapter.loadMoreComplete();
            mSwipeRefreshLayout.setRefreshing(false);
            adapter.setEmptyView(notDataView);
            return;
        }
        totalPages = Integer.parseInt(model.getCount());
        adapter.addData(model.getChild());

        list = adapter.getData();
//        dataLists=newsAdapter.getData();
        adapter.loadMoreComplete();
    }

    @Override
    public void freCommentsSuccess(BasePagingBean<CommentsBean> model) {
        if(model.getChild()==null){
            adapter.loadMoreComplete();
            mSwipeRefreshLayout.setRefreshing(false);
            adapter.setEmptyView(notDataView);
            return;
        }
        totalPages = Integer.parseInt(model.getCount());
        mSwipeRefreshLayout.setRefreshing(false);
        list.clear();
        list = adapter.getData();
        list.addAll(model.getChild());
        adapter.setNewData(list);
        page = 1;
    }

    @Override
    public void commentSuccess(BaseInfoBean model) {

        if(model.getCode()==1){
            CommentsBean commentsBean=new CommentsBean(SharedPreferencesUtil.getValue(QuestionAnswerDetailActivity.this,SharedPreferencesUtil.HEAD_PATH,"")+"",
                    SharedPreferencesUtil.getValue(QuestionAnswerDetailActivity.this,SharedPreferencesUtil.NICK_NAME,"")+"",tvCommentContent.getText().toString().trim(),
                    "刚刚");
            adapter.addData(0,commentsBean);
           adapter.notifyDataSetChanged();
            ToastUtil.showShortToast("评论成功");
            tvCommentContent.setText("");
        }else if(model.getCode()==2){
            ToastUtil.showShortToast("评论需购买");
        }else{
            ToastUtil.showShortToast("失败");
        }

    }

    @Override
    public void buyQuestiontSuccess(BaseInfoBean model) {

    }

    @Override
    public void getQuestionAnswerDetialtSuccess(QuestionAnswerDetailBean model) {
        questionAnswerDetailBean = model;

        if (questionAnswerDetailBean.getBuy() == 1 || questionAnswerDetailBean.getFree() == 1||questionAnswerDetailBean.getFree_type()==0) {
            seekBar.setVisibility(View.VISIBLE);
            tvMoney.setVisibility(View.GONE);
        }else{
            tvMoney.setText("语音播放需要"+questionAnswerDetailBean.getMoney()+"元");
        }
//        questionListBean = (QuestionListBean) getIntent().getSerializableExtra("question");
//        Log.e("TTT","这是："+(QuestionListBean) getIntent().getSerializableExtra("question"));
        if (questionAnswerDetailBean != null) {
            tvContent.setText(questionAnswerDetailBean.getProblem());
            tvNameTime.setText(questionAnswerDetailBean.getAdd_time() + "  提问");
            tvName.setText(questionAnswerDetailBean.getTeacher_name());
            GlideUtil.getInstance().LoadContextCircleBitmap(QuestionAnswerDetailActivity.this, questionAnswerDetailBean.getTeacher_thumb(), imgHead, R.drawable.icon_head_normal, R.drawable.icon_head_normal);

        }
    }
    @Override
    public void getPayOrderInfoSuccess(final PayInfoBean model) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(QuestionAnswerDetailActivity.this);
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

        api.sendReq(req);
    }

    @Override
    public void loadFail(String msg) {
            ToastUtil.showShortToast(msg);
    }


    @Override
    public void showLoading() {
        mLoadingDialog = new LoadingDialog(QuestionAnswerDetailActivity.this, "加载中...", false);
        mLoadingDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.cancelDialog();
        }
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

    @Override
    public void onLoadMoreRequested() {
        if (adapter.getData().size() < 10) {
            adapter.loadMoreEnd(false);
            return;
        } else {
            int maxpage = 1;
            if (totalPages % 10 == 0) {
                maxpage = totalPages / 10 - 1;
            } else {
                maxpage = totalPages / 10;
            }
            if (page > maxpage) {
                adapter.loadMoreEnd(false);
                return;
            }
            page++;
//            Log.e("TTT",page+"好吧");
            loadData(false);
        }
    }




   /* class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
            // TODO Auto-generated method stub
        }
        @Override
        public void onStartTrackingTouch(SeekBar arg0) {
//开始拖动进度条
            // TODO Auto-generated method stub
            isChanging=true;
        }
        @Override
        public void onStopTrackingTouch(SeekBar arg0) {
//停止拖动进度条
            // TODO Auto-generated method stub
            mediaPlayer.seekTo(arg0.getProgress());
//将media进度设置为当前seekbar的进度
            isChanging=false;
        }
    }*/


    //因为MediaPlayer类没有播放进度的回调方法，所以需要设置一个线程实现实时刷新
    class DelayThread extends Thread {
        int milliseconds;

        public DelayThread(int i) {
            milliseconds = i;
        }

        public void run() {
            while (true) {
                try {
                    sleep(milliseconds);
//设置音乐进度读取频率
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        }

    }

  /*  @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription != null&&!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }*/
}
