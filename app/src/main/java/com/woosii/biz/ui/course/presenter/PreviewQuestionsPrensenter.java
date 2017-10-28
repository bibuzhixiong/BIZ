package com.woosii.biz.ui.course.presenter;

import com.woosii.biz.api.Api;
import com.woosii.biz.base.bean.json.PreViewQuestonsListBean;
import com.woosii.biz.base.rx.RxSubscriber;
import com.woosii.biz.ui.course.contract.PreviewQuestionsContract;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/26.
 */

public class PreviewQuestionsPrensenter extends PreviewQuestionsContract.Presenter{

    @Override
    public void getPreviewQuestions(Map<String, String> map) {
        mView.showLoading();
        addSubscrebe(Api.getInstance().getPreviewQuestions(map ),
                new RxSubscriber<PreViewQuestonsListBean>(mContext,false) {
                    @Override
                    protected void onSuccess(PreViewQuestonsListBean model) {

                        mView.hideLoading();
                        mView.getPreviewQuestions(model);
                    }
                    @Override
                    protected void onFailure(String message) {
                        mView.hideLoading();
                        mView.loadFail(message);
                    }
                });
    }
}
