package com.woosii.biz.api;

import com.woosii.biz.base.bean.LoginBean;
import com.woosii.biz.base.bean.json.BaseInfoBean;

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
    Observable<BaseInfoBean> getRegisterCode(@FieldMap Map<String, String> map);

    //手机注册
    @FormUrlEncoded
    @POST("/index.php/Api/User/register/")
    Observable<BaseInfoBean> register(@FieldMap Map<String, String> map);

    //密码登录
    @FormUrlEncoded
    @POST("/index.php/Api/User/login/")
    Observable<LoginBean> loginByPassword(@FieldMap Map<String, String> map);

}
