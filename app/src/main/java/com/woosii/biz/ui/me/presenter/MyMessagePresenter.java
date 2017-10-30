package com.woosii.biz.ui.me.presenter;

import com.woosii.biz.api.Api;
import com.woosii.biz.base.bean.json.MyMessageBean;
import com.woosii.biz.base.rx.RxSubscriber;
import com.woosii.biz.ui.me.contract.MyMessageContract;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/30.
 */

public class MyMessagePresenter extends MyMessageContract.Presenter {
    @Override
    public void getMyMessage(Map<String, String> map) {
        mView.showLoading();
        addSubscrebe(Api.getInstance().getMyMessage(map),new RxSubscriber<List<MyMessageBean>>(mContext,true){
            @Override
            protected void onSuccess(List<MyMessageBean> info) {

                mView.hideLoading();
                mView.getMyMessageSuccess(info);



            }
            @Override
            protected void onFailure(String message) {
                mView.loadFail(message);
            }
        });
    }
}
