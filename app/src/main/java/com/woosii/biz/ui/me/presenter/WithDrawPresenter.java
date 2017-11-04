package com.woosii.biz.ui.me.presenter;

import com.woosii.biz.api.Api;
import com.woosii.biz.base.bean.json.BaseInfoBean;
import com.woosii.biz.base.bean.json.WechatBean;
import com.woosii.biz.base.rx.RxSubscriber;
import com.woosii.biz.ui.me.contract.WithDrawContract;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/3.
 */

public class WithDrawPresenter extends WithDrawContract.Presenter {
    @Override
    public void loadWeChatData(String url) {
        mView.showLoading();
        addSubscrebe(Api.getInstance().getWeChatLoginData(url),
                new RxSubscriber<WechatBean>(mContext, false) {
                    @Override
                    protected void onSuccess(WechatBean wechatBean) {
//                        Log.e("TTT",wechatBean.toString());
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

    @Override
    public void withDraw(Map<String, String> map) {
        mView.showLoading();
        addSubscrebe(Api.getInstance().withDraw(map),
                new RxSubscriber<BaseInfoBean>(mContext, false) {
                    @Override
                    protected void onSuccess(BaseInfoBean wechatBean) {
                        mView.hideLoading();
                        if(wechatBean!=null){
                            mView.withDrawSuccess(wechatBean);
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
