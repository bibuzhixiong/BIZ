package com.woosii.biz.ui.login.presenter;

import com.woosii.biz.api.Api;
import com.woosii.biz.base.bean.LoginBean;
import com.woosii.biz.base.bean.json.BaseInfoBean;
import com.woosii.biz.base.bean.json.WechatBean;
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
                new RxSubscriber<BaseInfoBean>(mContext,false) {
                    @Override
                    protected void onSuccess(BaseInfoBean registerInfoBean) {
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
                new RxSubscriber<BaseInfoBean>(mContext,false) {
                    @Override
                    protected void onSuccess(BaseInfoBean registerInfoBean) {
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

    @Override
    public void loginByPassword(Map<String, String> map) {
        mView.showLoading();
        addSubscrebe(Api.getInstance().loginByPassword(map),
                new RxSubscriber<LoginBean>(mContext,false) {
                    @Override
                    protected void onSuccess(LoginBean registerInfoBean) {
                        mView.hideLoading();
                        if(registerInfoBean.getCode()==1){
                            mView.loginByPassword(registerInfoBean);
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
    public void loginByWX(Map<String, String> map) {
        mView.showLoading();
        addSubscrebe(Api.getInstance().loginByWX(map),
                new RxSubscriber<LoginBean>(mContext, false) {
                    @Override
                    protected void onSuccess(LoginBean loginBean) {
                        mView.hideLoading();

                            mView.loginByWeChat(loginBean);

                    }
                    @Override
                    protected void onFailure(String message) {
                        mView.hideLoading();
                        mView.loadFail(message);
                    }
                });
    }

    @Override
    public void loadWeChatData(String url) {
        mView.showLoading();
        addSubscrebe(Api.getInstance().getWeChatLoginData(url),
                new RxSubscriber<WechatBean>(mContext, false) {
                    @Override
                    protected void onSuccess(WechatBean wechatBean) {
                        mView.hideLoading();
                        if(wechatBean!=null){
                            mView.loadWeChatData(wechatBean);
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
