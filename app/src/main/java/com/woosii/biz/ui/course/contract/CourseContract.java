package com.woosii.biz.ui.course.contract;

import com.woosii.biz.base.BasePresenter;
import com.woosii.biz.base.BaseView;
import com.woosii.biz.base.bean.json.BasePagingBean;
import com.woosii.biz.base.bean.json.CourseListBean;

import java.util.Map;

/**
 * Created by Administrator on 2017/9/28.
 */

public interface CourseContract {
    interface View extends BaseView {


        void getCoursesSuccess(BasePagingBean<CourseListBean> model);
        void getRefreshCoursesSuccess(BasePagingBean<CourseListBean> model);


        void loadFail(String msg);
        void showLoading();
        void hideLoading();

    }

    abstract class Presenter extends BasePresenter<CourseContract.View> {
        public abstract void getCourses(Map<String, String> map);
        public abstract void getRefreshCourses(Map<String, String> map);



        @Override
        public void onStart() {

        }
    }
}
