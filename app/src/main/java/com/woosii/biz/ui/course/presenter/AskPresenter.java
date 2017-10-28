package com.woosii.biz.ui.course.presenter;

import com.woosii.biz.api.Api;
import com.woosii.biz.base.bean.json.AskBean;
import com.woosii.biz.base.bean.json.BaseInfoBean;
import com.woosii.biz.base.bean.json.PayInfoBean;
import com.woosii.biz.base.bean.json.PayReqBean;
import com.woosii.biz.base.rx.RxSubscriber;
import com.woosii.biz.ui.course.contract.AskContract;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/10/13.
 */

public class AskPresenter extends AskContract.Presenter{

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

    @Override
    public void askByUser(Map<String, String> map) {
        mView.showLoading();
        addSubscrebe(Api.getInstance().askByUser(map),new RxSubscriber<AskBean>(mContext,true){
            @Override
            protected void onSuccess(AskBean model) {
                mView.hideLoading();
                mView.askByUserSuccess(model);

            }
            @Override
            protected void onFailure(String message) {
                mView.hideLoading();
                mView.loadFail(message);
            }
        });
    }

    @Override
    public void changeHeadSuccess(RequestBody uid,RequestBody pid,MultipartBody.Part body) {
        addSubscrebe(Api.getInstance().upAudio(uid,pid,body),new RxSubscriber<BaseInfoBean>(mContext,true){
            @Override
            protected void onSuccess(BaseInfoBean info) {

            }
            @Override
            protected void onFailure(String message) {
                mView.loadFail(message);
            }
        });
    }
}
