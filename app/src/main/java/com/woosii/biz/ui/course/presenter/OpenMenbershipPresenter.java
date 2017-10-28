package com.woosii.biz.ui.course.presenter;

import com.woosii.biz.api.Api;
import com.woosii.biz.base.bean.json.PayInfoBean;
import com.woosii.biz.base.bean.json.PayReqBean;
import com.woosii.biz.base.rx.RxSubscriber;
import com.woosii.biz.ui.course.contract.OpenMenbershipContract;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/24.
 */

public class OpenMenbershipPresenter extends OpenMenbershipContract.Presenter {
    @Override
    public void getPayOrderInfo(Map<String, String> map) {
        mView.showLoading();
        addSubscrebe(Api.getInstance().getPayOrderInfo( map),
                new RxSubscriber<PayInfoBean>(mContext,false) {
                    @Override
                    protected void onSuccess(PayInfoBean model) {

                        mView.hideLoading();
                        mView.getPayOrderInfoSuccess(model);
                    }
                    @Override
                    protected void onFailure(String message) {
                        mView.hideLoading();
                        mView.loadFail(message);
                    }
                });
    }

    @Override
    public void getWeChatOrderInfo(Map<String, String> map) {
        mView.showLoading();
        addSubscrebe(Api.getInstance().weChatPay( map),
                new RxSubscriber<PayReqBean>(mContext,false) {
                    @Override
                    protected void onSuccess(PayReqBean model) {

                        mView.hideLoading();
                        mView.getWeChatOrderInfoSuccess(model);
                    }
                    @Override
                    protected void onFailure(String message) {
                        mView.hideLoading();
                        mView.loadFail(message);
                    }
                });
    }

}
