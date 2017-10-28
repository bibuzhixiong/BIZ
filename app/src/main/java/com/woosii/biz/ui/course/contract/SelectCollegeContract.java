package com.woosii.biz.ui.course.contract;

import com.woosii.biz.base.BasePresenter;
import com.woosii.biz.base.BaseView;
import com.woosii.biz.base.bean.json.CollegeBean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/18.
 */

public interface SelectCollegeContract {
    interface View extends BaseView {


        void getCollegeSuccess(List<CollegeBean> model);



        void loadFail(String msg);
        void showLoading();
        void hideLoading();

    }

    abstract class Presenter extends BasePresenter<SelectCollegeContract.View> {
        public abstract void getCollege();



        @Override
        public void onStart() {

        }
    }
}
