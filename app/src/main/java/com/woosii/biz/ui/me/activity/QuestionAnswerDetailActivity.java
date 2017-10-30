package com.woosii.biz.ui.me.activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.woosii.biz.AppConstant;
import com.woosii.biz.R;
import com.woosii.biz.base.BaseActivity;
import com.woosii.biz.base.BaseToolbar;
import com.woosii.biz.base.bean.json.QuestionListBean;
import com.woosii.biz.utils.GlideUtil;

import java.io.IOException;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/10/28.
 */

public class QuestionAnswerDetailActivity extends BaseActivity {
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

    MediaPlayer mediaPlayer =null;
    boolean isPaused=false,isChanging=false;
    private Handler handler;

    private String alltime="";
private   QuestionListBean questionListBean=new QuestionListBean(0,0,0,"","","","","");
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
          questionListBean = (QuestionListBean) getIntent().getSerializableExtra("question");
        Log.e("TTT","这是："+(QuestionListBean) getIntent().getSerializableExtra("question"));
        if(questionListBean!=null){
            tvContent.setText(questionListBean.getProblem());
            tvNameTime.setText(questionListBean.getAdd_time() + "  提问");
            tvName.setText(questionListBean.getNick_name());
            GlideUtil.getInstance().LoadContextCircleBitmap(QuestionAnswerDetailActivity.this,questionListBean.getThumb(),imgHead,R.drawable.icon_head_normal,R.drawable.icon_head_normal);

        }

        llPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer!=null){
                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                        isPaused=true;
                    }else{
                        mediaPlayer.start();
                        isPaused=false;
                    }



//                    mediaPlayer.release();
//                    mediaPlayer = null;
//                    isPaused=false;
                    return;
                }else{
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
                    mediaPlayer.setDataSource(AppConstant.BASE_URL+questionListBean.getAudio());
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
                            alltime=mediaPlayer.getDuration()/1000+"";
                            Log.e("WWW",mediaPlayer.getDuration()+"--");

//                            tvPlay.setText( mediaPlayer.getDuration()+"=--");
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        handler=new Handler(){
            public void handleMessage(Message msg){
                if(mediaPlayer!=null){
                    Log.e("EEE",mediaPlayer.getCurrentPosition()+"--");
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    tvPlayTime.setText(mediaPlayer.getCurrentPosition()/1000+" / "+alltime);
                }



            }
        };
        //实现消息传递
        DelayThread delaythread=new DelayThread(100);
        delaythread.start();
    }


    /**
     148.148      * 停止播放
     149.149      */
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
        if (mediaPlayer != null ) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
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
    class DelayThread extends Thread{
        int milliseconds;
        public DelayThread(int i){
            milliseconds=i;
        }
        public void run(){
            while(true){
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
}
