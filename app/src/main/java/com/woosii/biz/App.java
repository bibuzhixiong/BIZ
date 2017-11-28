package com.woosii.biz;

import android.app.Application;
import android.content.Context;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by lan on 2017/6/22.
 */
public class App extends Application{
    private static App context;
    public static Application instance;

    public static Application getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();

     UMShareAPI.get(this);
        context=this;
        instance = this;
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        PlatformConfig.setWeixin("wx319000e379121eed", "435fc1a0aa9f851fb1a83ea673a4b7b4");

        PlatformConfig.setQQZone("1106354377", "2UQGE26oHw8n3pOP");
//        //新浪微博
//        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");


    }

    public static Context getAppContext() {
        return context;
    }

}
