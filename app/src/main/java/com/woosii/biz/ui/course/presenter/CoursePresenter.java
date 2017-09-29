package com.woosii.biz.ui.course.presenter;

import com.woosii.biz.api.Api;
import com.woosii.biz.base.bean.json.CourseListBean;
import com.woosii.biz.base.rx.RxSubscriber;
import com.woosii.biz.ui.course.contract.CourseContract;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/28.
 */

public class CoursePresenter  extends CourseContract.Presenter{

    @Override
    public void getCourses(Map<String, String> map) {
        addSubscrebe(Api.getInstance().getCourses( map),
                new RxSubscriber<List<CourseListBean>>(mContext,false) {
                    @Override
                    protected void onSuccess(List<CourseListBean> model) {

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
}
