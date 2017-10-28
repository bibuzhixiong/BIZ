package com.woosii.biz.ui.course.contract;

import com.woosii.biz.base.BasePresenter;
import com.woosii.biz.base.BaseView;
import com.woosii.biz.base.bean.json.BaseInfoBean;

import java.util.Map;

/**
 * Created by Administrator on 2017/9/28.
 */

public interface CourseDetailContract {
    interface View extends BaseView {


        void signUpSuccess(BaseInfoBean model);


        void loadFail(String msg);
        void showLoading();
        void hideLoading();

    }

    abstract class Presenter extends BasePresenter<CourseDetailContract.View> {

        public abstract void signUp(Map<String, String> map);



        @Override
        public void onStart() {

        }
    }
}
