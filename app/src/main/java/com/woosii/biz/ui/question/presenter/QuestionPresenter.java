package com.woosii.biz.ui.question.presenter;

import com.woosii.biz.api.Api;
import com.woosii.biz.base.bean.json.BasePagingBean;
import com.woosii.biz.base.bean.json.QuestionListBean;
import com.woosii.biz.base.rx.RxSubscriber;
import com.woosii.biz.ui.question.contract.QuestionContract;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/28.
 */

public class QuestionPresenter extends QuestionContract.Presenter {
    @Override
    public void getQuestionList(Map<String, String> map) {
        addSubscrebe(Api.getInstance().getQuestionList(map), new RxSubscriber<BasePagingBean<QuestionListBean>>(mContext, false){
            @Override
            protected void onSuccess(BasePagingBean<QuestionListBean> model) {
                mView.hideLoading();
                mView.getQuestionListSuccess(model);
            }

            @Override
            protected void onFailure(String message) {
                mView.hideLoading();
                mView.loadFail(message);
            }
        });
    }

    @Override
    public void getfreshQuestionList(Map<String, String> map) {
        addSubscrebe(Api.getInstance().getQuestionList(map), new RxSubscriber<BasePagingBean<QuestionListBean>>(mContext, false){
            @Override
            protected void onSuccess(BasePagingBean<QuestionListBean> model) {
                mView.hideLoading();
                mView.getfreshQuestionListSuccess(model);
            }

            @Override
            protected void onFailure(String message) {
                mView.hideLoading();
                mView.loadFail(message);
            }
        });
    }

}
