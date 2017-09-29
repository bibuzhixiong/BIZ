package com.woosii.biz.ui.course.contract;

import com.woosii.biz.base.BasePresenter;
import com.woosii.biz.base.BaseView;
import com.woosii.biz.base.bean.json.CourseListBean;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/28.
 */

public interface CourseContract {
    interface View extends BaseView {


        void getCoursesSuccess(List<CourseListBean> model);


        void loadFail(String msg);
        void showLoading();
        void hideLoading();

    }

    abstract class Presenter extends BasePresenter<CourseContract.View> {
        public abstract void getCourses(Map<String, String> map);



        @Override
        public void onStart() {

        }
    }
}
