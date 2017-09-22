package com.woosii.biz.api;

import com.woosii.biz.base.bean.json.RegisterInfoBean;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2017/9/21.
 */

public interface ApiService {
    //注册获取验证码
    @FormUrlEncoded
    @POST("/index.php/Api/User/vcode/")
    Observable<RegisterInfoBean> getRegisterCode(@FieldMap Map<String, String> map);

    //手机注册
    @FormUrlEncoded
    @POST("/index.php/Api/User/register/")
    Observable<RegisterInfoBean> register(@FieldMap Map<String, String> map);
}
