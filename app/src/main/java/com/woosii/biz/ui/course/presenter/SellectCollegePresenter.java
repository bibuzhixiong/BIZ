package com.woosii.biz.ui.course.presenter;

import com.woosii.biz.api.Api;
import com.woosii.biz.base.bean.json.CollegeBean;
import com.woosii.biz.base.rx.RxSubscriber;
import com.woosii.biz.ui.course.contract.SelectCollegeContract;

import java.util.List;

/**
 * Created by Administrator on 2017/10/18.
 */

public class SellectCollegePresenter extends SelectCollegeContract.Presenter{

    @Override
    public void getCollege() {
        mView.showLoading();
        addSubscrebe(Api.getInstance().getCollege( ),
                new RxSubscriber<List<CollegeBean>>(mContext,false) {
                    @Override
                    protected void onSuccess(List<CollegeBean> model) {

                        mView.hideLoading();
                        mView.getCollegeSuccess(model);
                    }
                    @Override
                    protected void onFailure(String message) {
                        mView.hideLoading();
                        mView.loadFail(message);
                    }
                });
    }
}
