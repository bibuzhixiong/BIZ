package com.woosii.biz.utils;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

import com.woosii.biz.App;


/**
 * Created by lan on 2017/6/21.
 * 获取资源的工具类
 */
public class RescourseUtil {

    public static Drawable getDrawable(@DrawableRes int id){
        return ContextCompat.getDrawable(App.getAppContext(),id);
    }
    public static int getColor(@ColorRes int id){
        return ContextCompat.getColor(App.getAppContext(),id);
    }
    public static String getString(@StringRes int id){
        return App.getAppContext().getString(id);
    }
}
