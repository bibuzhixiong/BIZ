package com.woosii.biz.api;

import com.woosii.biz.base.bean.LoginBean;
import com.woosii.biz.base.bean.json.BaseInfoBean;
import com.woosii.biz.base.bean.json.BasePagingBean;
import com.woosii.biz.base.bean.json.NewsBean;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by Administrator on 2017/9/21.
 */

public class Api {
    public final static String API_BASE_URL="http://biz.woosii.com/";
    public static Api instance;
    private ApiService service;

    public Api() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//请求数据打印
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 添加Rx适配器
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .client(okHttpClient)
                .build();
        service = retrofit.create(ApiService.class);
    }

    public static Api getInstance() {
        if (instance == null)
            instance = new Api();
        return instance;
    }

    //注册获取手机验证码
    public Observable<BaseInfoBean> getRegisterCode(Map<String, String> map){
        return service.getRegisterCode(map);
    }
    //手机注册
    public Observable<BaseInfoBean> register(Map<String, String> map){
        return service.register(map);
    }
    //密码登录
    public Observable<LoginBean> loginByPassword(Map<String, String> map){
        return service.loginByPassword(map);
    }
    //新闻列表
    public Observable<BasePagingBean<NewsBean>> getNews(Map<String,String> map){
        return service.getNews(map);
    }
}
