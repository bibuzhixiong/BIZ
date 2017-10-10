package com.woosii.biz.api;

import com.woosii.biz.base.bean.LoginBean;
import com.woosii.biz.base.bean.json.BaseInfoBean;
import com.woosii.biz.base.bean.json.BasePagingBean;
import com.woosii.biz.base.bean.json.CourseDetailBean;
import com.woosii.biz.base.bean.json.CourseListBean;
import com.woosii.biz.base.bean.json.NewsBean;
import com.woosii.biz.base.bean.json.WechatBean;

import java.util.List;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
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

    //新闻列表
    @GET("/index.php/Api/News/news_list/")
    Observable<BasePagingBean<NewsBean>> getNews(@QueryMap Map<String,String> map);

    //课程列表
    @GET("/index.php/Api/User/course/")
    Observable<List<CourseListBean>> getCourses(@QueryMap Map<String,String> map);

    //课程详情
    @GET("/index.php/Api/User/course_info/")
    Observable<CourseDetailBean> getCoursesDetail(@QueryMap Map<String,String> map);

    //获得微信登录的数据
    @GET()
    Observable<WechatBean> getWeChatLoginData(@Url String url);
}
