package com.woosii.biz;

import android.app.Application;
import android.content.Context;

/**
 * Created by lan on 2017/6/22.
 */
public class App extends Application{
    private static App context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        //讯飞语音初始化工作
//        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=59a8c40b");

    }
    public static Context getAppContext() {
        return context;
    }

}
