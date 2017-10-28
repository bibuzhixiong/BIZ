package com.woosii.biz.ui.login.presenter;

import com.woosii.biz.api.Api;
import com.woosii.biz.base.bean.LoginBean;
import com.woosii.biz.base.bean.json.BaseInfoBean;
import com.woosii.biz.base.rx.RxSubscriber;
import com.woosii.biz.ui.login.contract.ForgetPasswordContract;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/21.
 */

public class ForgetPasswordPresenter extends ForgetPasswordContract.Presenter{
    @Override
    public void getCode(Map<String, String> map) {
        addSubscrebe(Api.getInstance().getCode(map),
                new RxSubscriber<BaseInfoBean>(mContext, false) {
                    @Override
                    protected void onSuccess(BaseInfoBean model) {
                        mView.hideLoading();

                        mView.getCodeSuccess(model);

                    }
                    @Override
                    protected void onFailure(String message) {
                        mView.hideLoading();
                        mView.loadFail(message);
                    }
                });
    }

    @Override
    public void forgetPassword(Map<String, String> map) {
        mView.showLoading();
        addSubscrebe(Api.getInstance().forgetPassword(map),
                new RxSubscriber<LoginBean>(mContext, false) {
                    @Override
                    protected void onSuccess(LoginBean model) {
                        mView.hideLoading();

                        mView.forgetPassworddSuccess(model);

                    }
                    @Override
                    protected void onFailure(String message) {
                        mView.hideLoading();
                        mView.loadFail(message);
                    }
                });
    }
}
