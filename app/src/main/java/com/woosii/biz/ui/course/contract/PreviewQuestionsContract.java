package com.woosii.biz.ui.course.contract;

import com.woosii.biz.base.BasePresenter;
import com.woosii.biz.base.BaseView;
import com.woosii.biz.base.bean.json.PreViewQuestonsListBean;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/26.
 */

public interface PreviewQuestionsContract {
    interface View extends BaseView {


        void getPreviewQuestions(PreViewQuestonsListBean model);


        void loadFail(String msg);
        void showLoading();
        void hideLoading();

    }

    abstract class Presenter extends BasePresenter<PreviewQuestionsContract.View> {
        public abstract void getPreviewQuestions(Map<String, String> map);



        @Override
        public void onStart() {

        }
    }
}
