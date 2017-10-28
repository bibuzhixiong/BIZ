package com.woosii.biz.ui.login.presenter;

import com.woosii.biz.api.Api;
import com.woosii.biz.base.bean.LoginBean;
import com.woosii.biz.base.bean.json.BaseInfoBean;
import com.woosii.biz.base.bean.json.WechatInfoBean;
import com.woosii.biz.base.rx.RxSubscriber;
import com.woosii.biz.ui.login.contract.BindingPhoneContract;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/13.
 */

public class BindingPhonePresenter extends BindingPhoneContract.Presenter {

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
    public void wechatBinding(Map<String, String> map) {
        mView.showLoading();
        addSubscrebe(Api.getInstance().wecahtBinding(map),
                new RxSubscriber<LoginBean>(mContext, false) {
                    @Override
                    protected void onSuccess(LoginBean loginBean) {
                        mView.hideLoading();

                        mView.wechatBinding(loginBean);

                    }
                    @Override
                    protected void onFailure(String message) {
                        mView.hideLoading();
                        mView.loadFail(message);
                    }
                });
    }

    @Override
    public void getWeChatInfo(String url) {
        mView.showLoading();
        addSubscrebe(Api.getInstance().getWeChatInfo(url),
                new RxSubscriber<WechatInfoBean>(mContext, false) {
                    @Override
                    protected void onSuccess(WechatInfoBean wechatInfoBean) {
                        mView.hideLoading();

                        mView.getWeChatInfoSuccess(wechatInfoBean);

                    }
                    @Override
                    protected void onFailure(String message) {
                        mView.hideLoading();
                        mView.loadFail(message);
                    }
                });
    }
}
