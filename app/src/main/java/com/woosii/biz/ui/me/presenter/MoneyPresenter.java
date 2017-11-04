package com.woosii.biz.ui.me.presenter;

import com.woosii.biz.api.Api;
import com.woosii.biz.base.bean.json.MoneyListBean;
import com.woosii.biz.base.rx.RxSubscriber;
import com.woosii.biz.ui.me.contract.MoneyContract;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/1.
 */

public class MoneyPresenter extends MoneyContract.Presenter {
    @Override
    public void getMoneyRecord(Map<String, String> map) {
        addSubscrebe(Api.getInstance().getWalletRecord(map),
                new RxSubscriber<MoneyListBean>(mContext, false) {
                    @Override
                    protected void onSuccess(MoneyListBean model) {
                        mView.hideLoading();
                        mView.getMoneyRecordSuccess(model);


                    }
                    @Override
                    protected void onFailure(String message) {
                        mView.hideLoading();
                        mView.loadFail(message);
                    }
                });
    }
}
