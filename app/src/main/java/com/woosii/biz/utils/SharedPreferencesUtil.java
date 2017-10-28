package com.woosii.biz.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by lan on 2017/6/21.
 * SharedPreferences工具类，
 */
public class SharedPreferencesUtil {
    //用户ID
    public static final String USER_ID="USERID_ID";
    //用户token
    public static final String TOKEN="TOKEN";
    //用户昵称
    public static final String NICK_NAME="NICK_NAME";
    //用户头像
    public static final String HEAD_PATH="HEAD_PATH";
    //是否vip
    public static final String VIP="VIP";
    //是否vip
    public static final String GENDER="GENDER";

    //是否vip
    public static final String USER_TYPE="USER_TYPE";

    //学院名字
    public static final String CLASS_NAME="CLASS_NAME";




    private static final String FILE_NAME="shared_data";
    private static SharedPreferences sp;

    private static void init(Context context){
        sp=context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * @param context
     * @param key
     * @param object
     * 保存数据，使用Object接收，再判断是那种类型
     */
    public static void putValue(Context context, String key, Object object){
        if(sp==null){
            init(context);
        }
        SharedPreferences.Editor editor=sp.edit();
        if(object instanceof String){
            editor.putString(key, (String) object);
        }else if(object instanceof Integer){
            editor.putInt(key, (Integer) object);
        }else if(object instanceof Long){
            editor.putLong(key, (Long) object);
        }else if(object instanceof Float){
            editor.putFloat(key, (Float) object);
        }else if(object instanceof Boolean){
            editor.putBoolean(key, (Boolean) object);
        }else{
            editor.putString(key,object.toString());
        }
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * @param context
     * @param key
     * @param defaultobject
     * @return
     * 获得保存的数据，通过传进来的defaultobject来判断是什么类型的数据
     */
    public static Object getValue(Context context, String key, Object defaultobject){
        if(sp==null){
            init(context);
        }
        if(defaultobject instanceof String){
            return sp.getString(key, (String) defaultobject);
        }else if(defaultobject instanceof Integer){
            return sp.getInt(key, (Integer) defaultobject);
        }else if(defaultobject instanceof Long){
            return sp.getLong(key, (Long) defaultobject);
        }else if(defaultobject instanceof Float){
            return sp.getFloat(key, (Float) defaultobject);
        }else if(defaultobject instanceof Boolean){
            return sp.getBoolean(key, (Boolean) defaultobject);
        }
            return null;
    }

    /**
     * @param context
     * @param key
     * 移除某个key对应的值
     */
    public static void removeValue(Context context, String key){
        if (sp==null){
            init(context);
        }
        SharedPreferences.Editor editor=sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * @param context
     * 移除所有的数据
     */
    public static void removeAll(Context context){
        if(sp==null){
            init(context);
        }
        SharedPreferences.Editor editor=sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * @param context
     * @param key
     * @return
     *根据key查询某个key对应的值是否存在
     */
    public static boolean contains(Context context, String key){
        if(sp==null){
            init(context);
        }
       return sp.contains(key);

    }
    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private static class SharedPreferencesCompat
    {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         */
        @SuppressWarnings({ "unchecked", "rawtypes" })
        private static Method findApplyMethod()
        {
            try
            {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e)
            {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         */
        public static void apply(SharedPreferences.Editor editor)
        {
            try
            {
                if (sApplyMethod != null)
                {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e)
            {
            } catch (IllegalAccessException e)
            {
            } catch (InvocationTargetException e)
            {
            }
            editor.commit();
        }
    }
}
