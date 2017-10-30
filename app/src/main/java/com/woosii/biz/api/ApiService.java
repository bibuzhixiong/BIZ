package com.woosii.biz.api;

import com.woosii.biz.base.bean.LoginBean;
import com.woosii.biz.base.bean.json.AskBean;
import com.woosii.biz.base.bean.json.BaseInfoBean;
import com.woosii.biz.base.bean.json.BasePagingBean;
import com.woosii.biz.base.bean.json.CollegeBean;
import com.woosii.biz.base.bean.json.CourseDetailBean;
import com.woosii.biz.base.bean.json.CourseListBean;
import com.woosii.biz.base.bean.json.MyMessageBean;
import com.woosii.biz.base.bean.json.MyQuestionAnswerBean;
import com.woosii.biz.base.bean.json.NewsBean;
import com.woosii.biz.base.bean.json.PayInfoBean;
import com.woosii.biz.base.bean.json.PayReqBean;
import com.woosii.biz.base.bean.json.PointBean;
import com.woosii.biz.base.bean.json.PreViewQuestonsListBean;
import com.woosii.biz.base.bean.json.QuestionListBean;
import com.woosii.biz.base.bean.json.ThumbHeadBean;
import com.woosii.biz.base.bean.json.UserInfoBean;
import com.woosii.biz.base.bean.json.WechatBean;
import com.woosii.biz.base.bean.json.WechatInfoBean;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Administrator on 2017/9/21.
 */

public interface ApiService {
    //注册获取验证码
    @FormUrlEncoded
    @POST("index.php/Api/User/vcode/")
    Observable<BaseInfoBean> getRegisterCode(@FieldMap Map<String, String> map);
    //绑定手机号或其他验证码
    @FormUrlEncoded
    @POST("index.php/Api/User/wxphone_code/")
    Observable<BaseInfoBean> getCode(@FieldMap Map<String, String> map);
    //手机注册
    @FormUrlEncoded
    @POST("index.php/Api/User/register/")
    Observable<BaseInfoBean> register(@FieldMap Map<String, String> map);


    //微信绑定
    @FormUrlEncoded
    @POST("index.php/Api/User/wxphone_binding/")
    Observable<LoginBean> wecahtBinding(@FieldMap Map<String, String> map);
    //密码登录
    @FormUrlEncoded
    @POST("index.php/Api/User/login/")
    Observable<LoginBean> loginByPassword(@FieldMap Map<String, String> map);

    //忘记密码
    @FormUrlEncoded
    @POST("index.php/Api/User/modify_password/")
    Observable<LoginBean> forgetPassword(@FieldMap Map<String, String> map);


    //微信登录
    @FormUrlEncoded
    @POST("index.php/Api/User/wx_login/")
    Observable<LoginBean> loginByWX(@FieldMap Map<String, String> map);


    //用户信息
    @FormUrlEncoded
    @POST("index.php/Api/User/obtain_info/")
    Observable<UserInfoBean> getUserInfo(@FieldMap Map<String,String> map);

    //新闻列表
    @GET("index.php/Api/News/news_list/")
    Observable<BasePagingBean<NewsBean>> getNews(@QueryMap Map<String,String> map);
    //新闻轮播
    @GET("index.php/Api/News/news_photo/")
    Observable<List<NewsBean>> getNewsBanner(@QueryMap Map<String,String> map);
    //课程列表
    @GET("index.php/Api/User/course/")
    Observable<BasePagingBean<CourseListBean>> getCourses(@QueryMap Map<String,String> map);

    //课程详情
    @GET("index.php/Api/User/course_info/")
    Observable<CourseDetailBean> getCoursesDetail(@QueryMap Map<String,String> map);

    //加载微信用户信息
    @GET()
    Observable<WechatInfoBean> getWeChatInfo(@Url String url);
    //获得微信登录的数据
    @GET()
    Observable<WechatBean> getWeChatLoginData(@Url String url);

    //微信支付
    @FormUrlEncoded
    @POST("index.php/Api/Wxpay/wx_pay/")
    Observable<PayReqBean> weChatPay(@FieldMap Map<String, String> map);
    //获取支付订单信息2
    @FormUrlEncoded
    @POST("index.php/Api/Alipay/payment_order/")
    Observable<PayInfoBean> getPayOrderInfo(@FieldMap Map<String, String> map);

    //获取提问支付订单信息1
    @GET("index.php/Api/User/problem/")
    Observable<AskBean> askByUser(@QueryMap Map<String, String> map);

    //上传文件
    @Multipart
    @POST("index.php/Api/User/answer/")
    Observable<BaseInfoBean> changeUserHead(@Part("user_id") RequestBody user_id,@Part("p_id") RequestBody p_id, @Part MultipartBody.Part file);


    //学院选择
    @GET("index.php/Api/Company/company/")
    Observable<List<CollegeBean>> getCollege();

    //签到
    @GET("index.php/Api/User/sign_code/")
    Observable<BaseInfoBean> scan(@QueryMap Map<String,String> map);


    //签到获得积分
    @GET("index.php/Api/User/sign/")
    Observable<PointBean> getPoint(@QueryMap Map<String,String> map);

    //上传头像
    @Multipart
    @POST("index.php/Api/User/up_img")
    Observable<ThumbHeadBean> upUserHead(@Part MultipartBody.Part file);
    //修改个人资料
    @GET("index.php/Api/User/update_info/")
    Observable<BaseInfoBean> updateInfo(@QueryMap Map<String,String> map);

    //获取预习题
    @GET("index.php/Api/User/question/")
    Observable<PreViewQuestonsListBean> getPreviewQuestions(@QueryMap Map<String,String> map);

    //报名
    @GET("index.php/Api/User/sign_up/")
    Observable<BaseInfoBean> signUp(@QueryMap Map<String,String> map);

    //我的问答（普通用户）
    @GET("index.php/Api/User/problem_user/")
    Observable<BasePagingBean<MyQuestionAnswerBean>> getMyQuestionAnswer(@QueryMap Map<String,String> map);

    //我的问答（老师）
    @GET("index.php/Api/User/problem_teacher/")
    Observable<BasePagingBean<MyQuestionAnswerBean>> getMyQuestionAnswerByTeacher(@QueryMap Map<String,String> map);



    //问题列表
    @GET("index.php/Api/User/problem_list/")
    Observable<BasePagingBean<QuestionListBean>> getQuestionList(@QueryMap Map<String,String> map);


    //意见反馈
    @GET("index.php/Api/User/idea/")
    Observable<BaseInfoBean> suggestionFeedback(@QueryMap Map<String,String> map);

    //我的消息
    @GET(" index.php/Api/User/message/")
    Observable<List<MyMessageBean>> getMyMessage(@QueryMap Map<String,String> map);

}

