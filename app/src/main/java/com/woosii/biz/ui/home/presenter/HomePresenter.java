package com.woosii.biz.ui.home.presenter;

import com.woosii.biz.api.Api;
import com.woosii.biz.base.bean.json.BasePagingBean;
import com.woosii.biz.base.bean.json.NewsBean;
import com.woosii.biz.base.rx.RxSubscriber;
import com.woosii.biz.ui.home.contract.HomeContract;

import java.util.Map;

/**
 * Created by Administrator on 2017/9/26.
 */

public class HomePresenter extends HomeContract.Presenter {
    @Override
    public void getNews(Map<String,String> map) {
//        mView.showLoading();
        addSubscrebe(Api.getInstance().getNews( map),
                new RxSubscriber<BasePagingBean<NewsBean>>(mContext,false) {
                    @Override
                    protected void onSuccess(BasePagingBean<NewsBean> model) {

                        mView.hideLoading();
                        mView.getNewsSuccess(model);
                    }
                    @Override
                    protected void onFailure(String message) {
                        mView.hideLoading();
                        mView.loadFail(message);
                    }
                });
    }

    @Override
    public void refreshNews(Map<String, String> map) {
//        mView.showLoading();
        addSubscrebe(Api.getInstance().getNews( map),
                new RxSubscriber<BasePagingBean<NewsBean>>(mContext,false) {
                    @Override
                    protected void onSuccess(BasePagingBean<NewsBean> model) {

                        mView.hideLoading();
                        mView.refreshNewsSuccess(model);
                    }
                    @Override
                    protected void onFailure(String message) {
                        mView.hideLoading();
                        mView.loadFail(message);
                    }
                });
    }
}
