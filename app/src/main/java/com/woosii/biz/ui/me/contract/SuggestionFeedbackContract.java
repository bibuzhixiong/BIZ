package com.woosii.biz.ui.me.contract;

import com.woosii.biz.base.BasePresenter;
import com.woosii.biz.base.BaseView;
import com.woosii.biz.base.bean.json.BaseInfoBean;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/30.
 */

public interface SuggestionFeedbackContract {

    interface View extends BaseView {



        void  suggestionFeedbackSuccess(BaseInfoBean model);


        void loadFail(String msg);
        void showLoading();
        void hideLoading();

    }

    abstract class Presenter extends BasePresenter<SuggestionFeedbackContract.View> {

        public abstract void suggestionFeedback(Map<String, String> map);

        @Override
        public void onStart() {

        }
    }

}
