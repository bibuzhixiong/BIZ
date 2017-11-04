package com.woosii.biz.ui.home.contract;

import com.woosii.biz.base.BasePresenter;
import com.woosii.biz.base.BaseView;
import com.woosii.biz.base.bean.json.BaseInfoBean;
import com.woosii.biz.base.bean.json.BasePagingBean;
import com.woosii.biz.base.bean.json.NewsBean;
import com.woosii.biz.base.bean.json.PointBean;
import com.woosii.biz.base.bean.json.VersionBean;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/26.
 */

public interface HomeContract {
    interface View extends BaseView {


        void getNewsSuccess(BasePagingBean<NewsBean> model);
        void getNewsBannerSuccess(List<NewsBean> model);
        void refreshNewsSuccess(BasePagingBean<NewsBean> model);
        void scanSuccess(BaseInfoBean model);

        void getVersionSuccess(VersionBean model);

        void getPointSuccess(PointBean model);

//        void scanFail(String mag);
        void loadFail(String msg);
        void showLoading();
        void hideLoading();

    }

    abstract class Presenter extends BasePresenter<HomeContract.View> {
        public abstract void getNews(Map<String, String> map);
        public abstract void getNewsBanner(Map<String, String> map);
        public abstract void refreshNews(Map<String, String> map);

        public abstract void scan(Map<String, String> map);
        public abstract void getPoint(Map<String, String> map);
        public abstract void getVersion();


        @Override
        public void onStart() {

        }
    }
}
