package com.woosii.biz.ui.me.contract;

import com.woosii.biz.base.BasePresenter;
import com.woosii.biz.base.BaseView;
import com.woosii.biz.base.bean.json.BaseInfoBean;
import com.woosii.biz.base.bean.json.BasePagingBean;
import com.woosii.biz.base.bean.json.CommentsBean;
import com.woosii.biz.base.bean.json.PayInfoBean;
import com.woosii.biz.base.bean.json.PayReqBean;
import com.woosii.biz.base.bean.json.QuestionAnswerDetailBean;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/2.
 */

public interface QuestionAnswerDetailContract {
    interface View extends BaseView {


        //        void getPayOrderInfoSuccess(PayInfoBean model);
//        void changeHeadSuccess(BaseInfoBean model);
        void getCommentsSuccess(BasePagingBean<CommentsBean> model);
        void freCommentsSuccess(BasePagingBean<CommentsBean> model);
//        void askByUserSuccess(AskBean model);
        void commentSuccess(BaseInfoBean model);
        void buyQuestiontSuccess(BaseInfoBean model);

        void getQuestionAnswerDetialtSuccess(QuestionAnswerDetailBean model);


        void getPayOrderInfoSuccess(PayInfoBean model);
        void getWeChatOrderInfoSuccess(PayReqBean model);

        void loadFail(String msg);
        void showLoading();
        void hideLoading();

    }

    abstract class Presenter extends BasePresenter<QuestionAnswerDetailContract.View> {
        //        public abstract void getPayOrderInfo(Map<String, String> map);
//        public abstract void getWeChatOrderInfo(Map<String, String> map);
        public abstract void comment(Map<String, String> map);
        public abstract void getComments(Map<String, String> map);
        public abstract void freshComments(Map<String, String> map);
        public abstract void getQuestionAnswerDetial(Map<String, String> map);

        public abstract void getPayOrderInfo(Map<String, String> map);
        public abstract void getWeChatOrderInfo(Map<String, String> map);

        public abstract void buyQuestion(Map<String, String> map);

//        public abstract void changeHead(RequestBody uid, RequestBody pid, MultipartBody.Part body);




        @Override
        public void onStart() {

        }
    }
}
