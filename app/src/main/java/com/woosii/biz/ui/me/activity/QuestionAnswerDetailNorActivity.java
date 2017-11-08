package com.woosii.biz.ui.me.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.woosii.biz.R;
import com.woosii.biz.base.BaseActivity;
import com.woosii.biz.base.BaseToolbar;
import com.woosii.biz.base.bean.json.BaseInfoBean;
import com.woosii.biz.base.bean.json.MyQuestionAnswerBean;
import com.woosii.biz.base.rx.RxBus;
import com.woosii.biz.common.dialog.DialogInterface;
import com.woosii.biz.common.dialog.LoadingDialog;
import com.woosii.biz.common.dialog.NormalAlertDialog;
import com.woosii.biz.event.FreshMyQuestionsEvent;
import com.woosii.biz.ui.me.contract.QuestionAnswerDetailNorContract;
import com.woosii.biz.ui.me.presenter.QuestionAnswerDetailNorPresenter;
import com.woosii.biz.utils.AudioRecoderUtils;
import com.woosii.biz.utils.SharedPreferencesUtil;
import com.woosii.biz.utils.TimeUtils;
import com.woosii.biz.utils.ToastUtil;
import com.woosii.biz.widget.CustomProgress;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/10/31.
 */

public class QuestionAnswerDetailNorActivity extends BaseActivity<QuestionAnswerDetailNorPresenter> implements QuestionAnswerDetailNorContract.View {
    @Bind(R.id.custom_progress)
    CustomProgress customProgress;
    @Bind(R.id.img_dian)
    ImageView imgDian;
    @Bind(R.id.toolbar)
    BaseToolbar toolbar;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.tv_name_time)
    TextView tvNameTime;
    @Bind(R.id.tv_audio_time)
    TextView tvAudioTime;
    @Bind(R.id.ll_huida_null)
    LinearLayout llHuidaNull;
    @Bind(R.id.bt_lijihuida)
    Button btLijihuida;
    @Bind(R.id.tv_cancle_send)
    TextView tvCancleSend;
    @Bind(R.id.tv_send)
    TextView tvSend;
    @Bind(R.id.ll_yuyin)
    LinearLayout llYuyin;
    @Bind(R.id.ll_send_or_cancle)
    LinearLayout llSendOrCancle;
    @Bind(R.id.ll_send_narrow)
    View llSendNarrow;
    @Bind(R.id.img_star)
    ImageView img_star;

    private AudioRecoderUtils mAudioRecoderUtils;

    private int isAudio = 0;//0是准备，1是正在录音，2是暂停录音，3是播放，4是正在播放，5是暂停播放

    int progress = 0;
    boolean isPaused = false, isChanging = false;
    MediaPlayer mediaPlayer = null;

    private MyQuestionAnswerBean questionListBean = new MyQuestionAnswerBean("", "", "", "");

    private String path;

    private LoadingDialog mLoadingDialog;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (progress == 0) {
                    return;
                }
                customProgress.setProgress(msg.arg1);
                if (mediaPlayer != null) {
                    tvAudioTime.setText(TimeUtils.long2String(mediaPlayer.getCurrentPosition()));
                }

                if (msg.arg1 == progress) {
                    customProgress.setProgress(0);
                    imgDian.setImageResource(R.drawable.aio_record_play_nor);
                    isAudio = 2;
                }
            }
            if (msg.what == 2) {
                progress = 0;
                isAudio = 2;
                tvAudioTime.setText(TimeUtils.long2String(mediaPlayer.getDuration()));
                mediaPlayer.stop();
                customProgress.setProgress(0);
                imgDian.setImageResource(R.drawable.aio_record_play_nor);

            }

            super.handleMessage(msg);
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_question_answer_detail_nor;
    }

    @Override
    protected void initToolBar() {
        toolbar.setLeftButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_Activity(QuestionAnswerDetailNorActivity.this);
            }
        });
    }

    @Override
    protected void initView() {
        questionListBean = (MyQuestionAnswerBean) getIntent().getSerializableExtra("question");

        if (questionListBean != null) {
            tvContent.setText(questionListBean.getProblem());
            tvNameTime.setText(questionListBean.getNick_name() + "  " + questionListBean.getAdd_time() + "  提问");
//            tvName.setText(questionListBean.getNick_name());
//            GlideUtil.getInstance().LoadContextCircleBitmap(QuestionAnswerDetailActivity.this,questionListBean.getThumb(),imgHead,R.drawable.icon_head_normal,R.drawable.icon_head_normal);

        }


        imgDian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();
            }
        });


        mAudioRecoderUtils = new AudioRecoderUtils();

        //录音回调
        mAudioRecoderUtils.setOnAudioStatusUpdateListener(new AudioRecoderUtils.OnAudioStatusUpdateListener() {

            //录音中....db为声音分贝，time为录音时长
            @Override
            public void onUpdate(double db, long time) {
//                mImageView.getDrawable().setLevel((int) (3000 + 6000 * db / 100));
                tvAudioTime.setText(TimeUtils.long2String(time));
            }

            //录音结束，filePath为保存路径
            @Override
            public void onStop(String filePath) {
                llSendOrCancle.setVisibility(View.VISIBLE);
                llSendNarrow.setBackgroundResource(R.color.common_divider_narrow);
                path = filePath;
//                Toast.makeText(QuestionAnswerDetailNorActivity.this, "录音保存在：" + filePath, Toast.LENGTH_SHORT).show();
//                tvAudioTime.setText(TimeUtils.long2String(0));
            }
        });


        //6.0以上需要权限申请
//        requestPermissions();

    }

    @OnClick({R.id.bt_lijihuida, R.id.tv_cancle_send, R.id.tv_send})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bt_lijihuida:
                llSendOrCancle.setVisibility(View.INVISIBLE);
                llSendNarrow.setBackgroundResource(R.color.white);
                isAudio = -1;
                tvAudioTime.setText("点击录音");
                img_star.setVisibility(View.VISIBLE);

                requestPermissions();

                break;
            case R.id.tv_cancle_send:
                if (mediaPlayer != null) {
                    Message message = handler.obtainMessage();
                    message.what = 2;

                    handler.sendMessage(message);
                }
                mAudioRecoderUtils.stopRecord();
                llYuyin.setVisibility(View.GONE);
                break;
            case R.id.tv_send:
                     File file = new File(path);

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("audio", file.getName(), requestFile);
//        Map<String, RequestBody> map = new HashMap<>();
        RequestBody uidBody = RequestBody.create(MediaType.parse("text/plain"), SharedPreferencesUtil.getValue(QuestionAnswerDetailNorActivity.this,SharedPreferencesUtil.USER_ID,"")+"");
        RequestBody pidBody = RequestBody.create(MediaType.parse("text/plain"), questionListBean.getP_id());
//            RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//            map.put("Filedata", fileBody);
        mPresenter.changeHead(uidBody,pidBody, body);
                break;

        }
    }

    /**
     * 开启扫描之前判断权限是否打开
     */
    private void requestPermissions() {
        //判断是否开启摄像头权限
        if ((ContextCompat.checkSelfPermission(QuestionAnswerDetailNorActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(QuestionAnswerDetailNorActivity.this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
            StartListener();

            //判断是否开启语音权限
        } else {
            //请求获取摄像头权限
            ActivityCompat.requestPermissions(QuestionAnswerDetailNorActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, VOICE_REQUEST_CODE);
        }

    }

    static final int VOICE_REQUEST_CODE = 66;

    /**
     * 请求权限回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == VOICE_REQUEST_CODE) {
            if ((grantResults[0] == PackageManager.PERMISSION_GRANTED) && (grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                StartListener();
            } else {
                Toast.makeText(QuestionAnswerDetailNorActivity.this, "已拒绝权限！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void StartListener() {
        llYuyin.setVisibility(View.VISIBLE);
        if(isAudio==-1){

            isAudio = 0;
            return;
        }
        if (isAudio == 0) {
            isAudio = 1;
            tvAudioTime.setText("00:00");
            img_star.setVisibility(View.GONE);
            mAudioRecoderUtils.startRecord();
            imgDian.setImageResource(R.drawable.aio_record_stop_nor);
            return;

        } else if (isAudio == 1) {
            isAudio = 2;
            mAudioRecoderUtils.stopRecord();
            imgDian.setImageResource(R.drawable.aio_record_play_nor);
            return;
        } else if (isAudio == 2) {
            isAudio = 3;
            Log.e("WWW", "isAudio:" + isAudio);
            imgDian.setImageResource(R.drawable.aio_record_stop_nor);


            if (mediaPlayer != null) {
                /*if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    isPaused = true;
                } else {
                    mediaPlayer.start();
                    isPaused = false;
                }*/
               /* mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;*/
                mediaPlayer = new MediaPlayer();
            } else {
                mediaPlayer = new MediaPlayer();

            }
            try {
                mediaPlayer.setDataSource(path);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.prepareAsync();
//                    mediaPlayer.prepare();
//                    mediaPlayer.start();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {

                        // 装载完毕回调
                        mediaPlayer.start();
                        progress = mediaPlayer.getDuration() / 100;
                        Log.e("WWW", mediaPlayer.getDuration() + "--");
                        customProgress.setmTotalProgress(progress);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    for (int i = 1; i <= progress; i++) {
                                        Thread.sleep(100);
                                        Message message = handler.obtainMessage();
                                        message.what = 1;
                                        message.arg1 = i;
                                        handler.sendMessage(message);

                                    }
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
//                            tvPlay.setText( mediaPlayer.getDuration()+"=--");
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (isAudio == 3) {
            Message message = handler.obtainMessage();
            message.what = 2;

            handler.sendMessage(message);


        }
//        progress = mediaPlayer.getDuration() / 1000;
//        Log.e("TTT", progress + "---" + mediaPlayer.getDuration());


        return;
    }


    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            progress = 0;
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }


    @Override
    public void changeHeadSuccess(BaseInfoBean model) {
        if(model.getCode()==1){
            if (mediaPlayer != null) {
                Message message = handler.obtainMessage();
                message.what = 2;

                handler.sendMessage(message);
            }
            mAudioRecoderUtils.stopRecord();
            llYuyin.setVisibility(View.GONE);

            //刷新上个界面的列表
            RxBus.$().postEvent(new FreshMyQuestionsEvent(1));
            NormalAlertDialog dialog = new NormalAlertDialog.Builder(this)
                    .setBoolTitle(false)
                    .setContentText("回答成功")
                    .setSingleModel(true)
                    .setSingleText("确认")
                    .setHeight(0.23f)
                    .setWidth(0.65f)
                    .setSingleListener(new DialogInterface.OnSingleClickListener<NormalAlertDialog>() {
                        @Override
                        public void clickSingleButton(NormalAlertDialog dialog, View view) {
                            finish_Activity(QuestionAnswerDetailNorActivity.this);
                            dialog.dismiss();
                        }
                    }).setTouchOutside(false)
                    .build();
            dialog.show();
        }else{
            ToastUtil.showShortToast(model.getMessage());
        }

    }

    @Override
    public void loadFail(String msg) {
        ToastUtil.showShortToast(msg);
    }
    @Override
    public void showLoading() {
        mLoadingDialog = new LoadingDialog(QuestionAnswerDetailNorActivity.this, "加载中...", false);
        mLoadingDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.cancelDialog();
        }
    }
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
            if (llYuyin.getVisibility() == View.VISIBLE) {
                if (mediaPlayer != null) {
                    Message message = handler.obtainMessage();
                    message.what = 2;

                    handler.sendMessage(message);
                }
                mAudioRecoderUtils.stopRecord();
                llYuyin.setVisibility(View.GONE);
            } else {
                finish_Activity(QuestionAnswerDetailNorActivity.this);
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

}
