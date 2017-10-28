package com.woosii.biz.ui.course.presenter;

import com.woosii.biz.api.Api;
import com.woosii.biz.base.bean.json.BaseInfoBean;
import com.woosii.biz.base.rx.RxSubscriber;
import com.woosii.biz.ui.course.contract.CourseDetailContract;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/26.
 */

public class CourseDetailPresenter extends CourseDetailContract.Presenter {
    @Override
    public void signUp(Map<String, String> map) {
        mView.showLoading();
        addSubscrebe(Api.getInstance().signUp( map),
                new RxSubscriber<BaseInfoBean>(mContext,false) {
                    @Override
                    protected void onSuccess(BaseInfoBean model) {

                        mView.hideLoading();
                        mView.signUpSuccess(model);
                    }
                    @Override
                    protected void onFailure(String message) {
                        mView.hideLoading();
                        mView.loadFail(message);
                    }
                });
    }
}
