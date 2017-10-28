package com.woosii.biz;

import android.app.Application;
import android.content.Context;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

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
        PlatformConfig.setWeixin("wx319000e379121eed", "435fc1a0aa9f851fb1a83ea673a4b7b4");
//        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
//        //新浪微博
//        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");


    }

    public static Context getAppContext() {
        return context;
    }

}
