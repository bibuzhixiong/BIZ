package com.woosii.biz.ui.me.presenter;

import com.woosii.biz.api.Api;
import com.woosii.biz.base.bean.json.UserInfoBean;
import com.woosii.biz.base.rx.RxSubscriber;
import com.woosii.biz.ui.me.contract.MeContract;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/14.
 */

public class MePresenter extends MeContract.Presenter{
    @Override
    public void getUserInfo(final Map<String, String> map) {

        addSubscrebe(Api.getInstance().getUserInfo(map),
                new RxSubscriber<UserInfoBean>(mContext, false) {
                    @Override
                    protected void onSuccess(UserInfoBean model) {
                        mView.hideLoading();

                            mView.getUserInfoSuccess(model);



                    }
                    @Override
                    protected void onFailure(String message) {
                        mView.hideLoading();
                        mView.loadFail(message);
                    }
                });

    }
}














