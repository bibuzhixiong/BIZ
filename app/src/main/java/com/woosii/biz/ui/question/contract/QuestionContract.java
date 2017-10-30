package com.woosii.biz.ui.question.contract;

import com.woosii.biz.base.BasePresenter;
import com.woosii.biz.base.BaseView;
import com.woosii.biz.base.bean.json.BasePagingBean;
import com.woosii.biz.base.bean.json.QuestionListBean;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/28.
 */

public interface QuestionContract {
    interface View extends BaseView {


        void getQuestionListSuccess(BasePagingBean<QuestionListBean> model);


        void getfreshQuestionListSuccess(BasePagingBean<QuestionListBean> model);

        void loadFail(String msg);
        void showLoading();
        void hideLoading();

    }

    abstract class Presenter extends BasePresenter<QuestionContract.View> {
        public abstract void getQuestionList(Map<String, String> map);

        public abstract void getfreshQuestionList(Map<String, String> map);

        @Override
        public void onStart() {

        }
    }
}
