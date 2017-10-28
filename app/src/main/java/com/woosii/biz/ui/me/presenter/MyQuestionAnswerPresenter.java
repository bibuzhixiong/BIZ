package com.woosii.biz.ui.me.presenter;

import com.woosii.biz.api.Api;
import com.woosii.biz.base.bean.json.BasePagingBean;
import com.woosii.biz.base.bean.json.MyQuestionAnswerBean;
import com.woosii.biz.base.rx.RxSubscriber;
import com.woosii.biz.ui.me.contract.MyQuestionAnswerContract;
import com.woosii.biz.utils.SharedPreferencesUtil;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/27.
 */

public class MyQuestionAnswerPresenter extends MyQuestionAnswerContract.Presenter {
    @Override
    public void getMyQuestionAnswer(Map<String, String> map) {
//        mView.showLoading();

        if((SharedPreferencesUtil.getValue(mContext,SharedPreferencesUtil.USER_TYPE,"")+"").equals("1")){
            addSubscrebe(Api.getInstance().getMyQuestionAnswerByTeacher(map), new RxSubscriber<BasePagingBean<MyQuestionAnswerBean>>(mContext, false){
                @Override
                protected void onSuccess(BasePagingBean<MyQuestionAnswerBean> model) {
                    mView.hideLoading();
                    mView.getMyQuestionAnswerSuccess(model);
                }

                @Override
                protected void onFailure(String message) {
                    mView.hideLoading();
                    mView.loadFail(message);
                }
            });
        }else{
            addSubscrebe(Api.getInstance().getMyQuestionAnswer(map), new RxSubscriber<BasePagingBean<MyQuestionAnswerBean>>(mContext, false){
                @Override
                protected void onSuccess(BasePagingBean<MyQuestionAnswerBean> model) {
                    mView.hideLoading();
                    mView.getMyQuestionAnswerSuccess(model);
                }

                @Override
                protected void onFailure(String message) {
                    mView.hideLoading();
                    mView.loadFail(message);
                }
            });
        }

    }

    @Override
    public void getfreshMyQuestionAnswer(Map<String, String> map) {
        if((SharedPreferencesUtil.getValue(mContext,SharedPreferencesUtil.USER_TYPE,"")+"").equals("1")){
            mView.showLoading();
            addSubscrebe(Api.getInstance().getMyQuestionAnswerByTeacher(map), new RxSubscriber<BasePagingBean<MyQuestionAnswerBean>>(mContext, false){
                @Override
                protected void onSuccess(BasePagingBean<MyQuestionAnswerBean> model) {
                    mView.hideLoading();
                    mView.getfreshMyQuestionAnswerSuccess(model);
                }

                @Override
                protected void onFailure(String message) {
                    mView.hideLoading();
                    mView.loadFail(message);
                }
            });
        }else{
            mView.showLoading();
            addSubscrebe(Api.getInstance().getMyQuestionAnswer(map), new RxSubscriber<BasePagingBean<MyQuestionAnswerBean>>(mContext, false){
                @Override
                protected void onSuccess(BasePagingBean<MyQuestionAnswerBean> model) {
                    mView.hideLoading();
                    mView.getfreshMyQuestionAnswerSuccess(model);
                }

                @Override
                protected void onFailure(String message) {
                    mView.hideLoading();
                    mView.loadFail(message);
                }
            });
        }

    }
}
