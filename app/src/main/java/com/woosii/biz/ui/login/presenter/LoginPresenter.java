package com.woosii.biz.ui.login.presenter;

import com.woosii.biz.api.Api;
import com.woosii.biz.base.bean.json.RegisterInfoBean;
import com.woosii.biz.base.rx.RxSubscriber;
import com.woosii.biz.ui.login.contract.LoginContract;

import java.util.Map;

/**
 * Created by Administrator on 2017/9/22.
 */

public class LoginPresenter extends LoginContract.Presenter {
    @Override
    public void getRegisterCode(Map<String, String> map) {
        mView.showLoading();
        addSubscrebe(Api.getInstance().getRegisterCode(map),
                new RxSubscriber<RegisterInfoBean>(mContext,false) {
                    @Override
                    protected void onSuccess(RegisterInfoBean registerInfoBean) {
                        mView.hideLoading();
                        if(registerInfoBean.getCode()==1){
                                mView.getRegisterCodeSuccess(registerInfoBean);
                        }else{
                            mView.loadFail(registerInfoBean.getMessage());


                        }
                    }
                    @Override
                    protected void onFailure(String message) {
                        mView.hideLoading();
                        mView.loadFail(message);
                    }
                });
    }

    @Override
    public void register(Map<String, String> map) {
        mView.showLoading();
        addSubscrebe(Api.getInstance().register(map),
                new RxSubscriber<RegisterInfoBean>(mContext,false) {
                    @Override
                    protected void onSuccess(RegisterInfoBean registerInfoBean) {
                        mView.hideLoading();
                        if(registerInfoBean.getCode()==3){
                            mView.registerSuccess(registerInfoBean);
                        }else{
                            mView.loadFail(registerInfoBean.getMessage());


                        }
                    }
                    @Override
                    protected void onFailure(String message) {
                        mView.hideLoading();
                        mView.loadFail(message);
                    }
                });
    }
}
