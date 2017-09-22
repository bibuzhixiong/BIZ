package com.woosii.biz.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lan on 2017/4/25.
 */
public class CheckUtils {
    /**
     * 验证手机号
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(17[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 验证密码:是否为数字、字母、下划线
     * @param pwd
     * @return
     */
    public static boolean isPwd(String pwd){
        Pattern p=Pattern.compile("^[A-Za-z0-9_]{6,20}$");
        Matcher m=p.matcher(pwd);
        return m.matches();
    }

    /**
     * 验证是否为字符串
     * @param str
     * @return
     */
    public static boolean isString(String str){
        Pattern p=Pattern.compile("^[\u4E00-\u9FA5A-Za-z0-9 ]+$");
        Matcher m=p.matcher(str);
        return m.matches();
    }
    /**
     * 验证是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher match = pattern.matcher(str);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * 验证邮箱是否正确
     * @param email  邮箱地址
     * @return boolean
     */
    public static boolean isEmail(String email) {
        Pattern email_pattern = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
        return email_pattern.matcher(email).matches();
    }
    /*
     * 获取应用版本
     */
    public static float getVersionCode(Context context){

        int versionCode = 0;
        try {
            //获取包管理者
            PackageManager pm = context.getPackageManager();
            //获取packageInfo
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            //获取versionCode
            versionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String md5(String content) {
        String s = null;
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            MessageDigest e = MessageDigest.getInstance("MD5");
            e.update(content.getBytes());
            byte[] tmp = e.digest();
            char[] str = new char[tmp.length * 2];
            int k = 0;

            for(int i = 0; i < tmp.length; ++i) {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 15];
                str[k++] = hexDigits[byte0 & 15];
            }

            s = new String(str);
        } catch (Exception var9) {
            var9.printStackTrace();
        }

        return s;
    }
}
