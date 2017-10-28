package com.woosii.biz.ui.me.contract;

import com.woosii.biz.base.BasePresenter;
import com.woosii.biz.base.BaseView;
import com.woosii.biz.base.bean.json.BasePagingBean;
import com.woosii.biz.base.bean.json.MyQuestionAnswerBean;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/27.
 */

public interface MyQuestionAnswerContract {
    interface View extends BaseView {


        void getMyQuestionAnswerSuccess(BasePagingBean<MyQuestionAnswerBean> model);
        void getfreshMyQuestionAnswerSuccess(BasePagingBean<MyQuestionAnswerBean> model);



        void loadFail(String msg);
        void showLoading();
        void hideLoading();

    }

    abstract class Presenter extends BasePresenter<MyQuestionAnswerContract.View> {
        public abstract void getMyQuestionAnswer(Map<String, String> map);
        public abstract void getfreshMyQuestionAnswer(Map<String, String> map);

        @Override
        public void onStart() {

        }
    }
}
