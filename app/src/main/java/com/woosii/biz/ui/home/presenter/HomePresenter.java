package com.woosii.biz.ui.home.presenter;

import com.woosii.biz.api.Api;
import com.woosii.biz.base.bean.json.BaseInfoBean;
import com.woosii.biz.base.bean.json.BasePagingBean;
import com.woosii.biz.base.bean.json.NewsBean;
import com.woosii.biz.base.bean.json.PointBean;
import com.woosii.biz.base.bean.json.VersionBean;
import com.woosii.biz.base.rx.RxSubscriber;
import com.woosii.biz.ui.home.contract.HomeContract;

import java.util.List;
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
    public void getNewsBanner(Map<String, String> map) {
        addSubscrebe(Api.getInstance().getNewsBanner( map),
                new RxSubscriber<List<NewsBean>>(mContext,false) {
                    @Override
                    protected void onSuccess(List<NewsBean> model) {

                        mView.hideLoading();
                        mView.getNewsBannerSuccess(model);
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

    @Override
    public void scan(Map<String, String> map) {
        addSubscrebe(Api.getInstance().scan( map),
                new RxSubscriber<BaseInfoBean>(mContext,false) {
                    @Override
                    protected void onSuccess(BaseInfoBean model) {

                        mView.hideLoading();
                        mView.scanSuccess(model);
                    }
                    @Override
                    protected void onFailure(String message) {
                        mView.hideLoading();
                        mView.loadFail(message);
                    }
                });
    }

    @Override
    public void getPoint(Map<String, String> map) {
        addSubscrebe(Api.getInstance().getPoint( map),
                new RxSubscriber<PointBean>(mContext,false) {
                    @Override
                    protected void onSuccess(PointBean model) {

                        mView.hideLoading();
                        mView.getPointSuccess(model);
                    }
                    @Override
                    protected void onFailure(String message) {
                        mView.hideLoading();
                        mView.loadFail(message);
                    }
                });
    }

    @Override
    public void getVersion() {
        addSubscrebe(Api.getInstance().getVersion(),
                new RxSubscriber<VersionBean>(mContext,false) {
                    @Override
                    protected void onSuccess(VersionBean model) {
                        mView.getVersionSuccess(model);
                    }
                    @Override
                    protected void onFailure(String message) {

                    }
                });
    }
}
