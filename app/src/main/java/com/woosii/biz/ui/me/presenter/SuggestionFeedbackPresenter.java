package com.woosii.biz.ui.me.presenter;

import com.woosii.biz.api.Api;
import com.woosii.biz.base.bean.json.BaseInfoBean;
import com.woosii.biz.base.rx.RxSubscriber;
import com.woosii.biz.ui.me.contract.SuggestionFeedbackContract;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/30.
 */

public class SuggestionFeedbackPresenter extends SuggestionFeedbackContract.Presenter{
    @Override
    public void suggestionFeedback(Map<String, String> map) {
        mView.showLoading();
        addSubscrebe(Api.getInstance().suggestionFeedback(map),new RxSubscriber<BaseInfoBean>(mContext,true){
            @Override
            protected void onSuccess(BaseInfoBean info) {

                    mView.hideLoading();
                    mView.suggestionFeedbackSuccess(info);



            }
            @Override
            protected void onFailure(String message) {
                mView.loadFail(message);
            }
        });
    }
}
