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
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
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

    //绑定手机号或其他验证码
    public Observable<BaseInfoBean> getCode(Map<String, String> map){
        return service.getCode(map);
    }
        //微信绑定
    public Observable<LoginBean> wecahtBinding(Map<String, String> map){
        return service.wecahtBinding(map);
    }
    //手机注册
    public Observable<BaseInfoBean> register(Map<String, String> map){
        return service.register(map);
    }
    //密码登录
    public Observable<LoginBean> loginByPassword(Map<String, String> map){
        return service.loginByPassword(map);
    }
    //忘记密码
    public Observable<LoginBean> forgetPassword(Map<String, String> map){
        return service.forgetPassword(map);
    }
    //微信登录
    public Observable<LoginBean> loginByWX(Map<String, String> map){
        return service.loginByWX(map);
    }
    //获取用户信息
    public Observable<UserInfoBean> getUserInfo(Map<String, String> map){
        return service.getUserInfo(map);
    }

    //新闻列表
    public Observable<BasePagingBean<NewsBean>> getNews(Map<String,String> map){
        return service.getNews(map);
    }
    //新闻轮播
    public Observable<List<NewsBean>> getNewsBanner(Map<String,String> map){
        return service.getNewsBanner(map);
    }
    //课程详情
    public Observable<CourseDetailBean> getCourseDetail(Map<String,String> map){
        return service.getCoursesDetail(map);
    }

    //课程列表
    public Observable<BasePagingBean<CourseListBean>> getCourses(Map<String,String> map){
        return service.getCourses(map);
    }


    //获得微信登录的数据
    public Observable<WechatInfoBean> getWeChatInfo(String url){
        return service.getWeChatInfo(url);
    }
    //获得微信登录的数据
    public Observable<WechatBean> getWeChatLoginData(String url){
        return service.getWeChatLoginData(url);
    }

    //支付订单信息
    public Observable<PayInfoBean> getPayOrderInfo(Map<String,String> map){
        return service.getPayOrderInfo(map);
    }
    //支付提问订单信息1
    public Observable<AskBean> askByUser(Map<String,String> map){
        return service.askByUser(map);
    }
    //测试
    public Observable<BaseInfoBean> upAudio(RequestBody user_id,RequestBody p_id,MultipartBody.Part body){
        return service.changeUserHead(user_id,p_id,body);
    }

    //学院列表
    public Observable<List<CollegeBean>> getCollege(){
        return service.getCollege();
    }

    //微信支付
    public Observable<PayReqBean> weChatPay(Map<String,String> map){
        return service.weChatPay(map);
    }

    //扫码签到判断
    public Observable<BaseInfoBean> scan(Map<String,String> map){
        return service.scan(map);
    }
    //获取积分
    public Observable<PointBean> getPoint(Map<String,String> map){
        return service.getPoint(map);
    }

    //上传头像
    public Observable<ThumbHeadBean> upUserHead(MultipartBody.Part body){
        return service.upUserHead(body);
    }

    //修改个人资料
    public Observable<BaseInfoBean> updateInfo(Map<String,String> map){
        return service.updateInfo(map);
    }
    //预习题
    public Observable<PreViewQuestonsListBean> getPreviewQuestions(Map<String,String> map){
        return service.getPreviewQuestions(map);
    }
    //报名
    public Observable<BaseInfoBean> signUp(Map<String,String> map){
        return service.signUp(map);
    }
    //我的问答（普通用户）
    public Observable<BasePagingBean<MyQuestionAnswerBean>> getMyQuestionAnswer(Map<String,String> map){
        return service.getMyQuestionAnswer(map);
    }
    //我的问答（老师）
    public Observable<BasePagingBean<MyQuestionAnswerBean>> getMyQuestionAnswerByTeacher(Map<String,String> map){
        return service.getMyQuestionAnswerByTeacher(map);
    }
    //我的问答（老师）
    public Observable<BasePagingBean<QuestionListBean>> getQuestionList(Map<String,String> map){
        return service.getQuestionList(map);
    }

    //意见反馈
    public Observable<BaseInfoBean> suggestionFeedback(Map<String,String> map){
        return service.suggestionFeedback(map);
    }

    //我的消息
    public Observable<List<MyMessageBean>> getMyMessage(Map<String,String> map){
        return service.getMyMessage(map);
    }
}
