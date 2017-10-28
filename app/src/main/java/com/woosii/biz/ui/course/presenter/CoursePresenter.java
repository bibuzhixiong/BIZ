package com.woosii.biz.ui.course.presenter;

import com.woosii.biz.api.Api;
import com.woosii.biz.base.bean.json.BasePagingBean;
import com.woosii.biz.base.bean.json.CourseListBean;
import com.woosii.biz.base.rx.RxSubscriber;
import com.woosii.biz.ui.course.contract.CourseContract;

import java.util.Map;

/**
 * Created by Administrator on 2017/9/28.
 */

public class CoursePresenter  extends CourseContract.Presenter{

    @Override
    public void getCourses(Map<String, String> map) {
        addSubscrebe(Api.getInstance().getCourses( map),
                new RxSubscriber<BasePagingBean<CourseListBean>>(mContext,false) {
                    @Override
                    protected void onSuccess(BasePagingBean<CourseListBean> model) {

                        mView.hideLoading();
                        mView.getCoursesSuccess(model);
                    }
                    @Override
                    protected void onFailure(String message) {
                        mView.hideLoading();
                        mView.loadFail(message);
                    }
                });
    }

    @Override
    public void getRefreshCourses(Map<String, String> map) {
        addSubscrebe(Api.getInstance().getCourses( map),
                new RxSubscriber<BasePagingBean<CourseListBean>>(mContext,false) {
                    @Override
                    protected void onSuccess(BasePagingBean<CourseListBean> model) {

                        mView.hideLoading();
                        mView.getRefreshCoursesSuccess(model);
                    }
                    @Override
                    protected void onFailure(String message) {
                        mView.hideLoading();
                        mView.loadFail(message);
                    }
                });
    }
}
