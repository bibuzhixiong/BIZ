package com.woosii.biz.ui.me.presenter;

import com.woosii.biz.api.Api;
import com.woosii.biz.base.bean.json.BaseInfoBean;
import com.woosii.biz.base.rx.RxSubscriber;
import com.woosii.biz.ui.me.contract.QuestionAnswerDetailNorContract;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/11/1.
 */

public class QuestionAnswerDetailNorPresenter extends QuestionAnswerDetailNorContract.Presenter {
    @Override
    public void changeHead(RequestBody uid, RequestBody pid, MultipartBody.Part body) {
        mView.showLoading();
        addSubscrebe(Api.getInstance().upAudio(uid,pid,body),new RxSubscriber<BaseInfoBean>(mContext,true){
            @Override
            protected void onSuccess(BaseInfoBean info) {
                mView.hideLoading();
                mView.changeHeadSuccess(info);

            }
            @Override
            protected void onFailure(String message) {
                mView.hideLoading();
                mView.loadFail(message);
            }
        });
    }
}
