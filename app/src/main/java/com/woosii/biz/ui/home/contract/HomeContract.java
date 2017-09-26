package com.woosii.biz.ui.home.contract;

import com.woosii.biz.base.BasePresenter;
import com.woosii.biz.base.BaseView;
import com.woosii.biz.base.bean.json.BasePagingBean;
import com.woosii.biz.base.bean.json.NewsBean;

import java.util.Map;

/**
 * Created by Administrator on 2017/9/26.
 */

public interface HomeContract {
    interface View extends BaseView {


        void getNewsSuccess(BasePagingBean<NewsBean> model);

        void loadFail(String msg);
        void showLoading();
        void hideLoading();

    }

    abstract class Presenter extends BasePresenter<HomeContract.View> {
        public abstract void getNews(Map<String, String> map);


        @Override
        public void onStart() {

        }
    }
}
