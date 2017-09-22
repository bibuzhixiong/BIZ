package com.woosii.biz.ui;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.woosii.biz.R;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AnswerModelActivity extends AppCompatActivity {
    @Bind(R.id.tv_play)
    TextView tvPlay;
    @Bind(R.id.seekBar)
    SeekBar seekBar;

     MediaPlayer     mediaPlayer =null;
    boolean isPaused=false,isChanging=false;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_model);
        ButterKnife.bind(this);
//        seekBar.setOnSeekBarChangeListener(new SeekBarChangeEvent());
        tvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer!=null){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                    isPaused=false;
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
                    mediaPlayer.setDataSource("http://qiniuadmin.woosii.com//storage/emulated/0/data/filesandroid_b4:0b:44:28:c9:151470620555758.wav");
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
                              Log.e("WWW",mediaPlayer.getDuration()+"--");
                              tvPlay.setText( mediaPlayer.getDuration()+"=--");
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
                    tvPlay.setEnabled(true);
                       Toast.makeText(this, "停止播放", Toast.LENGTH_SHORT).show();
              }

      }
    @Override
    protected void onDestroy() {
           if (mediaPlayer != null && mediaPlayer.isPlaying()) {
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
