package com.woosii.biz.ui.me.presenter;

import com.woosii.biz.api.Api;
import com.woosii.biz.base.bean.json.BaseInfoBean;
import com.woosii.biz.base.bean.json.BasePagingBean;
import com.woosii.biz.base.bean.json.CommentsBean;
import com.woosii.biz.base.bean.json.PayInfoBean;
import com.woosii.biz.base.bean.json.PayReqBean;
import com.woosii.biz.base.bean.json.QuestionAnswerDetailBean;
import com.woosii.biz.base.rx.RxSubscriber;
import com.woosii.biz.ui.me.contract.QuestionAnswerDetailContract;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/2.
 */

public class QuestionAnswerDetailPresenter extends QuestionAnswerDetailContract.Presenter{

    @Override
    public void comment(Map<String, String> map) {
        mView.showLoading();
        addSubscrebe(Api.getInstance().comment(map), new RxSubscriber<BaseInfoBean>(mContext, false){
            @Override
            protected void onSuccess(BaseInfoBean model) {
                mView.hideLoading();
                mView.commentSuccess(model);
            }

            @Override
            protected void onFailure(String message) {
                mView.hideLoading();
                mView.loadFail(message);
            }
        });
    }

    @Override
    public void getComments(Map<String, String> map) {

        addSubscrebe(Api.getInstance().getComments(map), new RxSubscriber<BasePagingBean<CommentsBean>>(mContext, false){
            @Override
            protected void onSuccess(BasePagingBean<CommentsBean> model) {
                mView.hideLoading();
                mView.getCommentsSuccess(model);
            }

            @Override
            protected void onFailure(String message) {
                mView.hideLoading();
                mView.loadFail(message);
            }
        });
    }

    @Override
    public void freshComments(Map<String, String> map) {
        addSubscrebe(Api.getInstance().getComments(map), new RxSubscriber<BasePagingBean<CommentsBean>>(mContext, false){
            @Override
            protected void onSuccess(BasePagingBean<CommentsBean> model) {
                mView.hideLoading();
                mView.freCommentsSuccess(model);
            }

            @Override
            protected void onFailure(String message) {
                mView.hideLoading();
                mView.loadFail(message);
            }
        });
    }

    @Override
    public void getQuestionAnswerDetial(Map<String, String> map) {

        addSubscrebe(Api.getInstance().getQuestionAnswerDetial(map), new RxSubscriber<QuestionAnswerDetailBean>(mContext, false){
            @Override
            protected void onSuccess(QuestionAnswerDetailBean model) {
                mView.hideLoading();
                mView.getQuestionAnswerDetialtSuccess(model);
            }

            @Override
            protected void onFailure(String message) {
                mView.hideLoading();
                mView.loadFail(message);
            }
        });
    }

    @Override
    public void buyQuestion(Map<String, String> map) {
        mView.showLoading();
        addSubscrebe(Api.getInstance().buyQuestion(map), new RxSubscriber<BaseInfoBean>(mContext, false){
            @Override
            protected void onSuccess(BaseInfoBean model) {
                mView.hideLoading();
                mView.buyQuestiontSuccess(model);
            }

            @Override
            protected void onFailure(String message) {
                mView.hideLoading();
                mView.loadFail(message);
            }
        });
    }



    @Override
    public void getPayOrderInfo(Map<String, String> map) {
        mView.showLoading();
        addSubscrebe(Api.getInstance().getPayOrderInfo( map),
                new RxSubscriber<PayInfoBean>(mContext,false) {
                    @Override
                    protected void onSuccess(PayInfoBean model) {

                        mView.hideLoading();
                        mView.getPayOrderInfoSuccess(model);
                    }
                    @Override
                    protected void onFailure(String message) {
                        mView.hideLoading();
                        mView.loadFail(message);
                    }
                });
    }

    @Override
    public void getWeChatOrderInfo(Map<String, String> map) {
        mView.showLoading();
        addSubscrebe(Api.getInstance().weChatPay( map),
                new RxSubscriber<PayReqBean>(mContext,false) {
                    @Override
                    protected void onSuccess(PayReqBean model) {
                        mView.hideLoading();
                        mView.getWeChatOrderInfoSuccess(model);
                    }
                    @Override
                    protected void onFailure(String message) {
                        mView.hideLoading();
                        mView.loadFail(message);
                    }
                });
    }
}
