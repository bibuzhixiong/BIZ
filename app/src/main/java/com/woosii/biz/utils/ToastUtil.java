package com.woosii.biz.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.woosii.biz.App;
import com.woosii.biz.R;


/**
 * Created by lan on 2017/6/21.
 * Toast工具类，加入了自定义Toast
 */
public class ToastUtil {
    //自定义的toast;
    private static Toast customtoast;
    //系统的Toast
    private static Toast mToast;

    private static Context context= App.getAppContext();

    /**
     * @param msg 显示的消息
     * @param duration 显示的时间
     * 自定义的Toast
     */
    public static void customShortToast(String msg, int duration){
        if(customtoast==null){
            customtoast=new Toast(context);
            customtoast.setDuration(duration);
            View view=((LayoutInflater)(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))).inflate(R.layout.sys_toast,null);
            TextView textView= (TextView) view.findViewById(R.id.sys_toast_txt);
            textView.setText(msg);
            customtoast.setView(view);
        }else{
            customtoast.setDuration(duration);
            TextView textView= (TextView) customtoast.getView().findViewById(R.id.sys_toast_txt);
            textView.setText(msg);
        }
        customtoast.show();
    }
    /*================================系统的Toast=========================*/

    /**
     * @param msg
     * @param duration
     * @return
     * 当mToast已经创建，只是设置提示改变，不会重复弹出
     */
    private static Toast getSingleToast(String msg, int duration){
        if(mToast==null){
            mToast= Toast.makeText(context,msg,duration);
        }else{
            mToast.setText(msg);
        }
        return mToast;
    }
    /**
     * @param msg
     *显示Short的Toast
     */
    public static void showShortToast(String msg){
        getSingleToast(msg, Toast.LENGTH_SHORT).show();
    }
    /**
     * @param msg
     * 显示Long的Toast
     */
    public static void showLongToast(String msg){
        getSingleToast(msg, Toast.LENGTH_LONG).show();
    }

    /**
     * @param msg
     * 显示在顶部的Short的Toast
     */
    public static  void showTopShortToast(String msg){
        Toast toast=getSingleToast(msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP,0,0);
        toast.show();

    }
    /**
     * @param msg
     * 显示在中间的Short的Toast
     */
    public static  void showCenterShortToast(String msg){
        Toast toast=getSingleToast(msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
    /**
     * @param msg
     * 显示在底部的Short的Toast
     */
    public static  void showbottomShortToast(String msg){
        Toast toast=getSingleToast(msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * @param msg
     * 显示在顶部的Short的Toast
     */
    public static  void showTopLongToast(String msg){
        Toast toast=getSingleToast(msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP,0,0);
        toast.show();

    }
    /**
     * @param msg
     * 显示在中间的Short的Toast
     */
    public static  void showCenterLongToast(String msg){
        Toast toast=getSingleToast(msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
    /**
     * @param msg
     * 显示在底部的Long的Toast
     */
    public static  void showbottomLongToast(String msg){
        Toast toast=getSingleToast(msg, Toast.LENGTH_LONG);
        toast.show();
    }

}
